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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

/*
 *
 * Adapter untuk recyclerview dari regular news
 *
 * */
public class RegularNewsAdapter extends RecyclerView.Adapter<RegularNewsAdapter.NewsViewHolder> {

	private final Activity activity;
	private List<NewsEntity> regularNewsList = new ArrayList<>();

	public RegularNewsAdapter(Activity activity) {
		this.activity = activity;
	}

	public void setRegularNewsList(List<NewsEntity> regularNewsList) {
		if (regularNewsList != null) {
			this.regularNewsList.clear();
			this.regularNewsList.addAll(regularNewsList);
			notifyDataSetChanged();
		}
	}

	public void clear() {
		this.regularNewsList.clear();
		notifyDataSetChanged();
	}

	@NonNull
	@Override
	public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_regular, parent, false);
		return new NewsViewHolder(rootView);
	}

	@Override
	public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
		holder.tvTitle.setText(regularNewsList.get(position).getTitle());
		holder.tvSource.setText(regularNewsList.get(position).getSource());
		GlideApp.with(holder.itemView.getContext())
				.load(regularNewsList.get(position).getUrlToImage())
				.apply(RequestOptions.placeholderOf(R.drawable.ic_broken_image_black_24dp).error(R.drawable.ic_broken_image_black_24dp))
				.apply(new RequestOptions().transform(new RoundedCorners(40)))
				.into(holder.imgPoster);

		// Click listener
		holder.itemView.setOnClickListener(view -> {
			Intent moveToDetailedIntent = new Intent(activity, DetailedActivity.class);
			moveToDetailedIntent.putExtra(DetailedActivity.EXTRA_NEWS_URL, regularNewsList.get(position).getUrlArticle());
			moveToDetailedIntent.putExtra(DetailedActivity.EXTRA_NEWS_TYPE, regularNewsList.get(position).getNewsType());
			activity.startActivity(moveToDetailedIntent);
		});
	}

	@Override
	public int getItemCount() {
		return regularNewsList.size();
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
