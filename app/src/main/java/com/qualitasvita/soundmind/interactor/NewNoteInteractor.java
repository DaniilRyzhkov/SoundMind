package com.qualitasvita.soundmind.interactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.qualitasvita.soundmind.R;
import com.qualitasvita.soundmind.di.App;
import com.qualitasvita.soundmind.model.Answer;
import com.qualitasvita.soundmind.model.Note;
import com.qualitasvita.soundmind.repository.EmotionsDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import io.reactivex.schedulers.Schedulers;

public class NewNoteInteractor {

    @Inject
    Context context;

    @Inject
    EmotionsDatabase database;

    private String situationText = "";
    private String emotionText = "";
    private String thoughtText = "";
    private String actionText = "";
    private String responseText = "";
    private String resultText = "";
    private String resultText2 = "";
    private String resultTextPositive = "";

    private List<Answer> emotions;
    private List<Answer> thoughts = new ArrayList<>();
    private ArrayList<Answer> results;

    private Subject<Integer> emotionAdapterSubject = PublishSubject.create();
    private Subject<String> situationSubject = PublishSubject.create();
    private Subject<ArrayList<Answer>> resultsSubject = PublishSubject.create();
    private Subject<Note> noteSubject = PublishSubject.create();

    final private int RESULT_LEVEL = 101;

