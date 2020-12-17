package com.example.gruppe3_movieapp.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.gruppe3_movieapp.view.FavoritesFragment;
import com.example.gruppe3_movieapp.view.SearchFragment;

/**
 * @author Elena Ozsvald
 */
public class PageAdapter extends FragmentPagerAdapter {
    private final int numberOfTabs;

    public PageAdapter(@NonNull FragmentManager fm, int numberOfTabs) {
        super(fm, numberOfTabs);
        this.numberOfTabs = numberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FavoritesFragment();
            case 1:
                return new SearchFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
