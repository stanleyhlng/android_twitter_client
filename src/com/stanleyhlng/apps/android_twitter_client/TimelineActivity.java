package com.stanleyhlng.apps.android_twitter_client;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.stanleyhlng.apps.android_twitter_client.models.Tweet;
import com.stanleyhlng.apps.android_twitter_client.models.User;

public class TimelineActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		TwitterRestClientApp.getRestClient().getCredentials(new JsonHttpResponseHandler() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public void onSuccess(JSONObject jsonCredentials) {
				User user = User.fromJson(jsonCredentials);
				
				Log.d("DEBUG", user.getScreenName());
				Log.d("DEBUG", user.getProfileImageurl());
				
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {		
					// title
					getActionBar().setTitle(String.format("@%s", user.getScreenName()));
				
					// icon
					ImageLoader.getInstance().loadImage(user.getProfileImageurl(), new SimpleImageLoadingListener() {

						@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
						@Override
						public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
							getActionBar().setIcon(new BitmapDrawable(getResources(), loadedImage));
						}
						
					});
				}
				
				Log.d("DEBUG", jsonCredentials.toString());
			}

		});
		
		TwitterRestClientApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
			
				ListView lvTweets = (ListView) findViewById(R.id.lvTweets);
				TweetsAdapter adapter = (TweetsAdapter) new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);
				
				//Log.d("DEBUG", jsonTweets.toString());
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

}
