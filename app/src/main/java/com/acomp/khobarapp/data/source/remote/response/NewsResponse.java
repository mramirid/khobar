package com.acomp.khobarapp.data.source.remote.response;

/*
*
* Kelas ini untuk objek penampung data-data hasil request API
*
* */
public class NewsResponse {

	private String newsType, title, description, source, urlArticle, urlToImage, timeStamp, content;

	public NewsResponse(String newsType, String title, String description, String source, String urlArticle, String urlToImage, String timeStamp, String content) {
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

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUrlArticle() {
		return urlArticle;
	}

	public void setUrlArticle(String urlArticle) {
		this.urlArticle = urlArticle;
	}

	public String getUrlToImage() {
		return urlToImage;
	}

	public void setUrlToImage(String urlToImage) {
		this.urlToImage = urlToImage;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
