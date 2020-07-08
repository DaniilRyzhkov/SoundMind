package com.qualitasvita.soundmind.adapters;

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
import androidx.recyclerview.widget.RecyclerView;

import com.qualitasvita.soundmind.R;
import com.qualitasvita.soundmind.di.App;
import com.qualitasvita.soundmind.interactor.NewNoteInteractor;
import com.qualitasvita.soundmind.model.Answer;

import java.util.ArrayList;

import javax.inject.Inject;


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    @Inject
    NewNoteInteractor newNoteInteractor;

    private ArrayList<Answer> results;
    String resultText;

    private final int TYPE_ITEM1 = 0; // rename this
    private final int TYPE_ITEM2 = 1; // rename this

    public ResultAdapter() {
        App.getComponent().inject(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_ITEM2) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_list_result_edit, parent, false);
            final ViewHolder viewHolder = new ViewHolder(view);
            // EditText initiate
            viewHolder.etResultText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    resultText = charSequence.toString();
                    results.get(0).setText(resultText);
                    newNoteInteractor.saveResultArray(results);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            return viewHolder;
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_list_result, parent, false);


            final ViewHolder viewHolder = new ViewHolder(view);


            viewHolder.sbEmotionLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    String str = (progress / 10) + " %";
                    viewHolder.tvLevel.setText(str);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    results.get(viewHolder.getAdapterPosition()).setLevel(seekBar.getProgress() / 10);
                    viewHolder.sbEmotionLevel.setVisibility(View.GONE);
                    newNoteInteractor.saveResultArray(results);
                }
            });

            viewHolder.cardViewRoot.setOnClickListener(view1 -> {
                if (viewHolder.sbEmotionLevel.getVisibility() == View.GONE) {
                    viewHolder.sbEmotionLevel.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.sbEmotionLevel.setVisibility(View.GONE);
                }
            });
            return viewHolder;
        }
    }

    public void setList(ArrayList<Answer> list) {
        results = list;
        notifyDataSetChanged();
    }

    public void refreshList(ArrayList<Answer> list) {
        results = list;
        resultText = results.get(0).getText();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ITEM2;
        } else {
            return TYPE_ITEM1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_ITEM1:
                final Answer answer = results.get(holder.getAdapterPosition());
                String str = (answer.getLevel()) + " %";
                holder.tvEmotion.setText(answer.getText());
                holder.tvLevel.setText(str);
                holder.sbEmotionLevel.setProgress(answer.getLevel() * 10);
                break;

            case TYPE_ITEM2:
                holder.etResultText.setText(resultText);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvEmotion, tvLevel;
        final SeekBar sbEmotionLevel;
        final CardView cardViewRoot;
        final EditText etResultText;


        private ViewHolder(View view) {
            super(view);
            tvEmotion = view.findViewById(R.id.tvEmotion_result);
            tvLevel = view.findViewById(R.id.tvEmLevel_result);
            sbEmotionLevel = view.findViewById(R.id.sbEmotionLevel_result);
            cardViewRoot = view.findViewById(R.id.item_result_root_layout);
            etResultText = itemView.findViewById(R.id.resultText);
        }
    }
}


