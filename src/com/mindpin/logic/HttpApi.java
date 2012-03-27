package com.mindpin.logic;

import java.io.InputStream;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import com.mindpin.base.http.MindpinHttpRequest;
import com.mindpin.base.http.MindpinPostRequest;

public class HttpApi {
	
	public static final String SITE = "http://192.168.1.8:3001";
	
    // 各种路径常量
	public static final String 用户登录				= "/login";
	public static final String 用户注册                = "/signup_submit";
	
	
    // LoginActivity
    // 用户登录请求
	public static boolean user_authenticate(String email, String password) throws Exception {
		return new MindpinPostRequest<Boolean>(
		        用户登录, 
			new BasicNameValuePair("email", email),
			new BasicNameValuePair("password", password)
		){
			@Override
			public Boolean on_success(String response_text) throws Exception{
				JSONObject json = new JSONObject(response_text);
				String user_info = ((JSONObject)json.get("user")).toString();
				AccountManager.login(get_cookies(), user_info);
				return true;
			}
		}.go();
	}
	
	public static boolean user_signup(String email, String name, String password) throws Exception {
        return new MindpinPostRequest<Boolean>(
                用户注册, 
            new BasicNameValuePair("user[email]", email),
            new BasicNameValuePair("user[name]", name),
            new BasicNameValuePair("user[password]", password),
            new BasicNameValuePair("user[password_confirmation]", password)
        ){
            @Override
            public Boolean on_success(String response_text) throws Exception{
                JSONObject json = new JSONObject(response_text);
                String user_info = ((JSONObject)json.get("user")).toString();
                AccountManager.login(get_cookies(), user_info);
                return true;
            }
        }.go();
    }
	
	public static InputStream download_image(String image_url) {
		try {
			HttpGet httpget = new HttpGet(image_url);
			httpget.setHeader("User-Agent", "android");
			HttpResponse response = MindpinHttpRequest.get_httpclient_instance().execute(httpget);
			int status_code = response.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == status_code) {
				return response.getEntity().getContent();
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static class IntentException extends Exception{
		private static final long serialVersionUID = -4969746083422993611L;
	}


}
