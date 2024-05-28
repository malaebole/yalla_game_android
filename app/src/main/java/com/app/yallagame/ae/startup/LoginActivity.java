package com.app.yallagame.ae.startup;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.yallagame.ae.R;
import com.app.yallagame.ae.base.BaseActivity;
import com.app.yallagame.ae.databinding.ActivityLoginBinding;
import com.app.yallagame.ae.fragments.SocialBottomSheetDialogFragment;
import com.app.yallagame.ae.util.Functions;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeStatusbarTransperant();



        binding.btnLogin.setOnClickListener(this);
        binding.btnSocial.setOnClickListener(this);
        binding.tvForgetEmail.setOnClickListener(this);
        binding.tvForgetPass.setOnClickListener(this);
        binding.btnSignup.setOnClickListener(this);
        binding.passwordToggle.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnLogin){
            btnLoginClicked();
        }
        else if (v == binding.btnSocial) {
            btnSocialClicked();
        }
        else if (v == binding.btnSignup) {
            btnSignupClicked();
        }
        else if (v == binding.tvForgetEmail) {
            forgetEmailClicked();
        }
        else if (v == binding.tvForgetPass) {
            forgetPasswordClicked();
        }
        else if (v == binding.passwordToggle) {
            passwordToggle();
        }

    }

    private void passwordToggle() {
        TransformationMethod currentMethod = binding.etPass.getTransformationMethod();
        if (currentMethod instanceof PasswordTransformationMethod) {
            binding.etPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.passwordToggle.setImageResource(R.drawable.show);
        } else {
            binding.etPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.passwordToggle.setImageResource(R.drawable.hide);
        }
        binding.etPass.setSelection(binding.etPass.getText().length());
    }

    private void forgetPasswordClicked() {

    }

    private void forgetEmailClicked() {

    }

    private void btnSignupClicked() {

    }

    private void btnSocialClicked() {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("SocialBottomSheetDialogFragment");
            if (fragment != null) {
                fragmentTransaction.remove(fragment);
            }
            fragmentTransaction.addToBackStack(null);
            SocialBottomSheetDialogFragment dialogFragment = new SocialBottomSheetDialogFragment();

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

    private void btnLoginClicked() {

    }
}