package com.qualitasvita.soundmind.di;

import com.qualitasvita.soundmind.adapters.ThoughtAdapter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    void inject(ThoughtAdapter thoughtAdapter);
}
