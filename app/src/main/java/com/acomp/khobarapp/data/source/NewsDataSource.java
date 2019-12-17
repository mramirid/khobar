package com.acomp.khobarapp.data.source;

import androidx.lifecycle.LiveData;

import com.acomp.khobarapp.data.source.local.entity.NewsEntity;
import com.acomp.khobarapp.vo.Resource;

import java.util.List;

public interface NewsDataSource {

	LiveData<Resource<List<NewsEntity>>> getHeadlineNewsList(boolean reFetch);

	LiveData<Resource<List<NewsEntity>>> getRegularNewsList(boolean reFetch);

	LiveData<Resource<NewsEntity>> getNews(String urlArticle);
}
