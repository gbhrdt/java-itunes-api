/*
 * Copyright 2008,2009 Ronald Martijn Morrien
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

public class AppleScriptValueParserTests {

	@Test
	public void testBooleans() {
		// Only when the result is in a list we can get Boolean, and only if
		// all characters are lowercase. Expecting a String in all other situations.
		// "true" cannot become a Boolean because it could also be the name of a song.
		assertEquals(list(new Boolean(true)), new AppleScriptValueParser().parse("{true}")); 
		assertEquals(list(new Boolean(false)), new AppleScriptValueParser().parse("{false}"));

		assertEquals(list("true"), new AppleScriptValueParser().parse("{\"true\"}")); 
		assertEquals(list("false"), new AppleScriptValueParser().parse("{\"false\"}"));
		assertEquals(list("True"), new AppleScriptValueParser().parse("{\"True\"}")); 
		assertEquals(list("faLSe"), new AppleScriptValueParser().parse("{\"faLSe\"}"));		
		
		assertEquals("true", new AppleScriptValueParser().parse("true")); 
		assertEquals("false", new AppleScriptValueParser().parse("false"));
		assertEquals("True", new AppleScriptValueParser().parse("True")); 
		assertEquals("faLSe", new AppleScriptValueParser().parse("faLSe"));
	}
	
	@Test
	public void testStrings() {
		// Empty String
		assertEquals("", new AppleScriptValueParser().parse(""));
		
        // Quotes
		assertEquals("hi there", new AppleScriptValueParser().parse("\"hi there\"")); 
		assertEquals("a quote: \"", new AppleScriptValueParser().parse("\"a quote: \\\"\""));
		
		// Backslash
		assertEquals("a backslash: \\", new AppleScriptValueParser().parse("\"a backslash: \\\\\""));
		
		// Escape
		assertEquals("no escape: \\ ", new AppleScriptValueParser().parse("\"no escape: \\ \"")); 
		assertEquals("no escape: \\", new AppleScriptValueParser().parse("\"no escape: \\")); 
		
		// String in a list
        assertEquals(list(), new AppleScriptValueParser().parse("{}")); 
		assertEquals(list("hi there"), new AppleScriptValueParser().parse("{\"hi there\"}")); 
		assertEquals(list(12, "hi there", list()), new AppleScriptValueParser().parse("{12, \"hi there\", {}}"));
	}
	
	@Test
	public void testEnumerations() {
		assertEquals("none", new AppleScriptValueParser().parse("none"));
		assertEquals(list(new AppleScriptEnumeration("none")), new AppleScriptValueParser().parse("{none}"));

		// Enumeration value... like from get special kind of playlist 1
		// Versus:              like from get {special kind} of playlist 1 
		assertEquals("Party Shuffle", new AppleScriptValueParser().parse("Party Shuffle"));
		assertEquals(list("Partyshuffle"), new AppleScriptValueParser().parse("{\"Partyshuffle\"}"));
		assertEquals(list(new AppleScriptEnumeration("Party Shuffle")), new AppleScriptValueParser().parse("{Party Shuffle}")); 

		assertEquals(list(new AppleScriptEnumeration("True")), new AppleScriptValueParser().parse("{True}")); 
		assertEquals(list(new AppleScriptEnumeration("faLSe")), new AppleScriptValueParser().parse("{faLSe}"));		
	}

	@Test
	public void testNumbers() {
		// AppleScript integer
		assertEquals("1", new AppleScriptValueParser().parse("1"));
		assertEquals(list(new Integer(1)), new AppleScriptValueParser().parse("{1}"));
		assertEquals("-1", new AppleScriptValueParser().parse("-1"));
		assertEquals(list(new Integer(-1)), new AppleScriptValueParser().parse("{-1}"));
		assertEquals("1000", new AppleScriptValueParser().parse("1000"));
		assertEquals(list(new Integer(1000)), new AppleScriptValueParser().parse("{1000}"));

		// AppleScript real
		assertEquals("1.0", new AppleScriptValueParser().parse("1.0"));
		assertEquals(list(new Double(1.0)), new AppleScriptValueParser().parse("{1.0}"));
		assertEquals("123.45", new AppleScriptValueParser().parse("123.45"));
		assertEquals(list(new Double(123.45)), new AppleScriptValueParser().parse("{123.45}"));
		assertEquals("123.", new AppleScriptValueParser().parse("123."));
		assertEquals(list(new Double(123)), new AppleScriptValueParser().parse("{123.}"));
		assertEquals("-1.234", new AppleScriptValueParser().parse("-1.234"));
		assertEquals(list(new Double(-1.234)), new AppleScriptValueParser().parse("{-1.234}"));
	}
	
	@Test
	public void testDate() {
		// TODO: How to deal with other Locales.... ?! We depend on the Locale in use by iTunes...
		SimpleDateFormat dateFormat = new SimpleDateFormat("E, MMM dd, yyyy HH:mm:ss", Locale.ENGLISH);
		Date date = null;
		try {
			date = dateFormat.parse("Monday, April 1, 2002 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss", new Locale("nl", "NL"));
		Date dutchDate = null;
		try {
			dutchDate = dateFormat2.parse("woensdag, 21 mei 2008 16:28:42");
			//Wed May 21 16:28:42 CEST 2008
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		SimpleDateFormat dateFormat3 = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss"); // Warning: this test only runs correctly when default locale is new Locale("nl", "NL")
		try {
			dateFormat3.parse("woensdag, 21 mei 2008 16:28:42");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// DISABLED Wed May 21 16:28:42 CEST 2008
		//assertEquals(dutchDate, new AppleScriptValueParser().parse("date \"woensdag, 21 mei 2008 16:28:42\"")); 
		//assertEquals(list(dutchDate), new AppleScriptValueParser().parse("{date \"woensdag, 21 mei 2008 16:28:42\"}"));
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
