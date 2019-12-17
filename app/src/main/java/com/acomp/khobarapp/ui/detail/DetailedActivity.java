package com.acomp.khobarapp.ui.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.acomp.khobarapp.R;
import com.acomp.khobarapp.data.source.local.entity.NewsEntity;
import com.acomp.khobarapp.utils.GlideApp;
import com.acomp.khobarapp.viewmodel.ViewModelFactory;
import com.bumptech.glide.request.RequestOptions;

public class DetailedActivity extends AppCompatActivity {

	public static final String EXTRA_NEWS_URL = "extra_news_url";
	public static final String EXTRA_NEWS_TYPE = "extra_type_type";

	private ImageView imgPoster;
	private TextView tvTitle, tvTimeStamp, tvDescription, tvContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed);

		imgPoster = findViewById(R.id.img_poster);
		tvTitle = findViewById(R.id.tv_title);
		tvTimeStamp = findViewById(R.id.tv_time_stamp);
		tvDescription = findViewById(R.id.tv_description);
		tvContent = findViewById(R.id.tv_content);
		Button btnReadMore = findViewById(R.id.btn_read_more);
		View viewLoading = findViewById(R.id.view_loading);
		Toolbar toolbar = findViewById(R.id.toolbar);

		DetailedViewModel detailedViewModel = obtainViewModel(this);

		String newsUrl = null, newsType;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			newsUrl = extras.getString(EXTRA_NEWS_URL);
			newsType = extras.getString(EXTRA_NEWS_TYPE);

			if (newsUrl != null && newsType != null) {
				toolbar.setTitle(newsType);
				detailedViewModel.setNewsUrl(newsUrl);
			}
		}

		detailedViewModel.news.observe(this, newsEntityResource -> {
			if (newsEntityResource != null) {
				switch (newsEntityResource.status) {
					case LOADING:
						viewLoading.setVisibility(View.VISIBLE);
						break;
					case SUCCESS:
						viewLoading.setVisibility(View.GONE);
						if (newsEntityResource.data != null)
							populateDetailedActivity(newsEntityResource.data);
						else
							Toast.makeText(this, "News data is empty!", Toast.LENGTH_SHORT).show();
						break;
					case ERROR:
						viewLoading.setVisibility(View.GONE);
						Toast.makeText(this, "Cannot get news data", Toast.LENGTH_SHORT).show();
						break;
				}
			}
		});

		String finalNewsUrl = newsUrl;
		btnReadMore.setOnClickListener(view -> {
			Uri linkToArticle = Uri.parse(finalNewsUrl);
			Intent goToTheLink = new Intent(Intent.ACTION_VIEW, linkToArticle);
			startActivity(goToTheLink);
		});

		setSupportActionBar(toolbar);

		if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onSupportNavigateUp() {
		finish();
		return true;
	}

	private void populateDetailedActivity(NewsEntity news) {
		GlideApp.with(getApplicationContext())
				.load(news.getUrlToImage())
				.apply(RequestOptions.placeholderOf(R.drawable.ic_broken_image_black_24dp).error(R.drawable.ic_broken_image_black_24dp))
				.into(imgPoster);
		tvTitle.setText(news.getTitle());
		tvTimeStamp.setText(news.getTimeStamp());
		tvDescription.setText(news.getDescription());
		tvContent.setText(news.getContent());
	}

	private DetailedViewModel obtainViewModel(AppCompatActivity activity) {
		ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
		return ViewModelProviders.of(activity, factory).get(DetailedViewModel.class);
	}
}
