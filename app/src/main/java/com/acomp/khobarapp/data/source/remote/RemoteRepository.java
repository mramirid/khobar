package com.acomp.khobarapp.data.source.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.acomp.khobarapp.data.source.remote.response.NewsResponse;
import com.acomp.khobarapp.utils.JsonHelper;

import java.util.List;

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

	public LiveData<ApiResponse<List<NewsResponse>>> getHeadlineNewsList() {
		MutableLiveData<ApiResponse<List<NewsResponse>>> resultHeadlineNewsList = new MutableLiveData<>();

		Observer<List<NewsResponse>> resultHeadlineNewsListObserver = new Observer<List<NewsResponse>>() {
			@Override
			public void onChanged(List<NewsResponse> newsResponses) {
				if (newsResponses.size() != 0)
					resultHeadlineNewsList.setValue(ApiResponse.success(newsResponses));
				else
					resultHeadlineNewsList.setValue(ApiResponse.error("Headline News request failed", newsResponses));

				jsonHelper.loadHeadlineNewsList().removeObserver(this);
			}
		};

		jsonHelper.loadHeadlineNewsList().observeForever(resultHeadlineNewsListObserver);

		return resultHeadlineNewsList;
	}

	public LiveData<ApiResponse<List<NewsResponse>>> getRegularNewsList() {
		MutableLiveData<ApiResponse<List<NewsResponse>>> resultRegularNewsList = new MutableLiveData<>();

		Observer<List<NewsResponse>> resultRegularNewsListObserver = new Observer<List<NewsResponse>>() {
			@Override
			public void onChanged(List<NewsResponse> newsResponses) {
				if (newsResponses.size() != 0)
					resultRegularNewsList.setValue(ApiResponse.success(newsResponses));
				else
					resultRegularNewsList.setValue(ApiResponse.error("Regular News request failed", newsResponses));

				jsonHelper.loadRegularNewsList().removeObserver(this);
			}
		};

		jsonHelper.loadRegularNewsList().observeForever(resultRegularNewsListObserver);

		return resultRegularNewsList;
	}
}
