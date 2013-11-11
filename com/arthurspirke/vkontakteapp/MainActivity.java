package com.arthurspirke.vkontakteapp;

import com.arthurspirke.vkontakteapp.api.API;
import com.arthurspirke.vkontakteapp.entity.UserAccount;
import com.arthurspirke.vkontakteapp.service.CacheManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button loginButton;
	private Button logoutButton;
	private Button showAlbums;
	private MenuItem nextAlbum;
	
	private final int REQUEST_LOGIN = 1;
	
	private UserAccount account = new UserAccount();
	private API api;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initViews();
		
		account.loadUserSessionInfo(this);
		
		if(account.getAccessToken() != null){
			api = new API(account.getAccessToken(), getString(R.string.app_id), account.getUserId());
		}
		
		buttonVisibility();
		
	}

	
	private void initViews(){
		loginButton = (Button) findViewById(R.id.login_button);
		logoutButton = (Button) findViewById(R.id.logout_button);
		showAlbums = (Button) findViewById(R.id.show_albums);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		nextAlbum = menu.add(Menu.NONE, R.id.next_from_main, Menu.NONE, R.string.next_button_to_albums);
		nextAlbum.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		buttonVisibility();
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem){
		switch(menuItem.getItemId()){
		case R.id.next_from_main:
			startActivity(new Intent(this, ShowAlbums.class));
			return true;
		}
		
		return super.onOptionsItemSelected(menuItem);
	}
	
	
	public void logoutButton(View view){
         api = null;
         account.setAccessToken(null);
         account.setUserId(0);
         account.saveUserSessionInfo(this, null, 0);
         if(nextAlbum != null) nextAlbum.setEnabled(false);
         buttonVisibility();
	}
	
	public void showAlbums(View view){
		Intent intent = new Intent(this, ShowAlbums.class);
		intent.putExtra(UserAccount.ACCESS_TOKEN, account.getAccessToken());
		intent.putExtra(UserAccount.APP_ID, getString(R.string.app_id));
		intent.putExtra(UserAccount.USER_ID, account.getUserId());
		
		startActivity(intent);
		
	}
	

    public void startLoginActivity(View view) {
        Intent intent = new Intent(this, LoginAccount.class);
        startActivityForResult(intent, REQUEST_LOGIN);
    }
    
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_LOGIN) {
            if (resultCode == RESULT_OK) {
            	String accessToken = data.getStringExtra(UserAccount.ACCESS_TOKEN);
            	int userId = data.getIntExtra(UserAccount.USER_ID, 0);
            	account.setAccessToken(accessToken);
            	account.setUserId(userId);
                account.saveUserSessionInfo(this, accessToken, userId);
                api = new API(account.getAccessToken(), getString(R.string.app_id), account.getUserId());
                buttonVisibility();
            }
        }
    }
    
	private void buttonVisibility(){
		if(api != null){
			logoutButton.setVisibility(View.VISIBLE);
			showAlbums.setVisibility(View.VISIBLE);
			loginButton.setVisibility(View.GONE);
			if(nextAlbum != null) nextAlbum.setEnabled(true);
		} else {
			logoutButton.setVisibility(View.GONE);
			showAlbums.setVisibility(View.GONE);
			loginButton.setVisibility(View.VISIBLE);
			if(nextAlbum != null) nextAlbum.setEnabled(false);
		}
	}
	
	@Override
	public void onDestroy(){
		CacheManager cacheManager = CacheManager.getInstance();
		cacheManager.clearAllCaches();
		
		super.onDestroy();
	}
}
