package com.acomp.khobarapp.data.source.local.room;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.acomp.khobarapp.data.source.local.entity.NewsEntity;

import java.util.List;

@Dao
public interface NewsDao {

	@WorkerThread
	@Query("SELECT * FROM news WHERE news_type = :newsType")
	LiveData<List<NewsEntity>> getNewsList(String newsType);

	@Query("SELECT * FROM news WHERE url_article = :urlArticle")
	LiveData<NewsEntity> getNews(String urlArticle);

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	void insertNewsList(List<NewsEntity> newsList);

	@Query("DELETE FROM news WHERE news_type = :newsType")
	void nukeTable(String newsType);
}
