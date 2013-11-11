package com.arthurspirke.vkontakteapp.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map.Entry;
import java.util.TreeMap;

public class RequestParams {


	private final String methodName;
	private final TreeMap<String, String> paramsCargo;

	public RequestParams(String methodName) {
		this.methodName = methodName;
		this.paramsCargo = new TreeMap<String, String>();
	}

	public boolean contains(String param){
		return paramsCargo.containsKey(param);
	}
	
	public void put(String paramName, String paramValue) {
		if (!notNull(paramValue))
			return;

		paramsCargo.put(paramName, paramValue);
	}

	public void put(String paramName, long paramValue) {
		if (!notNull(paramValue))
			return;

		paramsCargo.put(paramName, String.valueOf(paramValue));
	}

	public void put(String paramName, int paramValue) {
		if (!notNull(paramValue))
			return;

		paramsCargo.put(paramName, String.valueOf(paramValue));
	}

	public void put(String paramName, double paramValue) {
		if (!notNull(paramValue))
			return;

		paramsCargo.put(paramName, String.valueOf(paramValue));
	}

	public String getParamRequestString() {
		StringBuilder paramsBuilder = new StringBuilder();
        int count = 0;    
        
		try {
			for (Entry<String, String> param : paramsCargo.entrySet()) {
				paramsBuilder.append(param.getKey() + "=" + URLEncoder.encode(param.getValue(), "utf-8"));
				if(count != paramsCargo.size()){
					paramsBuilder.append("&");
				}
				
				count++;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return paramsBuilder.toString();
	}
	
	public String getMethodName(){
		return methodName;
	}

	private boolean notNull(Object value) {
		if (value != null) {
			return true;
		}

		return false;
	}
}
