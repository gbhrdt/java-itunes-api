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

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import nl.escay.javaitunesapi.utils.CommandUtil;
import nl.escay.javaitunesapi.utils.ConvertUtil;

public class ITunesSuite {
	private static Logger logger = Logger.getLogger(ITunesSuite.class.toString());
	private Application application = new Application();
	
	public Application getApplication() {
		return application;
	}

	public static void main(String [] args) {
	    ITunesSuite iTunes = new ITunesSuite();
	    iTunes.start();
	    /*
	    System.out.println("Nr. of playlists: " + iTunes.getNumberOfPlaylists());
	    System.out.println("Playlist name of playlist 1: " + iTunes.getPlaylistName(1));
	    System.out.println("Playlist names: " + iTunes.getPlaylistNames());
	    */

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
	    
	}
	
	public ITunesSuite() {
		ScriptEngineManager sem = new ScriptEngineManager();
		CommandUtil.setScriptEngine(sem.getEngineByName("Java-AppleScript-Connector"));
	}
	
    public void start() {
    	String command = "tell application \"itunes\" to run";
    	try {
    		CommandUtil.getScriptEngine().eval(command);
    		logger.info("iTunes started");
    	} catch (ScriptException ex) {
    		ex.printStackTrace();
    	}
    }
    
    public void stop() {
    	String command = "tell application \"itunes\" to quit";
        try {
        	CommandUtil.getScriptEngine().eval(command);
            logger.info("iTunes stopped");
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
    }
    
    public Integer getNumberOfPlaylists() {
    	String command = "tell application \"iTunes\"\nget count of playlists\nend tell";
    	Object result = null;
    	try {
    		result = CommandUtil.getScriptEngine().eval(command);
    	} catch (ScriptException ex) {
    		ex.printStackTrace();
    	}
    	return ConvertUtil.convertToInteger(result);
    }
    
    public List<String> getPlaylistNames() {
    	Integer nrOfPlaylists = getNumberOfPlaylists();
    	List<String> playlistNames = new ArrayList<String>();
    	if (nrOfPlaylists != null) {
    		int i = 1; // TODO: document it starts at 1... not 0!
    		while (i <= nrOfPlaylists) {
    			playlistNames.add(getPlaylistName(i));
    			i++;
    		}
    	}
    	return playlistNames;
    }
    
    public String getPlaylistName(int index) {
    	// TODO: validate index value
    	String command = "tell application \"iTunes\"\nget name of playlist " + index + "\nend tell";
    	Object result = null;
    	try {
    		result = CommandUtil.getScriptEngine().eval(command);
    	} catch (ScriptException ex) {
    		ex.printStackTrace();
    		return null;
    	}
    	return ConvertUtil.convertToString(result);
    }

    public void test() {
    	String command = "tell application \"iTunes\"\nname of track 1 of library playlist 1\nend tell";
    	try {
    		Object result = CommandUtil.getScriptEngine().eval(command);
			System.out.println(result.toString());
    	} catch (ScriptException ex) {
    		ex.printStackTrace();
    	}
    }
}
