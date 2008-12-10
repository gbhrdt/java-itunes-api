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

import nl.escay.javaitunesapi.utils.CommandUtil;
import nl.escay.javaitunesapi.utils.ConvertUtil;

/**
 * A playable audio source
 * 
 *  From the iTunes script library 8.0.1:
 * 
    # contains artworks; contained by playlists.

	album (text) : the album name of the track
	album artist (text) : the album artist of the track
	album rating (integer) : the rating of the album for this track (0 to 100)
	album rating kind (user/computed, r/o) : the rating kind of the album rating for this track
	#artist (text) : the artist/source of the track
	bit rate (integer, r/o) : the bit rate of the track (in kbps)
	bookmark (real) : the bookmark time of the track in seconds
	bookmarkable (boolean) : is the playback position for this track remembered?
	bpm (integer) : the tempo of this track in beats per minute
	category (text) : the category of the track
	#comment (text) : freeform notes about the track
	compilation (boolean) : is this track from a compilation album?
	composer (text) : the composer of the track
	database ID (integer, r/o) : the common, unique ID for this track. If two tracks in different playlists have the same database ID, they are sharing the same data.
	date added (date, r/o) : the date the track was added to the playlist
	description (text) : the description of the track
	disc count (integer) : the total number of discs in the source album
	disc number (integer) : the index of the disc containing this track on the source album
	duration (real, r/o) : the length of the track in seconds
	enabled (boolean) : is this track checked for playback?
	episode ID (text) : the episode ID of the track
	episode number (integer) : the episode number of the track
	EQ (text) : the name of the EQ preset of the track
	finish (real) : the stop time of the track in seconds
	gapless (boolean) : is this track from a gapless album?
	genre (text) : the music/audio genre (category) of the track
	grouping (text) : the grouping (piece) of the track. Generally used to denote movements within a classical work.
	kind (text, r/o) : a text description of the track
	long description (text)
	lyrics (text) : the lyrics of the track
	modification date (date, r/o) : the modification date of the content of this track
	#played count (integer) : number of times this track has been played
	#played date (date) : the date and time this track was last played
	podcast (boolean, r/o) : is this track a podcast episode?
	#rating (integer) : the rating of this track (0 to 100)
	#rating kind (user/computed, r/o) : the rating kind of this track
	sample rate (integer, r/o) : the sample rate of the track (in Hz)
	season number (integer) : the season number of the track
	shufflable (boolean) : is this track included when shuffling?
	skipped count (integer) : number of times this track has been skipped
	skipped date (date) : the date and time this track was last skipped
	show (text) : the show name of the track
	sort album (text) : override string to use for the track when sorting by album
	sort artist (text) : override string to use for the track when sorting by artist
	sort album artist (text) : override string to use for the track when sorting by album artist
	sort name (text) : override string to use for the track when sorting by name
	sort composer (text) : override string to use for the track when sorting by composer
	sort show (text) : override string to use for the track when sorting by show name
	size (integer, r/o) : the size of the track (in bytes)
	start (real) : the start time of the track in seconds
	time (text, r/o) : the length of the track in MM:SS format
	track count (integer) : the total number of tracks on the source album
	track number (integer) : the index of the track on the source album
	unplayed (boolean) : is this track unplayed?
	video kind (none/movie/music video/TV show) : kind of video track
	volume adjustment (integer) : relative volume adjustment of the track (-100% to 100%)
	year (integer) : the year the track was recorded/released
 */
public class Track extends Item {
	
	private static Logger logger = Logger.getLogger(Track.class.toString());
	private List<ArtWork> artworks;
	private Playlist playlist;
	private List<TrackProperty> trackProperties = new ArrayList<TrackProperty>();
	private String artist;
	private String comment;
	private String name;
	private int rating;
	
	public enum TrackProperty {
		artist,
		comment,
		name,
		played_count,
		played_date,
		rating,
		rating_kind,
		rating_of_track
	}

	public Track() {
		// TODO: Cannot be used for now, testing with the 
		// other constructor.
	}

