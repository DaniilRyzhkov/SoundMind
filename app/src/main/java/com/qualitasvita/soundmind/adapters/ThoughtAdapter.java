package com.qualitasvita.soundmind.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.qualitasvita.soundmind.Answer;
import com.qualitasvita.soundmind.R;

import java.util.List;

/**
 * Вспомогательный класс ThoughtActivity.
 * Отвечает за отображение и изменение списка мыслей
 */
public class ThoughtAdapter extends ArrayAdapter<Answer> {

    private LayoutInflater inflater;
    private int resource;
    private List<Answer> thoughts;

    public ThoughtAdapter(@NonNull Context context, int resource, @NonNull List<Answer> thoughts) {
        super(context, resource, thoughts);
        this.inflater = LayoutInflater.from(context);
        this.resource = resource;
        this.thoughts = thoughts;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.resource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Answer thought = thoughts.get(position);
        String mn = getContext().getResources().getString(R.string.thought) + " " + (position + 1) + ":";
        String str = (thought.getLevel()) + " %";
        viewHolder.tvThoughtLevel.setText(str);
        viewHolder.sbThoughtLevel.setProgress(thought.getLevel() * 10);
        viewHolder.etText.setText(thought.getText());
        viewHolder.tvMindNum.setText(mn);
        setActivateColor(thought, viewHolder);
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
                thought.setLevel(seekBar.getProgress() / 10);
                thought.setText(viewHolder.etText.getText().toString());
                if (thoughts.size() < position + 2 && !thought.getText().equals("") && thought.getLevel() > 0)
                    thoughts.add(new Answer());
                setActivateColor(thought, viewHolder);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private class ViewHolder {
        final EditText etText;
        final TextView tvThoughtLevel, tvMindNum;
        final SeekBar sbThoughtLevel;

        private ViewHolder(View view) {
            etText = view.findViewById(R.id.thoughtText);
            tvThoughtLevel = view.findViewById(R.id.thought_level_view);
            sbThoughtLevel = view.findViewById(R.id.sbThoughtLevel);
            tvMindNum = view.findViewById(R.id.mindNum);
        }
    }

    private void setActivateColor(Answer answer, ViewHolder viewHolder) {
        String str = viewHolder.etText.getText().toString();
        if (answer.getLevel() > 0 && !(str.equals(""))) {
            viewHolder.tvMindNum.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            viewHolder.tvThoughtLevel.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            viewHolder.etText.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryVeryDark));
        } else {
            viewHolder.tvMindNum.setTextColor(ContextCompat.getColor(getContext(), R.color.colorDisable));
            viewHolder.tvThoughtLevel.setTextColor(ContextCompat.getColor(getContext(), R.color.colorDisable));
            viewHolder.etText.setTextColor(ContextCompat.getColor(getContext(), R.color.colorDisable));
        }
    }

}
