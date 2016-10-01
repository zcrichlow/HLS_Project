package edu.psgv.sweng861;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class MasterP extends Playlist {

	private static final Logger logger = LogManager.getLogger();	

		ArrayList<Playlist> variants = new ArrayList<Playlist>();
		
		protected ArrayList<Playlist> getVariants() {
			return variants;
		}

		protected void setVariants(ArrayList<Playlist> variants) {
			this.variants = variants;
		}

		@Override
		public String toString() {
			logger.info(">>toString()");
			String str = "\n\n\n\tMaster Playlist\n";	
			str = "Playlist URL: " + this.url + "\nPlaylist Type: " + this.urlContent
					+ "\nVariants: ";
			for (Playlist variant : variants) {
				str = str + "\n" + variant.toString() + "\n";
			}
			logger.info("<<toString()");
			return str;
		}
		
	
	} 