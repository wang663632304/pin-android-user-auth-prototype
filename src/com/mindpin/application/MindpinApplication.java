package com.mindpin.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MindpinApplication extends Application {
	public static Context context;
	public static LayoutInflater mInflater;
	public static Properties config;
	public static String site;
	public static boolean has_signup = true;

	public static View inflate(int resource, ViewGroup root, boolean attachToRoot){
		return mInflater.inflate(resource, root, attachToRoot);
	}

	public static View inflate(int resource, ViewGroup root){
		return mInflater.inflate(resource, root);
	}

	@Override
	public void onCreate() {
		context = getApplicationContext();
		mInflater = LayoutInflater.from(context);
		get_config();
		super.onCreate();
	}
	
	private void get_config() {
	    config = new Properties();
	    InputStream is;
        try {
            is = context.getAssets().open("config.properties");
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        site = config.getProperty("SITE");
        String signup = config.getProperty("SIGNUP");
        if(signup.equals("true")){
            has_signup = true;
        }else{
            has_signup = false;
        }
    }

    final public static String now_loading = "�������롭";  
	final public static String now_sending = "���ڷ��͡�";  
}
