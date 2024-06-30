package com.app.yallagame.ae.startup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.yallagame.ae.R;
import com.app.yallagame.ae.activities.HomeActivity;
import com.app.yallagame.ae.base.BaseActivity;
import com.app.yallagame.ae.databinding.ActivityLoginBinding;
import com.app.yallagame.ae.fragments.SocialBottomSheetDialogFragment;
import com.app.yallagame.ae.models.UserInfo;
import com.app.yallagame.ae.util.AppManager;
import com.app.yallagame.ae.util.Constants;
import com.app.yallagame.ae.util.Functions;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONObject;

import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ActivityLoginBinding binding;
    private UserInfo userInfo;

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

        binding.tvForgetPass.setPaintFlags(binding.tvForgetPass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.tvForgetEmail.setPaintFlags(binding.tvForgetEmail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnLogin){

            if (binding.etEmail.getText().toString().isEmpty()){
                Functions.showToast(getContext(), "Email cannot be empty!", FancyToast.ERROR);
                return;
            }
            if (binding.etPass.getText().toString().isEmpty()){
                Functions.showToast(getContext(), "Password cannot be empty!", FancyToast.ERROR);
                return;
            }

            userLogin(binding.etEmail.getText().toString(), binding.etPass.getText().toString());

        }
        else if (v == binding.btnSocial) {
            socialClicked();
        }
        else if (v == binding.btnSignup) {
            signupClicked();
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
        Intent intent = new Intent(getContext(), ForgetPasswordActivity.class);
        startActivity(intent);

    }

    private void forgetEmailClicked() {
        Intent intent = new Intent(getContext(), ForgetEmailActivity.class);
        startActivity(intent);
    }

    private void signupClicked() {
        Intent intent = new Intent(getContext(), SignupActivity.class);
        startActivity(intent);
    }

    private void socialClicked() {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("SocialBottomSheetDialogFragment");
            if (fragment != null) {
                fragmentTransaction.remove(fragment);
            }
            fragmentTransaction.addToBackStack(null);
            SocialBottomSheetDialogFragment dialogFragment = new SocialBottomSheetDialogFragment(true);

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

        if (userInfo !=null){
            if (userInfo.getIsProfileComplete()){
                Intent intent = new Intent(getContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(getContext(), CompleteProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("update", false);
                startActivity(intent);
                finish();
            }
        }

    }

    private void userLogin(String email, String password) {
        KProgressHUD hud = Functions.showLoader(getContext(), "Image processing");
        Call<ResponseBody> call = AppManager.getInstance().apiInterface.userLogin(email, password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Functions.hideLoader(hud);
                if (response.body() != null) {
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getInt(Constants.kStatus) == Constants.kSuccessCode) {
                            Functions.showToast(getContext(), object.getString(Constants.kMsg), FancyToast.SUCCESS);
                            JSONObject data = object.getJSONObject(Constants.kData);

                            userInfo = new Gson().fromJson(data.toString(), UserInfo.class);
                            SharedPreferences.Editor editor = getContext().getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE).edit();
                            editor.putString(Constants.kUserID, userInfo.getId().toString());
                            editor.putString(Constants.kUserType, userInfo.getRole());
                            editor.putString(Constants.kAccessToken, userInfo.getTokens().getAccess());
                            editor.putString(Constants.kRefreshToken, userInfo.getTokens().getRefresh());
                            editor.putString(Constants.kEmailToken, userInfo.getTokens().getEmailVerification());
                            editor.putString(Constants.kIsSignIn, "1");
                            editor.apply();

                            Functions.saveUserinfo(getContext(), userInfo);
                            String fcmToken = Functions.getPrefValue(getContext(), Constants.kFCMToken);
                            if (!fcmToken.isEmpty()) {
                                sendFcmTokenApi(fcmToken);
                            }

                            btnLoginClicked();

                        }
                        else {
                            Functions.showToast(getContext(), object.getString(Constants.kMsg), FancyToast.ERROR);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Functions.showToast(getContext(), e.getLocalizedMessage(), FancyToast.ERROR);
                    }
                } else if (response.errorBody() != null) {
                    try {
                        JSONObject errorObject = new JSONObject(response.errorBody().string());
                        Functions.showToast(getContext(), errorObject.getString(Constants.kMsg), FancyToast.ERROR);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Functions.showToast(getContext(), getString(R.string.error_occured), FancyToast.ERROR);
                    }
                } else {
                    Functions.showToast(getContext(), getString(R.string.error_occured), FancyToast.ERROR);
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Functions.hideLoader(hud);
                if (t instanceof UnknownHostException) {
                    Functions.showToast(getContext(), getString(R.string.check_internet_connection), FancyToast.ERROR);
                } else {
                    Functions.showToast(getContext(), t.getLocalizedMessage(), FancyToast.ERROR);
                }
            }
        });
    }
}