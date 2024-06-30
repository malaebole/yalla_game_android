package com.app.yallagame.ae.startup;

import static android.content.ContentValues.TAG;
import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.app.yallagame.ae.R;
import com.app.yallagame.ae.activities.MainActivity;
import com.app.yallagame.ae.base.BaseActivity;
import com.app.yallagame.ae.databinding.ActivitySignupBinding;
import com.app.yallagame.ae.fragments.SocialBottomSheetDialogFragment;
import com.app.yallagame.ae.models.UserInfo;
import com.app.yallagame.ae.util.AppManager;
import com.app.yallagame.ae.util.Constants;
import com.app.yallagame.ae.util.Functions;
import com.app.yallagame.ae.util.KeyboardUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONException;
import org.json.JSONObject;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends BaseActivity implements View.OnClickListener {
    private ActivitySignupBinding binding;
    private CredentialManager credentialManager;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeStatusbarTransperant();
        setText();

        credentialManager = CredentialManager.create(getContext());
        callbackManager = CallbackManager.Factory.create();


        binding.btnBack.setOnClickListener(this);
        binding.btnBackText.setOnClickListener(this);
        binding.btnSignup.setOnClickListener(this);
        binding.btnSocial.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
        binding.passwordToggle.setOnClickListener(this);
        binding.confirmPasswordToggle.setOnClickListener(this);

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
                } else {
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

    @Override
    public void onClick(View v) {
        if (v == binding.btnBack || v == binding.btnLogin || v == binding.btnBackText) {
            finish();
        } else if (v == binding.btnSignup) {
            if (binding.etEmail.getText().toString().isEmpty()) {
                Functions.showToast(getContext(), "Email cannot be empty!", FancyToast.ERROR);
                return;
            }
            if (binding.etUsername.getText().toString().isEmpty()) {
                Functions.showToast(getContext(), "Username cannot be empty!", FancyToast.ERROR);
                return;
            }
            if (binding.etFullName.getText().toString().isEmpty()) {
                Functions.showToast(getContext(), "Full name cannot be empty!", FancyToast.ERROR);
                return;
            }
            if (binding.etPass.getText().toString().isEmpty()) {
                Functions.showToast(getContext(), "Password cannot be empty!", FancyToast.ERROR);
                return;
            }
            if (binding.etConfirmPass.getText().toString().isEmpty()) {
                Functions.showToast(getContext(), "Confirm password cannot be empty!", FancyToast.ERROR);
                return;
            }
            if (!binding.etConfirmPass.getText().toString().equalsIgnoreCase(binding.etPass.getText().toString())) {
                Functions.showToast(getContext(), "Password does not match!", FancyToast.ERROR);
                return;
            }
            signupUser(binding.etFullName.getText().toString(), binding.etEmail.getText().toString(), binding.etUsername.getText().toString(), binding.etConfirmPass.getText().toString());
        } else if (v == binding.btnSocial) {
            socialClicked();
        } else if (v == binding.passwordToggle) {
            passwordToggle();
        } else if (v == binding.confirmPasswordToggle) {
            confirmPasswordToggle();
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

    private void signupClicked() {
        Intent intent = new Intent(getContext(), CompleteProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("update", false);
        startActivity(intent);
        finish();
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
                runGoogleLogin("387799745356-7e9akgaridrfib1okpauue29qc2ns5d0.apps.googleusercontent.com");
            }

            @Override
            public void didFacebookClicked(DialogFragment df) {
                df.dismiss();
                Functions.showToast(getContext(), "Facebook Clicked", FancyToast.SUCCESS);
                runFacebookLogin();
            }
        });
        dialogFragment.show(fragmentTransaction, "SocialBottomSheetDialogFragment");

    }

    private void runFacebookLogin(){
        //Facebook login setup

//        to Logout user
//            LoginManager.getInstance().logOut();

        LoginManager.getInstance().logInWithReadPermissions(SignupActivity.this, Arrays.asList("public_profile", "email"));

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                PendingIntent pendingIntent;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    pendingIntent = PendingIntent.getActivity(SignupActivity.this,
                            0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                } else {
                    pendingIntent = PendingIntent.getActivity(SignupActivity.this,
                            0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                }
                AccessToken accessToken = loginResult.getAccessToken();
                String token = accessToken.getToken();

                // Do something with the token, e.g., send it to your server
                Log.d("FacebookLogin", "Token: " + token);

                // Use the pendingIntent as needed
            }

            @Override
            public void onCancel() {
                Log.d("FacebookLogin", "Login canceled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("FacebookLogin", "Login error: " + error.getMessage());
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle isTeam = data.getExtras();
            Log.d("Success", "Try Again" + resultCode + isTeam);
        }
    }


    private void runGoogleLogin(@NonNull String clientId) {

        GetSignInWithGoogleOption googleIdOption = new GetSignInWithGoogleOption.Builder(clientId)
                .build();

        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();

        credentialManager.getCredentialAsync(getContext(), request, null,
                THREAD_POOL_EXECUTOR,
                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                    @Override
                    public void onResult(GetCredentialResponse getCredentialResponse) {
                        GoogleIdTokenCredential credential = (GoogleIdTokenCredential) getCredentialResponse.getCredential();
                        String idToken = credential.getIdToken();
                        Log.i("Google Token", idToken);

                    }
                    @Override
                    public void onError(@NonNull GetCredentialException e) {
                        Log.e("Googleerror", e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Functions.showToast(getContext(), e.getMessage(), FancyToast.ERROR);
                            }
                        });

                    }
                });
    }

    private void userAgreementClicked() {
        Intent intent = new Intent(getContext(), UserAgreementActivity.class);
        startActivity(intent);
    }

    private void privacyPolicyClicked() {
        Intent intent = new Intent(getContext(), PrivacyPolicyActivity.class);
        startActivity(intent);
    }

    private void signupUser(String fullName, String email, String userName, String password) {
        KProgressHUD hud = Functions.showLoader(getContext(), "Image processing");
        Call<ResponseBody> call = AppManager.getInstance().apiInterface.signupUser(fullName, email, userName, password);
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

                            UserInfo userInfo = new Gson().fromJson(data.toString(), UserInfo.class);
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

                            signupClicked();

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
                        if (errorObject.has("error")) {
                            JSONObject errors = errorObject.getJSONObject("error");
                            StringBuilder errorMessage = new StringBuilder("Signup failed:\n");

                            if (errors.has("email")) {
                                errorMessage.append(errors.getJSONObject("email").getString("message")).append("\n");
                            }
                            if (errors.has("username")) {
                                errorMessage.append(errors.getJSONObject("username").getString("message")).append("\n");
                            }

                            Functions.showToast(getContext(), errorMessage.toString().trim(), FancyToast.ERROR);
                        } else {
                            Functions.showToast(getContext(), errorObject.getString("message"), FancyToast.ERROR);
                        }
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