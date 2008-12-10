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
import java.util.logging.Logger;

import nl.escay.javaitunesapi.itunes.Track.TrackProperty;
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
	
	private static Logger logger = Logger.getLogger(Playlist.class.toString());
	
	public Playlist() {
		super();
	}

	public Playlist(int index) {
		super(index);
	}

	private List<Track> tracks;

	public List<Track> getTracks(TrackProperty... trackProperties) {
		tracks = new ArrayList<Track>();
		int numberOfTracks = getCount();
		if (numberOfTracks > 0) {
			for(int i = 1; i <= numberOfTracks;i++) {
				tracks.add(new Track(i, this, trackProperties));
				if (i % 20 == 1) {
			        logger.info("processing track " + i + " of " + numberOfTracks);
				}
			}
		}
		return tracks;
	}

	/**
	 * Returns the number of items in the playlist
	 * @return int the size
	 */
	public int getCount() {
		return ConvertUtil.asInt(CommandUtil.executeCommand("count of tracks of playlist " + getIndex()));
	}
	
	/**
	 * Returns the total length of all songs (in seconds)
	 * @return int the total length of all songs (in seconds)
	 */
	public int getDuration() {
		// TODO: fails for 'Films', for now only show for none and Music...
		String specialKind = getSpecialKind(); // TODO: cache the value...
		if (specialKind.equals("none") || specialKind.equals("Music") ) {
		    Object result = CommandUtil.executeCommand("get {duration} of playlist " + getIndex());
		    return ConvertUtil.asInteger(result).intValue();
		}
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
		Object result = CommandUtil.executeCommand("get name of playlist " + getIndex());
		return ConvertUtil.asString(result);
	}
	
	/**
	 * Returns folder which contains this playlist (if any)
	 * @return
	 */
	public Playlist getParent() {
		//Object result = CommandUtil.executeCommand("get parent of playlist " + getIndex());
		//String test = ConvertUtil.asString(result);
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
		Object result = CommandUtil.executeCommand("get {shuffle} of playlist " + getIndex());
		return ConvertUtil.asBoolean(result).booleanValue();
	}

	/** 
	 * Returns the total size of all songs, in bytes
	 * @return the total size of all songs in bytes
	 */
	public int getSize() {
		// Object result = CommandUtil.executeCommand("get total size of playlist " + getIndex());
		// return ConvertUtil.asInteger(result).intValue();
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
		Object result = CommandUtil.executeCommand("get {song repeat} of playlist " + getIndex());
		return ConvertUtil.asString(result);
	}
	
	/**
	 * Returns the special playlist kind
	 * @return String none/Audiobooks/folder/Movies/Music/Party Shuffle/Podcasts/Purchased Music/TV Shows/Videos
	 */
	public String getSpecialKind() {
		Object result = CommandUtil.executeCommand("get {special kind} of playlist " + getIndex());
		return ConvertUtil.asString(result);
	}
	
	/**
	 * Returns the length of all songs in MM:SS format
	 * @return the length of all songs in MM:SS format
	 */
	public String getTime() {
		//Object result = CommandUtil.executeCommand("get length of playlist " + getIndex());
		//return ConvertUtil.asString(result);
		return null;
	}
	
	/**
	 * Returns true if this playlist is visible in the Source list
	 * @return true if this playlist is visible in the Source list
	 */
	public boolean isVisible() {
		Object result = CommandUtil.executeCommand("get visible of playlist " + getIndex());
		return ConvertUtil.asBoolean(result).booleanValue();
	}
	
	public String toString() {
		return getName();
	}
}
