package com.qualitasvita.soundmind.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qualitasvita.soundmind.Answer;
import com.qualitasvita.soundmind.R;

import java.util.List;

public class PositiveAdapter extends RecyclerView.Adapter<PositiveAdapter.ViewHolder> {

    private List<Answer> emotions;

    public PositiveAdapter(List<Answer> emotions) {
        this.emotions = emotions;
    }

    @NonNull
    @Override
    public PositiveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_list_positive, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    emotions.get(viewHolder.getAdapterPosition()).setLevel(1);
                } else {
                    emotions.get(viewHolder.getAdapterPosition()).setLevel(0);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PositiveAdapter.ViewHolder holder, int position) {
        final Answer positive = emotions.get(position);
        holder.textView.setText(positive.getText());
        if (positive.getLevel() == 0) {
            holder.checkBox.setChecked(false);
        } else {
            holder.checkBox.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return emotions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final CheckBox checkBox;
        final TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkboxPositiveItem);
            textView = itemView.findViewById(R.id.tvPositiveItem);
        }
    }
}
