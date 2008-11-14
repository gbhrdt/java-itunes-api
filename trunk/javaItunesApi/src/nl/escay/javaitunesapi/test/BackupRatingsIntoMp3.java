package nl.escay.javaitunesapi.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nl.escay.javaitunesapi.itunes.Track;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * Test code to eventually save rating into mp3's comment field
 */
public class BackupRatingsIntoMp3 {
	public static void main(String [] args) {
//		ITunesSuite iTunes = new ITunesSuite();
//	    iTunes.start();
//	    
//	    Source source = new Source();
//	    
//	    List<Playlist> playLists = source.getPlaylists();
//	    System.out.println("Playlists: " + playLists);
//	    for (Playlist playlist : playLists) {
//	    	System.out.println("Playlist, index: " + playlist.getIndex() + ", data: " + playlist);
//	    	
//	    	if (playlist.getCount() < 100) {
//	    		List<Track> tracks = playlist.getTracks();
//	    		for (Track track : tracks) {
//	    			System.out.println("Track, index: " + track.getIndex() + ", data: " + track + ", rating: " + track.getRating() + ", comment: " + track.getComment());
//	    		}
//	    	}
//	    }
//	    
//	    /*
//	    System.out.println("Nr. of playlists: " + iTunes.getNumberOfPlaylists());
//	    System.out.println("Playlist name of playlist 1: " + iTunes.getPlaylistName(1));
//	    System.out.println("Playlist names: " + iTunes.getPlaylistNames());
//	    */
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
//	    
	    testXml();
	    System.out.println(createTrackXml(20));
	}
	
	// <track><rating>80</rating><playedcount>0</playedcount><playeddate></playeddate><originalcomment></originalcomment><!-- iTunes Rating Script - www.escay.nl/itunes/ --></track>
	
	private static String createTrackXml(int rating) {
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
			
			Element playedDateElement = doc.createElement("playeddate");
			
			Element originalCommentElement = doc.createElement("originalcomment");
			
			Comment comment = doc.createComment(" iTunes Rating Script - www.escay.nl/itunes/ ");
			
			trackElement.appendChild(ratingElement);
			trackElement.appendChild(playedCountElement);
			trackElement.appendChild(playedDateElement);
			trackElement.appendChild(originalCommentElement);
			trackElement.appendChild(comment);

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
		return xmlString;
	}
	
	private static void testXml() {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			String xmlString = "<book><person><first>Kiran</first><last>Pai</last><age>22</age></person><person><first>Bill</first><last>Gates</last><age>46</age></person><person><first>Steve</first><last>Jobs</last><age>40</age></person></book>";
			InputStream is = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
			Document doc = docBuilder.parse(is);
			// normalize text representation
            doc.getDocumentElement ().normalize ();
            System.out.println ("Root element of the doc is " + doc.getDocumentElement().getNodeName());

            NodeList listOfPersons = doc.getElementsByTagName("person");
            int totalPersons = listOfPersons.getLength();
            System.out.println("Total no of people : " + totalPersons);

            for(int s=0; s<listOfPersons.getLength() ; s++){
                Node firstPersonNode = listOfPersons.item(s);
                if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE){
                    Element firstPersonElement = (Element)firstPersonNode;

                    //-------
                    NodeList firstNameList = firstPersonElement.getElementsByTagName("first");
                    Element firstNameElement = (Element)firstNameList.item(0);

                    NodeList textFNList = firstNameElement.getChildNodes();
                    System.out.println("First Name : " + 
                           ((Node)textFNList.item(0)).getNodeValue().trim());

                    //-------
                    NodeList lastNameList = firstPersonElement.getElementsByTagName("last");
                    Element lastNameElement = (Element)lastNameList.item(0);

                    NodeList textLNList = lastNameElement.getChildNodes();
                    System.out.println("Last Name : " + 
                           ((Node)textLNList.item(0)).getNodeValue().trim());

                    //----
                    NodeList ageList = firstPersonElement.getElementsByTagName("age");
                    Element ageElement = (Element)ageList.item(0);

                    NodeList textAgeList = ageElement.getChildNodes();
                    System.out.println("Age : " + 
                           ((Node)textAgeList.item(0)).getNodeValue().trim());

                    //------
                }//end of if clause
            }//end of for loop with s var

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
