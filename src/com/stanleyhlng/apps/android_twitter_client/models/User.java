package com.stanleyhlng.apps.android_twitter_client.models;

import java.io.Serializable;

import org.json.JSONObject;

public class User extends BaseModel {

	public String getName() {
		return getString("name");
	}
	
	public long getId() {
		return getLong("id");
	}
	
	public String getScreenName() {
		return getString("screen_name");
	}
	
	public String getProfileImageurl() {
		return getString("profile_image_url");
	}
	
	public String getProfileBackgroundImageUrl() {
		return getString("profile_background_image_url");
	}
	
	public int getNumTweets() {
		return getInt("statuses_count");
	}
	
	public int getFollowersCount() {
		return getInt("followers_count");
	}
	
	public int getFriendsCount() {
		return getInt("friends_count");
	}
	
	public static User fromJson(JSONObject jsonObject) {
		User user = new User();
		
		try {
			user.jsonObject = jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return user;
	}
}
