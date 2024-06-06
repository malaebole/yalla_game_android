package com.app.yallagame.ae.activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.app.yallagame.ae.R;
import com.app.yallagame.ae.adapters.MyPagerAdapter;
import com.app.yallagame.ae.base.BaseTabActivity;
import com.app.yallagame.ae.databinding.ActivityPlayerMainTabsBinding;
import com.app.yallagame.ae.fragments.ChatFragment;
import com.app.yallagame.ae.fragments.GamesFragment;
import com.app.yallagame.ae.fragments.ProfileFragment;
import com.app.yallagame.ae.fragments.RewardsFragment;
import com.app.yallagame.ae.fragments.TournamentFragment;
import com.app.yallagame.ae.util.AppManager;
import com.app.yallagame.ae.util.Constants;
import com.app.yallagame.ae.util.CustomTabView;
import com.app.yallagame.ae.util.Functions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

public class HomeActivity extends BaseTabActivity {

    private ActivityPlayerMainTabsBinding binding;
    private MyPagerAdapter adapter;

    private final ProfileFragment profileFragment = new ProfileFragment();
    private final ChatFragment chatFragment = new ChatFragment();
    private final GamesFragment gamesFragment = new GamesFragment();
    private final TournamentFragment tournamentFragment = new TournamentFragment();
    private final RewardsFragment rewardsFragment = new RewardsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerMainTabsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeStatusbarTransperant();

        setupViewPager(binding.content.contentMain.viewPager);
        setupTabLayout();
        setupMenu();

