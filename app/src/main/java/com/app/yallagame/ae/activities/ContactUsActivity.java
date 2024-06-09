package com.app.yallagame.ae.activities;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.yallagame.ae.R;
import com.app.yallagame.ae.base.BaseActivity;
import com.app.yallagame.ae.databinding.ActivityContactUsBinding;
import com.app.yallagame.ae.databinding.ActivityFollowersBinding;

public class ContactUsActivity extends BaseActivity implements View.OnClickListener {

    private ActivityContactUsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeStatusbarTransperant();

        binding.btnBack.setOnClickListener(this);
        binding.btnBackText.setOnClickListener(this);


//        Glide.with(getApplicationContext()).load(R.drawable.temp_img).apply(RequestOptions.circleCropTransform()).into(binding.imgVu);


    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnBack || v == binding.btnBackText){
            finish();
        }

    }
}