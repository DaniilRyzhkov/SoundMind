package com.qualitasvita.soundmind.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qualitasvita.soundmind.Answer;
import com.qualitasvita.soundmind.R;
import com.qualitasvita.soundmind.di.App;

import java.util.List;

import javax.inject.Inject;

/**
 * Вспомогательный класс ThoughtActivity.
 * Отвечает за отображение и изменение списка мыслей
 */
public class ThoughtAdapter extends RecyclerView.Adapter<ThoughtAdapter.ViewHolder> {

    @Inject
    Context context;

    private List<Answer> thoughts;
    private LinearLayoutManager linearLayoutManager;

    public ThoughtAdapter(List<Answer> thoughts, LinearLayoutManager linearLayoutManager) {
        this.thoughts = thoughts;
        this.linearLayoutManager = linearLayoutManager;
        App.getComponent().inject(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_list_thought, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.etText.requestFocus();
        viewHolder.etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                thoughts.get(viewHolder.getAdapterPosition()).setText(s.toString());
                //setActivateColor(thoughts.get(viewHolder.getAdapterPosition()), viewHolder);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewHolder.sbThoughtLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String str = (progress / 10) + " %";
                viewHolder.tvThoughtLevel.setText(str);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                thoughts.get(viewHolder.getAdapterPosition()).setLevel(seekBar.getProgress() / 10);
                if (viewHolder.getAdapterPosition() == 0 &&
                        !thoughts.get(viewHolder.getAdapterPosition()).getText().equals("") &&
                        thoughts.get(viewHolder.getAdapterPosition()).getLevel() > 0) {
                    thoughts.add(0, new Answer());
                    notifyItemInserted(viewHolder.getAdapterPosition());
                    linearLayoutManager.scrollToPositionWithOffset(0, 0);
                }
                //setActivateColor(thoughts.get(viewHolder.getAdapterPosition()), viewHolder);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Answer thought = thoughts.get(position);
        String thoughtTitle = context.getResources().getString(R.string.thought) + " " + (thoughts.size() - position) + ":";
        String thoughtText = (thought.getLevel()) + " %";
        holder.tvThoughtLevel.setText(thoughtText);
        holder.sbThoughtLevel.setProgress(thoughts.get(holder.getAdapterPosition()).getLevel() * 10);
        holder.etText.setText(thoughts.get(holder.getAdapterPosition()).getText());
        holder.tvMindNum.setText(thoughtTitle);
        //setActivateColor(thought, holder);
    }

    @Override
    public int getItemCount() {
        return thoughts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final EditText etText;
        final TextView tvThoughtLevel, tvMindNum;
        final SeekBar sbThoughtLevel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etText = itemView.findViewById(R.id.thoughtText);
            tvThoughtLevel = itemView.findViewById(R.id.thought_level_view);
            sbThoughtLevel = itemView.findViewById(R.id.sbThoughtLevel);
            tvMindNum = itemView.findViewById(R.id.mindNum);
        }
    }

    private void setActivateColor(Answer answer, ViewHolder viewHolder) {
        String str = viewHolder.etText.getText().toString();
        if (answer.getLevel() > 0 && !(str.equals(""))) {
            viewHolder.tvMindNum.setTextColor(ContextCompat.getColor(context, R.color.colorTextPlain));
            viewHolder.tvThoughtLevel.setTextColor(ContextCompat.getColor(context, R.color.colorTextPlain));
            viewHolder.etText.setTextColor(ContextCompat.getColor(context, R.color.colorTextPlain));
        } else {
            viewHolder.tvMindNum.setTextColor(ContextCompat.getColor(context, R.color.colorDisable));
            viewHolder.tvThoughtLevel.setTextColor(ContextCompat.getColor(context, R.color.colorDisable));
            viewHolder.etText.setTextColor(ContextCompat.getColor(context, R.color.colorDisable));
        }
    }

}
