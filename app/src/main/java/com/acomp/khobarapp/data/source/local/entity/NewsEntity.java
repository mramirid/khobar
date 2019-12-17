package com.acomp.khobarapp.data.source.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news")
public class NewsEntity {

	// Penentu tipe berita (headline news / regular news)
	public static final String TYPE_HEADLINE_NEWS = "Headline News";
	public static final String TYPE_REGULAR_NEWS = "Regular News";

	@ColumnInfo(name = "news_type")
	private String newsType;

	@ColumnInfo(name = "title")
	private String title;

	@ColumnInfo(name = "description")
	private String description;

	@ColumnInfo(name = "source")
	private String source;

	@NonNull
	@PrimaryKey
	@ColumnInfo(name = "url_article")
	private String urlArticle;

	@ColumnInfo(name = "url_image")
	private String urlToImage;

	@ColumnInfo(name = "time_stamp")
	private String timeStamp;

	@ColumnInfo(name = "content")
	private String content;

	public NewsEntity(String newsType, String title, String description, String source,
					  @NonNull String urlArticle, String urlToImage, String timeStamp, String content) {
		this.newsType = newsType;
		this.title = title;
		this.description = description;
		this.source = source;
		this.urlArticle = urlArticle;
		this.urlToImage = urlToImage;
		this.timeStamp = timeStamp;
		this.content = content;
	}

	public String getNewsType() {
		return newsType;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@NonNull
	public String getUrlArticle() {
		return urlArticle;
	}

	public String getUrlToImage() {
		return urlToImage;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public String getContent() {
		return content;
	}
}
