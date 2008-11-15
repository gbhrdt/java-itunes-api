package nl.escay.javaitunesapi.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nl.escay.javaitunesapi.itunes.ITunesSuite;
import nl.escay.javaitunesapi.itunes.Playlist;
import nl.escay.javaitunesapi.itunes.Source;
import nl.escay.javaitunesapi.itunes.Track;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Backups iTunes track data into the mp3's comment field.
 * Why? To prevent the loss of rating data. Or to sync between different 
 * iTunes libraries or different pc's.
 * 
 * Example xml in the comment field: <track><rating>80</rating><playedcount>0</playedcount><playeddate></playeddate><originalcomment></originalcomment><!-- iTunes Rating Script - www.escay.nl/itunes/ --></track>
 */
public class BackupRatingsIntoMp3 {
	public static void main(String [] args) {
		ITunesSuite iTunes = new ITunesSuite();
	    iTunes.start();

	    Source source = new Source();
	    
	    List<Playlist> playLists = source.getPlaylists();
	    System.out.println("Playlists: " + playLists);
	    for (Playlist playlist : playLists) {
	    	if (playlist.getName().equals("Muziek")) {
		    	System.out.println("Playlist, index: " + playlist.getIndex() + ", data: " + playlist);
		    	
	    		List<Track> tracks = playlist.getTracks();
	    		for (Track track : tracks) {
	    			System.out.println("Track, index: " + track.getIndex() + ", data: " + track);
	    		    
	    			if (track.getComment().startsWith("<track>")) {
	    		        // Update existing track xml contents
	    				updateTrackXml(track);
	    		    } else {
	    		    	// Create new track xml contents
	    			    createTrackXml(track);
	    		    }
	    		}
	    	}
	    }
	    
//	    System.out.println("Nr. of playlists: " + iTunes.getNumberOfPlaylists());
//	    System.out.println("Playlist name of playlist 1: " + iTunes.getPlaylistName(1));
//	    System.out.println("Playlist names: " + iTunes.getPlaylistNames());
//
//	    System.out.println("Sound volume: " + iTunes.getApplication().getSoundVolume());
//	    iTunes.getApplication().setSoundVolume(80);
//	    System.out.println("Sound volume: " + iTunes.getApplication().getSoundVolume());
//	    
//	    System.out.println("Is frontmost: " + iTunes.getApplication().isFrontMost());
//	    //iTunes.getApplication().setFrontMost(true);
//	    //System.out.println("Is frontmost: " + iTunes.getApplication().isFrontMost());
//	    
//	    System.out.println("Is fullscreen: " + iTunes.getApplication().isFullScreen());
//	    iTunes.getApplication().setFullScreen(true);
//	    System.out.println("Is fullscreen: " + iTunes.getApplication().isFullScreen());
//
//	    iTunes.getApplication().mute(false);
//	    System.out.println("Is muted: " + iTunes.getApplication().isMuted());
//	    iTunes.getApplication().mute(true);
//	    System.out.println("Is muted: " + iTunes.getApplication().isMuted());
//	    
//	    System.out.println("Name: " + iTunes.getApplication().getName());
//	    System.out.println("Version: " + iTunes.getApplication().getVersion());
//	    System.out.println("Player state: " + iTunes.getApplication().getPlayerState());
	}
	
	
	private static void createTrackXml(Track track) {
		setTrackXml(track, track.getRating(), track.getPlayedCount(), track.getPlayedDate(), track.getComment());
	}
	
