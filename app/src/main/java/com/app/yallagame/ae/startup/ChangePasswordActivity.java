package com.app.yallagame.ae.startup;

import android.os.Bundle;
import android.os.Handler;
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
import com.app.yallagame.ae.adapters.AvatarGridAdapter;
import com.app.yallagame.ae.adapters.GamesGridAdapter;
import com.app.yallagame.ae.base.BaseActivity;
import com.app.yallagame.ae.databinding.ActivityChangePasswordBinding;
import com.app.yallagame.ae.databinding.ActivitySignupBinding;
import com.app.yallagame.ae.models.Avatar;
import com.app.yallagame.ae.models.Games;
import com.app.yallagame.ae.util.Functions;
import com.app.yallagame.ae.util.KeyboardUtils;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {
    private ActivityChangePasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeStatusbarTransperant();

        binding.btnBack.setOnClickListener(this);
        binding.btnBackText.setOnClickListener(this);
        binding.btnChange.setOnClickListener(this);
        binding.oldPasswordToggle.setOnClickListener(this);
        binding.newPasswordToggle.setOnClickListener(this);
        binding.confirmPasswordToggle.setOnClickListener(this);

        checkKeyboardListener();
    }


    private void checkKeyboardListener() {
        KeyboardUtils.addKeyboardToggleListener(this, new KeyboardUtils.SoftKeyboardToggleListener() {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible) {
                if (isVisible) {
                    binding.btnChange.setVisibility(View.GONE);
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
                                    binding.btnChange.setVisibility(View.VISIBLE);
                                }
                            }, 50);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnBack || v == binding.btnBackText){
            finish();

        } else if (v == binding.btnChange) {

            if (!binding.etOldPass.getText().toString().isEmpty()){
                Functions.showToast(getContext(), "Old password cannot be empty", FancyToast.ERROR);
                return;
            }
            if (!binding.etConfirmPass.getText().toString().equalsIgnoreCase(binding.etNewPass.getText().toString())){
                Functions.showToast(getContext(), "Password does not match!", FancyToast.ERROR);
                return;
            }
                changePassword();
        }
        else if (v == binding.oldPasswordToggle) {
            oldPasswordToggle();
        }
        else if (v == binding.newPasswordToggle) {
            newPasswordToggle();
        }
        else if (v == binding.confirmPasswordToggle) {
            confirmPasswordToggle();
        }
    }

    private void changePassword() {
        Functions.showToast(getContext(), "Password changed successfully!", FancyToast.SUCCESS);

    }

    private void oldPasswordToggle() {
        TransformationMethod currentMethod = binding.etOldPass.getTransformationMethod();
        if (currentMethod instanceof PasswordTransformationMethod) {
            binding.etOldPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.oldPasswordToggle.setImageResource(R.drawable.show);
        } else {
            binding.etOldPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.oldPasswordToggle.setImageResource(R.drawable.hide);
        }
        binding.etOldPass.setSelection(binding.etOldPass.getText().length());
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
