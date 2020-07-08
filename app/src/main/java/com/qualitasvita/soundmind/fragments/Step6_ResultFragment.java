package com.qualitasvita.soundmind.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.qualitasvita.soundmind.R;
import com.qualitasvita.soundmind.adapters.ResultAdapter;
import com.qualitasvita.soundmind.model.Answer;
import com.qualitasvita.soundmind.presenters.Step6_ResultPresenter;
import com.qualitasvita.soundmind.views.Step6;

import java.util.ArrayList;

public class Step6_ResultFragment extends MvpAppCompatFragment implements Step6 {

    @InjectPresenter
    Step6_ResultPresenter step6_resultPresenter;
    private static ArrayList<Answer> results;
    ResultAdapter resultAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_6_result, container, false);
        results = step6_resultPresenter.getResults();



        // List initiate
        RecyclerView resultList = view.findViewById(R.id.resultList);
        resultAdapter = new ResultAdapter();
        resultList.setHasFixedSize(true);
        resultList.setLayoutManager(new LinearLayoutManager(getActivity()));
        resultList.setAdapter(resultAdapter);
        resultAdapter.setList(results);

        // SaveButton listener
        Button btnSave = view.findViewById(R.id.btnSaveNote);
        btnSave.setOnClickListener(view1 -> step6_resultPresenter.saveNote());

        // PositiveButton listener
        Button btnPositive = view.findViewById(R.id.btnAddPositiveEmotion);

        return view;
    }

    @Override
    public void setList(ArrayList<Answer> list) {
        resultAdapter.refreshList(list);
    }
}
