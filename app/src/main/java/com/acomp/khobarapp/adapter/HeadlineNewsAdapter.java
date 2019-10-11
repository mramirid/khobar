package com.acomp.khobarapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.acomp.khobarapp.R;
import com.acomp.khobarapp.data.entity.NewsEntity;
import com.acomp.khobarapp.utils.GlideApp;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/*
*
* Adapter untuk recyclerview dari headline news
*
* */
public class HeadlineNewsAdapter extends RecyclerView.Adapter<HeadlineNewsAdapter.NewsViewHolder> {

	private final Activity activity;
	private ArrayList<NewsEntity> headlineNews = new ArrayList<>();

	public HeadlineNewsAdapter(Activity activity) {
		this.activity = activity;
	}

	public void setHeadlineNews(ArrayList<NewsEntity> headlineNews) {
		if (headlineNews != null) {
			this.headlineNews.clear();
			this.headlineNews.addAll(headlineNews);
		}
	}

	@NonNull
	@Override
	public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_headline, parent, false);
		return new NewsViewHolder(rootView);
	}

	@Override
	public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
		holder.tvTitle.setText(headlineNews.get(position).getTitle());
		holder.tvSource.setText(headlineNews.get(position).getSource());
		GlideApp.with(holder.itemView.getContext())
				.load(headlineNews.get(position).getUrlToImage())
				.apply(RequestOptions.placeholderOf(R.drawable.loading_indicator).error(R.drawable.ic_broken_image_black_24dp))
				.into(holder.imgPoster);

		// Click listener
		holder.itemView.setOnClickListener(view -> {
			// Nanti
		});
	}

	@Override
	public int getItemCount() {
		return headlineNews.size();
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
