package edu.psgv.sweng861;

import java.util.ArrayList;

public class ErrorLogger {
	public ArrayList<String> list = new ArrayList<String>();
	
	public void report() {
		if (list.size() > 0) {
			System.out.println("Errors found: " + list.size());
		} else {
			System.out.println("No errors found");
		}
		for (String err : list) {
			System.out.println(err.toString());
		}	
		
		
	}
	
}
