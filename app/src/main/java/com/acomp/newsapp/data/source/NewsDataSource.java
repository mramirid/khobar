package com.acomp.newsapp.data.source;

import androidx.lifecycle.LiveData;

import com.acomp.newsapp.data.entity.NewsEntity;

import java.util.ArrayList;

public interface NewsDataSource {
	LiveData<ArrayList<NewsEntity>> getHeadlineNews();
	LiveData<ArrayList<NewsEntity>> getRegularNews();
	LiveData<NewsEntity> getNews(String newsType, String url);
}
