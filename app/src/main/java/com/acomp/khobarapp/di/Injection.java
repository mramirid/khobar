package com.acomp.khobarapp.di;

import android.app.Application;

import com.acomp.khobarapp.data.source.NewsRepository;
import com.acomp.khobarapp.data.source.local.LocalRepository;
import com.acomp.khobarapp.data.source.local.room.NewsDatabase;
import com.acomp.khobarapp.data.source.remote.RemoteRepository;
import com.acomp.khobarapp.utils.AppExecutors;
import com.acomp.khobarapp.utils.JsonHelper;

/*
 *
 * Depedency injection untuk si repository
 * https://agung-setiawan.com/java-memahami-dependency-injection/
 *
 */
public class Injection {

	public static NewsRepository provideRepository(Application application) {
		NewsDatabase database = NewsDatabase.getInstance(application.getApplicationContext());

		LocalRepository localRepository = LocalRepository.getInstance(database.newsDao());
		RemoteRepository remoteRepository = RemoteRepository.getInstance(new JsonHelper());
		AppExecutors appExecutors = new AppExecutors();

		return NewsRepository.getInstance(remoteRepository, localRepository, appExecutors);
	}
}
