package com.qualitasvita.soundmind.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.qualitasvita.soundmind.Answer;
import com.qualitasvita.soundmind.R;

import java.util.List;

/**
 * Вспомогательный класс EmotionActivity.
 * Отвечает за отображение и изменение списка эмоций
 */
public class EmotionAdapter extends ArrayAdapter<Answer> {

    private LayoutInflater inflater;
    private int resource;
    private List<Answer> emotions;

    public EmotionAdapter(@NonNull Context context, int resource, @NonNull List<Answer> emotions) {
        super(context, resource, emotions);
        this.inflater = LayoutInflater.from(context);
        this.resource = resource;
        this.emotions = emotions;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.resource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Answer emotion = emotions.get(position);
        viewHolder.tvEmotion.setText(emotion.getText());
        String str = (emotion.getLevel()) + " %";
        viewHolder.tvLevel.setText(str);
        viewHolder.sbEmotionLevel.setProgress(emotion.getLevel() * 10);
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
                emotion.setLevel(seekBar.getProgress() / 10);
                setActivateColor(emotion, viewHolder);
                //notifyDataSetChanged();
            }
        });

        setActivateColor(emotion, viewHolder);
        return convertView;
    }

    private class ViewHolder {
        final TextView tvEmotion, tvLevel;
        final SeekBar sbEmotionLevel;

        private ViewHolder(View view) {
            tvEmotion = view.findViewById(R.id.tvEmotion);
            tvLevel = view.findViewById(R.id.tvEmLevel);
            sbEmotionLevel = view.findViewById(R.id.sbEmotionLevel);
        }
    }

    private void setActivateColor(Answer emotion, ViewHolder viewHolder) {
        if (emotion.getLevel() > 0) {
            viewHolder.tvEmotion.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextPlain));
            viewHolder.tvLevel.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextPlain));
        } else {
            viewHolder.tvEmotion.setTextColor(ContextCompat.getColor(getContext(), R.color.colorDisable));
            viewHolder.tvLevel.setTextColor(ContextCompat.getColor(getContext(), R.color.colorDisable));
        }
    }
}