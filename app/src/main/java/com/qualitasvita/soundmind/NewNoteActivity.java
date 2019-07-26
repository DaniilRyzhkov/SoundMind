package com.qualitasvita.soundmind;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Активити для создания новой записи.
 * На данный момент реализована система поочередного открытия 6 других активити для получения данных.
 * В дальнейшем, вероятно, они будут заменены на фрагменты.
 * После получения всех данных создается объект Note и передается обратно в MainActivity,
 * там он добавляется в список notes, список сразу сохраняется в базу данных.
 * Объект Note также из MainActivity сразу передается CompletedNoteActivity,
 * в которой он и отображается.
 */
public class NewNoteActivity extends AppCompatActivity {

    private static String situationText;
    private static String emotionText;
    private static String thoughtText;
    private static String actionText;
    private static String responseText;
    private static String resultText;

    private static ArrayList<Answer> emotions;
    private static ArrayList<Answer> thoughts;

    private static final int REQUEST_CODE_SITUATION = 1;
    private static final int REQUEST_CODE_EMOTION = 2;
    private static final int REQUEST_CODE_THOUGHT = 3;
    private static final int REQUEST_CODE_ACTION = 4;
    private static final int REQUEST_CODE_RESPONSE = 5;
    private static final int REQUEST_CODE_RESULT = 6;

    public static final String EXTRA_SITUATION_TEXT = "com.qualitasvita.soundmind.situation_text";
    public static final String EXTRA_EMOTION_TEXT = "com.qualitasvita.soundmind.emotion_text";
    public static final String EXTRA_THOUGHT_TEXT = "com.qualitasvita.soundmind.thought_text";
    public static final String EXTRA_ACTION_TEXT = "com.qualitasvita.soundmind.action_text";
    public static final String EXTRA_RESPONSE_TEXT = "com.qualitasvita.soundmind.response_text";
    public static final String EXTRA_RESULT_TEXT = "com.qualitasvita.soundmind.result_text";
    public static final String EXTRA_EMOTION_LIST = "com.qualitasvita.soundmind.emotion_list";
    public static final String EXTRA_THOUGHT_LIST = "com.qualitasvita.soundmind.thought_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
    }

    @Override
    protected void onResume() {
        super.onResume();
        createNote();
    }

    /**
     * Метод управляет созданием записи, открываю по очереди другие активити.
     * Всё работает, но реализовано не очень удачно. Тестирую.
     */
    // Может обратиться к фрагментам?
    private void createNote() {
        if (situationText == null) {
            startActivityForResult(new Intent(this, S1_SituationActivity.class), REQUEST_CODE_SITUATION);
        } else {
            if (emotionText == null) {
                startActivityForResult(new Intent(this, S2_EmotionActivity.class), REQUEST_CODE_EMOTION);
            } else {
                if (thoughtText == null) {
                    startActivityForResult(new Intent(this, S3_ThoughtActivity.class), REQUEST_CODE_THOUGHT);
                } else {
                    if (actionText == null) {
                        startActivityForResult(new Intent(this, S4_ActionActivity.class), REQUEST_CODE_ACTION);
                    } else {
                        if (responseText == null) {
                            startActivityForResult(new Intent(this, S5_ResponseActivity.class), REQUEST_CODE_RESPONSE);
                        } else {
                            if (resultText == null) {
                                Intent intent = new Intent(this, S6_ResultActivity.class);
                                intent.putExtra(EXTRA_EMOTION_LIST, emotions);
                                intent.putExtra(EXTRA_THOUGHT_LIST, thoughts);
                                startActivityForResult(intent, REQUEST_CODE_RESULT);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    situationText = data.getStringExtra(EXTRA_SITUATION_TEXT);
                } else {
                    reset();
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    emotionText = data.getStringExtra(EXTRA_EMOTION_TEXT);
                    emotions = getList(data.getSerializableExtra(EXTRA_EMOTION_LIST));
                } else {
                    reset();
                }
                break;
            case 3:
                if (resultCode == RESULT_OK) {
                    thoughtText = data.getStringExtra(EXTRA_THOUGHT_TEXT);
                    thoughts = getList(data.getSerializableExtra(EXTRA_THOUGHT_LIST));
                } else {
                    reset();
                }
                break;
            case 4:
                if (resultCode == RESULT_OK) {
                    actionText = data.getStringExtra(EXTRA_ACTION_TEXT);
                } else {
                    reset();
                }
                break;
            case 5:
                if (resultCode == RESULT_OK) {
                    responseText = data.getStringExtra(EXTRA_RESPONSE_TEXT);
                } else {
                    reset();
                }
                break;
            case 6:
                if (resultCode == RESULT_OK) {
                    resultText = data.getStringExtra(EXTRA_RESULT_TEXT);
                    // Сохранение записи:
                    Note newNote = new Note(initialDate(), situationText, emotionText, thoughtText, actionText, responseText, resultText);
                    Intent intent = new Intent();
                    intent.putExtra(Note.class.getSimpleName(), newNote);
                    setResult(RESULT_OK, intent);
                    reset();
                } else {
                    reset();
                }
                break;
        }

    }

    /**
     * Метод преобразует список объектов Answer в String
     *
     * @param list список эмоций и/или мыслей
     * @return если длинна строки !>0, то возвращяем пустую строку,
     * в остальных случаях возвращаем строку, удалив последний перенос строки (\n).
     */
    public static String toStringFromAnswer(List<Answer> list) {
        StringBuilder text = new StringBuilder();
        for (Answer answer : list) {
            if (answer.getLevel() > 0) {
                text.append(answer.getText());
                text.append(" - ");
                text.append(answer.getLevel());
                text.append("%\n");
            }
        }
        String str = text.toString();
        // Может стоит использовать не одну строку, а массив строк?
        if (str.length() > 0) return str.substring(0, str.length() - 1);
        else return "";
        // Пересмотри использование этого метода из других классов, может стоит отправлять списки?
    }

    /**
     * Метод вызывается в EmotionActivity и в ThoughtActivity.
     * Результат используется в ResultActivity
     *
     * @param list emotions или thoughts
     * @return список из элементов, чей уровень больше 0
     */
    public static ArrayList<Answer> createResultList(List<Answer> list) {
        ArrayList<Answer> result = new ArrayList<>();
        for (Answer answer : list) {
            if (answer.getLevel() > 0) {
                result.add(new Answer(answer.getText(), answer.getLevel()));
            }
        }
        return result;
    }

    /**
     * Метод используется для безопасной передачи объекта List между активити.
     * Проверяет каждый элемент списка на соответствие типу Answer.
     *
     * @param list emotions или thoughts,
     *             прошедшие метод createResultList() и отправленные в другое активити.
     * @return новый список состоящий только из элементов соответствующего типа
     */
    public static ArrayList<Answer> getList(Object list) {
        ArrayList<Answer> result = new ArrayList<>();
        if (list instanceof ArrayList) {
            for (int i = 0; i < ((ArrayList<?>) list).size(); i++) {
                Object item = ((ArrayList<?>) list).get(i);
                if (item instanceof Answer) {
                    result.add((Answer) item);
                }
            }
        }
        return result;
    }

    private String initialDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        return simpleDateFormat.format(new Date(System.currentTimeMillis()));
    }

    private void reset() {
        situationText = null;
        emotionText = null;
        thoughtText = null;
        actionText = null;
        responseText = null;
        resultText = null;
        finish();
    }
}
