package edu.psgv.sweng861;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * creates Playlists by type	
 */
	public class PlaylistFactory {
		
		private static final Logger logger = LogManager.getLogger();	
		PlaylistManager pm;
		
		public Playlist getPlaylist(String playlistType, String url, String urlContent) {
			logger.info(">>getPlaylist()");
			if(playlistType == null){
				logger.info("<<getPlaylist()");
				return null;
			} if(playlistType.equalsIgnoreCase("simple")){
				SimpleP sp = new SimpleP();
				logger.debug("SimpleP: " + sp.getUrl());
				logger.debug("SimpleP: " + sp.getUrlContent());
				sp.setUrl(url);
				sp.setUrlContent(urlContent);
				sp.setType("Simple");
				logger.debug("SimpleP: " + sp.getUrl());
//				logger.debug("SimpleP: " + sp.getUrlContent());
				logger.info("<<getPlaylist()");
				return sp; 
			} if (playlistType.equalsIgnoreCase("master")){
				MasterP mp = new MasterP();
//				logger.debug("MasterP: " + mp.getUrl());
//				logger.debug("MasterP: " + mp.getUrlContent());				
				mp.setUrl(url);
//				System.out.println("mp.setUrl");
				mp.setUrlContent(urlContent);
//				System.out.println("urlContent");
				mp.setType("Master");
				mp.setVariants(createSimplePlaylist(url, urlContent));
				logger.debug("MasterP: " + mp.getUrl());
//				logger.debug("MasterP: " + mp.getUrlContent());						
				logger.info("<<getPlaylist()");
				return mp;
			} else if (playlistType.equalsIgnoreCase("neither")) {
				logger.info("<<getPlaylist()");
				return null;
			}
			logger.info("<<getPlaylist()");
			return null;
			}

		/*
		 * A method for the master playlist to get its variants
		 */
		public ArrayList<Playlist> createSimplePlaylist(String mpUrl, String mpContent) {
			logger.info(">>createSimplePlaylist()");
			ArrayList<Playlist> variants = new ArrayList<Playlist>();
			String vContent = "";
			String vUrl = "";
			HttpURLConnection vCon;
			pm = new PlaylistManager();
			
			// read the mpContent to find the variant urls that end in .m3u8
			Scanner scanner = new Scanner(mpContent);
			  String line = "";
			  String mFileName = "";
			  while (scanner.hasNextLine()) {
			  // process the line
				  line = scanner.nextLine();
				  if (line.endsWith(".m3u8")) {
						// for each url do the following
					  	mFileName = mpUrl.substring(mpUrl.lastIndexOf('/') + 1);
					  	vUrl = mpUrl.substring(0, (mpUrl.length() - mFileName.length()));
					  	logger.debug("vUrl: " + vUrl);
					  	logger.debug("line");
					  	vUrl = vUrl + line;
					  	logger.debug("vUrl: " + vUrl);					  	
						SimpleP sp = new SimpleP();
						logger.debug(sp.toString());
						vCon = pm.getHttpConnection(vUrl);
						logger.debug(vCon.toString());
						vContent = pm.getConnectionContents(vCon);
						sp.setUrl(vUrl);
						sp.setUrlContent(vContent);
						sp.setType("Simple");
//						System.out.println(sp.getUrlContent());
						logger.debug("sp url after setter:" + sp.url);
						variants.add(sp);
				  }
			  }
			  scanner.close();
				logger.info("<<createPlaylist()");
			return variants;
		}		
	}	