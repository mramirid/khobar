package com.acomp.khobarapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.acomp.khobarapp.data.source.NewsRepository;
import com.acomp.khobarapp.di.Injection;
import com.acomp.khobarapp.ui.home.HomeViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

	private static volatile ViewModelFactory INSTANCE;

	private final NewsRepository newsRepository;

	private ViewModelFactory(NewsRepository newsRepository) {
		this.newsRepository = newsRepository;
	}

	public static ViewModelFactory getInstance() {
		if (INSTANCE == null) {
			synchronized (ViewModelFactory.class) {
				if (INSTANCE == null)
					INSTANCE = new ViewModelFactory(Injection.provideRepository());
			}
		}
		return INSTANCE;
	}

	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		if (modelClass.isAssignableFrom(HomeViewModel.class)) {
			//noinspection unchecked
			return (T) new HomeViewModel(newsRepository);
		} else {
			throw new IllegalArgumentException("Unknown ViewModel class named " + modelClass.getName());
		}
	}
}
