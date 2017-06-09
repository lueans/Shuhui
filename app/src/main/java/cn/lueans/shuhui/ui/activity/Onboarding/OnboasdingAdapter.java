package cn.lueans.shuhui.ui.activity.Onboarding;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by 24277 on 2017/6/7.
 */

public class OnboasdingAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;

    public OnboasdingAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        OnboardingFragment instance;
        for (int i = 0; i < 3; i++) {
            instance = OnboardingFragment.getInstance(i);
            fragments.add(instance);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
