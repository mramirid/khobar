package com.acomp.khobarapp.data.source.local.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.acomp.khobarapp.data.source.local.entity.NewsEntity;

@Database(entities = {NewsEntity.class}, version = 1, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {

	private static NewsDatabase INSTANCE;

	public abstract NewsDao newsDao();

	private static final Object lock = new Object();

	public static NewsDatabase getInstance(Context context) {
		synchronized (lock) {
			if (INSTANCE == null) {
				INSTANCE = Room.databaseBuilder(
						context.getApplicationContext(),
						NewsDatabase.class,
						"News.db"
				).build();
			}
			return INSTANCE;
		}
	}
}
