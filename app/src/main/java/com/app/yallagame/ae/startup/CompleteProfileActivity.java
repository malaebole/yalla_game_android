package com.app.yallagame.ae.startup;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.yallagame.ae.R;
import com.app.yallagame.ae.activities.HomeActivity;
import com.app.yallagame.ae.activities.MainActivity;
import com.app.yallagame.ae.adapters.AvatarGridAdapter;
import com.app.yallagame.ae.adapters.GamesGridAdapter;
import com.app.yallagame.ae.base.BaseActivity;
import com.app.yallagame.ae.databinding.ActivityCompleteProfileBinding;
import com.app.yallagame.ae.dialogs.SelectionListDialog;
import com.app.yallagame.ae.models.Avatar;
import com.app.yallagame.ae.models.Country;
import com.app.yallagame.ae.models.Games;
import com.app.yallagame.ae.models.SelectionList;
import com.app.yallagame.ae.models.UserInfo;
import com.app.yallagame.ae.util.AppManager;
import com.app.yallagame.ae.util.Constants;
import com.app.yallagame.ae.util.Functions;
import com.app.yallagame.ae.util.KeyboardUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.hbb20.CCPCountry;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompleteProfileActivity extends BaseActivity implements View.OnClickListener {
    private ActivityCompleteProfileBinding binding;
    private Boolean isUpdate = false;
    private String gender = "";
    private final List<Country> countryList = new ArrayList<>();
    private String selectedCountryId = "";
    private String src = "";
    private String selectedAvatarId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCompleteProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeStatusbarTransperant();


        Bundle bundle = getIntent().getExtras();
        if (bundle !=null){
            isUpdate = bundle.getBoolean("update");
        }

        if (isUpdate){
            binding.emailLay.setVisibility(View.VISIBLE);
            binding.usernameLay.setVisibility(View.VISIBLE);
        }else{
            binding.emailLay.setVisibility(View.GONE);
            binding.usernameLay.setVisibility(View.GONE);
        }

        CCPCountry.setDialogTitle(getString(R.string.select_country_region));
        CCPCountry.setSearchHintMessage(getString(R.string.search_hint));


        binding.btnBack.setOnClickListener(this);
        binding.btnBackText.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);
        binding.male.setOnClickListener(this);
        binding.female.setOnClickListener(this);
        binding.btnSelectAvatar.setOnClickListener(this);
        binding.selectCountry.setOnClickListener(this);
        binding.relDob.setOnClickListener(this);

        Glide.with(getApplicationContext()).load(R.drawable.temp_img).apply(RequestOptions.circleCropTransform()).into(binding.imgVu);

//        checkKeyboardListener();

        getAllCountries(true);
    }

