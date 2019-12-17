package com.acomp.khobarapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.acomp.khobarapp.data.source.NewsRepository;
import com.acomp.khobarapp.data.source.local.entity.NewsEntity;
import com.acomp.khobarapp.vo.Resource;

import java.util.List;

public class HomeViewModel extends ViewModel {

	private NewsRepository newsRepository;

	private MutableLiveData<Boolean> fetchNow = new MutableLiveData<>();

	public HomeViewModel(NewsRepository newsRepository) {
		this.newsRepository = newsRepository;
	}

	LiveData<Resource<List<NewsEntity>>> headlineNewsList = Transformations.switchMap(
			fetchNow, input -> newsRepository.getHeadlineNewsList(input)
	);

	LiveData<Resource<List<NewsEntity>>> regularNewsList = Transformations.switchMap(
			fetchNow, input -> newsRepository.getRegularNewsList(input)
	);

	void fetch(boolean fetchNow) {
		this.fetchNow.setValue(fetchNow);
	}
}
