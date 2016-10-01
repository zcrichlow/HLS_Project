package edu.psgv.sweng861;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReadTextFile {

	private static final Logger logger = LogManager.getLogger();	
	
	protected String readTextFile(String filePath) {
		logger.info(">>readTextFile()");		
		String lines = "";
		String line = "";
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(filePath);
			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				// Don't forget to add the newline at the end.
				lines = lines + line + "\n";
			}
			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			logger.debug("Unable to open file");
			System.out.println("Unable to open file '" + filePath + "'");
			System.exit(2);
		} catch (IOException ex) {
			logger.debug("Error reading file");
			System.out.println("Error reading file '" + filePath + "'");
			System.exit(3);
		}
		logger.info("<<readTextFile()");				
		return lines;
	}

	protected void printTextFile(String lines) {
		logger.info(">>printTextFile()");
		BufferedReader bufferedReader = new BufferedReader(new StringReader(lines));
		String line;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			logger.debug("Error reading string");
			System.out.println("Error reading string");
			System.exit(4);
		}
		logger.info("<<printTextFile()");
	}
}
