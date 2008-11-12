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

import java.util.logging.Logger;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import nl.escay.javaitunesapi.utils.CommandUtil;

/**
 * The main AppleScript entrypoint representation for iTunes
 *
	add - add one or more files to a playlist
	back track - reposition to beginning of current track or go to previous track if already at start of current track
	convert - convert one or more files or tracks
	fast forward - skip forward in a playing track
	next track - advance to the next track in the current playlist
	pause - pause playback
	play - play the current track or the specified track or file.
	playpause - toggle the playing/paused state of the current track
	previous track - return to the previous track in the current playlist
	refresh - update file track information from the current information in the track’s file
	resume - disable fast forward/rewind and resume playback, if playing.
	reveal - reveal and select a track or playlist
	rewind - skip backwards in a playing track
	search - search a playlist for tracks matching the search string. Identical to entering search text in the Search field in iTunes.
	stop - stop playback
	update - update the specified iPod
	eject - eject the specified iPod
	subscribe - subscribe to a podcast feed
	updateAllPodcasts - update all subscribed podcast feeds
	updatePodcast - update podcast feed
	download - download podcast episode
 */
public class ITunesSuite {
	private static Logger logger = Logger.getLogger(ITunesSuite.class.toString());
	private Application application = new Application();
	
	public Application getApplication() {
		return application;
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
