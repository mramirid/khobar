package com.acomp.khobarapp.data.source.remote;

import com.acomp.khobarapp.data.source.remote.response.NewsResponse;
import com.acomp.khobarapp.utils.JsonHelper;

import java.util.ArrayList;

/*
 *
 * Kelas ini untuk melakukan pemanggilan ke remote data source
 *
 * */
public class RemoteRepository {

	private static RemoteRepository INSTANCE;
	private JsonHelper jsonHelper;

	private RemoteRepository(JsonHelper jsonHelper) {
		this.jsonHelper = jsonHelper;
	}

	public static RemoteRepository getInstance(JsonHelper jsonHelper) {
		if (INSTANCE == null)
			INSTANCE = new RemoteRepository(jsonHelper);
		return INSTANCE;
	}

	public void getHeadlineNews(LoadNewsCallback callback) {
		jsonHelper.loadHeadlineNews(callback);
	}

	public void getRegularNews(LoadNewsCallback callback) {
		jsonHelper.loadRegularNews(callback);
	}

	public interface LoadNewsCallback {
		void onNewsReceived(ArrayList<NewsResponse> newsResponses);
		void onDataNotAvailable();
	}
}
