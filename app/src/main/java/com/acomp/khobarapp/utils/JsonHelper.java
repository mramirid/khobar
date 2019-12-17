package com.acomp.khobarapp.utils;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.acomp.khobarapp.BuildConfig;
import com.acomp.khobarapp.data.source.remote.response.NewsResponse;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.acomp.khobarapp.data.source.local.entity.NewsEntity.TYPE_HEADLINE_NEWS;
import static com.acomp.khobarapp.data.source.local.entity.NewsEntity.TYPE_REGULAR_NEWS;

/*
 *
 * Kelas ini untuk melakukan request data ke API (hasilnya dalam bentuk JSON)
 * Lalu memparsing Json-nya dan menyimpan data-datanya ke dalam list objek POJO (ArrayList NewsResponse)
 *
 * */
public class JsonHelper {

	public LiveData<List<NewsResponse>> loadRegularNewsList() {
		MutableLiveData<List<NewsResponse>> regularNewsList = new MutableLiveData<>();
		String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=" + BuildConfig.API_KEY;

		new AsyncHttpClient().get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				List<NewsResponse> regularNewsResponses = parseJsonToArrayList(new String(responseBody), TYPE_REGULAR_NEWS);
				regularNewsList.postValue(regularNewsResponses);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				Log.e(this.getClass().getSimpleName(), "loadRegularNewsList -> onFailure: request failed");
				regularNewsList.postValue(new ArrayList<>());
			}
		});

		return regularNewsList;
	}

	public LiveData<List<NewsResponse>> loadHeadlineNewsList() {
		MutableLiveData<List<NewsResponse>> headlineNewsList = new MutableLiveData<>();
		String url = "https://newsapi.org/v2/everything?domains=wsj.com,nytimes.com&apiKey=" + BuildConfig.API_KEY;

		new AsyncHttpClient().get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				List<NewsResponse> headlineNewsResponses = parseJsonToArrayList(new String(responseBody), TYPE_HEADLINE_NEWS);
				headlineNewsList.postValue(headlineNewsResponses);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				Log.e(this.getClass().getSimpleName(), "loadHeadlineNewsList -> onFailure: request failed");
				headlineNewsList.postValue(new ArrayList<>());
			}
		});

		return headlineNewsList;
	}

	private ArrayList<NewsResponse> parseJsonToArrayList(String responseJson, String newsType) {
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
				String timeStamp = responseNews.getString("publishedAt").substring(0, 10);
				String content = responseNews.getString("content");

				newsResponses.add(new NewsResponse(newsType, title, description, source, urlArticle, urlToImage, timeStamp, content));
			}
		} catch (JSONException e) {
			Log.e(this.getClass().getSimpleName(), "parseJsonToArrayList: parsing failed");
		}

		return newsResponses;
	}
}
