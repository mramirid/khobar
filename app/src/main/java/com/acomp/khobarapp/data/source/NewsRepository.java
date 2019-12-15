package com.acomp.khobarapp.data.source;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.acomp.khobarapp.data.source.local.entity.NewsEntity;
import com.acomp.khobarapp.data.source.remote.RemoteRepository;
import com.acomp.khobarapp.data.source.remote.response.NewsResponse;

import java.util.ArrayList;

import static com.acomp.khobarapp.data.source.local.entity.NewsEntity.TYPE_HEADLINE_NEWS;
import static com.acomp.khobarapp.data.source.local.entity.NewsEntity.TYPE_REGULAR_NEWS;

/*
 *
 * Kelas ini sebagai penentu apakah data diambil dari database/API
 *
 * */
public class NewsRepository implements NewsDataSource {

	private volatile static NewsRepository INSTANCE = null;

	private final RemoteRepository remoteRepository;

	private NewsRepository(RemoteRepository remoteRepository) {
		this.remoteRepository = remoteRepository;
	}

	public static NewsRepository getInstance(RemoteRepository remoteRepository) {
		if (INSTANCE == null) {
			synchronized (NewsRepository.class) {
				if (INSTANCE == null)
					INSTANCE = new NewsRepository(remoteRepository);
			}
		}
		return INSTANCE;
	}

	@Override
	public LiveData<ArrayList<NewsEntity>> getHeadlineNews() {
		MutableLiveData<ArrayList<NewsEntity>> headlineNewsResults = new MutableLiveData<>();

		remoteRepository.getHeadlineNews(new RemoteRepository.LoadNewsCallback() {
			@Override
			public void onNewsReceived(ArrayList<NewsResponse> newsResponses) {
				ArrayList<NewsEntity> headlineNews = new ArrayList<>();

				for (NewsResponse newsResponse : newsResponses) {
					headlineNews.add(new NewsEntity(
							newsResponse.getNewsType(),
							newsResponse.getTitle(),
							newsResponse.getDescription(),
							newsResponse.getSource(),
							newsResponse.getUrlArticle(),
							newsResponse.getUrlToImage(),
							newsResponse.getTimeStamp(),
							newsResponse.getContent()
					));
				}
				headlineNewsResults.postValue(headlineNews);
			}

			@Override
			public void onDataNotAvailable() {
				Log.e(this.getClass().getSimpleName(), "getHeadlineNews -> onDataNotAvailable: request failed");
			}
		});

		return headlineNewsResults;
	}

	@Override
	public LiveData<ArrayList<NewsEntity>> getRegularNews() {
		MutableLiveData<ArrayList<NewsEntity>> regularNewsResults = new MutableLiveData<>();

		remoteRepository.getRegularNews(new RemoteRepository.LoadNewsCallback() {
			@Override
			public void onNewsReceived(ArrayList<NewsResponse> newsResponses) {
				ArrayList<NewsEntity> regularNews = new ArrayList<>();

				for (NewsResponse newsResponse : newsResponses) {
					regularNews.add(new NewsEntity(
							newsResponse.getNewsType(),
							newsResponse.getTitle(),
							newsResponse.getDescription(),
							newsResponse.getSource(),
							newsResponse.getUrlArticle(),
							newsResponse.getUrlToImage(),
							newsResponse.getTimeStamp(),
							newsResponse.getContent()
					));
				}
				regularNewsResults.postValue(regularNews);
			}

			@Override
			public void onDataNotAvailable() {
				Log.e(this.getClass().getSimpleName(), "getRegularNews -> onDataNotAvailable: request failed");
			}
		});

		return regularNewsResults;
	}

	@Override
	public LiveData<NewsEntity> getNews(String newsType, String url) {
		MutableLiveData<NewsEntity> newsResult = new MutableLiveData<>();

		if (newsType.equals(TYPE_HEADLINE_NEWS)) {
			remoteRepository.getHeadlineNews(new RemoteRepository.LoadNewsCallback() {
				@Override
				public void onNewsReceived(ArrayList<NewsResponse> newsResponses) {
					for (NewsResponse newsResponse : newsResponses) {
						if (newsResponse.getUrlArticle().equals(url)) {
							NewsEntity headlineNews = new NewsEntity(
									newsResponse.getNewsType(),
									newsResponse.getTitle(),
									newsResponse.getDescription(),
									newsResponse.getSource(),
									newsResponse.getUrlArticle(),
									newsResponse.getUrlToImage(),
									newsResponse.getTimeStamp(),
									newsResponse.getContent()
							);
							newsResult.postValue(headlineNews);
							break;
						}
					}
				}

				@Override
				public void onDataNotAvailable() {
					Log.e(this.getClass().getSimpleName(), "getHeadlineNews -> onDataNotAvailable: request failed");
				}
			});
		} else if (newsType.equals(TYPE_REGULAR_NEWS)) {
			remoteRepository.getRegularNews(new RemoteRepository.LoadNewsCallback() {
				@Override
				public void onNewsReceived(ArrayList<NewsResponse> newsResponses) {
					for (NewsResponse newsResponse : newsResponses) {
						if (newsResponse.getUrlArticle().equals(url)) {
							NewsEntity regularNews = new NewsEntity(
									newsResponse.getNewsType(),
									newsResponse.getTitle(),
									newsResponse.getDescription(),
									newsResponse.getSource(),
									newsResponse.getUrlArticle(),
									newsResponse.getUrlToImage(),
									newsResponse.getTimeStamp(),
									newsResponse.getContent()
							);
							newsResult.postValue(regularNews);
							break;
						}
					}
				}

				@Override
				public void onDataNotAvailable() {
					Log.e(this.getClass().getSimpleName(), "getHeadlineNews -> onDataNotAvailable: request failed");
				}
			});
		}

		return newsResult;
	}
}
