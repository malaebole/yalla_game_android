package com.app.yallagame.ae.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.yallagame.ae.R;
import com.app.yallagame.ae.base.BaseActivity;
import com.app.yallagame.ae.databinding.ActivityFollowingBinding;
import com.app.yallagame.ae.databinding.ActivityMenuBinding;
import com.app.yallagame.ae.startup.ChangePasswordActivity;
import com.app.yallagame.ae.startup.CompleteProfileActivity;
import com.app.yallagame.ae.startup.SignupActivity;
import com.app.yallagame.ae.util.Constants;
import com.app.yallagame.ae.util.Functions;
import com.shashank.sony.fancytoastlib.FancyToast;

import io.github.vejei.cupertinoswitch.CupertinoSwitch;

public class MenuActivity extends BaseActivity implements View.OnClickListener {

    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeStatusbarTransperant();

        binding.btnBack.setOnClickListener(this);
        binding.btnBackText.setOnClickListener(this);
        binding.langAr.setOnClickListener(this);
        binding.langEn.setOnClickListener(this);
        binding.profile.setOnClickListener(this);
        binding.pp.setOnClickListener(this);
        binding.contactUs.setOnClickListener(this);
        binding.changePass.setOnClickListener(this);
        binding.logoutVu.setOnClickListener(this);

        setLabelText(Functions.getAppLangStr(getContext()));


        binding.notifSwitch.setOnStateChangeListener(new CupertinoSwitch.OnStateChangeListener() {
            @Override
            public void onChanged(CupertinoSwitch view, boolean checked) {
                Functions.showToast(getContext(), "onChanged", FancyToast.SUCCESS);
            }

            @Override
            public void onSwitchOn(CupertinoSwitch view) {
                Functions.showToast(getContext(), "onSwitchOn", FancyToast.SUCCESS);
            }

            @Override
            public void onSwitchOff(CupertinoSwitch view) {
                Functions.showToast(getContext(), "onSwitchOff", FancyToast.SUCCESS);
            }
        });

        boolean isEnglish = isEnglishLanguage(); // Replace with your logic to check the language


        // Apply animations based on language setting
        if (isEnglish) {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else {
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }


        // Glide.with(getApplicationContext()).load(R.drawable.temp_img).apply(RequestOptions.circleCropTransform()).into(binding.imgVu);


    }
    private boolean isEnglishLanguage() {
        // Replace this with your actual logic to determine the language setting
        String currentLanguage = getResources().getConfiguration().locale.getLanguage();
        return "en".equals(currentLanguage);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnBack || v == binding.btnBackText){
            finish();
        }
        else if (v == binding.langEn) {
            English();
        }
        else if (v == binding.langAr) {
            Arabic();
        }
        else if (v == binding.profile) {
            userProfile();
        }
        else if (v == binding.pp) {
            privacyPolicy();
        }
        else if (v == binding.contactUs) {
            contactUs();
        }
        else if (v == binding.changePass) {
            changePassword();
        }
        else if (v == binding.logoutVu) {
            logoOut();
        }

    }

    private void logoOut() {
    }

    private void changePassword() {
        Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
        startActivity(intent);
    }

    private void contactUs() {
        Intent intent = new Intent(getContext(), ContactUsActivity.class);
        startActivity(intent);
    }

    private void privacyPolicy() {

    }

    private void userProfile() {
        Intent intent = new Intent(getContext(), CompleteProfileActivity.class);
        intent.putExtra("update", true);
        startActivity(intent);
    }

    private void English() {
        binding.langEn.setBackground(getResources().getDrawable(R.drawable.full_rounded_corner_bg_pink));
        binding.langAr.setBackground(getResources().getDrawable(R.drawable.transparent_bg));
        selectLanguage("en");
    }

    private void Arabic() {
        binding.langEn.setBackground(getResources().getDrawable(R.drawable.transparent_bg));
        binding.langAr.setBackground(getResources().getDrawable(R.drawable.full_rounded_corner_bg_pink));
        selectLanguage("ar");
    }


    public void selectLanguage(String lang) {
        if (Functions.getAppLangStr(getContext()).equalsIgnoreCase(lang)) {
            return;
        }
        setLabelText(lang);
        if (lang.equalsIgnoreCase("ar")){
            Functions.setAppLangAr(getContext(), "ar");
        }else{
            Functions.setAppLangAr(getContext(), "en");
        }
        Functions.setAppLang(getContext(), lang);
        Functions.changeLanguage(getContext(), lang);
//        sendAppLangApi();

        Intent intent = new Intent(getContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finishAffinity();
        finish();
        startActivity(intent);

    }

    private void setLabelText(String lang) {
        if (lang.equalsIgnoreCase("ar")) {
            binding.langAr.setText("العربية");
            binding.langEn.setBackground(getResources().getDrawable(R.drawable.transparent_bg));
            binding.langAr.setBackground(getResources().getDrawable(R.drawable.full_rounded_corner_bg_pink));
        }
        else {
            binding.langEn.setText("English");
            binding.langEn.setBackground(getResources().getDrawable(R.drawable.full_rounded_corner_bg_pink));
            binding.langAr.setBackground(getResources().getDrawable(R.drawable.transparent_bg));
        }
    }




}