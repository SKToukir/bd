package com.vumobile.shaboxbuddy;

public class SingleNodeXMLParser {
	
	public String parsing(String mainString,String startTag, String endTag, int stringSubstring){
		
		int dotpositionStart = mainString.lastIndexOf(startTag);
		int dotpositionEnd = mainString.lastIndexOf(endTag);		
		String parsedString = mainString.substring(dotpositionStart,dotpositionEnd);
		parsedString = parsedString.substring(stringSubstring);
		return parsedString;
	}

}
