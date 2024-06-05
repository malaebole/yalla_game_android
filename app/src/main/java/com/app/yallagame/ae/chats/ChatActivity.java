package com.app.yallagame.ae.chats;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.yallagame.ae.R;
import com.app.yallagame.ae.base.BaseActivity;
import com.app.yallagame.ae.databinding.ActivityAllGamesBinding;
import com.app.yallagame.ae.databinding.ActivityChatBinding;
import com.app.yallagame.ae.fragments.ReportBottomSheetDialogFragment;
import com.app.yallagame.ae.fragments.SocialBottomSheetDialogFragment;
import com.app.yallagame.ae.util.Functions;
import com.shashank.sony.fancytoastlib.FancyToast;

public class ChatActivity extends BaseActivity implements View.OnClickListener {

    private ActivityChatBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeStatusbarTransperant();

        binding.etMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.etMsg.getText().toString().isEmpty()){
                    binding.btnAttachment.setVisibility(View.VISIBLE);
                    binding.btnSend.setVisibility(View.GONE);
                }else{
                    binding.btnAttachment.setVisibility(View.GONE);
                    binding.btnSend.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding.etMsg.getLineCount() > 1) {
                    binding.msgLay.setBackgroundResource(R.drawable.rounded_corner_bg_black);
                } else {
                    binding.msgLay.setBackgroundResource(R.drawable.full_rounded_corner_bg_black);
                }
            }
        });
        binding.btnBack.setOnClickListener(this);
        binding.btnBackText.setOnClickListener(this);
        binding.btnSend.setOnClickListener(this);
        binding.btnAttachment.setOnClickListener(this);
        binding.reportMenu.setOnClickListener(this);
        binding.imgVu.setOnClickListener(this);
        binding.nameLay.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnBack || v == binding.btnBackText){
            finish();
        }
        else  if (v == binding.imgVu || v == binding.nameLay){
            viewProfile();
        }
        else  if (v == binding.reportMenu){
            reportClicked();
        }
        else  if (v == binding.btnSend){
            sendMessage();
        }
        else  if (v == binding.btnAttachment){
            sendAttachment();
        }
    }

    private void sendAttachment() {

    }

    private void sendMessage() {

    }

    private void reportClicked() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("ReportBottomSheetDialogFragment");
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }
        fragmentTransaction.addToBackStack(null);
        ReportBottomSheetDialogFragment dialogFragment = new ReportBottomSheetDialogFragment(false);

        dialogFragment.setDialogCallback(new ReportBottomSheetDialogFragment.ResultDialogCallback() {
            @Override
            public void didReportClicked(DialogFragment df) {
                Functions.showToast(getContext(), "Report Clicked", FancyToast.SUCCESS);

            }

            @Override
            public void didRemoveClicked(DialogFragment df) {
                Functions.showToast(getContext(), "Remove Clicked", FancyToast.SUCCESS);

            }

            @Override
            public void didBlockClicked(DialogFragment df) {
                Functions.showToast(getContext(), "Block Clicked", FancyToast.SUCCESS);

            }
        });

        dialogFragment.show(fragmentTransaction, "SocialBottomSheetDialogFragment");
    }

    private void viewProfile() {

    }
}