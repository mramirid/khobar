package com.acomp.newsapp.utils;

import android.util.Log;

import com.acomp.newsapp.BuildConfig;
import com.acomp.newsapp.data.source.remote.RemoteRepository.LoadNewsCallback;
import com.acomp.newsapp.data.source.remote.response.NewsResponse;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class JsonHelper {

	public void loadRegularNews(LoadNewsCallback loadNewsCallback) {
		String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=" + BuildConfig.API_KEY;
		new AsyncHttpClient().get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				ArrayList<NewsResponse> regularNewsResponses = parseJsonToArrayList(new String(responseBody));
				loadNewsCallback.onNewsReceived(regularNewsResponses);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				loadNewsCallback.onDataNotAvailable();
				Log.e(this.getClass().getSimpleName(), "loadRegularNews -> onFailure: request failed");
			}
		});
	}

	public void loadHeadlineNews(LoadNewsCallback loadNewsCallback) {
		String url = "https://newsapi.org/v2/everything?domains=wsj.com,nytimes.com&apiKey=" + BuildConfig.API_KEY;
		new AsyncHttpClient().get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				ArrayList<NewsResponse> headlineNewsResponses = parseJsonToArrayList(new String(responseBody));
				loadNewsCallback.onNewsReceived(headlineNewsResponses);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				loadNewsCallback.onDataNotAvailable();
				Log.e(this.getClass().getSimpleName(), "loadHeadlineNews -> onFailure: request failed");
			}
		});
	}

	private ArrayList<NewsResponse> parseJsonToArrayList(String responseJson) {
		ArrayList<NewsResponse> newsResponses = new ArrayList<>();

		try {
			JSONObject responseObject = new JSONObject(responseJson);
			JSONArray responseArray = responseObject.getJSONArray("articles");

			for (int i = 0; i < responseArray.length(); ++i) {
				JSONObject responseNews = responseArray.getJSONObject(i);

				String title = responseNews.getString("title");
				String description = responseNews.getString("description");
				String source = responseNews.getJSONObject("source").getString("name");
				String urlArticle = responseNews.getString("url");
				String urlToImage = responseNews.getString("urlToImage");
				String timeStamp = responseNews.getString("publishedAt").substring(0, 11);
				String content = responseNews.getString("content");

				newsResponses.add(new NewsResponse(title, description, source, urlArticle, urlToImage, timeStamp, content));
			}
		} catch (JSONException e) {
			Log.e(this.getClass().getSimpleName(), "parseJsonToArrayList: parsing failed");
		}

		return newsResponses;
	}
}
