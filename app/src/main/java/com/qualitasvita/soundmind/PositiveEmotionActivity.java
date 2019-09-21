package com.qualitasvita.soundmind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.qualitasvita.soundmind.adapters.PositiveAdapter;
import com.qualitasvita.soundmind.adapters.ThoughtAdapter;

import java.util.ArrayList;
import java.util.List;

public class PositiveEmotionActivity extends AppCompatActivity {

    private static List<Answer> emotions;
    RecyclerView positiveList;
    Button btnSavePositive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positive_emotion);

        setEmotionArray();

        positiveList = findViewById(R.id.positiveList);
        //PositiveAdapter positiveAdapter = new PositiveAdapter(emotions);
        positiveList.setLayoutManager(new LinearLayoutManager(PositiveEmotionActivity.this));
        positiveList.setHasFixedSize(true);
        positiveList.setAdapter(new PositiveAdapter(emotions));

        btnSavePositive = findViewById(R.id.btnSavePositive);
        btnSavePositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String positiveText = getPositiveString(emotions);
                Intent intent = new Intent();
                intent.putExtra("positive", positiveText);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    /**
     * Инициализация списка положительных эмоций
     */
    private void setEmotionArray() {
        emotions = new ArrayList<>();
        emotions.add(new Answer(getResources().getString(R.string.positive_emotion_inspiration)));
        emotions.add(new Answer(getResources().getString(R.string.positive_emotion_satisfaction)));
        emotions.add(new Answer(getResources().getString(R.string.positive_emotion_serenity)));
        emotions.add(new Answer(getResources().getString(R.string.positive_emotion_optimism)));
        emotions.add(new Answer(getResources().getString(R.string.positive_emotion_empathy)));
        emotions.add(new Answer(getResources().getString(R.string.positive_emotion_calm)));
        emotions.add(new Answer(getResources().getString(R.string.positive_emotion_hope)));
        emotions.add(new Answer(getResources().getString(R.string.positive_emotion_enthusiasm)));
        emotions.add(new Answer(getResources().getString(R.string.positive_emotion_gratitude)));
        emotions.add(new Answer(getResources().getString(R.string.positive_emotion_joy)));
        emotions.add(new Answer(getResources().getString(R.string.positive_emotion_pride)));
    }

    /**
     * Преобразование списка эмоций в строку, включив только выбранные эмоции
     * @param emotions целевой список
     * @return готовая строка
     */
    private String getPositiveString(List<Answer> emotions) {
        StringBuilder text = new StringBuilder();
        for (Answer emotion : emotions) {
            if (emotion.getLevel() > 0) {
                text.append(emotion.getText());
                text.append("\n");
            }
        }
        String str = text.toString();
        // Может стоит использовать не одну строку, а массив строк?
        if (str.length() > 0) return "\n\n" + str.substring(0, str.length() - 1);
        else return "";
    }
}
