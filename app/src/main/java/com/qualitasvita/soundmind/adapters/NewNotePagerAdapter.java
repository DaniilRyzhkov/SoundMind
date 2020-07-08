package com.qualitasvita.soundmind.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class NewNotePagerAdapter extends FragmentStateAdapter {

    private ArrayList<Fragment> steps;

    public NewNotePagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, ArrayList<Fragment> steps) {
        super(fragmentManager, lifecycle);
        this.steps = steps;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return steps.get(position);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }
}

