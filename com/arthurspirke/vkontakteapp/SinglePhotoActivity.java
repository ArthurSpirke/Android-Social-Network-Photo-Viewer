package com.arthurspirke.vkontakteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;


public class SinglePhotoActivity extends FragmentActivity{

	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	
	private int startPosition;
	private String[] urls;
	private String albumTitle;
	private int count;
	private long albumId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_photo);
		
	    init();
	
		viewPager = (ViewPager) findViewById(R.id.pager);
		pagerAdapter = new PagerScreenAdapter(getSupportFragmentManager());
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(startPosition);
		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                invalidateOptionsMenu();
            }
        });
		

		
	}
	
    private void init(){
		Intent intent = getIntent();
		urls = intent.getStringArrayExtra("urls");
		albumId = intent.getLongExtra(ShowAlbums.ALBUM_ID, 0L);
		albumTitle = intent.getStringExtra(ShowAlbums.ALBUM_TITLE);
        startPosition = intent.getIntExtra(ShowAlbums.START_POSITION, 0);
		count = urls.length;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.single_photo, menu);
		
		MenuItem back = menu.add(Menu.NONE, R.id.back_to_gallery, Menu.NONE, getString(R.string.next_button_to_gallery));
		back.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		MenuItem previous = menu.add(Menu.NONE, R.id.action_previous, Menu.FIRST, "");
		previous.setIcon(R.drawable.ic_action_previous_item);
		previous.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		previous.setEnabled(viewPager.getCurrentItem() > 0);
		
		MenuItem next = menu.add(Menu.NONE, R.id.action_next, Menu.FIRST, "");
		next.setIcon(R.drawable.ic_action_next_item);
		next.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem){
		switch(menuItem.getItemId()){
		case R.id.action_previous:
			viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
			return true;
		case R.id.action_next:
			viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
			return true;
		case R.id.back_to_gallery:
			Intent intent = new Intent(this, PhotosActivity.class);
			intent.putExtra(ShowAlbums.CURRENT_POSITION, viewPager.getCurrentItem());
			intent.putExtra(ShowAlbums.ALBUM_ID, albumId);
			intent.putExtra(ShowAlbums.ALBUM_TITLE, albumTitle);
			startActivity(intent);
			return true;
		}
		
		return super.onOptionsItemSelected(menuItem);
	}

	private class PagerScreenAdapter extends FragmentStatePagerAdapter{

		public PagerScreenAdapter(android.support.v4.app.FragmentManager fm){
			super(fm);
		}
		
		@Override
		public Fragment getItem(int position) {
			return PhotosSlideFragment.create(position, urls[position]); 
		}

		@Override
		public int getCount() {
			return count;
		}
		
	}

}
