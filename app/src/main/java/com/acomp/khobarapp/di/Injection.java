package com.acomp.khobarapp.di;

import com.acomp.khobarapp.data.source.NewsRepository;
import com.acomp.khobarapp.data.source.remote.RemoteRepository;
import com.acomp.khobarapp.utils.JsonHelper;

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
