package com.qualitasvita.soundmind.di;

import androidx.annotation.NonNull;

import com.qualitasvita.soundmind.interactor.NewNoteInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class InteractorModule {

    @Singleton
    @Provides
    @NonNull
    public NewNoteInteractor provideInteractor(){
        return new NewNoteInteractor();
    }
}
