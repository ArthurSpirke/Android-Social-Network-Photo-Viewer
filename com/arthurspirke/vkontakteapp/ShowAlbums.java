package com.arthurspirke.vkontakteapp;

import java.util.List;

import com.arthurspirke.vkontakteapp.api.API;
import com.arthurspirke.vkontakteapp.entity.Album;
import com.arthurspirke.vkontakteapp.entity.Photo;
import com.arthurspirke.vkontakteapp.entity.UserAccount;
import com.arthurspirke.vkontakteapp.service.AlbumsAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ShowAlbums extends Activity {

	public static final String ALBUM_ID = "albumId";
	public static final String ALBUM_TITLE = "albumTitle";
	public static final String CURRENT_POSITION = "currentPosition";
	public static final String START_POSITION = "startPosition";
	
	private ListView albumsPreviewList;
	private AlbumsAdapter albumListViewAdapter;

    private List<Album> albums;   
    private String[] urls;
    private String[] titles;
    private long albumId;
    
    private API api;
    private UserAccount userAccount = new UserAccount();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_super_view);		

		init();
		
		userAccount.loadUserSessionInfo(this);
		
		if(userAccount.getAccessToken() != null){
			api = new API(userAccount.getAccessToken(), getString(R.string.app_id), userAccount.getUserId());
		} else {
			initApi();
		}
		
		showAlbumAction();
		
	}
	
	private void init(){
		Intent intent = getIntent();
		albumId = intent.getLongExtra(ALBUM_ID, 0L);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.super_view, menu);
		
		
		MenuItem mainPage = menu.add(Menu.NONE, R.id.back_to_main_page, Menu.NONE, getString(R.string.back_to_main_page));
		mainPage.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		

		MenuItem nextGal = menu.add(Menu.NONE, R.id.next_gal, Menu.NONE, getString(R.string.next_button_to_gallery));
		nextGal.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		nextGal.setEnabled(albumId != 0);
		 
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem){
		switch(menuItem.getItemId()){
		case R.id.next_gal:
			Intent intent = new Intent(this, PhotosActivity.class);
			intent.putExtra(ALBUM_ID, albumId);
			startActivity(intent);
			return true;
		case R.id.back_to_main_page:
			startActivity(new Intent(this, MainActivity.class));
			return true;
		}
		
		return super.onOptionsItemSelected(menuItem);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		
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
			albumsPreviewList = (ListView) findViewById(R.id.my_list);
			albumListViewAdapter = new AlbumsAdapter(getBaseContext(), urls, titles);
			albumsPreviewList.setAdapter(albumListViewAdapter);
			
			albumsPreviewList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {
					
					Album album = albums.get(position);
					long albumId = album.getId();
					
					Intent intent = new Intent(getBaseContext(), PhotosActivity.class);
					intent.putExtra(ALBUM_ID, albumId);
					intent.putExtra(ALBUM_TITLE, album.getTitle());
					
					Intent externalIntent = getIntent();
					intent.putExtra(UserAccount.ACCESS_TOKEN, externalIntent.getStringExtra(UserAccount.ACCESS_TOKEN));
					intent.putExtra(UserAccount.APP_ID, externalIntent.getStringExtra(UserAccount.APP_ID));
					intent.putExtra(UserAccount.USER_ID, externalIntent.getIntExtra(UserAccount.USER_ID, 0));
					
					startActivity(intent);
				}
				
			});
		}
		
	};

	private void showAlbumAction(){
		new Thread(){
			
			@Override
			public void run(){
				try{

					albums = api.getPhotoApi().getAlbums(true);
					
					urls = new String[albums.size()];
					titles = new String[albums.size()];
					
					for(int i = 0; i < albums.size(); i++){
						titles[i] = albums.get(i).getTitle();
						
						Photo photo = api.getPhotoApi().getPhoto((int)albums.get(i).getThumbId(), false, false);
						
						urls[i] = photo.getMostLargestPhotoSizeUrl();
						titles[i] = albums.get(i).getTitle();
					}
					
					runOnUiThread(runnable);
				} catch(Exception ex){
					ex.printStackTrace();
				}
			}
			
		}.start();
	}
	

}
