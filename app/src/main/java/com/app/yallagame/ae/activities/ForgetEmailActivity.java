package com.app.yallagame.ae.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import com.app.yallagame.ae.R;
import com.app.yallagame.ae.base.BaseActivity;
import com.app.yallagame.ae.databinding.ActivityForgetEmailBinding;

public class ForgetEmailActivity extends BaseActivity implements View.OnClickListener {

    private ActivityForgetEmailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeStatusbarTransperant();

        binding.btnBack.setOnClickListener(this);
        binding.btnGoBack.setOnClickListener(this);
        binding.btnFind.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnBack || v == binding.btnGoBack){
            finish();
        }
        else if (v == binding.btnFind) {
            findEmail();
        }
    }

    private void findEmail() {

        binding.mainLay.setVisibility(View.GONE);
        binding.successLay.setVisibility(View.VISIBLE);

        String email = "na……..1@email.com";
        String username = "uxninja";

        SpannableStringBuilder spannable = new SpannableStringBuilder();
        spannable.append(getString(R.string.discovered));
        int start = spannable.length();
        spannable.append(email);
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0A6C")), start, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.append(" "+getString(R.string.is_username));
        start = spannable.length();
        spannable.append(username);
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0A6C")), start, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.taglineText.setText(spannable);
    }
}