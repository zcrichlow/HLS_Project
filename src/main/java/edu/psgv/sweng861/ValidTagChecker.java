package edu.psgv.sweng861;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ValidTagChecker extends PlaylistChecker {
	ErrorLogger er = new ErrorLogger();
	
	public static final String FIRSTTAG = "#EXTM3U";
	public static final String VERSION = "#EXT-X-VERSION";
	
	public static final String TARGET_DURATION_TAG = "#EXT-X-TARGETDURATION";
	public static final String MEDIA_SEQUENCE = "#EXT-X-MEDIA-SEQUENCE";
	public static final String KEY = "#EXT-X-KEY";
	public static final String PROGRAM_DATE_TIME = "#EXT-X-PROGRAM-DATE-TIME";
	public static final String ALLOW_CACHE = "#EXT-X-ALLOW-CACHE";
	public static final String ENDLIST = "#EXT-X-ENDLIST";
	public static final String STREAM_INF = "#EXT-X-STREAM-INF";
	public static final String DISCONTINUITY = "#EXT-X-DISCONTINUITY";
	public static final String DISC_SEQ = "EXT-X-DISCONTINUITY-SEQUENCE";
	public static final String EXTINF = "#EXTINF";
	public static final String BYTERANGE = "#EXT-X-BYTERANGE";
	public static final String MAP = "#EXT-X-MAP";
	public static final String DATERANGE = "#EXT-X-DATERANGE";
	public static final String IFRAMESONLY = "#EXT-X-I-FRAMES-ONLY";
	public static final String PLAYLIST_TYPE = "#EXT-X-PLAYLIST-TYPE";
	// Master Tags
	public static final String MEDIA = "#EXT-X-MEDIA";
	public static final String IFRAMESTREAMINF = "#EXT-X-I-FRAME-STREAM-INF";
	public static final String SESSIONDATA = "#EXT-X-SESSION-DATA";
	public static final String SESSIONKEY = "#EXT-X-SESSION-KEY";
	
	public static final String INDESEG = "#EXT-X-INDEPENDENT-SEGMENTS";
	public static final String START = "#EXT-X-START";
	
	public static final Collection<String> validMediaTags = new ArrayList<String>();
	public static final Collection<String> validMasterTags = new ArrayList<String>();
	public static final Collection<String> playlistTags = new ArrayList<String>();
	
	private static final Logger logger = LogManager.getLogger();	
	
	public void check(Playlist p) {
		logger.info(">>check()");
		loadTags();
		System.out.println("\n	VALID TAG CHECK\n");
		System.out.println("Playlist URL: " + p.getUrl());
		System.out.println("Type: " + p.getType());	
		Scanner line = new Scanner(p.getUrlContent());
		
//		if (p.type.equals("Master")) {
//			
//		}
		
		int lineCounter = 0;
		while (line.hasNextLine()) {
			String tagline = line.nextLine();
			if (tagline.startsWith("#EXT")) {
				int index = tagline.indexOf(":");
				if (index != -1) {
					String tag = tagline.substring(0, index);
//					System.out.println(tag);
					playlistTags.add(tag);
				} else {
//					System.out.println(tagline);
					playlistTags.add(tagline);
				}
			}
		}
		
//		System.out.println(playlistTags.toString());
		
//		Find tags that don't belong
		if (p.type.equals("Master")) {
			playlistTags.removeAll(validMasterTags);
		}
		if (p.type.equals("Simple")) {
			playlistTags.removeAll(validMediaTags);
		}
		if (playlistTags.size() > 0) {
//			System.out.println(playlistTags.toString());
			for (String tag: playlistTags) {
				line = new Scanner(p.getUrlContent());
//				System.out.println("Tag: " + tag);
				while(line.hasNext()) {
					lineCounter = lineCounter + 1;
					String t = line.nextLine();
					if (tag.equals("#EXT")) {
						er.list.add("Line: " + lineCounter + " MAJOR: " + tag + " is an invalid tag.");
						break;
					} else if (t.startsWith(tag) && !(tag.equals("#EXT"))) {
						er.list.add("Line: " + lineCounter + " MAJOR: " + tag + " is an invalid tag. "
								+ "It may be undefined, \nbelong in a different type of playlist,"
								+ " or removed prior to Draft 19 of the HLS Protocol.");						
					}
				}				
			}
		} else {
//			System.out.println("No invalid tags found.");
		}
		playlistTags.clear();
		er.report();
		line.close();
		logger.info("<<check()");		
	}
	
	public void loadTags() {
		logger.info(">>load()");
//		4.3.1.  Basic Tags  . . . . . . . . . . . . . . . . . . . . .  10
		validMediaTags.add(FIRSTTAG); 				// 4.3.1.1.  EXTM3U  . . . . . . . . . . . . . . . . . . . . .  10
		validMasterTags.add(FIRSTTAG);
		validMediaTags.add(VERSION);  				// 4.3.1.2.  EXT-X-VERSION . . . . . . . . . . . . . . . . . .  10
		validMasterTags.add(VERSION);
//		4.3.2.  Media Segment Tags  . . . . . . . . . . . . . . . . .  10
		validMediaTags.add(EXTINF);   				// 4.3.2.1.  EXTINF  . . . . . . . . . . . . . . . . . . . . .  11
		validMediaTags.add(BYTERANGE);				// 4.3.2.2.  EXT-X-BYTERANGE . . . . . . . . . . . . . . . . .  11		
		validMediaTags.add(DISCONTINUITY);			// 4.3.2.3.  EXT-X-DISCONTINUITY . . . . . . . . . . . . . . .  12
		validMediaTags.add(KEY);						// 4.3.2.4.  EXT-X-KEY . . . . . . . . . . . . . . . . . . . .  12
		validMediaTags.add(MAP);						// 4.3.2.5.  EXT-X-MAP . . . . . . . . . . . . . . . . . . . .  14
		validMediaTags.add(PROGRAM_DATE_TIME);		// 4.3.2.6.  EXT-X-PROGRAM-DATE-TIME . . . . . . . . . . . . .  15
		validMediaTags.add(DATERANGE); 				// 4.3.2.7.  EXT-X-DATERANGE . . . . . . . . . . . . . . . . .  15
//      4.3.3.  Media Playlist Tags . . . . . . . . . . . . . . . . .  19		
		validMediaTags.add(TARGET_DURATION_TAG);		// 4.3.3.1.  EXT-X-TARGETDURATION  . . . . . . . . . . . . . .  19
		validMediaTags.add(MEDIA_SEQUENCE);			// 4.3.3.2.  EXT-X-MEDIA-SEQUENCE  . . . . . . . . . . . . . .  19
		validMediaTags.add(DISC_SEQ);				// 4.3.3.3.  EXT-X-DISCONTINUITY-SEQUENCE  . . . . . . . . . .  20
		validMediaTags.add(ENDLIST);			        // 4.3.3.4.  EXT-X-ENDLIST . . . . . . . . . . . . . . . . . .  20
        validMediaTags.add(PLAYLIST_TYPE);			// 4.3.3.5.  EXT-X-PLAYLIST-TYPE . . . . . . . . . . . . . . .  21
		validMediaTags.add(IFRAMESONLY);		        // 4.3.3.6.  EXT-X-I-FRAMES-ONLY . . . . . . . . . . . . . . .  21
//      4.3.4.  Master Playlist Tags  . . . . . . . . . . . . . . . .  22
		validMasterTags.add(MEDIA);  					// 4.3.4.1.  EXT-X-MEDIA . . . . . . . . . . . . . . . . . . .  22	
//		      4.3.4.1.1.  Rendition Groups  . . . . . . . . . . . . . .  25
		validMasterTags.add(STREAM_INF);				// 4.3.4.2.  EXT-X-STREAM-INF  . . . . . . . . . . . . . . . .  25
//      		4.3.4.2.1.  Alternative Renditions  . . . . . . . . . . .  28
		validMasterTags.add(IFRAMESTREAMINF);			// 4.3.4.3.  EXT-X-I-FRAME-STREAM-INF  . . . . . . . . . . . .  29
		validMasterTags.add(SESSIONDATA);				// 4.3.4.4.  EXT-X-SESSION-DATA  . . . . . . . . . . . . . . .  30
		validMasterTags.add(SESSIONKEY);				// 4.3.4.5.  EXT-X-SESSION-KEY . . . . . . . . . . . . . . . .  31
//    	4.3.5.  Media or Master Playlist Tags . . . . . . . . . . . .  31
		validMediaTags.add(INDESEG);					// 4.3.5.1.  EXT-X-INDEPENDENT-SEGMENTS  . . . . . . . . . . .  31
		validMasterTags.add(INDESEG);
		validMediaTags.add(START);					// 4.3.5.2.  EXT-X-START . . . . . . . . . . . . . . . . . . .  32
		validMasterTags.add(START);

//		validMediaTags.add(ALLOW_CACHE);

		logger.info("<<load()");
	}
}
