package com.arthurspirke.vkontakteapp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import com.arthurspirke.vkontakteapp.service.RequestParams;

public class Utils {

	public static final String BASE_URL = "https://api.vk.com/method/";
	
	
	public static String convertStreamToString(InputStream in){
		InputStreamReader reader = new InputStreamReader(in);
		StringWriter writer = new StringWriter();
		char[] buffer = new char[1024];
		
		try{
			int n = 0;
			while((n = reader.read(buffer)) != -1){
				writer.write(buffer, 0, n);
			}
		} catch(IOException ex){
			ex.printStackTrace();
			return "";
		} finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return writer.toString();
		
	}
	
	public static String getSignedUrl(RequestParams params, String accessToken, String apiId){
        
		params.put("access_token", accessToken);
		
		if(params.contains("v")){
			params.put("v", apiId);
		}
		
		String args = params.getParamRequestString();
		
		return BASE_URL+params.getMethodName() + "?" + args;
	}
}
