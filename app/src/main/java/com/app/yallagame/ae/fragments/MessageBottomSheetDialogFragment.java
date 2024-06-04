package com.app.yallagame.ae.fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.yallagame.ae.R;
import com.app.yallagame.ae.databinding.FragmentMessageBottomSheetDialogBinding;
import com.app.yallagame.ae.databinding.FragmentSocialBottomSheetDialogBinding;
import com.app.yallagame.ae.util.Functions;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MessageBottomSheetDialogFragment extends DialogFragment implements View.OnClickListener{

    private FragmentMessageBottomSheetDialogBinding binding;
    // private String incomeId = "";
    private ResultDialogCallback dialogCallback;
//    private IncomeDetailsModel incomeDetailsModel;

    private boolean isLogin;



    public MessageBottomSheetDialogFragment(Boolean isLogin) {
        this.isLogin = isLogin;
    }

    public void setDialogCallback(ResultDialogCallback dialogCallback) {
        this.dialogCallback = dialogCallback;
    }

    public MessageBottomSheetDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTransparentStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMessageBottomSheetDialogBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        binding.btnClose.setOnClickListener(this);
        binding.btnChat.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        if (v == binding.btnClose){
            dismiss();
        }
        else if (v == binding.btnChat) {
            if (binding.etMsg.getText().toString().isEmpty()){
                Functions.showToast(getContext(), "Message cannot be empty", FancyToast.ERROR);
                return;
            }
            dialogCallback.didSubmitMsg(this, binding.etMsg.getText().toString());
        }
    }


    public interface ResultDialogCallback {
        void didSubmitMsg(DialogFragment df, String msg);
    }
}