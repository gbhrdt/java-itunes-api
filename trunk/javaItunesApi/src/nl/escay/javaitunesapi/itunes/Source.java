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

package nl.escay.javaitunesapi.itunes;

/**
 * A music source (music library, CD, device, etc.)
 */
public class Source extends Item {
	/* From the iTunes script library 8.0.1:
		capacity (double integer, r/o) : the total size of the source if it has a fixed size
		free space (double integer, r/o) : the free space on the source if it has a fixed size
		kind (library/iPod/audio CD/MP3 CD/device/radio tuner/shared library/unknown, r/o)
     */
}
