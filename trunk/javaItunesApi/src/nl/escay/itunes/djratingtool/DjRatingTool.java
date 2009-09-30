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

package nl.escay.itunes.djratingtool;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

import javax.script.ScriptException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import nl.escay.javaitunesapi.itunes.ITunesSuite;
import nl.escay.javaitunesapi.utils.CommandUtil;

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
	private PreferencesFrame preferencesFrame = new PreferencesFrame();
	private DjRatingToolKeyListener djRatingToolKeyListener;
	
    public static void main(String[] args) {
    	// Make menus part of OSX menu and use OSX look and feel
    	System.setProperty("apple.laf.useScreenMenuBar", "true");
    	
    	// The following doesn't work when this class extends Application, 
    	// use -Xdock:name="DJ Rating Tool" as vm argument instead
    	// System.setProperty("com.apple.mrj.application.apple.menu.about.name", DJ_RATING_TOOL);
    	
    	new DjRatingTool();
    	
    	String command = "tell application \"itunes\"\n name of current track\n end tell";
    	try {
    		System.out.println("test: " + CommandUtil.getScriptEngine().eval(command));
    	} catch (ScriptException ex) {
    		ex.printStackTrace();
    	}
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

		djRatingToolKeyListener = new DjRatingToolKeyListener(this);

		// Start iTunes
		getITunes();
    	
    	frame = new JFrame(DJ_RATING_TOOL);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        JPanel panel = new JPanel(new FlowLayout());

        JButton playButton = new JButton("Play");
        playButton.addKeyListener(djRatingToolKeyListener);
        playButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		iTunes.playPause();
        	}});
        panel.add(playButton);

        JButton pauseButton = new JButton("Pause");
        pauseButton.addKeyListener(djRatingToolKeyListener);
        pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iTunes.pause();
			}});
        panel.add(pauseButton);
        
        JButton stopButton = new JButton("Stop");
        stopButton.addKeyListener(djRatingToolKeyListener);
        stopButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		updateStatus();
        	}});
        panel.add(stopButton);
        
        JButton fastForwardButton = new JButton("FFWD");
        fastForwardButton.addKeyListener(djRatingToolKeyListener);
        fastForwardButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		iTunes.fastForward();
        	}});
        panel.add(fastForwardButton);
        
        JButton rewindButton = new JButton("RWD");
        rewindButton.addKeyListener(djRatingToolKeyListener);
        rewindButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		iTunes.rewind();
        	}});
        panel.add(rewindButton);
        
        JButton resumeButton = new JButton("Resume");
        resumeButton.addKeyListener(djRatingToolKeyListener);
        resumeButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		iTunes.resume();
        	}});
        panel.add(resumeButton);
        
        JButton nextButton = new JButton("Next");
        nextButton.addKeyListener(djRatingToolKeyListener);
        nextButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		iTunes.nextTrack();
        	}});
        panel.add(nextButton);
        
        JButton previousButton = new JButton("Prev");
        previousButton.addKeyListener(djRatingToolKeyListener);
        previousButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		iTunes.previousTrack();
        	}});
        panel.add(previousButton);
        
        JButton needleDropBigBackwardButton = new JButton("<< Drop");
        needleDropBigBackwardButton.addKeyListener(djRatingToolKeyListener);
        needleDropBigBackwardButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		int seconds = iTunes.getApplication().getPlayerPosition();
        		iTunes.getApplication().setPlayerPosition(seconds - preferencesFrame.getLargeNeedleDropTime());
        	}});
        panel.add(needleDropBigBackwardButton);

        JButton needleDropBackwardButton = new JButton("< Drop");
        needleDropBackwardButton.addKeyListener(djRatingToolKeyListener);
        needleDropBackwardButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		needleDropBackward();
        	}});
        panel.add(needleDropBackwardButton);

        JButton needleDropForwardButton = new JButton("Drop >");
        needleDropForwardButton.addKeyListener(djRatingToolKeyListener);
        needleDropForwardButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		needleDropForward();
        	}});
        panel.add(needleDropForwardButton);

        JButton needleDropBigForwardButton = new JButton("Drop >>");
        needleDropBigForwardButton.addKeyListener(djRatingToolKeyListener);
        needleDropBigForwardButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		int seconds = iTunes.getApplication().getPlayerPosition();
        		iTunes.getApplication().setPlayerPosition(seconds + preferencesFrame.getLargeNeedleDropTime());
        	}});
        panel.add(needleDropBigForwardButton);

        frame.add(panel);
        frame.pack();
        
        frame.setFocusable(true);
        frame.addKeyListener(djRatingToolKeyListener);
        
        frame.enableInputMethods(true);
        
        // Show the frame
        frame.setVisible(true);
        
        Timer timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
        		updateStatus();
			}});
        timer.start(); 

	}
    
    private void updateStatus() {
//    	String state = iTunes.getApplication().getPlayerState();
//    	String trackName = "" + getITunes().getApplication().getCurrentTrackRating();
//    	int seconds = iTunes.getApplication().getPlayerPosition();
//    	frame.setTitle(DJ_RATING_TOOL + " - state: " + 
//    			state + " track: " + trackName + " (" + secondsToHHMMSS(seconds) + ")");
	}

	protected ITunesSuite getITunes() {
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
	
	protected void needleDropForward() {
		int seconds = iTunes.getApplication().getPlayerPosition();
		iTunes.getApplication().setPlayerPosition(seconds + preferencesFrame.getNeedleDropTime());
	}

	protected void needleDropBackward() {
		int seconds = iTunes.getApplication().getPlayerPosition();
		iTunes.getApplication().setPlayerPosition(seconds - preferencesFrame.getNeedleDropTime());
	}

	private class DjRatingToolApplicationAdapter extends ApplicationAdapter {
		//public void handleAbout(ApplicationEvent event) {
	         //new AboutDialog(new JFrame()).show();
	     //}
		
		@Override
		public void handlePreferences(ApplicationEvent arg0) {
			preferencesFrame = new PreferencesFrame();
			preferencesFrame.setVisible(true);
		}
		
		@Override
		public void handleQuit(ApplicationEvent arg0) {
			System.exit(0);
		}
	}

	private class PreferencesFrame extends JFrame {
		private static final long serialVersionUID = 1L;
		final Preferences preferences = Preferences.userNodeForPackage(getClass());
		private int needleDropTime = preferences.getInt("needleDropTime", 30);
		
		public int getNeedleDropTime() {
			return needleDropTime;
		}

		public int getLargeNeedleDropTime() {
			return largeNeedleDropTime;
		}

		private int largeNeedleDropTime = preferences.getInt("largeNeedleDropTime", 60);

		public PreferencesFrame() {
			
	        final JPanel panel = new JPanel();
			GridLayout gridLayout = new GridLayout(3,2);
			panel.setLayout(gridLayout);
			
			panel.add(new JLabel("Needle Drop time"));
			final JTextField needleDropTimeTF = new JTextField("" + needleDropTime);
			panel.add(needleDropTimeTF);
			panel.add(new JLabel("Large Needle Drop Time"));
			final JTextField largeNeedleDropTimeTF = new JTextField("" + largeNeedleDropTime);
			panel.add(largeNeedleDropTimeTF);
			
			JButton okButton = new JButton("Ok");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						needleDropTime = new Integer(needleDropTimeTF.getText());
						preferences.putInt("needleDropTime", needleDropTime);
					} catch (NumberFormatException nfe) {
						// Do not update the value
					}
					try {
						largeNeedleDropTime = new Integer(largeNeedleDropTimeTF.getText());
						preferences.putInt("largeNeedleDropTime", largeNeedleDropTime);
					} catch (NumberFormatException nfe) {
						// Do not update the value
					}
					setVisible(false);
				}});
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setVisible(false);
				}});
			panel.add(okButton);
			panel.add(cancelButton);

			add(panel);
			pack();
		}
	}

	protected void rateStars(int i) {
		String currentTrackName = getITunes().getApplication().getCurrentTrackName();
		int currentRating = getITunes().getApplication().getCurrentTrackRating();
		if (currentRating > 0) {
			int result = JOptionPane.showConfirmDialog(null, 
					"Do you want to override the current rating?" +
					"\nSong: " + currentTrackName + 
					"\nRating: " + currentRating / 20, "Override rating?", JOptionPane.YES_NO_OPTION);
            if (JOptionPane.NO_OPTION == result) {
            	// Do not update the rating
            	return;
            }
		}
		
		int rating = i * 20;
		System.out.println("Rate: " + rating + " - " + currentTrackName);
		getITunes().getApplication().setCurrentTrackRating(rating);
	}
}
