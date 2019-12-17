package com.acomp.khobarapp.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DiskIOThreadExecutor implements Executor {

	private final Executor diskIO;

	DiskIOThreadExecutor() {
		diskIO = Executors.newSingleThreadExecutor();
	}

	@Override
	public void execute(Runnable runnable) {
		diskIO.execute(runnable);
	}
}