	public Track(int index, Playlist playlist, TrackProperty... properties) {
		super(index);
		this.playlist = playlist;
		
		for (TrackProperty property : properties) {
			trackProperties.add(property);
		}
		
		if (trackProperties.size() == 0) {
			// Default properties to retrieve
			trackProperties.add(TrackProperty.name);
			trackProperties.add(TrackProperty.artist);
		}
		
		String propertiesQuery = "";

		for (TrackProperty property : trackProperties) {
			if (propertiesQuery.length() > 0) {
				propertiesQuery = propertiesQuery + ", ";
			}
			// Convert enum to query parameter string
			propertiesQuery += property.name().replace('_', ' ');
		}
		
		Object commandResult = CommandUtil.executeCommand("get {" + propertiesQuery + "} of track " + getIndex() + " of playlist " + playlist.getIndex());
		if (commandResult.equals("")) {
			// TODO: how is this possible?
			return;
		}
		List<?> parseResult = (List<?>) commandResult;
		
		assert(parseResult.size() == trackProperties.size());
		
		int i = 0;
		for (TrackProperty property : trackProperties) {
		    if (property.equals(TrackProperty.artist)) {
		    	artist = (String) parseResult.get(i);
		    }
		    if (property.equals(TrackProperty.name)) {
		    	name = (String) parseResult.get(i);
		    }
		    if (property.equals(TrackProperty.comment)) {
		    	comment = (String) parseResult.get(i);
		    }
		    if (property.equals(TrackProperty.rating)) {
		    	Object tmp = parseResult.get(i);
		    	rating = (Integer) tmp;
		    }
		    i++;
		}
		logger.fine("Retrieved track: " + this);
	}

	public List<ArtWork> getArtworks() {
		return artworks;
	}

	/**
	 * Returns the artist/source of the track
	 * @return the artist/source of the track
	 */
	public String getArtist() {
		if (trackProperties.contains(TrackProperty.artist)) {
			return artist;
		}
		artist = ConvertUtil.asString(CommandUtil.executeCommand("get artist of track " + getIndex() + " of playlist " + playlist.getIndex()));
		trackProperties.add(TrackProperty.artist);
		return artist;
	}
	
	/**
	 * Set the artist/source of the track
	 */
	public void setArtist() {
	    // TODO	
	}
	
	/**
	 * Get freeform notes about the track
	 * @return String containing the notes
	 */
	public String getComment() {
		if (trackProperties.contains(TrackProperty.comment)) {
			return comment;
		}
		comment = ConvertUtil.asString(CommandUtil.executeCommand("get comment of track " + getIndex() + " of playlist " + playlist.getIndex()));
		trackProperties.add(TrackProperty.comment);
		return comment;
	}
	
	/**
	 * Set freeform notes about the track
	 * @param comment the notes to set
	 */
	public void setComment(String comment) {
		System.out.println("### new comment value:\n" + comment);
		CommandUtil.executeCommand("set comment of track " + getIndex() + " of playlist " + playlist.getIndex() + " to \"" + comment + "\"");
		if (!trackProperties.contains(TrackProperty.comment)) {
		    trackProperties.add(TrackProperty.comment);
		}
		this.comment = comment; 
	}

	@Override
	public String getName() {
		if (trackProperties.contains(TrackProperty.name)) {
			return name;
		}
		name = ConvertUtil.asString(CommandUtil.executeCommand("get name of track " + getIndex() + " of playlist " + playlist.getIndex()));
		trackProperties.add(TrackProperty.name);
		return name;
	}

	/**
	 * Returns the number of times this track has been played
	 * @return the number of times this track has been played, -1 if failed
	 */
	public int getPlayedCount() {
		return ConvertUtil.asInt(CommandUtil.executeCommand("get played count of track " + getIndex() + " of playlist " + playlist.getIndex()));
	}

	/**
	 * Set the number of times this track has been played
	 * @param value the number of times this track has been played
	 */
	public void setPlayedCount(int value) {
		// TODO
	}

	/**
	 * Returns the date and time this track was last played
	 * TODO: analyse the format, have to dive into AppleScript date
	 * Example string returned on my system: "dinsdag, 1 april 2008 15:12:23"
	 * 
	 * @return the date and time this track was last played
	 */
	public String getPlayedDate() {
		return ConvertUtil.asString(CommandUtil.executeCommand("get played date of track " + getIndex() + " of playlist " + playlist.getIndex()));
	}
	
	/**
	 * Set the date and time this track was last played
	 * @param value the date and time this track was last played
	 */
	public void setPlayedDate(String value) {
		// TODO
	}

	/**
	 * Returns the rating of this track (0 to 100)
	 * @return the rating of this track (0 to 100), -1 if failed
	 */
	public int getRating() {
		if (trackProperties.contains(TrackProperty.rating)) {
			return rating;
		}
		rating = ConvertUtil.asInt(CommandUtil.executeCommand("get rating of track " + getIndex() + " of playlist " + playlist.getIndex()));
		trackProperties.add(TrackProperty.rating);
		return rating;
	}
	
	/**
	 * Set the rating of this track (0 to 100)
	 * @param value (0 to 100)
	 */
	public void setRating(int value) {
		// TODO
	}
	
	/**
	 * Returns the rating kind of this track
	 * @return String user/computed
	 */
	public String getRatingKind() {
		return ConvertUtil.asString(CommandUtil.executeCommand("get rating kind of track " + getIndex() + " of playlist " + playlist.getIndex()));
	}
	
	public String toString() {
		return getArtist() + " - " + getName();
	}
}
