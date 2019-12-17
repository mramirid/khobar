package com.acomp.khobarapp.data.source.local;

import androidx.lifecycle.LiveData;

import com.acomp.khobarapp.data.source.local.entity.NewsEntity;
import com.acomp.khobarapp.data.source.local.room.NewsDao;

import java.util.List;

public class LocalRepository {

	private final NewsDao newsDao;

	private static LocalRepository INSTANCE;

	private LocalRepository(NewsDao newsDao) {
		this.newsDao = newsDao;
	}

	public static LocalRepository getInstance(NewsDao newsDao) {
		if (INSTANCE == null)
			INSTANCE = new LocalRepository(newsDao);
		return INSTANCE;
	}

	public LiveData<List<NewsEntity>> getNewsList(String newsType) {
		return newsDao.getNewsList(newsType);
	}

	public LiveData<NewsEntity> getNews(String urlArticle) {
		return newsDao.getNews(urlArticle);
	}

	public void insertNewsList(List<NewsEntity> newslist) {
		newsDao.insertNewsList(newslist);
	}

	public void clearNews(String newsType) {
		newsDao.nukeTable(newsType);
	}
}
