package nl.escay.javaitunesapi.test;

import java.util.List;

import nl.escay.javaitunesapi.itunes.ITunesSuite;
import nl.escay.javaitunesapi.itunes.Playlist;
import nl.escay.javaitunesapi.itunes.Source;
import nl.escay.javaitunesapi.itunes.Track;

/**
 * Test code to eventually save rating into mp3's comment field
 */
public class BackupRatingsIntoMp3 {
	public static void main(String [] args) {
		ITunesSuite iTunes = new ITunesSuite();
	    iTunes.start();
	    
	    Source source = new Source();
	    
	    List<Playlist> playLists = source.getPlaylists();
	    System.out.println("Playlists: " + playLists);
	    for (Playlist playlist : playLists) {
	    	System.out.println("Playlist, index: " + playlist.getIndex() + ", data: " + playlist);
	    	
	    	if (playlist.getCount() < 100) {
	    		List<Track> tracks = playlist.getTracks();
	    		for (Track track : tracks) {
	    			System.out.println("Track, index: " + track.getIndex() + ", data: " + track + ", rating: " + track.getRating() + ", comment: " + track.getComment());
	    		}
	    	}
	    }
	    
	    /*
	    System.out.println("Nr. of playlists: " + iTunes.getNumberOfPlaylists());
	    System.out.println("Playlist name of playlist 1: " + iTunes.getPlaylistName(1));
	    System.out.println("Playlist names: " + iTunes.getPlaylistNames());
	    */

	    System.out.println("Sound volume: " + iTunes.getApplication().getSoundVolume());
	    iTunes.getApplication().setSoundVolume(80);
	    System.out.println("Sound volume: " + iTunes.getApplication().getSoundVolume());
	    
	    System.out.println("Is frontmost: " + iTunes.getApplication().isFrontMost());
	    //iTunes.getApplication().setFrontMost(true);
	    //System.out.println("Is frontmost: " + iTunes.getApplication().isFrontMost());
	    
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
}
