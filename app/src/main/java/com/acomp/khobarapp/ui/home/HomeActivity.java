package com.acomp.khobarapp.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.acomp.khobarapp.R;
import com.acomp.khobarapp.ui.adapter.HeadlineNewsAdapter;
import com.acomp.khobarapp.ui.adapter.RegularNewsAdapter;
import com.acomp.khobarapp.utils.SpacesItemDecoration;
import com.acomp.khobarapp.viewmodel.ViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

public class HomeActivity extends AppCompatActivity {

	private HomeViewModel homeViewModel;
	private ProgressBar progressBar;

	private int recyclerViewsLoadedCount = 0;

	private boolean doubleBackPressed = false;

	@Override
	public void onBackPressed() {

		if(doubleBackPressed) {
			super.onBackPressed();
			return;
		} else {
			doubleBackPressed = true;
			final ConstraintLayout constraintLayout = findViewById(R.id.acivity_home);
			Snackbar.make(constraintLayout, getString(R.string.tekan_lagi), Snackbar.LENGTH_SHORT).show();

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					doubleBackPressed = false;
				}
			}, 2000);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		if (getSupportActionBar() != null)
			getSupportActionBar().setTitle("");

		RecyclerView rvHeadlineNews = findViewById(R.id.rv_headline);
		RecyclerView rvRegularNews = findViewById(R.id.rv_regular);
		progressBar = findViewById(R.id.progress_bar);
		progressBar.setVisibility(View.VISIBLE);

		HeadlineNewsAdapter headlineNewsAdapter = new HeadlineNewsAdapter(this);
		RegularNewsAdapter regularNewsAdapter = new RegularNewsAdapter(this);
		homeViewModel = obtainViewModel(this);

		homeViewModel.fetch(false);

		homeViewModel.headlineNewsList.observe(this, listResource -> {
			if (listResource != null) {
				switch (listResource.status) {
					case LOADING:
						break;
					case SUCCESS:
						Log.d(this.getClass().getSimpleName(), "onCreate: HEADLINE DONE");
						countLoadedOrFailNews();
						headlineNewsAdapter.setHeadlineNews(listResource.data);
						break;
					case ERROR:
						countLoadedOrFailNews();
						Toast.makeText(this, listResource.message, Toast.LENGTH_SHORT).show();
						break;
				}
			}
		});

		homeViewModel.regularNewsList.observe(this, listResource -> {
			if (listResource != null) {
				switch (listResource.status) {
					case LOADING:
						break;
					case SUCCESS:
						Log.d(this.getClass().getSimpleName(), "onCreate: REGULAR DONE");
						countLoadedOrFailNews();
						regularNewsAdapter.setRegularNews(listResource.data);
						break;
					case ERROR:
						countLoadedOrFailNews();
						Toast.makeText(this, listResource.message, Toast.LENGTH_SHORT).show();
						break;
				}
			}
		});

		rvHeadlineNews.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
		rvHeadlineNews.setHasFixedSize(true);
		rvHeadlineNews.setAdapter(headlineNewsAdapter);

		int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.rv_item_margin);
		rvRegularNews.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
		rvRegularNews.setLayoutManager(new LinearLayoutManager(getBaseContext()));
		rvRegularNews.setHasFixedSize(true);
		rvRegularNews.setAdapter(regularNewsAdapter);
	}

	private HomeViewModel obtainViewModel(AppCompatActivity activity) {
		ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
		return ViewModelProviders.of(activity, factory).get(HomeViewModel.class);
	}

	private void countLoadedOrFailNews() {
		// Progress bar akan dihilangkan dari layar apabila kedua recyclerview telah diload
		if (++recyclerViewsLoadedCount == 2)
			progressBar.setVisibility(View.GONE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.action_fetch) {
			recyclerViewsLoadedCount -= 2;
			homeViewModel.fetch(true);
		}
		return super.onOptionsItemSelected(item);
	}
}
