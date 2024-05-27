package com.app.yallagame.ae;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.app.yallagame.ae.util.ThemeUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.applyTheme(this);
        setContentView(R.layout.activity_main);


        Switch themeSwitch = findViewById(R.id.theme_switch);

        themeSwitch.setChecked(ThemeUtils.isDarkTheme(this));
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ThemeUtils.toggleTheme(MainActivity.this);
                recreate();
            }
        });





    }
}