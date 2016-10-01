package edu.psgv.sweng861;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Playlist {

	private static final Logger logger = LogManager.getLogger();	
	
	String url;
	String urlContent;
	String type;

	public void accept(PlaylistChecker c) {
//		logger.info(">>accept()");
		c.check(this);
//		logger.info("<<accept()");
	}	
	
	protected String getType() {
		return type;
	}

	public String getUrl() {
		return this.url;
	}
	public String getUrlContent() {
		return this.urlContent;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setUrlContent(String content) {
		this.urlContent = content;
	}

	protected void setType(String type) {
		this.type = type;
	}
	
	protected ArrayList<Playlist> getVariants() {
		ArrayList<Playlist> v = new ArrayList<Playlist>();
		System.out.println("Empty playlist");
		return v;
	}
	
	
	
	// constructor
	
	
}