package com.acomp.khobarapp.data.source;

import androidx.lifecycle.LiveData;

import com.acomp.khobarapp.data.source.local.LocalRepository;
import com.acomp.khobarapp.data.source.local.entity.NewsEntity;
import com.acomp.khobarapp.data.source.remote.ApiResponse;
import com.acomp.khobarapp.data.source.remote.RemoteRepository;
import com.acomp.khobarapp.data.source.remote.response.NewsResponse;
import com.acomp.khobarapp.utils.AppExecutors;
import com.acomp.khobarapp.vo.Resource;

import java.util.ArrayList;
import java.util.List;

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
	private final LocalRepository localRepository;
	private final AppExecutors appExecutors;

	private NewsRepository(RemoteRepository remoteRepository, LocalRepository localRepository, AppExecutors appExecutors) {
		this.remoteRepository = remoteRepository;
		this.localRepository = localRepository;
		this.appExecutors = appExecutors;
	}

	public static NewsRepository getInstance(RemoteRepository remoteRepository, LocalRepository localRepository, AppExecutors appExecutors) {
		if (INSTANCE == null) {
			synchronized (NewsRepository.class) {
				if (INSTANCE == null)
					INSTANCE = new NewsRepository(remoteRepository, localRepository, appExecutors);
			}
		}
		return INSTANCE;
	}

	@Override
	public LiveData<Resource<List<NewsEntity>>> getHeadlineNewsList(boolean reFetch) {
		return new NetworkBoundResource<List<NewsEntity>, List<NewsResponse>>(appExecutors) {
			@Override
			protected LiveData<List<NewsEntity>> loadFromDB() {
				return localRepository.getNewsList(TYPE_HEADLINE_NEWS);
			}

			@Override
			protected Boolean shouldFetch(List<NewsEntity> data) {
				return (data == null) || (data.size() == 0) || reFetch;
			}

			@Override
			protected LiveData<ApiResponse<List<NewsResponse>>> createCall() {
				return remoteRepository.getHeadlineNewsList();
			}

			@Override
			protected void saveCallResult(List<NewsResponse> data) {
				localRepository.clearNews(TYPE_HEADLINE_NEWS);

				List<NewsEntity> headlineNewsList = new ArrayList<>();

				for (NewsResponse newsResponse : data) {
					headlineNewsList.add(new NewsEntity(
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

				localRepository.insertNewsList(headlineNewsList);
			}
		}.asLiveData();
	}

	@Override
	public LiveData<Resource<List<NewsEntity>>> getRegularNewsList(boolean reFetch) {
		return new NetworkBoundResource<List<NewsEntity>, List<NewsResponse>>(appExecutors) {
			@Override
			protected LiveData<List<NewsEntity>> loadFromDB() {
				return localRepository.getNewsList(TYPE_REGULAR_NEWS);
			}

			@Override
			protected Boolean shouldFetch(List<NewsEntity> data) {
				return (data == null) || (data.size() == 0) || reFetch;
			}

			@Override
			protected LiveData<ApiResponse<List<NewsResponse>>> createCall() {
				return remoteRepository.getRegularNewsList();
			}

			@Override
			protected void saveCallResult(List<NewsResponse> data) {
				localRepository.clearNews(TYPE_REGULAR_NEWS);

				List<NewsEntity> regularNewsList = new ArrayList<>();

				for (NewsResponse newsResponse : data) {
					regularNewsList.add(new NewsEntity(
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

				localRepository.insertNewsList(regularNewsList);
			}
		}.asLiveData();
	}

	@Override
	public LiveData<Resource<NewsEntity>> getNews(String urlArticle) {
		return new NetworkBoundResource<NewsEntity, NewsResponse>(appExecutors) {
			@Override
			protected LiveData<NewsEntity> loadFromDB() {
				return localRepository.getNews(urlArticle);
			}

			@Override
			protected Boolean shouldFetch(NewsEntity data) {
				return false;
			}

			@Override
			protected LiveData<ApiResponse<NewsResponse>> createCall() {
				return null;
			}

			@Override
			protected void saveCallResult(NewsResponse data) { }
		}.asLiveData();
	}
}
