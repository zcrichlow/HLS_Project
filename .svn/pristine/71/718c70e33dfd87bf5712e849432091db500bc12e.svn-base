package edu.psgv.sweng861;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UriSequenceChecker extends PlaylistChecker {
	
	/*
	 * (non-Javadoc)
	 * @see edu.psgv.sweng861.PlaylistChecker#check(edu.psgv.sweng861.Playlist)
	 * 
	 */
	private static final Logger logger = LogManager.getLogger();		
	
	public void check(Playlist p) {
		logger.info(">>check()");
		String num = "";
		String line = "";
		int version = 0;
		int lineCounter = 0;		
		Double d;
		ErrorLogger er = new ErrorLogger();
		String content = p.getUrlContent();
		Scanner scanner = new Scanner(content);
		String classType = p.getClass().toString();
		
		System.out.println("URI/SEQUENCE CHECK");
		System.out.println("Playlist URL: " + p.getUrl());
//		System.out.println("Type: " + p.type);
		if (p.type.equals("Master")) {	
//			#EXT-X-STREAM-INF
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				lineCounter = lineCounter + 1;
				if (line.startsWith("#EXT-X-STREAM-INF")) {
					line = scanner.nextLine();
//					System.out.println(line);
					if (line.endsWith(".m3u8")){
						er.list.add("Line: " + lineCounter + " MAJOR: No variant playlist URI found.");
					}
				}
				if (line.startsWith("#EXTINF")) {
					String error = "Line: " + lineCounter + " MAJOR: Invalid Tag: Media tag"
							+ " #EXTINF does not belong in MASTER playlist.";
					er.list.add(error);
				}
				ArrayList<Playlist> v = new ArrayList<Playlist>();
				v = p.getVariants();
				System.out.println("		Variants " + v.size());
				for (Playlist variant : v) {
//					variant.getUrlContent();
					check(variant);	
				}
			}			
		}
		if (p.type.equals("Simple")) {
//			#EXTINF
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				lineCounter = lineCounter + 1;
				if (line.startsWith("#EXT-X-VERSION")) {
					String v = line.substring(line.lastIndexOf(":") + 1).trim();
					version = Integer.parseInt(v);
				}					
				if (line.startsWith("#EXTINF")) {
					num = line.substring(line.lastIndexOf(":") + 1).trim();
					num = num.substring(0, num.length()-1);		
					try {
					d = Double.parseDouble(num);
					} catch (NumberFormatException nfe) {
						er.list.add("Line: " + lineCounter + " MAJOR: EXTINF value is not a double");						
					}
					line = scanner.nextLine();
					lineCounter = lineCounter + 1;
					if (!(line.endsWith(".ts")) && !(line.endsWith(".aac"))) {
						er.list.add("Line: " + lineCounter + " MAJOR: No media URI found.");
					}
				}
			}
//			if (version == 0) {
//				er.list.add("MINOR: No EXT-X-VERSION tag found.");
//			}
		}
//		System.out.println("EXT-X-VERSION: " + version);
		if (p.type.equals("Simple")) {
			if (er.list.size() > 0) {
				for (String err : er.list) {
					System.out.println(err);
				}
			} else {
				System.out.println("No errors found.");
			}
		}
		scanner.close();
		logger.info("<<check()");		
	}
}
