package com.qualitasvita.soundmind.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.qualitasvita.soundmind.R;
import com.qualitasvita.soundmind.presenters.Step4_ActionPresenter;
import com.qualitasvita.soundmind.views.Step4;

public class Step4_ActionFragment extends MvpAppCompatFragment implements Step4 {

    @InjectPresenter
    Step4_ActionPresenter step4_actionPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_4_action, container, false);
        final EditText editTextAction = view.findViewById(R.id.action);
        editTextAction.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String actionText = editTextAction.getText().toString();
                step4_actionPresenter.readActionText(actionText);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }
}
