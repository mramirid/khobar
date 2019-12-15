package com.acomp.khobarapp.data.source.local.room;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.acomp.khobarapp.data.source.local.entity.NewsEntity;

import java.util.List;

@Dao
public interface NewsDao {

	@WorkerThread
	@Query("SELECT * FROM news WHERE news_type = :newsType")
	LiveData<List<NewsEntity>> getNews(String newsType);
}
