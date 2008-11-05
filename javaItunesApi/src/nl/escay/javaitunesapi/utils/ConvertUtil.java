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

public class ConvertUtil {
	public static Boolean convertToBoolean(Object object) {
		Boolean result = null;
		if (object instanceof StringBuilder) {
			try {
				StringBuilder sb = (StringBuilder) object;
				String value = sb.toString();
				// TODO: only expecting 1 value... should another approach be used?
				value = value.replace("\n", "");
				result = new Boolean(value);
			} catch (NumberFormatException nfe) {
				// Ignore, just return null
			}
		}
		return result;
	}

	public static Integer convertToInteger(Object object) {
    	Integer result = null;
        if (object instanceof StringBuilder) {
        	try {
        		StringBuilder sb = (StringBuilder) object;
        		String value = sb.toString();
        		// TODO: only expecting 1 value... should another approach be used?
        		value = value.replace("\n", "");
        		result = new Integer(value);
        	} catch (NumberFormatException nfe) {
        		// Ignore, just return null
        	}
        }
        return result;
    }

    public static String convertToString(Object object) {
    	String result = null;
    	if (object instanceof StringBuilder) {
			StringBuilder sb = (StringBuilder) object;
			String value = sb.toString();
			// TODO: only expecting 1 value... should another approach be used?
			result = value.replace("\n", "");
    	}
    	return result;
    }
}
