package com.qualitasvita.soundmind.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;
import com.qualitasvita.soundmind.R;
import com.qualitasvita.soundmind.adapters.EmotionsAdapter;
import com.qualitasvita.soundmind.model.Answer;
import com.qualitasvita.soundmind.presenters.Step2_EmotionPresenter;
import com.qualitasvita.soundmind.views.Step2;

import java.util.ArrayList;

public class Step2_EmotionFragment extends MvpAppCompatFragment implements Step2 {

    @InjectPresenter
    Step2_EmotionPresenter step2_emotionPresenter;

    CardView cvEmotionRoot;
    TextView tvEmotionLevel;
    VerticalSeekBar sbEmotion;

    EmotionsAdapter emotionsAdapter;
    ArrayList<Answer> emotions;
    int currentPosition;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_2_emotion, container, false);

        // ArrayList of emotions
        emotions = step2_emotionPresenter.getEmotions();

        // SeekBar root layout (CardView)
        cvEmotionRoot = view.findViewById((R.id.cv_emotion_root));
        cvEmotionRoot.setVisibility(View.GONE);

        // SeekBar counter
        tvEmotionLevel = view.findViewById(R.id.tv_emotion_level);

        // SeekBar - emotion level listener
        sbEmotion = view.findViewById(R.id.sb_emotion);
        sbEmotion.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                String str = (progress / 10) + " %";
                tvEmotionLevel.setText(str);
                emotions.get(currentPosition).setLevel(progress / 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //emotionsAdapter.refreshList(emotions.get(currentPosition).getLevel(), currentPosition);
                step2_emotionPresenter.saveEmotions(emotions, currentPosition);
            }
        });

        // RecyclerView initiate - emotions list
        RecyclerView emotionsList = view.findViewById(R.id.emotionList);
        emotionsAdapter = new EmotionsAdapter();
        emotionsList.setHasFixedSize(true);
        emotionsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        emotionsList.setAdapter(emotionsAdapter);
        emotionsAdapter.setList(emotions);

        return view;
    }

    @Override
    public void showSeekBar(Integer itemPosition) {
        if (cvEmotionRoot.getVisibility() == View.GONE) {
            // Отображаем seekBar
            cvEmotionRoot.setVisibility(View.VISIBLE);
            currentPosition = itemPosition;
            sbEmotion.setProgress(emotions.get(currentPosition).getLevel() * 10);

            String str = emotions.get(currentPosition).getLevel() + " %";
            tvEmotionLevel.setText(str);
        } else {
            cvEmotionRoot.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideSeekBar() {
        cvEmotionRoot.setVisibility(View.GONE);
    }

    @Override
    public void refreshList(int position) {
        emotionsAdapter.refreshList(emotions.get(position).getLevel(), position);
    }

    @Override
    public void setList() {
        emotionsAdapter.setList(emotions);
    }

    public void showSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.toggleSoftInput(3, 3);
        }
    }
}
