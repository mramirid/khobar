package com.acomp.newsapp.di;

import com.acomp.newsapp.data.source.NewsRepository;
import com.acomp.newsapp.data.source.remote.RemoteRepository;
import com.acomp.newsapp.utils.JsonHelper;

/*
 *
 * Depedency injection untuk si repository
 * https://agung-setiawan.com/java-memahami-dependency-injection/
 *
 */
public class Injection {

	public static NewsRepository provideRepository() {
		RemoteRepository remoteRepository = RemoteRepository.getInstance(new JsonHelper());
		return NewsRepository.getInstance(remoteRepository);
	}
}
