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

package nl.escay.javaitunesapi.demo;

import java.util.List;

import nl.escay.javaitunesapi.itunes.ITunesSuite;
import nl.escay.javaitunesapi.itunes.Playlist;
import nl.escay.javaitunesapi.itunes.Source;
import nl.escay.javaitunesapi.itunes.Track;
import nl.escay.javaitunesapi.parser.AppleScriptValueParser;
import nl.escay.javaitunesapi.utils.CommandUtil;

/**
 * Demo class to show some java-itunes-api capabilities
 */
public class Demo {

	public static void main(String[] args) {
        System.out.println("Demo javaitunesapi");
		ITunesSuite iTunes = new ITunesSuite();
		
		System.out.println("Starting iTunes");
	    iTunes.start();
	    System.out.println("iTunes started");

	    Source source = new Source();

	    List<Playlist> playLists = source.getPlaylists();
	    System.out.println("Nr. of playlists: " + playLists.size());
	    System.out.println("Playlists: " + playLists);
	    
	    for (Playlist playlist : playLists) {
	    	System.out.println("Playlist: " + playlist + ", contains " + playlist.getCount() + " song(s).");
	    	System.out.println("  duration: " + playlist.getDuration());
	    	System.out.println("  time: " + playlist.getTime());
	    	System.out.println("  size: " + playlist.getSize());
	    	System.out.println("  song repeat: " + playlist.getSongRepeat());
	    	System.out.println("  special kind: " + playlist.getSpecialKind());
	    	System.out.println("  parent: " + playlist.getParent());
	    	System.out.println("  shuffle: " + playlist.getShuffle());
	    	
	    	if (playlist.getCount() < 50) {
	    		List<Track> tracks = playlist.getTracks();
	    		for (Track track : tracks) {
	    			System.out.println("Track: " + track);
	    		}
	    	}
	    }

	    System.out.println("Sound volume: " + iTunes.getApplication().getSoundVolume());
	    iTunes.getApplication().setSoundVolume(80);
	    System.out.println("Sound volume: " + iTunes.getApplication().getSoundVolume());
	    
	    System.out.println("Is frontmost: " + iTunes.getApplication().isFrontMost());
	    iTunes.getApplication().setFrontMost(true);
	    System.out.println("Is frontmost: " + iTunes.getApplication().isFrontMost());
	    
	    System.out.println("Is fullscreen: " + iTunes.getApplication().isFullScreen());
	    iTunes.getApplication().setFullScreen(true);
	    System.out.println("Is fullscreen: " + iTunes.getApplication().isFullScreen());

	    iTunes.getApplication().mute(false);
	    System.out.println("Is muted: " + iTunes.getApplication().isMuted());
	    iTunes.getApplication().mute(true);
	    System.out.println("Is muted: " + iTunes.getApplication().isMuted());
	    
	    System.out.println("Name: " + iTunes.getApplication().getName());
	    System.out.println("Version: " + iTunes.getApplication().getVersion());
	    System.out.println("Player state: " + iTunes.getApplication().getPlayerState());

	    // String command = "get {id, name} of track 1";
	    String command = "get {id, name} of every track of library playlist 1";
	    long time1 = System.currentTimeMillis();
	    Object object = CommandUtil.executeCommand(command);
	    if (object instanceof StringBuilder) {
	    	Object result = new AppleScriptValueParser().parse(((StringBuilder) object).toString());
	    	long time2 = System.currentTimeMillis();
	    	System.out.println("time used: " + (time2 - time1));
	    	//System.out.println(result);
	    	if (result instanceof List) {
	    		List<?> results = (List<?>) result;
	    		System.out.println("length: " + results.size());
	    		for (Object value : results) {
	    			if (value instanceof List) {
	    				System.out.println("length inner list: " + ((List<?>)value).size());
	    			}
	    			//System.out.println(value);
	    		}
	    	}
	    }
	}
}
