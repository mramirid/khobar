package com.acomp.khobarapp.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.acomp.khobarapp.data.source.local.entity.NewsEntity;
import com.acomp.khobarapp.data.source.NewsRepository;

public class DetailedViewModel extends ViewModel {

	private String newsUrl, newsType;
	private NewsRepository newsRepository;

	public DetailedViewModel(NewsRepository newsRepository) {
		this.newsRepository = newsRepository;
	}

	void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}

	void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	LiveData<NewsEntity> getNews() {
		return newsRepository.getNews(newsType, newsUrl);
	}
}
