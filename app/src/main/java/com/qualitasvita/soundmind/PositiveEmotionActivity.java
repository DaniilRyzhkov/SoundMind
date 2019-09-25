package com.qualitasvita.soundmind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;

public class PositiveEmotionActivity extends AppCompatActivity {

    private static ArrayList<Answer> emotions;
    Button btnSavePositive;
    CheckBox checkBox_0;
    CheckBox checkBox_1;
    CheckBox checkBox_2;
    CheckBox checkBox_3;
    CheckBox checkBox_4;
    CheckBox checkBox_5;
    CheckBox checkBox_6;
    CheckBox checkBox_7;
    CheckBox checkBox_8;

    final private int numCheckBox_0 = 0;
    final private int numCheckBox_1 = 1;
    final private int numCheckBox_2 = 2;
    final private int numCheckBox_3 = 3;
    final private int numCheckBox_4 = 4;
    final private int numCheckBox_5 = 5;
    final private int numCheckBox_6 = 6;
    final private int numCheckBox_7 = 7;
    final private int numCheckBox_8 = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positive_emotion);

        emotions = NewNoteActivity.getList(getIntent().getSerializableExtra(S6_ResultActivity.EXTRA_POSITIVE));

        btnSavePositive = findViewById(R.id.btnSavePositive);
        btnSavePositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(S6_ResultActivity.EXTRA_POSITIVE, emotions);
                setResult(RESULT_OK, intent);
                Toast.makeText(PositiveEmotionActivity.this, R.string.toast_changes_saved, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        checkBox_0 = findViewById(R.id.cbPositive_0);
        if (emotions.get(numCheckBox_0).getLevel() > 0) checkBox_0.setChecked(true);
        checkBox_0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    emotions.get(numCheckBox_0).setLevel(1);
                } else {
                    emotions.get(numCheckBox_0).setLevel(0);
                }
            }
        });

        checkBox_1 = findViewById(R.id.cbPositive_1);
        if (emotions.get(numCheckBox_1).getLevel() > 0) checkBox_1.setChecked(true);
        checkBox_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    emotions.get(numCheckBox_1).setLevel(1);
                } else {
                    emotions.get(numCheckBox_1).setLevel(0);
                }
            }
        });

        checkBox_2 = findViewById(R.id.cbPositive_2);
        if (emotions.get(numCheckBox_2).getLevel() > 0) checkBox_2.setChecked(true);
        checkBox_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    emotions.get(numCheckBox_2).setLevel(1);
                } else {
                    emotions.get(numCheckBox_2).setLevel(0);
                }
            }
        });

        checkBox_3 = findViewById(R.id.cbPositive_3);
        if (emotions.get(numCheckBox_3).getLevel() > 0) checkBox_3.setChecked(true);
        checkBox_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    emotions.get(numCheckBox_3).setLevel(1);
                } else {
                    emotions.get(numCheckBox_3).setLevel(0);
                }
            }
        });

        checkBox_4 = findViewById(R.id.cbPositive_4);
        if (emotions.get(numCheckBox_4).getLevel() > 0) checkBox_4.setChecked(true);
        checkBox_4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    emotions.get(numCheckBox_4).setLevel(1);
                } else {
                    emotions.get(numCheckBox_4).setLevel(0);
                }
            }
        });

        checkBox_5 = findViewById(R.id.cbPositive_5);
        if (emotions.get(numCheckBox_5).getLevel() > 0) checkBox_5.setChecked(true);
        checkBox_5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    emotions.get(numCheckBox_5).setLevel(1);
                } else {
                    emotions.get(numCheckBox_5).setLevel(0);
                }
            }
        });

        checkBox_6 = findViewById(R.id.cbPositive_6);
        if (emotions.get(numCheckBox_6).getLevel() > 0) checkBox_6.setChecked(true);
        checkBox_6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    emotions.get(numCheckBox_6).setLevel(1);
                } else {
                    emotions.get(numCheckBox_6).setLevel(0);
                }
            }
        });

        checkBox_7 = findViewById(R.id.cbPositive_7);
        if (emotions.get(numCheckBox_7).getLevel() > 0) checkBox_7.setChecked(true);
        checkBox_7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    emotions.get(numCheckBox_7).setLevel(1);
                } else {
                    emotions.get(numCheckBox_7).setLevel(0);
                }
            }
        });

        checkBox_8 = findViewById(R.id.cbPositive_8);
        if (emotions.get(numCheckBox_8).getLevel() > 0) checkBox_8.setChecked(true);
        checkBox_8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    emotions.get(numCheckBox_8).setLevel(1);
                } else {
                    emotions.get(numCheckBox_8).setLevel(0);
                }
            }
        });

    }
}
