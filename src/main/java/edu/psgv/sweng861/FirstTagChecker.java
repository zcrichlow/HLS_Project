package edu.psgv.sweng861;

import java.util.ArrayList;
import java.util.Scanner;

public class FirstTagChecker extends PlaylistChecker {
	
	String tag;
	
	@Override
	public void check(Playlist p) {
		String content = p.getUrlContent();
		ErrorLogger er = new ErrorLogger();
		Scanner scanner = new Scanner(content);
		  tag = ""; 
		  tag = scanner.nextLine();
//		  System.out.println("tag: " + tag);
		  scanner.close();
		  if (p != null) {
				if (p.type.endsWith("Master")) {
					// check the master playist
					ArrayList<Playlist> v = new ArrayList<Playlist>();
					v = p.getVariants();
					System.out.println("			Variants: " + v.size() + "\n");
					for (Playlist variant : v) {
//						variant.getUrlContent();
						check(variant);
						System.out.println();
					}
			  }
			  System.out.println("\n	FIRST TAG CHECK\n");				
			  System.out.println("Playlist URL: " + p.getUrl());
			  System.out.println("Type: " + p.getType());			  
			  if (tag.equals("#EXTM3U")) {
//				  System.out.println("Playlist URL: " + p.getUrl());
//				  System.out.println("Type: " + p.getType());
//				  System.out.println(tag + " is a valid first tag");
//				  er.report();
//					logger.info("<<check");
			  }
			  else {
				  er.list.add("Line: 1 MAJOR: " + tag + " is not a valid tag (#EXTM3U).");
//				  er.report();
//				  logger.debug("Invalid playlist.");
//				  logger.info("<<check");
			  }
			  er.report();
		  }
	}	
}
