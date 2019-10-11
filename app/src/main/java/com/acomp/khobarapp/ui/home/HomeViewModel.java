package com.acomp.khobarapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.acomp.khobarapp.data.entity.NewsEntity;
import com.acomp.khobarapp.data.source.NewsRepository;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

	private NewsRepository newsRepository;

	public HomeViewModel(NewsRepository newsRepository) {
		this.newsRepository = newsRepository;
	}

	LiveData<ArrayList<NewsEntity>> getHeadlineNews() {
		return newsRepository.getHeadlineNews();
	}

	LiveData<ArrayList<NewsEntity>> getRegularNews() {
		return newsRepository.getRegularNews();
	}
}
