package com.app.yallagame.ae.startup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.yallagame.ae.R;
import com.app.yallagame.ae.activities.HomeActivity;
import com.app.yallagame.ae.adapters.AvatarGridAdapter;
import com.app.yallagame.ae.adapters.GamesGridAdapter;
import com.app.yallagame.ae.base.BaseActivity;
import com.app.yallagame.ae.databinding.ActivitySignupBinding;
import com.app.yallagame.ae.fragments.SocialBottomSheetDialogFragment;
import com.app.yallagame.ae.models.Avatar;
import com.app.yallagame.ae.models.Games;
import com.app.yallagame.ae.util.Functions;
import com.app.yallagame.ae.util.KeyboardUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hbb20.CCPCountry;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends BaseActivity implements View.OnClickListener {
    private ActivitySignupBinding binding;
    private final List<Avatar> avatarList = new ArrayList<>();
    private final List<Games> gamesList = new ArrayList<>();
    private AvatarGridAdapter adapter;
    private GamesGridAdapter gamesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeStatusbarTransperant();
        setText();

        CCPCountry.setDialogTitle(getString(R.string.select_country_region));
        CCPCountry.setSearchHintMessage(getString(R.string.search_hint));


        GridLayoutManager avatarGridLayoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
        binding.avatarRecyclerVu.setLayoutManager(avatarGridLayoutManager);
        adapter = new AvatarGridAdapter(getContext(), avatarList, false);
        adapter.setItemClickListener(avatarItemClickListener);
        binding.avatarRecyclerVu.setAdapter(adapter);


        GridLayoutManager gamesGridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        binding.gamesRecyclerVu.setLayoutManager(gamesGridLayoutManager);
        gamesAdapter = new GamesGridAdapter(getContext(), gamesList, false);
        gamesAdapter.setItemClickListener(gamesItemClickListener);
        binding.gamesRecyclerVu.setAdapter(gamesAdapter);


        binding.btnBack.setOnClickListener(this);
        binding.btnBackText.setOnClickListener(this);
        binding.btnSignup.setOnClickListener(this);
        binding.btnSocial.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);
        binding.male.setOnClickListener(this);
        binding.female.setOnClickListener(this);
        binding.btnSelectAvatar.setOnClickListener(this);
        binding.btnConfirm.setOnClickListener(this);
        binding.btnComplete.setOnClickListener(this);

        Glide.with(getApplicationContext()).load(R.drawable.temp_img).apply(RequestOptions.circleCropTransform()).into(binding.imgVu);


        checkKeyboardListener();
    }

    private void setText() {


        SpannableStringBuilder spannable = new SpannableStringBuilder();

        spannable.append(getString(R.string.by_clicking_on));

        int start = spannable.length();
        spannable.append(" ").append(getString(R.string.signup));
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFFFF")), start, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannable.append(" ").append(getString(R.string.you_agree_to));

        start = spannable.length();
        spannable.append(" ").append(getString(R.string.app_name));
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFFFF")), start, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = spannable.length();
        spannable.append(" ").append(getString(R.string.user_agreement));
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0A6C")), start, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Handle user agreement click
                userAgreementClicked();

            }


            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#FF0A6C")); // Set text color
                ds.setUnderlineText(true); // Remove underline
            }
        }, start, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannable.append(" ").append(getString(R.string.and));

        start = spannable.length();
        spannable.append(" ").append(getString(R.string.privacy_policy));
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0A6C")), start, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Handle privacy policy click
                privacyPolicyClicked();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#FF0A6C")); // Set text color
                ds.setUnderlineText(true); // Remove underline
            }
        }, start, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.termsText.setText(spannable);
        binding.termsText.setMovementMethod(LinkMovementMethod.getInstance());

    }
    private void checkKeyboardListener() {
        KeyboardUtils.addKeyboardToggleListener(this, new KeyboardUtils.SoftKeyboardToggleListener() {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible) {
                if (isVisible) {
                    binding.bottomLay.setVisibility(View.GONE);
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //your code here
                                    binding.bottomLay.setVisibility(View.VISIBLE);
                                }
                            }, 50);
                        }
                    });
                }
            }
        });
    }
    AvatarGridAdapter.ItemClickListener avatarItemClickListener = new AvatarGridAdapter.ItemClickListener() {
        @Override
        public void itemClicked(View view, int pos) {

        }
    };
    GamesGridAdapter.ItemClickListener gamesItemClickListener = new GamesGridAdapter.ItemClickListener() {
        @Override
        public void itemClicked(View view, int pos) {

        }
    };

    @Override
    public void onClick(View v) {
        if (v == binding.btnBack || v == binding.btnLogin || v == binding.btnBackText){
            finish();
        } else if (v == binding.btnSignup) {
//            if (binding.etEmail.getText().toString().isEmpty()){
//                Functions.showToast(getContext(), "Email cannot be empty!", FancyToast.ERROR);
//                return;
//            }
//            if (binding.etUsername.getText().toString().isEmpty()){
//                Functions.showToast(getContext(), "Username cannot be empty!", FancyToast.ERROR);
//                return;
//            }
//            if (binding.etNewPass.getText().toString().isEmpty()){
//                Functions.showToast(getContext(), "Password cannot be empty!", FancyToast.ERROR);
//                return;
//            }
//            if (binding.etConfirmPass.getText().toString().isEmpty()){
//                Functions.showToast(getContext(), "Confirm password cannot be empty!", FancyToast.ERROR);
//                return;
//            }
//            if (!binding.etConfirmPass.getText().toString().equalsIgnoreCase(binding.etNewPass.getText().toString())){
//                Functions.showToast(getContext(), "Password does not match!", FancyToast.ERROR);
//                return;
//            }
            signupClicked();
        }
        else if (v == binding.btnSocial) {
            socialClicked();
        }
        else if (v == binding.btnNext) {
            if (binding.etPhone.getText().toString().isEmpty()){
                Functions.showToast(getContext(), "Phone cannot be empty!", FancyToast.ERROR);
                return;
            }
            nextClicked();
        }
        else if (v == binding.male) {
            populateMale();
        }
        else if (v == binding.female) {
            populateFemale();
        }
        else if (v == binding.btnSelectAvatar) {
            selectAvatar();
        }
        else if (v == binding.btnConfirm) {
            goToComplete();
        }
        else if (v == binding.btnComplete) {
            //Final Step
            completeSignup();
        }


    }

    private void signupClicked() {
        binding.signupLay.setVisibility(View.GONE);
        binding.btnLogin.setVisibility(View.GONE);
        binding.infoLay.setVisibility(View.VISIBLE);
        binding.btnNext.setVisibility(View.VISIBLE);
    }

    private void selectAvatar() {
        binding.infoLay.setVisibility(View.GONE);
        binding.btnNext.setVisibility(View.GONE);
        binding.avatarRecyclerVu.setVisibility(View.VISIBLE);
        binding.btnConfirm.setVisibility(View.VISIBLE);
        binding.title.setText("Set Avatar");
    }

    private void goToComplete() {
        binding.avatarRecyclerVu.setVisibility(View.GONE);
        binding.btnConfirm.setVisibility(View.GONE);
        binding.infoLay.setVisibility(View.VISIBLE);
        binding.btnNext.setVisibility(View.VISIBLE);

    }

    private void nextClicked() {
        binding.infoLay.setVisibility(View.GONE);
        binding.btnNext.setVisibility(View.GONE);
        binding.gameLay.setVisibility(View.VISIBLE);
        binding.btnComplete.setVisibility(View.VISIBLE);
    }

    private void completeSignup() {
        Functions.showToast(getContext(), "Signup Successfully", FancyToast.SUCCESS);
        Intent intent = new Intent(getContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void populateMale(){
        binding.male.setBackground(getContext().getResources().getDrawable(R.drawable.rounded_corner_bg_pink_selected));
        binding.maleIc.setImageResource(R.drawable.male_s_ic);
        binding.tvMale.setTextColor(getContext().getResources().getColor(R.color.pinkColor));

        binding.female.setBackground(getContext().getResources().getDrawable(R.drawable.rounded_corner_bg_black));
        binding.femaleIc.setImageResource(R.drawable.female_ns_ic);
        binding.tvFemale.setTextColor(getContext().getResources().getColor(R.color.lightGreyTextColor));


    }
    private void populateFemale(){
        binding.female.setBackground(getContext().getResources().getDrawable(R.drawable.rounded_corner_bg_pink_selected));
        binding.femaleIc.setImageResource(R.drawable.female_s_ic);
        binding.tvFemale.setTextColor(getContext().getResources().getColor(R.color.pinkColor));

        binding.male.setBackground(getContext().getResources().getDrawable(R.drawable.rounded_corner_bg_black));
        binding.maleIc.setImageResource(R.drawable.male_ns_ic);
        binding.tvMale.setTextColor(getContext().getResources().getColor(R.color.lightGreyTextColor));
    }


    private void socialClicked() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("SocialBottomSheetDialogFragment");
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }
        fragmentTransaction.addToBackStack(null);
        SocialBottomSheetDialogFragment dialogFragment = new SocialBottomSheetDialogFragment(false);

        dialogFragment.setDialogCallback(new SocialBottomSheetDialogFragment.ResultDialogCallback() {
            @Override
            public void didGoogleClicked(DialogFragment df) {
                df.dismiss();
                Functions.showToast(getContext(), "Google Clicked", FancyToast.SUCCESS);
            }
            @Override
            public void didFacebookClicked(DialogFragment df) {
                df.dismiss();
                Functions.showToast(getContext(), "Facebook Clicked", FancyToast.SUCCESS);

            }
        });

        dialogFragment.show(fragmentTransaction, "SocialBottomSheetDialogFragment");

    }

    private void userAgreementClicked() {
        Intent intent = new Intent(getContext(), UserAgreementActivity.class);
        startActivity(intent);
    }

    private void privacyPolicyClicked() {
        Intent intent = new Intent(getContext(), PrivacyPolicyActivity.class);
        startActivity(intent);
    }

}