package nl.escay.itunes.djratingtool;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import nl.escay.javaitunesapi.itunes.ITunesSuite;

import com.apple.eawt.Application;
import com.apple.eawt.ApplicationAdapter;
import com.apple.eawt.ApplicationEvent;

/**
 * DJ Rating Tool
 * Rate new songs quickly.
 * 
 * Mac OSX java tips:
 * http://java.sun.com/developer/technicalArticles/JavaLP/JavaToMac/
 * http://java.sun.com/developer/technicalArticles/JavaLP/JavaToMac2/
 * http://java.sun.com/developer/technicalArticles/JavaLP/JavaToMac3/
 * http://developer.apple.com/mac/library/documentation/Java/Conceptual/Java14Deve
 * lopment/07-NativePlatformIntegration/NativePlatformIntegration.html#//apple_ref/
 * doc/uid/TP40001909-SW1
 */
public class DjRatingTool extends Application {
	private static final String DJ_RATING_TOOL = "DJ Rating Tool";
	ITunesSuite iTunes;
	private JFrame frame;
	
    public static void main(String[] args) {
    	// Make menus part of OSX menu and use OSX look and feel
    	System.setProperty("apple.laf.useScreenMenuBar", "true");
    	
    	// The following doesn't work when this class extends Application, 
    	// use -Xdock:name="DJ Rating Tool" as vm argument instead
    	// System.setProperty("com.apple.mrj.application.apple.menu.about.name", DJ_RATING_TOOL);
    	
    	new DjRatingTool();
    }
    
    public DjRatingTool() {
    	addPreferencesMenuItem();
    	setEnabledPreferencesMenu(true);
    	addApplicationListener(new DjRatingToolApplicationAdapter());

    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		// Start iTunes
		getITunes();
    	
    	frame = new JFrame(DJ_RATING_TOOL);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        JPanel panel = new JPanel(new FlowLayout());

        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		iTunes.playPause();
        	}});
        panel.add(playButton);

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iTunes.pause();
			}});
        panel.add(pauseButton);
        
        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		updateStatus();
        	}});
        panel.add(stopButton);
        
        JButton fastForwardButton = new JButton("FFWD");
        fastForwardButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		iTunes.fastForward();
        	}});
        panel.add(fastForwardButton);
        
        JButton rewindButton = new JButton("RWD");
        rewindButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		iTunes.rewind();
        	}});
        panel.add(rewindButton);
        
        JButton resumeButton = new JButton("Resume");
        resumeButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		iTunes.resume();
        	}});
        panel.add(resumeButton);
        
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		iTunes.nextTrack();
        	}});
        panel.add(nextButton);
        
        JButton previousButton = new JButton("Prev");
        previousButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		iTunes.previousTrack();
        	}});
        panel.add(previousButton);
        
        JButton needleDropBigBackwardButton = new JButton("<< Drop");
        needleDropBigBackwardButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		int seconds = iTunes.getApplication().getPlayerPosition();
        		iTunes.getApplication().setPlayerPosition(seconds - 40);
        	}});
        panel.add(needleDropBigBackwardButton);

        JButton needleDropBackwardButton = new JButton("< Drop");
        needleDropBackwardButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		int seconds = iTunes.getApplication().getPlayerPosition();
        		iTunes.getApplication().setPlayerPosition(seconds - 20);
        	}});
        panel.add(needleDropBackwardButton);

        JButton needleDropForwardButton = new JButton("Drop >");
        needleDropForwardButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		int seconds = iTunes.getApplication().getPlayerPosition();
        		iTunes.getApplication().setPlayerPosition(seconds + 20);
        	}});
        panel.add(needleDropForwardButton);

        JButton needleDropBigForwardButton = new JButton("Drop >>");
        needleDropBigForwardButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		int seconds = iTunes.getApplication().getPlayerPosition();
        		iTunes.getApplication().setPlayerPosition(seconds + 40);
        	}});
        panel.add(needleDropBigForwardButton);

        frame.add(panel);
        frame.pack();
        
        // Show the frame
        frame.setVisible(true);
        
        Timer timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
        		updateStatus();
			}});
        timer.start(); 

	}
    
    private void updateStatus() {
    	String state = iTunes.getApplication().getPlayerState();
    	int seconds = iTunes.getApplication().getPlayerPosition();
    	frame.setTitle(DJ_RATING_TOOL + " - iTunes: " + 
    			state + " (" + secondsToHHMMSS(seconds) + ")");
	}

	private ITunesSuite getITunes() {
    	if (iTunes == null) {
			iTunes = new ITunesSuite();
		    iTunes.start();	
    	}
    	return iTunes;
    }
    
	private String secondsToHHMMSS(int seconds) {
		int hours = seconds / 3600;
		int remainder = seconds % 3600;
		int minutes = remainder / 60;
		seconds = remainder % 60;

		return ((hours < 10 ? "0" : "") + hours + ":"
				+ (minutes < 10 ? "0" : "") + minutes + ":"
				+ (seconds < 10 ? "0" : "") + seconds);
	}
	
	private class DjRatingToolApplicationAdapter extends ApplicationAdapter {
	     //public void handleAbout(ApplicationEvent event) {
	         //new AboutDialog(new JFrame()).show();
	     //}
		
		
		@Override
		public void handlePreferences(ApplicationEvent arg0) {
			JFrame preferencesFrame = new JFrame();
			JLabel label = new JLabel("TODO");
			preferencesFrame.add(label);
			preferencesFrame.pack();
			preferencesFrame.setVisible(true);
		}
		
		@Override
		public void handleQuit(ApplicationEvent arg0) {
			System.exit(0);
		}
	}

}
