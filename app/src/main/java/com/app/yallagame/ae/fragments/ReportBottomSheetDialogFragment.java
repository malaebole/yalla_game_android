package com.app.yallagame.ae.fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.yallagame.ae.R;
import com.app.yallagame.ae.databinding.FragmentReportBottomSheetDialogBinding;
import com.app.yallagame.ae.databinding.FragmentSocialBottomSheetDialogBinding;


public class ReportBottomSheetDialogFragment extends DialogFragment implements View.OnClickListener{

    private FragmentReportBottomSheetDialogBinding binding;
    // private String incomeId = "";
    private ResultDialogCallback dialogCallback;
//    private IncomeDetailsModel incomeDetailsModel;

    private boolean isLogin;



    public ReportBottomSheetDialogFragment(Boolean isLogin) {
        this.isLogin = isLogin;
    }

    public void setDialogCallback(ResultDialogCallback dialogCallback) {
        this.dialogCallback = dialogCallback;
    }

    public ReportBottomSheetDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTransparentStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReportBottomSheetDialogBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        binding.btnClose.setOnClickListener(this);
        binding.btnReport.setOnClickListener(this);
        binding.btnRemove.setOnClickListener(this);
        binding.btnBlock.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        if (v == binding.btnClose){
            dismiss();
        }
        else if (v == binding.btnReport) {
            dialogCallback.didReportClicked(this);

        }
        else if (v == binding.btnRemove){
            dialogCallback.didRemoveClicked(this);
        }
        else if (v == binding.btnBlock){
            dialogCallback.didBlockClicked(this);
        }

    }


    public interface ResultDialogCallback {
        void didReportClicked(DialogFragment df);
        void didRemoveClicked(DialogFragment df);
        void didBlockClicked(DialogFragment df);
    }
}