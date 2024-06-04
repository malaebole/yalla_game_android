package com.app.yallagame.ae.games;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.yallagame.ae.base.BaseActivity;
import com.app.yallagame.ae.databinding.ActivityFindAddFriendsBinding;
import com.app.yallagame.ae.fragments.MessageBottomSheetDialogFragment;
import com.app.yallagame.ae.fragments.SocialBottomSheetDialogFragment;
import com.app.yallagame.ae.util.Functions;
import com.shashank.sony.fancytoastlib.FancyToast;

public class FindAddFriendsActivity extends BaseActivity implements View.OnClickListener {

    private ActivityFindAddFriendsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFindAddFriendsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeStatusbarTransperant();

        binding.btnBack.setOnClickListener(this);
        binding.btnBackText.setOnClickListener(this);
        binding.btnChat.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnBack || v == binding.btnBackText){
            finish();
        }
        else if (v == binding.btnChat) {
            chatClicked();
        }
        else if (v == binding.btnNext) {
            bringNextPlayer();
        }
    }

    private void bringNextPlayer() {

    }

    private void chatClicked() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("MessageBottomSheetDialogFragment");
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }
        fragmentTransaction.addToBackStack(null);
        MessageBottomSheetDialogFragment dialogFragment = new MessageBottomSheetDialogFragment(false);

        dialogFragment.setDialogCallback(new MessageBottomSheetDialogFragment.ResultDialogCallback() {
            @Override
            public void didSubmitMsg(DialogFragment df, String msg) {

            }
        });

        dialogFragment.show(fragmentTransaction, "MessageBottomSheetDialogFragment");


    }
}