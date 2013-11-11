package com.arthurspirke.vkontakteapp.service;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import org.json.JSONException;
import org.json.JSONObject;

import com.arthurspirke.vkontakteapp.utils.Utils;

public class RequestService {

	private final RequestParams requestParams;
	private static boolean enable_compression=true;
	private static final int ATTEMPTS = 3;
	
	public RequestService(RequestParams requestParams){
		this.requestParams = requestParams;
	}
	
	
	public JSONObject sendRequest(String accessToken, String apiId){
		String url = Utils.getSignedUrl(requestParams, accessToken, apiId);
		
		String body = "";
		
		String response = "";
			for(int i = 0; i < ATTEMPTS; ++i){
				try{
					response = sendRequestInternal(url, body);
					break;
				} catch(Exception ex){
					ex.printStackTrace();
				}			
		}
		
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
		
	}
	
	private String sendRequestInternal(String url, String body){
		HttpURLConnection connection = null;
		
		try{
			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			connection.setUseCaches(false);
			connection.setDoOutput(false);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			
			if(enable_compression){
				connection.setRequestProperty("Accept-Encoding", "gzip");
			}
			
			
			int responseCode = connection.getResponseCode();
			
			if(responseCode == -1){
				throw new Exception();
			}
			
			InputStream in = new BufferedInputStream(connection.getInputStream(), 8192);
			String encoding = connection.getHeaderField("Content-Encoding");
			if(encoding != null && encoding.equalsIgnoreCase("gzip")){
				in = new GZIPInputStream(in);
			}
			
			String response = Utils.convertStreamToString(in);
			return response;
		} catch(Exception ex){
			ex.printStackTrace();
			return null;
		} finally{
			if(connection != null){
				connection.disconnect();
			}
		}
	}
	
}
