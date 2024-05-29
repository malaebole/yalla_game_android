package com.app.yallagame.ae.startup;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.yallagame.ae.R;
import com.app.yallagame.ae.base.BaseActivity;
import com.app.yallagame.ae.databinding.ActivityLoginBinding;
import com.app.yallagame.ae.databinding.ActivitySignupBinding;
import com.app.yallagame.ae.fragments.SocialBottomSheetDialogFragment;
import com.app.yallagame.ae.util.Functions;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignupActivity extends BaseActivity implements View.OnClickListener {
    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeStatusbarTransperant();
        setText();


        binding.btnBack.setOnClickListener(this);
        binding.btnSignup.setOnClickListener(this);
        binding.btnSocial.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);


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

    @Override
    public void onClick(View v) {
        if (v == binding.btnBack || v == binding.btnLogin){
            finish();
        } else if (v == binding.btnSignup) {
            signupClicked();
        }
        else if (v == binding.btnSocial) {
            socialClicked();
        }


    }

    private void signupClicked() {

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
        Functions.showToast(getContext(), "User Agreement Clicked", FancyToast.SUCCESS);
    }

    private void privacyPolicyClicked() {
        Functions.showToast(getContext(), "Privacy policy Clicked", FancyToast.SUCCESS);
    }

}