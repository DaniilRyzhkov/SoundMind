package com.qualitasvita.soundmind.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.qualitasvita.soundmind.R;
import com.qualitasvita.soundmind.adapters.ThoughtAdapter;
import com.qualitasvita.soundmind.model.Answer;
import com.qualitasvita.soundmind.views.Step3;

import java.util.ArrayList;

public class Step3_ThoughtFragment extends MvpAppCompatFragment implements Step3 {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_3_thought, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        RecyclerView thoughtsList = view.findViewById(R.id.thoughtList);
        thoughtsList.setHasFixedSize(false);
        thoughtsList.setLayoutManager(linearLayoutManager);
        thoughtsList.setAdapter(new ThoughtAdapter(getThoughtsArray(), linearLayoutManager));

        return view;
    }

    private ArrayList<Answer> getThoughtsArray() {
        ArrayList<Answer> thoughts = new ArrayList<>();
        thoughts.add(new Answer("", 0));
        return thoughts;
    }
}
