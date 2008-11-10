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

import java.util.List;

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
	played count (integer) : number of times this track has been played
	played date (date) : the date and time this track was last played
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
	
	private List<ArtWork> artworks;
	private Playlist playlist;

	public Track() {
		// TODO: Cannot be used for now, testing with the 
		// other constructor.
	}

	public Track(int index, Playlist playlist) {
		super(index);
		this.playlist = playlist;
	}

	public List<ArtWork> getArtworks() {
		return artworks;
	}

	/**
	 * Returns the artist/source of the track
	 * @return the artist/source of the track
	 */
	public String getArtist() {
	    // TODO
		return null;
	}
	
	/**
	 * Set the artist/source of the track
	 */
	public void setArtist() {
	    // TODO	
	}
	
	/**
	 * Set freeform notes about the track
	 * @param comment the notes to set
	 */
	public void setComment(String comment) {
		// TODO
	}

	/**
	 * Get freeform notes about the track
	 * @return String containing the notes
	 */
	public String getComment() {
		// TODO
		return null;
	}
	
	/**
	 * Returns the rating of this track (0 to 100)
	 * @return the rating of this track (0 to 100)
	 */
	public int getRating() {
		// TODO
		return -1;
	}
	
	@Override
	public String getName() {
		//tell application "iTunes"
		//
		//end tell
		return ConvertUtil.convertToString(CommandUtil.executeCommand("get name of track " + getIndex() + " of playlist " + playlist.getIndex()));
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
		// TODO
		return null;
	}
	
	public String toString() {
		return "artist: " + getArtist() + ", name: " + getName();
	}
}
