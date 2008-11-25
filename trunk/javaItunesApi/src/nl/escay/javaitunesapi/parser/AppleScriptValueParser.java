/*
 * Copyright 2008 Ronald Martijn Morrien
 * 
 * This file is part of java-itunes-api.
 *
 * java-itunes-api is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * java-itunes-api is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with java-itunes-api. If not, see <http://www.gnu.org/licenses/>.
 */

package nl.escay.javaitunesapi.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to parse AppleScript results
 * 
 * For an exact definition of list and record see
 * http://developer.apple.com/documentation/applescript/conceptual/applescriptlangguide/reference/ASLR_classes.html#//apple_ref/doc/uid/TP40000983-CH1g-BBCDBHIE
 * 
 * List Example:
 * {"hello", variable, 5.55, date "Monday, April 1, 2002", true, {3, "another", "list"}}
 * 
 * TODO: support / recognize record results, although iTunes (8.0) never returns 
 * records it is handy for other applications that can return records.
 * 
 * Record Example:
 * {name:"Stephen", height:74.5, weight:175}
 * 
 * TODO: support Double (real/number) results
 */
public class AppleScriptValueParser {
	
	private String input;
	private int pos;

	public Object parse(String input) {
		this.input = input;
		this.pos = 0;
		return parseValue();
	}

	private Object parseValue() {
		skipWhitespace();
		if (pos >= input.length()) {
			return "";
		}
		switch (peek()) {
		case '"' : return parseString(); 
		case '{' : return parseList(); 
		default: return parsePrimitive();
		}
	}
	
	private String parseString() {
		assert peek() == '"';
		StringBuilder result = new StringBuilder();
		while (++pos < input.length()) {
			switch (peek()) {
			case '"': 
				pos++; 
				return result.toString(); 
			case '\\': 
				switch (peekNext()) {
				case '"': result.append('\"'); pos++; break;
				case '\\': result.append('\\'); pos++; break;
				default: result.append(peek()); 
				}
				break;
			default:
				result.append(peek());	
			}
		}
		return result.toString();
	}

	@SuppressWarnings("unchecked")
	private List parseList() {
		assert peek() == '{';
		pos++;
		skipWhitespace();
		List result = new ArrayList();
		while (pos < input.length()) {
			switch (peek()) {
			case '}': 
				pos++; 
				return result; 
			case ',': 
				pos++;
				result.add(parseValue());
				skipWhitespace();
				break;
			default:
				skipWhitespace();
				result.add(parseValue());	
			}
		}
		return result;
		
	}

	private Object parsePrimitive() {
		int start = pos;
		while (pos < input.length() && Character.isDigit(peek())) {
			pos++;
		}		
		return Integer.parseInt(input.substring(start, pos));
	}


	private char peek() {
		return input.charAt(pos);
	}
	
	private char peekNext() {
		if (pos + 1 < input.length()) {
			return input.charAt(pos + 1);
		}
		return 0;
	}
	
	private void skipWhitespace() {
		while (pos < input.length() && Character.isWhitespace(peek())) pos++;
	}
}