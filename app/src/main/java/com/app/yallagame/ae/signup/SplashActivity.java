package com.app.yallagame.ae.signup;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.app.yallagame.ae.R;
import com.app.yallagame.ae.base.BaseActivity;
import com.app.yallagame.ae.databinding.ActivitySplashBinding;
import com.app.yallagame.ae.util.AppManager;
import com.app.yallagame.ae.util.Constants;
import com.app.yallagame.ae.util.Functions;
import com.google.android.datatransport.backend.cct.BuildConfig;


public class SplashActivity extends BaseActivity {
    private ActivitySplashBinding binding;
    private Handler handler;
    TextView version_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeStatusbarTransperant();

        if (Functions.getAppLangStr(getContext()).isEmpty()) {
            Functions.setAppLang(getContext(), "en");
        }

        grantNotificationPermission();

        version_name = findViewById(R.id.version_text);
        PackageManager pm = getApplicationContext().getPackageManager();
        String pkgName = getApplicationContext().getPackageName();
        PackageInfo pkgInfo = null;
        try {
            pkgInfo = pm.getPackageInfo(pkgName, 0);
            String ver = BuildConfig.VERSION_NAME;
            version_name.setText("Version "+pkgInfo.versionName);
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //version_name.setText(pkgInfo.versionName);

    }

    @Override
    protected void onResume() {
        super.onResume();
        handler = new Handler();
        handler.postDelayed(runnable, 1000);
    }

    private void grantNotificationPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){

        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                resultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }

    }

    private final ActivityResultLauncher<String> resultLauncher =  registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            //Functions.showToast(getContext(), "Permission granted", FancyToast.SUCCESS);
        } else {
           // Functions.showToast(getContext(), "Permission denied", FancyToast.ERROR);
        }
    });

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            if (Functions.getPrefValue(getContext(), Constants.kIsSignIn).equalsIgnoreCase("1")) {

//                getProfileAPI(false);
//                Intent intent = new Intent(getContext(), CustomerMainTabsActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
            }
            else {
//                Intent i = new Intent(getContext(), LoginActivity.class);
//                startActivity(i);
//                finish();
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if(handler != null) {
            handler.removeCallbacks(runnable);
        }

    }
}
