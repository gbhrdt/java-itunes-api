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

package nl.escay.javaitunesapi.utils;

import java.util.List;

import nl.escay.javaitunesapi.parser.AppleScriptEnumeration;


public class ConvertUtil {
	public static Boolean asBoolean(Object parsedObject) {
		Boolean result = null;
        if (parsedObject instanceof Boolean) {
        	result = (Boolean) parsedObject;
        }
		return result;
	}

	public static Integer asInteger(Object parsedObject) {
    	Integer result = null;
        if (parsedObject instanceof Integer) {
        	result = (Integer) parsedObject;
        }
        return result;
    }

    public static String asString(Object parsedObject) {
    	String result = null;
    	if (parsedObject instanceof String) {
    		result = (String) parsedObject;
    	} else if (parsedObject instanceof List) {
    		List<?> results = (List<?>) parsedObject;
    		if (results.size() == 1 && results.get(0) instanceof AppleScriptEnumeration) {
    		    result = ((AppleScriptEnumeration) results.get(0)).getId();
    		}
    	}
    	return result;
    }

	public static int convertToInt(Object executeCommand) {
		Integer value = asInteger(executeCommand);
		assert(value != null);
		return value.intValue();
	}
}
