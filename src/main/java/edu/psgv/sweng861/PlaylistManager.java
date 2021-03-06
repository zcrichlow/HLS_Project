package edu.psgv.sweng861;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlaylistManager {

	private static final Logger logger = LogManager.getLogger();		
	
	protected ArrayList<String> urls;
	protected ArrayList<Playlist> playlists;
	protected ArrayList<String> contentsList;
	protected PlaylistFactory pf;
	
	// default constructor
	public PlaylistManager() {
		this.urls = new ArrayList<String>();
		this.playlists = new ArrayList<Playlist>();
		this.contentsList = new ArrayList<String>();
	}
	
	// initialization url constructor
	public PlaylistManager(String url) {
		this.urls = new ArrayList<String>();
		this.playlists = new ArrayList<Playlist>();
		this.contentsList = new ArrayList<String>();	
	
		this.urls.add(url);
		this.playlists = getPlaylistList(url);
		HttpURLConnection con =  getHttpConnection(url);
		this.contentsList.add(getConnectionContents(con));	
	}
		
	protected ArrayList<String> getUrls() {
		return urls;
	}
	protected void setUrls(ArrayList<String> urls) {
		this.urls = urls;
	}
	protected ArrayList<Playlist> getPlaylists() {
		return playlists;
	}
	protected void setPlaylists(ArrayList<Playlist> playlists) {
		this.playlists = playlists;
	}
	protected ArrayList<String> getContents() {
		return contentsList;
	}
	protected void setContents(ArrayList<String> contents) {
		this.contentsList = contents;
	}

	protected String printPlaylistReport(ArrayList<Playlist> list) {
		logger.info(">>printPlaylistReport");
		String str = "";
		String content = "";
		int counter = 6;
		for (Playlist p : list) {
			content = p.getUrlContent();
			Scanner scanner = new Scanner(content);
			while (counter > 6) {
				str = scanner.nextLine();
				counter--;
			}
			System.out.println(str);
		}
		logger.info("<<printPlaylistReport");
		return str;
	}	
	
	/*
	 * Check to see what type of playlist is in the url
	 */
	private static String getPlaylistType(String urlContent) {
		logger.info(">>getPlaylistType()");		
		Scanner scanner = new Scanner(urlContent);
		  String line = "";
		  String type = "";
		  while (scanner.hasNextLine()) {
		  // process the line
			  line = scanner.nextLine();
			  if (line.endsWith(".ts")) {
				  scanner.close();
				  type = "simple";
					logger.info("<<getPlaylistType()");
				  return type;
			  }
			  if (line.startsWith("#EXT-X-STREAM-INF")) {
				  scanner.close();
				  type = "master";
				  return type;
			  }
		  }
		  scanner.close();
			logger.info("<<getPlaylistType()");
			    return "neither";		
	}		
	
/*	
 *	get list of pre-validated playlist objects from the url
 */
	protected ArrayList<Playlist> getPlaylistList(String input) {
		logger.info(">>getPlaylistList()");
		logger.debug("String input: " + input);
		ArrayList<Playlist> list = new ArrayList<Playlist>();
		BufferedReader br = new BufferedReader(new StringReader(input));
		PlaylistFactory pf;
		String type;
		String line;
		try {
			while ((line = br.readLine()) != null) {
//			System.out.println(line);
		    // get the connection and contents
		    HttpURLConnection con = getHttpConnection(line);
		    if (con != null) {
		    String content = getConnectionContents(con);
		        // make sure the contents didn't fail
		        if (content.equals("exception")) {
		        	logger.error("Content returned an exception.");
		        	return null;
		        	// do nothing. loop restarts
		        } else {
		        	pf = new PlaylistFactory();
		        	type = getPlaylistType(content);
//		        	System.out.println("TYPE: " + type);
//		        	System.out.println("LINE: " + line);
//		        	System.out.println("CONTENT: " + content);
		        	Playlist p = pf.getPlaylist(type, line, content);
		        	list.add(p);
		        }
		        logger.debug("how many playlists?: " + list.size());
//		    	logger.info("<<getPlaylistList()");
//		        return list;
		    	}
			}
		    logger.info("<getPlaylistList");
		    return list;			
		} catch (Exception e) {
//			System.out.println("Error reading string");
			logger.error("General Exception.");
			e.printStackTrace();
			System.exit(4);
			}
			logger.info("<<getPlaylistList()");
			return list; 
	}	
	
	
	
	protected ArrayList<Playlist> getOnePlaylist(String url) {
	logger.info(">>getPlaylistList()");
	ArrayList<Playlist> list = new ArrayList<Playlist>();
	
	try {
//	    System.out.println(url);
	    // get the connection and contents
	    HttpURLConnection con = getHttpConnection(url);
	    if (con != null) {
	    String content = getConnectionContents(con);
	        // make sure the contents didn't fail
	        if (content.equals("exception")) {
	        	logger.error("Content returned an exception.");
	        	return null;
	        	// do nothing. loop restarts
	        } else {  	
	        	PlaylistFactory pF = new PlaylistFactory();
	        	String type = getPlaylistType(content);
//	        	System.out.println(type);
//	        	System.out.println(url);
//	        	System.out.println(content);
	        	Playlist p = pF.getPlaylist(type, url, content);
//	        	System.out.println(p.toString());
	        	list.add(p);
	        }
	    	logger.info("<<getPlaylistList()");
	        return list;
	    } 
	} catch (Exception e) {
//		System.out.println("Error reading string");
		logger.error("General Exception.");
		e.printStackTrace();
		System.exit(4);
		}
		logger.info("<<getPlaylistList()");
		return list; 
	}
	
//		
/*
 * Connect to the url and return that connection
 */
	protected HttpURLConnection getHttpConnection(String theUrl) {
		logger.info(">>getHttpConnection()");
		HttpURLConnection con = null;
		logger.debug("theUrl: " + theUrl);
		try {
			// create url object
			URL url = new URL(theUrl);
			// create an http connection object
			con = (HttpURLConnection) url.openConnection();
			// use buffered reader to read contents
		} catch (MalformedURLException e) {
			System.out.println("MalformedURL: Unable to connect with what was entered.");
			logger.debug("Not a URL.");
			logger.info("<<getHttpConnection()");
			return con;
		} catch (IOException e) {
			System.out.println("IOException: Unable to connect.");
			logger.debug("IOException occured.");
			logger.info("<<getHttpConnection()");
			return con;		
		} catch (Exception e) {
			System.out.println("Exception: Unable to connect");
			logger.error("General Exception.");
			logger.info("<<getHttpConnection()");
			return con;		
		}
		logger.info("<<getHttpConnection()");
		return con;
	}
	
/*
 * Get the contents of the provided url
 * return "exception" if failed, return content String if success
 */
	protected String getConnectionContents(HttpURLConnection con) {
		logger.info(">>getConnectionContents()");
		String content = "";
	//	String contentType = "";
		int response;
	
		try {
	//		check the response code
			response = con.getResponseCode();
		
			if (response == 404) {
				System.out.println("404: Cannot reach content");
				logger.error("404 Error.");
				return "exception";
			}
		
			// use buffered reader to read contents
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String ln;
			// read the contents of the buffered reader
			while ((ln = br.readLine()) != null) {
				content += ln + "\n";
			}
			br.close();		
		} catch (MalformedURLException e) {
			System.out.println("MalformedURL: Cannot get contents with this url.");
			logger.error("Not a URL");
			return "exception";
		} catch (IOException e) {
			System.out.println("IOException: Cannot fetch contents from that url.");
			logger.error("IOExceptionOccured.");
			return "exception";		
		} catch (Exception e) {
			System.out.println("Exception: Unable to fetch the contents.");
			logger.error("General Exception");
			return "exception";		
		}
		logger.info("<<getConnectionContents()");
		return content;
	}

}