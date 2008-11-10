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

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import nl.escay.javaitunesapi.utils.CommandUtil;
import nl.escay.javaitunesapi.utils.ConvertUtil;

/**
 * A list of songs/streams
 * 
	 # contains tracks; contained by sources.

	 #duration (integer, r/o) : the total length of all songs (in seconds)
	 #name (text) : the name of the playlist
	 #parent (playlist, r/o) : folder which contains this playlist (if any)
	 #shuffle (boolean) : play the songs in this playlist in random order?
	 #size (double integer, r/o) : the total size of all songs (in bytes)
	 #song repeat (off/one/all) : playback repeat mode
	 #special kind (none/Audiobooks/folder/Movies/Music/Party Shuffle/Podcasts/Purchased Music/TV Shows/Videos, r/o) : special playlist kind
	 #time (text, r/o) : the length of all songs in MM:SS format
	 #visible (boolean, r/o) : is this playlist visible in the Source list?
 */
public class Playlist extends Item {
	
	public Playlist() {
		super();
	}

	public Playlist(int index) {
		super(index);
	}

	private List<Track> tracks;

	public List<Track> getTracks() {
		tracks = new ArrayList<Track>();
		int numberOfTracks = getCount();
		if (numberOfTracks > 0) {
			for(int i = 1; i <= numberOfTracks;i++) {
				tracks.add(new Track(i, this));
			}
		}
		return tracks;
	}

	/**
	 * Returns the number of items in the playlist
	 * @return int the size
	 */
	public int getCount() {
		return ConvertUtil.convertToInt(CommandUtil.executeCommand("count of tracks of playlist " + getIndex()));
	}
	
	/**
	 * Returns the total length of all songs (in seconds)
	 * @return int the total length of all songs (in seconds)
	 */
	public int getDuration() {
		// TODO
		return -1;
	}
	
	/**
	 * Sets the name of the playlist
	 * @param name The name
	 */
	public void setName(String name) {
		// TODO
	}
	
	/**
	 * Returns the name of the playlist
	 */
	public String getName() {
		String command = "tell application \"iTunes\"\nget name of playlist " + getIndex() + "\nend tell";
		Object result = null;
		try {
			result = CommandUtil.getScriptEngine().eval(command);
		} catch (ScriptException ex) {
			ex.printStackTrace();
			return null;
		}
		return ConvertUtil.convertToString(result);
	}
	
	/**
	 * Returns folder which contains this playlist (if any)
	 * @return
	 */
	public Playlist getParent() {
		// TODO
		return null;
	}
	
	/**
	 * Enable or disable shuffle, if enabled the songs in this 
	 * playlist are played in random order.
	 */
	public void setShuffle(boolean value) {
	    // TODO
	}
	
	/**
	 * Returns true if this playlist is played in random order
	 * @return true if this playlist is played in random order
	 */
	public boolean getShuffle() {
		// TODO
		return false;
	}

	/** 
	 * Returns the total size of all songs, in bytes
	 * @return the total size of all songs in bytes
	 */
	public int getSize() {
		// TODO
		return -1;
	}
	
	/**
	 * Set the playback repeat mode. 
	 * @param mode off/one/all
	 */
	public void setSongRepeat(String mode) {
		// TODO
	}
	
	/**
	 * Returns the playback repeat mode
	 * @return String off/one/all
	 */
	public String getSongRepeat() {
		// TODO
		return null;
	}
	
	/**
	 * Returns the special playlist kind
	 * @return String none/Audiobooks/folder/Movies/Music/Party Shuffle/Podcasts/Purchased Music/TV Shows/Videos
	 */
	public String getSpecialKind() {
	    // TODO
		return null;
	}
	
	/**
	 * Returns the length of all songs in MM:SS format
	 * @return the length of all songs in MM:SS format
	 */
	public String getTime() {
		// TODO
		return null;
	}
	
	/**
	 * Returns true if this playlist is visible in the Source list
	 * @return true if this playlist is visible in the Source list
	 */
	public boolean isVisible() {
		// TODO
		return false;
	}
	
	public String toString() {
		return getName();
	}
}
