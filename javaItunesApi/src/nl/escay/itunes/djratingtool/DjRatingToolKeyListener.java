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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Currently handles all key presses. TODO: use a nicer solution instead of adding 
 * this listener to all components. Use a KeyEventDispatcher ??
 */
public class DjRatingToolKeyListener implements KeyListener {
	DjRatingTool djRatingTool;

	public DjRatingToolKeyListener(DjRatingTool djRatingTool) {
        this.djRatingTool = djRatingTool;
	}

	public void keyPressed(KeyEvent arg0) {
		System.out.println("Key pressed:" + arg0.getKeyChar() + " - " + arg0.getKeyCode());
		switch (arg0.getKeyCode()) {
		case 49:
			// 1
			djRatingTool.rateStars(1);
			break;
		case 50:
			// 2
			djRatingTool.rateStars(2);
			break;
		case 51:
			// 3
			djRatingTool.rateStars(3);
			break;
		case 52:
			// 4
			djRatingTool.rateStars(4);
			break;
		case 53:
			// 5
			djRatingTool.rateStars(5);
			break;
		case 80:
			// p
			djRatingTool.getITunes().playPause();
			break;
		case 39:
			// ->
			djRatingTool.needleDropForward();
			break;
		case 37:
			// <-
			djRatingTool.needleDropBackward();
			break;

		default:
			break;
		}
	}

	public void keyReleased(KeyEvent arg0) {
		//System.out.println("Key released:" + arg0.getKeyChar() + " - " + arg0.getKeyCode());
	}

	public void keyTyped(KeyEvent arg0) {
		//System.out.println("Key typed:" + arg0.getKeyChar() + " - " + arg0.getKeyCode());
	}
}
