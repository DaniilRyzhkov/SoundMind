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
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qualitasvita.soundmind.R;
import com.qualitasvita.soundmind.di.App;
import com.qualitasvita.soundmind.interactor.NewNoteInteractor;
import com.qualitasvita.soundmind.model.Answer;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Вспомогательный класс ThoughtActivity.
 * Отвечает за отображение и изменение списка мыслей
 */
public class ThoughtAdapter extends RecyclerView.Adapter<ThoughtAdapter.ViewHolder> {

    @Inject
    NewNoteInteractor newNoteInteractor;

    private ArrayList<Answer> thoughts;
    private LinearLayoutManager linearLayoutManager;
    private Context context;

    public ThoughtAdapter(ArrayList<Answer> thoughts, LinearLayoutManager linearLayoutManager) {
        this.thoughts = thoughts;
        this.linearLayoutManager = linearLayoutManager;
        App.getComponent().inject(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_list_thought, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        viewHolder.etText.requestFocus();

        // EditText TextWatcher
        viewHolder.etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                thoughts.get(viewHolder.getAdapterPosition()).setText(s.toString());
                if (viewHolder.sbThoughtLevel.getVisibility() == View.GONE) {
                    viewHolder.sbThoughtLevel.setVisibility(View.VISIBLE);
                }
                // if first: setLevel, second: setText. Check a color
                setActivateColor(viewHolder);
                // read array!
                if (thoughts.get(viewHolder.getAdapterPosition()).getLevel() > 0) {
                    newNoteInteractor.saveThoughtsArray(thoughts);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // EditText Listener
        viewHolder.etText.setOnClickListener(view1 -> {
            if (viewHolder.sbThoughtLevel.getVisibility() == View.GONE) {
                viewHolder.sbThoughtLevel.setVisibility(View.VISIBLE);
            }
        });

        // SeekBar Listener
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
                viewHolder.cardViewRoot.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                setActivateColor(viewHolder);
                thoughts.get(viewHolder.getAdapterPosition()).setLevel(seekBar.getProgress() / 10);

                //setActivateColor(viewHolder);
                // Если из EditText еще не был считан ни один символ, то есть он пуст, но SeekBar
                // был передвинут, то SeekBar НЕ будет скрыт, в противном случае юудет скрыт
                if (!thoughts.get(viewHolder.getAdapterPosition()).getText().isEmpty()) {
                    newNoteInteractor.saveThoughtsArray(thoughts);
                    viewHolder.sbThoughtLevel.setVisibility(View.GONE);
                }

                if (viewHolder.getAdapterPosition() == 0 &&
                        !thoughts.get(viewHolder.getAdapterPosition()).getText().equals("") &&
                        thoughts.get(viewHolder.getAdapterPosition()).getLevel() > 0) {
                    thoughts.add(0, new Answer("", 0));
                    notifyItemInserted(viewHolder.getAdapterPosition());
                    linearLayoutManager.scrollToPositionWithOffset(0, 0);
                }
            }
        });

        // CardView Listener
        viewHolder.cardViewRoot.setOnClickListener(view12 -> {
            if (viewHolder.sbThoughtLevel.getVisibility() == View.GONE) {
                viewHolder.sbThoughtLevel.setVisibility(View.VISIBLE);
            } else {
                viewHolder.sbThoughtLevel.setVisibility(View.GONE);
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
        setActivateColor(holder);
    }

    @Override
    public int getItemCount() {
        return thoughts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final EditText etText;
        final TextView tvThoughtLevel, tvMindNum;
        final SeekBar sbThoughtLevel;
        final CardView cardViewRoot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etText = itemView.findViewById(R.id.thoughtText);
            tvThoughtLevel = itemView.findViewById(R.id.thought_level_view);
            sbThoughtLevel = itemView.findViewById(R.id.sbThoughtLevel);
            tvMindNum = itemView.findViewById(R.id.mindNum);
            cardViewRoot = itemView.findViewById(R.id.item_thought_root_layout);
        }
    }

    private void setActivateColor(ViewHolder viewHolder) {
        String str = viewHolder.etText.getText().toString();
        if (viewHolder.sbThoughtLevel.getProgress() > 0 && !(str.equals(""))) {
            viewHolder.cardViewRoot.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryLight));
        } else {
            viewHolder.cardViewRoot.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorCardBackground));
        }
    }
}
