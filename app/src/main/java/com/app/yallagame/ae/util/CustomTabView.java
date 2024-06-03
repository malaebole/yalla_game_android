package com.app.yallagame.ae.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.yallagame.ae.R;

public class CustomTabView extends LinearLayout {

    public ImageView iconVu, line, imgBg;

    public CustomTabView(Context context) {
        super(context);
        LayoutInflater  mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.custom_tab, this, true);
        iconVu = findViewById(R.id.img_vu);
        line = findViewById(R.id.line);
        imgBg = findViewById(R.id.img_bg);
    }
}
