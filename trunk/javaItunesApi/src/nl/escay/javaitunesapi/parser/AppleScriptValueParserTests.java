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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AppleScriptValueParserTests {

	@Test
	public void testSingleValues() {
		assertEquals("", new AppleScriptValueParser().parse("")); 
		assertEquals(new Integer(1), new AppleScriptValueParser().parse("1")); 
		assertEquals("hi there", new AppleScriptValueParser().parse("\"hi there\"")); 
		assertEquals("a quote: \"", new AppleScriptValueParser().parse("\"a quote: \\\"\"")); 
		assertEquals("a backslash: \\", new AppleScriptValueParser().parse("\"a backslash: \\\\\"")); 
		assertEquals("no escape: \\ ", new AppleScriptValueParser().parse("\"no escape: \\ \"")); 
		assertEquals("no escape: \\", new AppleScriptValueParser().parse("\"no escape: \\")); 
        assertEquals(list(), new AppleScriptValueParser().parse("{}")); 
		assertEquals(list("hi there"), new AppleScriptValueParser().parse("{\"hi there\"}")); 
		assertEquals(list(12, "hi there", list()), new AppleScriptValueParser().parse("{12, \"hi there\", {}}")); 
		//TODO: assertEquals(TODO), new ScriptValueParser().parse("{\"hello\", variable, 5.55, date \"Monday, April 1, 2002\", true, {3, \"another\", \"list\"}}")); 
	}
	
	@Test
	public void testNumbers() {
		/*
		TODO test:
		Source: http://developer.apple.com/documentation/applescript/conceptual/applescriptlangguide/reference/ASLR_classes.html#//apple_ref/doc/uid/TP40000983-CH1g-BBCDBHIE
		
		integer:
		1
		-1
		1000
		
		number:
		1
		2
		-1
		1000
		10.2579432
		1.0
		1.
	
		real:
		10.2579432
		1.0
		1.
		
		*/
	}
	
	@SuppressWarnings("unchecked")
	public List list(Object... values) {
		ArrayList result = new ArrayList();
		for (Object value: values) {
			result.add(value);
		}
		return result;
	}
}
