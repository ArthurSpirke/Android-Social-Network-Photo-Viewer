package com.arthurspirke.vkontakteapp;

import com.arthurspirke.vkontakteapp.entity.UserAccount;
import com.arthurspirke.vkontakteapp.service.VkontakteAuthorized;
import com.arthurspirke.vkontakteapp.settings.Settings;
import com.arthurspirke.vkontakteapp.utils.Urls;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginAccount extends Activity {

	WebView vkLoginFormWebView;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_account);
		
		vkLoginFormWebView = (WebView) findViewById(R.id.login_web_view);
		vkLoginFormWebView.getSettings().setJavaScriptEnabled(true);
		vkLoginFormWebView.clearCache(true);
		
		vkLoginFormWebView.setWebViewClient(new VkWebViewClient());

		
		String url = Urls.appAuthUrl(getString(R.string.app_id), Settings.PHOTOS);
		vkLoginFormWebView.loadUrl(url);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login_account, menu);
		return true;
	}
	
	
	public class VkWebViewClient extends WebViewClient{
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            parseUrl(url);
        }
	}
	
	private void parseUrl(String url) {
		VkontakteAuthorized auth = new VkontakteAuthorized();
		
		if(url == null){
			return;
		}
		
		
		
		if(url.startsWith("https://oauth.vk.com/blank.html")){
			if(!url.contains("error=")){
		        Intent intent=new Intent();
		        auth.parseUrl(url);
		        intent.putExtra(UserAccount.ACCESS_TOKEN, auth.getAccessToken());
		        intent.putExtra(UserAccount.USER_ID, Integer.parseInt(auth.getUserId()));
		        setResult(Activity.RESULT_OK, intent);
			}
			   finish();
		}
		
     
       }
 

}