        binding.content.contentMain.tabLayout.setScrollPosition(2, 0, true);
        binding.content.contentMain.viewPager.setCurrentItem(2);

    }

    private void callUnreadNotifAPI() {
        getUnreadNotificationAPI(new UnreadCountCallback() {
            @Override
            public void unreadNotificationCount(int count) {
                AppManager.getInstance().notificationCount = count;
              //  setBadgeValue();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Functions.getPrefValue(getContext(),Constants.kIsSignIn).equalsIgnoreCase("1")){
            callUnreadNotifAPI();
           // checkLoyaltyAPI();
        }
        //callUnreadNotifAPI();
       // populateSideMenuData();
        // checkLoyaltyAPI();
        try {
            LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("receive_push"));
        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            AppManager.getInstance().notificationCount += 1;
            setBadgeValue();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        }
    }

//    @Override
//    public void onBackPressed() {
//        if (resideMenu.isOpened()) {
//            resideMenu.closeMenu();
//        }
//        else {
//            super.onBackPressed();
//        }
//    }

//    private void populateSideMenuData() {
//        UserInfo userInfo = Functions.getUserinfo(getContext());
//        if (userInfo != null) {
//            tvName.setText(userInfo.getName());
//            Glide.with(getContext()).load(userInfo.getBibUrl()).placeholder(R.drawable.bibl).into(shirtImgVu);  //(ImageView) menuVu.findViewById(R.id.shirt_img_vu));
//            Glide.with(getContext()).load(userInfo.getEmojiUrl()).into(emojiImgVu);
//            if (userInfo.getLevel() != null && !userInfo.getLevel().isEmpty() && !userInfo.getLevel().getValue().equalsIgnoreCase("")) {
//                tvRank.setVisibility(View.VISIBLE);
//                tvRank.setText(String.format("LV: %s", userInfo.getLevel().getValue()));
//            }
//            else {
//                tvRank.setVisibility(View.INVISIBLE);
//            }
//        }
//        else {
//            tvName.setText(R.string.guest);
//            userImageVu.setImageResource(R.drawable.player_active);
//            tvRank.setVisibility(View.INVISIBLE);
//        }
//        setBadgeValue();
//
//        if (Functions.getPrefValue(getContext(), Constants.kAppModule).equalsIgnoreCase(Constants.kPadelModule)) {
//            imgVuSide.setImageResource(R.drawable.sidemenu_padel);
//            // imgVuFootball.setVisibility(View.GONE);
//            // imgVuPadel.setVisibility(View.VISIBLE);
//            //  tvFootball.setTextColor(getResources().getColor(R.color.greenColor));
//            //  tvPadel.setTextColor(getResources().getColor(R.color.whiteColor));
//        }
//        else {
//            imgVuSide.setImageResource(R.drawable.sidemenu_football);
//            //  imgVuFootball.setVisibility(View.VISIBLE);
//            //  imgVuPadel.setVisibility(View.GONE);
//            //  tvFootball.setTextColor(getResources().getColor(R.color.whiteColor));
//            //  tvPadel.setTextColor(getResources().getColor(R.color.greenColor));
//        }
//    }

    private void setBadgeValue() {
        if (profileFragment.isVisible()) {
            profileFragment.setBadgeValue();
        }
        else if (chatFragment.isVisible()) {
            chatFragment.setBadgeValue();
        }
        else if (gamesFragment.isVisible()) {
            gamesFragment.setBadgeValue();
        }
        else if (tournamentFragment.isVisible()) {
            tournamentFragment.setBadgeValue();
        }
        else if (rewardsFragment.isVisible()) {
            rewardsFragment.setBadgeValue();
        }
    }

    private void setupTabLayout() {
        binding.content.contentMain.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        binding.content.contentMain.tabLayout.setupWithViewPager(binding.content.contentMain.viewPager);
        binding.content.contentMain.tabLayout.setBackgroundColor(getResources().getColor(R.color.appColor));

        for (int i = 0; i < binding.content.contentMain.tabLayout.getTabCount(); i++) {
            CustomTabView tabItemVu = adapter.getTabView(i);
            binding.content.contentMain.tabLayout.getTabAt(i).setCustomView(tabItemVu);
            tabItemVu.iconVu.getDrawable().setColorFilter(getResources().getColor(R.color.lightGreyTextColor), PorterDuff.Mode.SRC_IN);
        }
        binding.content.contentMain.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                CustomTabView customTabView = (CustomTabView) tab.getCustomView();

                if (customTabView != null) {
                    switch (tabPosition) {
                        case 0:
                            Glide.with(getApplicationContext()).load(R.drawable.temp_img).apply(RequestOptions.circleCropTransform()).into(customTabView.iconVu);
                            break;
                        case 1:
                            customTabView.iconVu.setImageDrawable(getResources().getDrawable(R.drawable.chat_ic_selected, null));
                            break;
                        case 2:
                            customTabView.iconVu.setImageDrawable(getResources().getDrawable(R.drawable.game_ic_selected, null));
                            break;
                        case 3:
                            customTabView.iconVu.setImageDrawable(getResources().getDrawable(R.drawable.tournament_ic_selected, null));
                            break;
                        case 4:
                            customTabView.iconVu.setImageDrawable(getResources().getDrawable(R.drawable.rewards_ic_selected, null));
                            break;
                    }
                    customTabView.line.setImageDrawable(getResources().getDrawable(R.drawable.gradient_bg, null));
                    customTabView.imgBg.setImageDrawable(getResources().getDrawable(R.drawable.pink_tab_bg, null));
                }
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                CustomTabView customTabView = (CustomTabView) tab.getCustomView();

                if (customTabView != null) {
                    switch (tabPosition) {
                        case 0:
                            Glide.with(getApplicationContext()).load(R.drawable.temp_img).apply(RequestOptions.circleCropTransform()).into(customTabView.iconVu);
                            break;
                        case 1:
                            customTabView.iconVu.setImageDrawable(getResources().getDrawable(R.drawable.chat_ic, null));
                            break;
                        case 2:
                            customTabView.iconVu.setImageDrawable(getResources().getDrawable(R.drawable.game_ic, null));
                            break;
                        case 3:
                            customTabView.iconVu.setImageDrawable(getResources().getDrawable(R.drawable.tournament_ic, null));
                            break;
                        case 4:
                            customTabView.iconVu.setImageDrawable(getResources().getDrawable(R.drawable.reward_ic, null));
                            break;
                    }
                    customTabView.line.setImageDrawable(getResources().getDrawable(R.drawable.transparent_bg, null));
                    customTabView.imgBg.setImageDrawable(getResources().getDrawable(R.drawable.transparent_bg, null));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // No action needed
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new MyPagerAdapter(getContext(), getSupportFragmentManager());
        adapter.addFrag(profileFragment, "");
        adapter.addFrag(chatFragment, "");
        adapter.addFrag(gamesFragment, "");
        adapter.addFrag(tournamentFragment, "");
        adapter.addFrag(rewardsFragment, "");

        viewPager.setAdapter(adapter);
    }

    public void notificationsClicked() {
        if (!Functions.getPrefValue(getContext(), Constants.kIsSignIn).equalsIgnoreCase("1")) {
            Functions.showToast(getContext(), "Please Login First", FancyToast.ERROR, FancyToast.LENGTH_SHORT);
            return;
        }
        startActivity(new Intent(getContext(), NotificationsActivity.class));
    }

    public void menuClicked() {
//        if (Functions.getAppLangStr(getContext()).equalsIgnoreCase("ar")) {
//            resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
//        }
//        else {
//            resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
//        }

        Intent intent = new Intent(getContext(), MenuActivity.class);
        startActivity(intent);

    }

}
