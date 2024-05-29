package com.app.yallagame.ae.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.yallagame.ae.R;
import com.app.yallagame.ae.base.BaseActivity;
import com.app.yallagame.ae.databinding.ActivityForgetPasswordBinding;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private ActivityForgetPasswordBinding binding;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeStatusbarTransperant();

        binding.btnBack.setOnClickListener(this);
        binding.btnGoBack.setOnClickListener(this);
        binding.btnSend.setOnClickListener(this);
        binding.etResendOtp.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);
        binding.btnResetPassword.setOnClickListener(this);
        binding.newPasswordToggle.setOnClickListener(this);
        binding.confirmPasswordToggle.setOnClickListener(this);

        startTimer();

    }


    private void startTimer() {
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsLeft = millisUntilFinished / 1000;
                String timeFormatted = String.format("0:%02d", secondsLeft);
                binding.etTimer.setText(String.format("%s %s", getString(R.string.resend_in), timeFormatted));

            }

            @Override
            public void onFinish() {
                binding.etTimer.setVisibility(View.GONE);
                binding.etResendOtp.setVisibility(View.VISIBLE);
            }
        }.start();
    }


    @Override
    public void onClick(View v) {
        if (v == binding.btnBack || v == binding.btnGoBack){
            finish();
        } else if (v == binding.btnSend) {
            sendOtpAndMoveToVerify();

        } else if (v == binding.etResendOtp) {
            resendOtpAndStay();
        }
        else if (v == binding.btnNext) {
            verifyOtpAndMoveToReset();
        }
        else if (v == binding.btnResetPassword) {
            resetPasswordAndStay();
        }
        else if (v == binding.newPasswordToggle) {
            newPasswordToggle();
        }
        else if (v == binding.confirmPasswordToggle) {
            confirmPasswordToggle();
        }

    }

    private void sendOtpAndMoveToVerify() {
        binding.mainLay.setVisibility(View.GONE);
        binding.otpLay.setVisibility(View.VISIBLE);

    }
    private void resendOtpAndStay() {
        //call APi Only and stay here
    }

    private void verifyOtpAndMoveToReset() {
        binding.otpLay.setVisibility(View.GONE);
        binding.resetLay.setVisibility(View.VISIBLE);
    }


    private void resetPasswordAndStay() {
        binding.resetLay.setVisibility(View.GONE);
        binding.successLay.setVisibility(View.VISIBLE);

    }

    private void newPasswordToggle() {
        TransformationMethod currentMethod = binding.etNewPass.getTransformationMethod();
        if (currentMethod instanceof PasswordTransformationMethod) {
            binding.etNewPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.newPasswordToggle.setImageResource(R.drawable.show);
        } else {
            binding.etNewPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.newPasswordToggle.setImageResource(R.drawable.hide);
        }
        binding.etNewPass.setSelection(binding.etNewPass.getText().length());
    }

    private void confirmPasswordToggle() {
        TransformationMethod currentMethod = binding.etConfirmPass.getTransformationMethod();
        if (currentMethod instanceof PasswordTransformationMethod) {
            binding.etConfirmPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.confirmPasswordToggle.setImageResource(R.drawable.show);
        } else {
            binding.etConfirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.confirmPasswordToggle.setImageResource(R.drawable.hide);
        }
        binding.etConfirmPass.setSelection(binding.etConfirmPass.getText().length());
    }








}