	private static void setTrackXml(Track track, int rating, int playedCount, String playedDate, String comment) {
		String xmlString = null;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			Element trackElement = doc.createElement("track");
			
			Element ratingElement = doc.createElement("rating");
			ratingElement.appendChild(doc.createTextNode("" + rating));
			
			Element playedCountElement = doc.createElement("playedcount");
			playedCountElement.appendChild(doc.createTextNode("" + playedCount));
			
			Element playedDateElement = doc.createElement("playeddate");
			if (playedDate != null) {
			    playedDateElement.appendChild(doc.createTextNode(playedDate));
			}
			Element originalCommentElement = doc.createElement("originalcomment");
			if (comment != null) {
			    originalCommentElement.appendChild(doc.createTextNode(comment));
			}
			Comment commentNode = doc.createComment(" iTunes Rating Script - www.escay.nl/itunes/ ");
			
			trackElement.appendChild(ratingElement);
			trackElement.appendChild(playedCountElement);
			trackElement.appendChild(playedDateElement);
			trackElement.appendChild(originalCommentElement);
			trackElement.appendChild(commentNode);

			doc.appendChild(trackElement);
			
			TransformerFactory tf = TransformerFactory.newInstance();
		    Transformer transformer = tf.newTransformer();
		    DOMSource source = new DOMSource(doc);  
            StringWriter sw = new StringWriter();
            StreamResult rsult = new StreamResult(sw);
            transformer.transform(source, rsult);
            xmlString = sw.toString();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
		// Remove <?xml version="1.0" encoding="UTF-8" standalone="no"?>
		if (xmlString.startsWith("<?xml")) {
			int endTag = xmlString.indexOf('>');
			xmlString = xmlString.substring(endTag + 1);
		}
		track.setComment(xmlString);
	}

	/**
	 * Compares track data with the comment field xml. Decides what the new values are to
	 * be stored in the comment field. Eventually the comment field is updated to reflect
	 * any changes. It is possible that track fields like the rating are updated if the
	 * comment xml contains higher / better values.  
	 * @param track, the track to be updated.
	 */
	private static void updateTrackXml(Track track) {
		int rating = 0;
		int playedCount = 0;
		String playedDate = null;
		String originalComment = null;
		
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			String xmlString = track.getComment();
			InputStream is = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
			Document doc = docBuilder.parse(is);
			
			// Look at the child nodes of <track>
			NodeList nodes = doc.getDocumentElement().getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (element.getNodeName().equals("rating")) {
                    	// Expecting 1 child, which is the text value
                      	Node valueNode = element.getFirstChild();
                    	if (valueNode != null && valueNode.getNodeType() == Node.TEXT_NODE) {
                    		//System.out.println("Rating from xml is: " + valueNode.getNodeValue());
                    		Integer xmlValue = new Integer(valueNode.getNodeValue());
                    		// Save the highest value
                    		rating = track.getRating();
                    		if (rating < xmlValue.intValue()) {
                    			track.setRating(xmlValue.intValue());
                    			rating = xmlValue.intValue();
                    		}
                    	}
                    } else if (element.getNodeName().equals("playedcount")) {
                    	// Expecting 1 child, which is the text value
                    	Node valueNode = element.getFirstChild();
                    	if (valueNode != null && valueNode.getNodeType() == Node.TEXT_NODE) {
                    		//System.out.println("PlayedCount from xml is: " + valueNode.getNodeValue());
                    		Integer xmlValue = new Integer(valueNode.getNodeValue());
                    		// Save the highest value
                    		// TODO: why is this out of sync, should we maybe increase the value, or let
                    		// the user decide what to do....
                    		playedCount = track.getPlayedCount();
                    		if (playedCount < xmlValue.intValue()) {
                    			track.setPlayedCount(xmlValue.intValue());
                    			playedCount = xmlValue.intValue();
                    		}
                    	}
                    } else if (element.getNodeName().equals("playeddate")) {
                    	// Expecting 1 child, which is the text value
                    	Node valueNode = element.getFirstChild();
                    	if (valueNode != null && valueNode.getNodeType() == Node.TEXT_NODE) {
                    		//System.out.println("PlayedDate from xml is: " + valueNode.getNodeValue());
                            // TODO: until I have no decent date value transformations
                    		// I only use the track.getPlayedDate value
                    		playedDate = track.getPlayedDate();
                    	}
                    } else if (element.getNodeName().equals("originalcomment")) {
                    	// Expecting 1 child, which is the text value
                    	Node valueNode = element.getFirstChild();
                    	if (valueNode != null && valueNode.getNodeType() == Node.TEXT_NODE) {
                    		//System.out.println("Original comment from xml is: " + valueNode.getNodeValue());
                    		originalComment = valueNode.getNodeValue();
                    	}
                    } else {
                    	// TODO: throw exception... for now just return
                    	System.out.println("Unsupported node name: " + element.getNodeName());
                    	return;
                    }
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Now update the track comment field with the found values
		setTrackXml(track, rating, playedCount, playedDate, originalComment);
	}
}