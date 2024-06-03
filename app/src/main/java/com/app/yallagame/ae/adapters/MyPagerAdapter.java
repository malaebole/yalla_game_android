package com.app.yallagame.ae.adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.yallagame.ae.R;
import com.app.yallagame.ae.util.CustomTabView;

import java.util.ArrayList;
import java.util.List;


public class MyPagerAdapter extends FragmentPagerAdapter {

    private final Context context;
    private final int[] tabIcons = {R.drawable.temp_img, R.drawable.chat_ic, R.drawable.game_ic, R.drawable.tournament_ic, R.drawable.reward_ic};

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public MyPagerAdapter(Context context, FragmentManager manager) {
        super(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public CustomTabView getTabView(int position) {
        CustomTabView tabView = new CustomTabView(context);
       // tabView.tvTitle.setText(tabText[position]);
        tabView.iconVu.setImageResource(tabIcons[position]);
        return tabView;
    }
}
