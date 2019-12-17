package com.acomp.khobarapp.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.acomp.khobarapp.R;
import com.acomp.khobarapp.data.source.local.entity.NewsEntity;
import com.acomp.khobarapp.ui.detail.DetailedActivity;
import com.acomp.khobarapp.utils.GlideApp;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

/*
*
* Adapter untuk recyclerview dari headline news
*
* */
public class HeadlineNewsAdapter extends RecyclerView.Adapter<HeadlineNewsAdapter.NewsViewHolder> {

	private final Activity activity;
	private List<NewsEntity> headlineNewsList = new ArrayList<>();

	public HeadlineNewsAdapter(Activity activity) {
		this.activity = activity;
	}

	public void setHeadlineNewsList(List<NewsEntity> headlineNewsList) {
		if (headlineNewsList != null) {
			this.headlineNewsList.clear();
			this.headlineNewsList.addAll(headlineNewsList);
			notifyDataSetChanged();
		}
	}

	public void clear() {
		this.headlineNewsList.clear();
		notifyDataSetChanged();
	}

	@NonNull
	@Override
	public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_headline, parent, false);
		return new NewsViewHolder(rootView);
	}

	@Override
	public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
		holder.tvTitle.setText(headlineNewsList.get(position).getTitle());
		holder.tvSource.setText(headlineNewsList.get(position).getSource());
		GlideApp.with(holder.itemView.getContext())
				.load(headlineNewsList.get(position).getUrlToImage())
				.apply(RequestOptions.placeholderOf(R.drawable.ic_broken_image_black_24dp).error(R.drawable.ic_broken_image_black_24dp))
				.into(holder.imgPoster);

		// Click listener
		holder.itemView.setOnClickListener(view -> {
			Intent moveToDetailedIntent = new Intent(activity, DetailedActivity.class);
			moveToDetailedIntent.putExtra(DetailedActivity.EXTRA_NEWS_URL, headlineNewsList.get(position).getUrlArticle());
			moveToDetailedIntent.putExtra(DetailedActivity.EXTRA_NEWS_TYPE, headlineNewsList.get(position).getNewsType());
			activity.startActivity(moveToDetailedIntent);
		});
	}

	@Override
	public int getItemCount() {
		return headlineNewsList.size();
	}

	class NewsViewHolder extends RecyclerView.ViewHolder {

		final ImageView imgPoster;
		final TextView tvTitle, tvSource;

		NewsViewHolder(@NonNull View itemView) {
			super(itemView);

			imgPoster = itemView.findViewById(R.id.img_poster);
			tvTitle = itemView.findViewById(R.id.tv_title);
			tvSource = itemView.findViewById(R.id.tv_source);
		}
	}
}