    public NewNoteInteractor() {
        App.getComponent().inject(this);
        //emotions = setEmotions();
        prepare();

        // перенеси инициализацию бд в другое место и избавься от этого костыля
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                reset();
            }
        }, 200);
        //setEmotions();
        //results = setResults();
        //reset();
    }

    // При первом запуске приложения также заполним базу данных "негативные эмоции" значениями
    // по умолчанию
    // Перенеси в мейн?
    private void prepare() {
        final String REF_FLAG = "default_emotions";
        final String REF_KEY = "emotions";
        SharedPreferences ref = context.getSharedPreferences(REF_KEY, Context.MODE_PRIVATE);
        if (ref.getBoolean(REF_FLAG, true)) {
            SharedPreferences.Editor editor = ref.edit();
            editor.putBoolean(REF_FLAG, false);
            editor.apply();
            setEmotionsDefault();
        }

        //setEmotionsDefault();
    }


    /**
     * Шаг 1 - Ситуация
     * Сохраняем данные
     *
     * @param text набранный текст
     */
    public void saveSituationText(String text) {
        situationText = text;
    }

    /**
     * Шаг 2 - Эмоции
     * Сохраняем данные в виде текста String для Note и в виде списка.
     * Список будет использован в шестом шаге, для переоценки.
     * Перезаписываем данные в список results, отправляем данные, используя Rx.
     *
     * @param list список эмоций
     */
    public void saveEmotionsArray(List<Answer> list) {
        emotions = list;
        emotionText = answerListToString(list);

        // result refresh
        results = setResults();
        results.addAll(createResultsList(emotions));
        results.addAll(createResultsList(thoughts));
        resultsSubject.onNext(results);
    }

    /**
     * Шаг 3 - Мысли
     * Сохраняем данные в виде текста String для Note и в виде списка.
     * Список будет использован в шестом шаге, для переоценки.
     * Перезаписываем данные в список results, отправляем данные, используя Rx.
     *
     * @param list список мыслей
     */
    public void saveThoughtsArray(ArrayList<Answer> list) {
        thoughts = invertList(list);
        thoughtText = answerListToString(thoughts);

        // result refresh
        results = setResults();
        results.addAll(createResultsList(emotions));
        results.addAll(createResultsList(thoughts));
        resultsSubject.onNext(results);
    }

    /**
     * Шаг 4 - Действие
     * Сохраняем данные
     *
     * @param text набранный текст
     */
    public void saveActionText(String text) {
        actionText = text;
    }

    /**
     * Шаг 5 - Адаптивный ответ
     * Сохраняем данные
     *
     * @param text набранный текст
     */
    public void saveResponseText(String text) {
        responseText = text;
    }

   /* *//**
     * Шаг 6 - Результат
     * Сохраняем данные - текст
     *
     * @param text набранный текст
     *//*
    // delete this
    public void saveResultText(String text) {
        resultText = text;
    }
*/
    /**
     * Шаг 6 - Результат
     * Сохраняем данные - список
     *
     * @param list набранный текст
     */
    public void saveResultArray(ArrayList<Answer> list) {
        results = list;
        resultText = results.get(0).getText();
        resultText2 = answerListToString(list);
    }

    public Subject<ArrayList<Answer>> getResultSubject() {
        return resultsSubject;
    }

    public Subject<Integer> getEmotionAdapterSubject() {
        return emotionAdapterSubject;
    }

    public Subject<Note> getNoteSubject() {
        return noteSubject;
    }

    public Subject<String> getSituationSubject() {
        return situationSubject;
    }

    public ArrayList<Answer> getResults() {
        return (ArrayList<Answer>) results;
    }

    public ArrayList<Answer> getEmotions() {
        return (ArrayList<Answer>) emotions;
    }

    public void setEmotions() {
        database.getEmotionDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Answer>>() {
                    @Override
                    public void onSuccess(List<Answer> answers) {
                        emotions = answers;
                    }

                    @Override
                    public void onError(Throwable e) {
                        // обработать
                    }
                });
    }

    public ArrayList<Answer> setResults() {
        ArrayList<Answer> results = new ArrayList<>();
        results.add(0, new Answer(resultText, RESULT_LEVEL));
        return results;
    }

    public void saveNote() {
        saveResultArray(results); // Сохранение результата, если уровень не был изменен
        resultText = resultText + "\n" + resultText2 + "\n" + resultTextPositive;
        Note note = new Note(initiateDate(), situationText, emotionText, thoughtText, actionText, responseText, resultText);
        noteSubject.onNext(note);
        reset();
    }

    /**
     * EmotionAdapter сообщает порядковый номер элемента, что был нажат.
     * В ответ Step2_EmotionFragment отобразит seekBar для определения интенсивности.
     *
     * @param position порядковый номер элемента
     */
    public void itemSelected(int position) {
        emotionAdapterSubject.onNext(position);
    }

    /**
     * Метод вызывается в EmotionActivity и в ThoughtActivity.
     * Результат используется в ResultActivity
     *
     * @param list emotions или thoughts
     * @return список из элементов, чей уровень больше 0
     */
    public static List<Answer> createResultsList(List<Answer> list) {
        List<Answer> results = new ArrayList<>();
        for (Answer answer : list) {
            if (answer.getLevel() > 0) {
                results.add(new Answer(answer.getText(), answer.getLevel()));
            }
        }
        return results;
    }

    public void reset() {
        situationText = "";
        emotionText = "";
        thoughtText = "";
        actionText = "";
        responseText = "";
        resultText = "";
        resultText2 = "";
        resultTextPositive = "";

        //emotions = setEmotions();
        setEmotions();
        thoughts = new ArrayList<>();
        results = setResults();
    }


    /**
     * Метод создает строку, содержащую текущую дату
     *
     * @return String currentDate
     */
    private String initiateDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        return simpleDateFormat.format(new Date(System.currentTimeMillis()));
    }


    /**
     * Метод преобразует список объектов Answer в String
     *
     * @param list список эмоций и/или мыслей
     * @return если длинна строки !>0, то возвращяем пустую строку,
     * в остальных случаях возвращаем строку, удалив последний перенос строки (\n).
     */
    public static String answerListToString(List<Answer> list) {
        StringBuilder text = new StringBuilder();
        for (Answer answer : list) {
            if (answer.getLevel() > 0 && answer.getLevel() <= 100) {
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
    }

    /**
     * Метод меняет расположение записей в списке в обратном порядке
     *
     * @param thoughts целевой список
     * @return список с обратным порядком
     */
    private ArrayList<Answer> invertList(ArrayList<Answer> thoughts) {
        ArrayList<Answer> invertThoughts = new ArrayList<>();
        for (Answer thought : thoughts) {
            invertThoughts.add(0, thought);
        }
        return invertThoughts;
    }

    // Этому здесь не место, реализация кривовата
    public void setEmotionsDefault() {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer(context.getResources().getString(R.string.negative_emotion_guilt), 0));
        answers.add(new Answer(context.getResources().getString(R.string.negative_emotion_anger), 0));
        answers.add(new Answer(context.getResources().getString(R.string.negative_emotion_sadness), 0));
        answers.add(new Answer(context.getResources().getString(R.string.negative_emotion_pity), 0));
        answers.add(new Answer(context.getResources().getString(R.string.negative_emotion_spite), 0));
        answers.add(new Answer(context.getResources().getString(R.string.negative_emotion_injustice), 0));
        answers.add(new Answer(context.getResources().getString(R.string.negative_emotion_insult), 0));
        answers.add(new Answer(context.getResources().getString(R.string.negative_emotion_despair), 0));
        answers.add(new Answer(context.getResources().getString(R.string.negative_emotion_oppression), 0));
        answers.add(new Answer(context.getResources().getString(R.string.negative_emotion_frustration), 0));
        answers.add(new Answer(context.getResources().getString(R.string.negative_emotion_jealousy), 0));
        answers.add(new Answer(context.getResources().getString(R.string.negative_emotion_fear), 0));
        answers.add(new Answer(context.getResources().getString(R.string.negative_emotion_shame), 0));
        answers.add(new Answer(context.getResources().getString(R.string.negative_emotion_anxiety), 0));
        answers.add(new Answer(context.getResources().getString(R.string.negative_emotion_apathy), 0));

        Completable.fromRunnable(() -> database.getEmotionDao().insertList(answers))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void savePositive(String positive) {
        resultTextPositive = positive;
    }
}
