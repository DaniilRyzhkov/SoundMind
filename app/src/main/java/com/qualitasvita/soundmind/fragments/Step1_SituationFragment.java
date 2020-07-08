package com.qualitasvita.soundmind.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.qualitasvita.soundmind.R;
import com.qualitasvita.soundmind.presenters.Step1_SituationPresenter;
import com.qualitasvita.soundmind.views.Step1;

/**
 *
 */
public class Step1_SituationFragment extends MvpAppCompatFragment implements Step1 {

    @InjectPresenter
    Step1_SituationPresenter step1_situationPresenter;
    EditText editTextSituation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_1_situation, container, false);

        // EditText change listener
        editTextSituation = view.findViewById(R.id.situation);
        editTextSituation.findFocus();

        editTextSituation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String situationText = editTextSituation.getText().toString();
                step1_situationPresenter.saveSituationText(situationText);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /*InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        editTextSituation.postDelayed(new Runnable() {
            @Override
            public void run() {
                editTextSituation.requestFocus();
                if (imm != null) imm.showSoftInput(editTextSituation, 0);
            }
        }, 200);*/
        //showSoftKeyboard();
        return view;
    }

    @Override
    public void readSituationText(String text) {
        editTextSituation.setText(text);
    }

    public void showHelpSituation(View view) {
        // Реалиция в NewNoteActivity
    }

    public void showSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        //imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


        if (imm != null) {
            //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            imm.hideSoftInputFromWindow(editTextSituation.getWindowToken(), InputMethodManager.SHOW_IMPLICIT);

        }
    }

    /*@Override
    public void onPause() {

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        *//*if (imm != null) {
            imm.hideSoftInputFromWindow(editTextSituation.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            Toast.makeText(getActivity(), R.string.toast_changes_saved, Toast.LENGTH_LONG).show();
        }*//*

        editTextSituation.postDelayed(new Runnable() {
            @Override
            public void run() {
                editTextSituation.requestFocus();
                if (imm != null) imm.hideSoftInputFromWindow(editTextSituation.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                Toast.makeText(getActivity(), R.string.toast_changes_saved, Toast.LENGTH_LONG).show();

            }
        }, 100);

        super.onPause();
    }*/
}