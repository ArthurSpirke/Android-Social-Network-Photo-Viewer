package com.arthurspirke.vkontakteapp;

import java.util.List;

import org.json.JSONException;

import com.arthurspirke.vkontakteapp.api.API;
import com.arthurspirke.vkontakteapp.entity.Photo;
import com.arthurspirke.vkontakteapp.entity.UserAccount;
import com.arthurspirke.vkontakteapp.service.PhotosAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class PhotosActivity extends Activity {

	private GridView gridPhotosView;
	
	private API api;
	private UserAccount userAccount = new UserAccount();
	
	private long albumId;
	private String albumTitle;
	private int currentPosition;
	
	private String[] urls;
	private List<Photo> photos;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photos);

		init();
		
		userAccount.loadUserSessionInfo(this);
		
		if(userAccount.getAccessToken() != null){
			api = new API(userAccount.getAccessToken(), getString(R.string.app_id), userAccount.getUserId());
		} else {
			initApi();
		}
		
        showPhotos();
		
	}

	private void init(){
		Intent intent = getIntent();
		albumId = intent.getLongExtra(ShowAlbums.ALBUM_ID, 0L);
		albumTitle = intent.getStringExtra(ShowAlbums.ALBUM_TITLE);
		currentPosition = intent.getIntExtra(ShowAlbums.CURRENT_POSITION, 0);
		this.setTitle(albumTitle);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.photos, menu);
		
		MenuItem item = menu.add(Menu.NONE, R.id.back_to_show_albums, Menu.NONE, getString(R.string.next_button_to_albums));
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		MenuItem nextToBigPhoto = menu.add(Menu.NONE, R.id.next_to_big_photo, Menu.FIRST, getString(R.string.next_button_to_photos));
		nextToBigPhoto.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem){
		switch(menuItem.getItemId()){
		case R.id.back_to_show_albums:
		   Intent intent = new Intent(this, ShowAlbums.class);
		   intent.putExtra(ShowAlbums.ALBUM_ID, albumId);
		   NavUtils.navigateUpTo(this, intent);
		   return true;
		case R.id.next_to_big_photo:
			Intent bigPhotoIntent = new Intent(getBaseContext(), SinglePhotoActivity.class);
			bigPhotoIntent.putExtra(ShowAlbums.START_POSITION, currentPosition);
			bigPhotoIntent.putExtra("urls", urls);
			bigPhotoIntent.putExtra(ShowAlbums.ALBUM_ID, albumId);
			startActivity(bigPhotoIntent);
			return true;
		}
		
		return super.onOptionsItemSelected(menuItem);
	}
	
	private void initApi(){
		Intent intent = getIntent();
		String accessToken = intent.getStringExtra(UserAccount.ACCESS_TOKEN);
		String appId = intent.getStringExtra(UserAccount.APP_ID);
		int userId = intent.getIntExtra(UserAccount.USER_ID, 0);
		
		userAccount.saveUserSessionInfo(this, accessToken, userId);
		
		api = new API(accessToken, appId, userId);
	}
	
	
	Runnable runnable = new Runnable(){

		@Override
		public void run() {
			gridPhotosView = (GridView) findViewById(R.id.photos_grid_views);
			PhotosAdapter adapter = new PhotosAdapter(getBaseContext(), urls);
			gridPhotosView.setAdapter(adapter);
			
			gridPhotosView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	
					Intent intent = new Intent(getBaseContext(), SinglePhotoActivity.class);
					intent.putExtra(ShowAlbums.ALBUM_TITLE, albumTitle);
					intent.putExtra(ShowAlbums.START_POSITION, position);
					intent.putExtra("urls", urls);
					intent.putExtra(ShowAlbums.ALBUM_ID, albumId);
					
					startActivity(intent);
				}
			});
		}
		
	};
	
	private void showPhotos(){
		new Thread(){
			
			@Override
			public void run(){
			   	try {
					photos = api.getPhotoApi().getPhotos(albumId, 0, 100);
					
					urls = new String[photos.size()];
					
					for(int i = 0; i < urls.length; i++){
						urls[i] = photos.get(i).getMostLargestPhotoSizeUrl();
					}
					
					runOnUiThread(runnable);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		}.start();
	}

}
