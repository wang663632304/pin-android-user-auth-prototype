package com.mindpin.base.http;

import java.io.UnsupportedEncodingException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPut;
import com.mindpin.logic.HttpApi;

public abstract class MindpinPutRequest<TResult> extends MindpinHttpRequest<TResult> {
	public MindpinPutRequest(final String request_path, final NameValuePair...nv_pairs) throws UnsupportedEncodingException{
		HttpEntity entity = build_entity(nv_pairs);
		this.http_uri_request = build_http_put(entity, request_path);
	}
	
	private HttpPut build_http_put(HttpEntity entity, String request_path){
		HttpPut http_post = new HttpPut(HttpApi.SITE + request_path);
		http_post.setHeader("User-Agent", "android");
		http_post.setEntity(entity);
		return http_post;
	}
}
