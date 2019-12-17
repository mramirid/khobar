package com.acomp.khobarapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.acomp.khobarapp.data.source.NewsRepository;
import com.acomp.khobarapp.di.Injection;
import com.acomp.khobarapp.ui.detail.DetailedViewModel;
import com.acomp.khobarapp.ui.home.HomeViewModel;

/*
*
* Kelas ini dibuat agar kita bisa passing argument ke dalam HomeViewModel
*
* */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

	private static volatile ViewModelFactory INSTANCE;

	private final NewsRepository newsRepository;

	private ViewModelFactory(NewsRepository newsRepository) {
		this.newsRepository = newsRepository;
	}

	public static ViewModelFactory getInstance(Application application) {
		if (INSTANCE == null) {
			synchronized (ViewModelFactory.class) {
				if (INSTANCE == null)
					INSTANCE = new ViewModelFactory(Injection.provideRepository(application));
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
		} else if (modelClass.isAssignableFrom(DetailedViewModel.class)) {
			//noinspection unchecked
			return (T) new DetailedViewModel(newsRepository);
		} else {
			throw new IllegalArgumentException("Unknown ViewModel class named " + modelClass.getName());
		}
	}
}
