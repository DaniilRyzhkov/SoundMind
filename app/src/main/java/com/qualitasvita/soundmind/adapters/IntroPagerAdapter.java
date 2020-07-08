package com.qualitasvita.soundmind.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qualitasvita.soundmind.R;
import com.qualitasvita.soundmind.di.App;

import javax.inject.Inject;

/**
 * Вспомогательный класс, реализуется поддержка PagerAdapter в IntroActivity
 */
public class IntroPagerAdapter extends RecyclerView.Adapter<IntroPagerAdapter.ViewHolder> {

    @Inject
    Context context;

    private int[] layouts;

    public IntroPagerAdapter(int[] layouts) {
        this.layouts = layouts;
        App.getComponent().inject(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_1, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IntroPagerAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(layouts[position]).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return layouts.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.intro_logo);
        }
    }
}