//    private void checkKeyboardListener() {
//        KeyboardUtils.addKeyboardToggleListener(this, new KeyboardUtils.SoftKeyboardToggleListener() {
//            @Override
//            public void onToggleSoftKeyboard(boolean isVisible) {
//                if (isVisible) {
//                    binding.bottomLay.setVisibility(View.GONE);
//                }
//                else {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            final Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    //your code here
//                                    binding.bottomLay.setVisibility(View.VISIBLE);
//                                }
//                            }, 50);
//                        }
//                    });
//                }
//            }
//        });
//    }


    ActivityResultLauncher<Intent> avatarResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {

                boolean avatarSelected = result.getData().getExtras().getBoolean("is_selected");
                if (avatarSelected){
                    src = result.getData().getExtras().getString("avatar");
                    selectedAvatarId = result.getData().getExtras().getString("avatar_id");
                    Glide.with(getApplicationContext())
                            .load(src)
                            .apply(RequestOptions.circleCropTransform())
                            .into(binding.imgVu);
                }
            }
        }
    });


    @Override
    public void onClick(View v) {
        if (v == binding.btnBack || v == binding.btnBackText){
            finish();
        }
        else if (v == binding.btnNext) {
            if (binding.etPhone.getText().toString().isEmpty()){
                Functions.showToast(getContext(), "Phone cannot be empty!", FancyToast.ERROR);
                return;
            }
            if (src.isEmpty()){
                Functions.showToast(getContext(), "Please select avatar!", FancyToast.ERROR);
                return;
            }

            Intent intent = new Intent(getContext(), GamesActivity.class);
            intent.putExtra("gender", gender);
            intent.putExtra("dob", binding.tvDob.getText().toString());
            intent.putExtra("phone", binding.etPhone.getText().toString());
            intent.putExtra("avatar", selectedAvatarId);
            intent.putExtra("country", selectedCountryId);

            startActivity(intent);
            finish();
        }
        else if (v == binding.male) {
            populateMale();
        }
        else if (v == binding.female) {
            populateFemale();
        }
        else if (v == binding.btnSelectAvatar) {
            selectAvatar();
        }

//        else if (v == binding.btnComplete) {
//            //Final Step
//            completeSignup();
//        }


        else if (v == binding.selectCountry) {
            choseCountry();
        }
        else if (v == binding.relDob) {
            choseDob();
        }


    }

    private void choseDob() {

        final Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                android.R.style.Theme_DeviceDefault_Dialog,
                null,
                currentYear, currentMonth, currentDay
        );

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000); // if you want to enable future dates remove this line

        datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "OK", (dialog, which) -> {
            int selectedYear = datePickerDialog.getDatePicker().getYear();
            int selectedMonth = datePickerDialog.getDatePicker().getMonth();
            int selectedDay = datePickerDialog.getDatePicker().getDayOfMonth();

            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(selectedYear, selectedMonth, selectedDay);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
            String formattedDate = dateFormat.format(selectedDate.getTime());

            binding.tvDob.setText(formattedDate);
        });

        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> {
            // Do nothing or handle cancel action
        });

        datePickerDialog.show();

    }


    private void choseCountry() {
        List<SelectionList> selectionList = new ArrayList<>();
        for (int i = 0; i < countryList.size(); i++) {
            selectionList.add(new SelectionList(String.valueOf(i), countryList.get(i).getName(), countryList.get(i).getFlag(),  countryList.get(i).getCode()));
        }
        SelectionListDialog dialog = new SelectionListDialog(getContext(), getString(R.string.select_country), false);
        dialog.setLists(selectionList);
        dialog.setShowSearch(true);
        dialog.setOnItemSelected(new SelectionListDialog.OnItemSelected() {
            @Override
            public void selectedItem(List<SelectionList> selectedItems) {
                SelectionList item = selectedItems.get(0);
                Country country = countryList.get(Integer.parseInt(item.getId()));
                binding.selectCountry.setText(country.getName());
                selectedCountryId = String.valueOf(country.getId());
                Functions.setSelectedCountry(getContext(), selectedCountryId);
                Glide.with(getApplicationContext()).load(country.getFlag()).into(binding.flagImg);
                Glide.with(getApplicationContext()).load(country.getFlag()).into(binding.codeFlagImg);
                binding.countryCode.setText(country.getCode());
            }
        });
        dialog.show();
    }

    private void selectAvatar() {
        Intent intent = new Intent(getContext(), AvatarActivity.class);
        avatarResultLauncher.launch(intent);
    }

    private void populateMale(){
        binding.male.setBackground(getContext().getResources().getDrawable(R.drawable.rounded_corner_bg_pink_selected));
        binding.maleIc.setImageResource(R.drawable.male_s_ic);
        binding.tvMale.setTextColor(getContext().getResources().getColor(R.color.pinkColor));

        binding.female.setBackground(getContext().getResources().getDrawable(R.drawable.rounded_corner_bg_black));
        binding.femaleIc.setImageResource(R.drawable.female_ns_ic);
        binding.tvFemale.setTextColor(getContext().getResources().getColor(R.color.lightGreyTextColor));
        gender = "male";


    }

    private void populateFemale(){
        binding.female.setBackground(getContext().getResources().getDrawable(R.drawable.rounded_corner_bg_pink_selected));
        binding.femaleIc.setImageResource(R.drawable.female_s_ic);
        binding.tvFemale.setTextColor(getContext().getResources().getColor(R.color.pinkColor));

        binding.male.setBackground(getContext().getResources().getDrawable(R.drawable.rounded_corner_bg_black));
        binding.maleIc.setImageResource(R.drawable.male_ns_ic);
        binding.tvMale.setTextColor(getContext().getResources().getColor(R.color.lightGreyTextColor));
        gender = "female";
    }

    private void getAllCountries(boolean isLoader) {
        KProgressHUD hud = isLoader ? Functions.showLoader(getContext()) : null;
        Call<ResponseBody> call = AppManager.getInstance().apiInterface.getAllCountries();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Functions.hideLoader(hud);
                if (response.body() != null) {
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getInt(Constants.kStatus) == Constants.kSuccessCode) {
                            JSONArray country = object.getJSONArray(Constants.kData);
                            Gson gson = new Gson();
                            countryList.clear();
                            for (int i = 0; i < country.length(); i++) {
                                countryList.add(gson.fromJson(country.get(i).toString(), Country.class));
                            }
                        } else {
                            Functions.showToast(getContext(), object.getString(Constants.kMsg), FancyToast.ERROR);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Functions.showToast(getContext(), e.getLocalizedMessage(), FancyToast.ERROR);
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