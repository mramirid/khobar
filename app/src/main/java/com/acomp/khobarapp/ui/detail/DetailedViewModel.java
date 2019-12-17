package com.acomp.khobarapp.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.acomp.khobarapp.data.source.local.entity.NewsEntity;
import com.acomp.khobarapp.data.source.NewsRepository;
import com.acomp.khobarapp.vo.Resource;

public class DetailedViewModel extends ViewModel {

	private MutableLiveData<String> newsUrl = new MutableLiveData<>();

	private NewsRepository newsRepository;

	public DetailedViewModel(NewsRepository newsRepository) {
		this.newsRepository = newsRepository;
	}

	LiveData<Resource<NewsEntity>> news = Transformations.switchMap(
			newsUrl, input -> newsRepository.getNews(input)
	);

	void setNewsUrl(String newsUrl) {
		this.newsUrl.setValue(newsUrl);
	}
}
