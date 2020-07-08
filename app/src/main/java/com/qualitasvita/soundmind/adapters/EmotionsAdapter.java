package com.qualitasvita.soundmind.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.qualitasvita.soundmind.R;
import com.qualitasvita.soundmind.di.App;
import com.qualitasvita.soundmind.interactor.NewNoteInteractor;
import com.qualitasvita.soundmind.model.Answer;

import java.util.ArrayList;

import javax.inject.Inject;

public class EmotionsAdapter extends RecyclerView.Adapter<EmotionsAdapter.ViewHolder> {

    @Inject
    NewNoteInteractor newNoteInteractor;

    private ArrayList<Answer> emotions;
    private Context context;

    public EmotionsAdapter() {
        App.getComponent().inject(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_list_emotion, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();

        // List's item click listener
        viewHolder.cardViewRoot.setOnClickListener(view1 -> newNoteInteractor.itemSelected(viewHolder.getAdapterPosition()));

        return viewHolder;
    }

    public void setList(ArrayList<Answer> list){
        emotions = list;
        notifyDataSetChanged();
    }

    public void refreshList(int level, int position) {
        emotions.get(position).setLevel(level);
        notifyItemChanged(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Answer emotion = emotions.get(holder.getAdapterPosition());
        String str = (emotion.getLevel()) + " %";

        holder.tvEmotion.setText(emotion.getText());
        holder.tvLevel.setText(str);
        if (emotion.getLevel() > 0) {
            holder.tvLevel.setVisibility(View.VISIBLE);
            // Выделяем пункт цветом
            holder.cardViewRoot.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryLight));
        } else {
            holder.tvLevel.setVisibility(View.GONE);
            holder.cardViewRoot.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorCardBackground));
        }
    }

    @Override
    public int getItemCount() {
        return emotions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvEmotion, tvLevel;
        //final SeekBar sbEmotionLevel;
        final CardView cardViewRoot;

        private ViewHolder(View view) {
            super(view);
            tvEmotion = view.findViewById(R.id.tvEmotion);
            tvLevel = view.findViewById(R.id.tvEmLevel);
            cardViewRoot = view.findViewById(R.id.item_emotion_root_layout);
        }
    }

    private void setActivateColor(Answer emotion, ViewHolder viewHolder) {
        if (emotion.getLevel() > 0) {
            viewHolder.tvLevel.setTextColor(ContextCompat.getColor(context, R.color.colorTextPlain));
        } else {
            viewHolder.tvLevel.setTextColor(ContextCompat.getColor(context, R.color.colorDisable));
        }
    }
}
