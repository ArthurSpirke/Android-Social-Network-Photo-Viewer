package com.arthurspirke.vkontakteapp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseUtils {

	public static String parseByPattern(String fullUrl, String pattern){
	    Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(fullUrl);
        
        if (!m.find()) return null;
        
        return m.toMatchResult().group(1);
	}
	
}
