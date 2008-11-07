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

/**
 * The master music library playlist
 * 
 * contains file tracks, URL tracks, shared tracks
 */
public class LibraryPlaylist extends Playlist {
	private List<FileTrack> fileTracks;
	private List<URLTrack> urlTracks;
	private List<SharedTrack> sharedTracks;

	public List<FileTrack> getFileTracks() {
		return fileTracks;
	}
	
	public List<URLTrack> getUrlTracks() {
		return urlTracks;
	}
	
	public List<SharedTrack> getSharedTracks() {
		return sharedTracks;
	}
}
