package com.app.yallagame.ae.fragments;

import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.app.yallagame.ae.R;
import com.app.yallagame.ae.databinding.FragmentSocialBottomSheetDialogBinding;

public class SocialBottomSheetDialogFragment extends DialogFragment implements View.OnClickListener{

    private FragmentSocialBottomSheetDialogBinding binding;
    // private String incomeId = "";
    private ResultDialogCallback dialogCallback;
//    private IncomeDetailsModel incomeDetailsModel;
    private String IncomeId = "";
    private String incomeFrom = "";
    private String Amount = "";
    private String Note = "";
    private String ReceiptUrl = "";
    private String bankName = "";
    private boolean paymentmethod;



    public SocialBottomSheetDialogFragment(String IncomeId, String incomeFrom, String Amount, String Note, String ReceiptUrl, Boolean paymentmethod,String bankName) {
        this.IncomeId = IncomeId;
        this.incomeFrom = incomeFrom;
        this.Amount = Amount;
        this.Note = Note;
        this.ReceiptUrl = ReceiptUrl;
        this.bankName = bankName;
        this.paymentmethod = paymentmethod;
    }

    public void setDialogCallback(ResultDialogCallback dialogCallback) {
        this.dialogCallback = dialogCallback;
    }

    public SocialBottomSheetDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTransparentStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSocialBottomSheetDialogBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        binding.btnClose.setOnClickListener(this);
        binding.btnGoogle.setOnClickListener(this);
        binding.btnFacebook.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        if (v == binding.btnClose){
            dismiss();
        }
        else if (v == binding.btnGoogle) {
            dialogCallback.didGoogleClicked(this);

        }
        else if (v == binding.btnFacebook){
            dialogCallback.didFacebookClicked(this);
        }

    }


    public interface ResultDialogCallback {
        void didGoogleClicked(DialogFragment df);
        void didFacebookClicked(DialogFragment df);
    }
}