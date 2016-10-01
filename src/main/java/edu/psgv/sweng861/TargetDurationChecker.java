package edu.psgv.sweng861;

import java.util.ArrayList;
import java.util.Scanner;

public class TargetDurationChecker extends PlaylistChecker {
	
	double targetDuration;
	double mediaDuration;
	
//	public void check(MasterP p) {
//		ArrayList v = new ArrayList();
//		v = p.getVariants();
//		System.out.println("Master playlist");
//	}
	
	public void check(Playlist p) {
		String num = "";
		int lineCounter = 0;
		String content = p.getUrlContent();
		Scanner scanner = new Scanner(content);
		String classType = p.getClass().toString();
		ErrorLogger er = new ErrorLogger();

		// we can use the toString() to get the tag info if need be	
		System.out.println("\n	TARGET DURATION CHECK\n");
		System.out.println("Playlist URL: " + p.getUrl());
		System.out.println("Type: " + p.getType());			
		
		if (classType.endsWith("MasterP")) {
			// check the master playist
			ArrayList<Playlist> v = new ArrayList<Playlist>();
			v = p.getVariants();
			System.out.println("			Variants: " + v.size() + "\n");
			for (Playlist variant : v) {
//				variant.getUrlContent();
				check(variant);
				System.out.println();
			}
		}	
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			lineCounter = lineCounter + 1;
			if (line.startsWith("#EXT-X-TARGETDURATION:")) {
				num = line.substring(line.lastIndexOf(":") + 1);
				targetDuration = Double.parseDouble(num);
//				System.out.println("#EXT-X-TARGETDURATION: " + (int)targetDuration);
			} 
			if (line.startsWith("#EXTINF:")) {
				num = line.substring(line.lastIndexOf(":") + 1).trim();
				num = num.substring(0, num.length()-1);
				try {
					mediaDuration = Double.parseDouble(num);					
				} catch (NumberFormatException nfe) {
					er.list.add("Line: " + lineCounter + " MAJOR: Media tag #EXTINF found in Master playlist.");
				}
			}
			// round the mediaDuration to the nearest int before comparing to the targetDuration
			int mediaRound = (int)Math.round(mediaDuration);
			if (mediaRound > targetDuration) {
				String error = "Line: " + lineCounter + " MAJOR: Media duration is greater than target duration\n" + 
				"		Media Duration: " + mediaDuration + "	Target Duration: " + targetDuration;
				er.list.add(error);
			} else {
//				System.out.println("Media durations match!");
			}
			mediaDuration = 0; // resetting to zero
		} // end read contents
		if (p.type.equals("Simple")) {
			System.out.println("Errors found: " + er.list.size());
			for (String err : er.list) {
				System.out.println(err);
				System.out.println();
			}
		}
		scanner.close();

	}
}
