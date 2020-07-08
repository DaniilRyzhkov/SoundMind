package com.qualitasvita.soundmind.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.qualitasvita.soundmind.R;
import com.qualitasvita.soundmind.activities.MistakesActivity;
import com.qualitasvita.soundmind.presenters.Step5_ResponsePresenter;
import com.qualitasvita.soundmind.views.Step5;

public class Step5_ResponseFragment extends MvpAppCompatFragment implements Step5 {

    @InjectPresenter
    Step5_ResponsePresenter step5_responsePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_5_response, container, false);
        final EditText editTextResponse = view.findViewById(R.id.response);
        editTextResponse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String responseText = editTextResponse.getText().toString();
                step5_responsePresenter.readResponseText(responseText);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final Button btnMistakes = view.findViewById(R.id.btnOpenMistakesActivity);
        btnMistakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MistakesActivity.class));
            }
        });
        return view;
    }
}
