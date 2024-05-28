package com.app.yallagame.ae.startup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.app.yallagame.ae.R;
import com.app.yallagame.ae.adapters.AppIntroAdapter;
import com.app.yallagame.ae.base.BaseActivity;
import com.app.yallagame.ae.databinding.ActivityIntroSliderBinding;
import com.app.yallagame.ae.external.PagerIndicatorDecoration;
import com.app.yallagame.ae.models.AppIntro;

import java.util.ArrayList;
import java.util.List;

public class IntroSliderActivity extends BaseActivity implements View.OnClickListener {

    private ActivityIntroSliderBinding binding;
    private final List<AppIntro> introList = new ArrayList<>();
    private AppIntroAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroSliderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeStatusbarTransperant();

        introList.clear();
        for (int i = 0; i < 3; i++) {
            AppIntro item = new AppIntro();
            item.setName("Latest Feature in Streaming Your Favorite Channel");
            item.setPhoto(R.drawable.yalla_logo);
            introList.add(item);
        }


        binding.recyclerVu.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.recyclerVu);
        binding.recyclerVu.addItemDecoration(new PagerIndicatorDecoration());
        adapter = new AppIntroAdapter(getContext(), introList);
        binding.recyclerVu.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        binding.btnStart.setOnClickListener(this);


//        getCarouselApi();




//        tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE); // checkx
//        countryCode = tm.getNetworkCountryIso();
//
//        if (!countryCode.equalsIgnoreCase("ae")){
//            binding.btnSkip.setVisibility(View.GONE);
//        }else {
//            binding.btnSkip.setVisibility(View.VISIBLE);
//        }
    }



    @Override
    public void onClick(View v) {
        if (v == binding.btnStart) {
            Intent i = new Intent(getContext(), LoginActivity.class);
            startActivity(i);
        }

    }


//    private void getCarouselApi() {
//        Call<ResponseBody> call = AppManager.getInstance().apiInterface.getCarousel(Functions.getAppLang(getContext()));
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.body() != null) {
//                    try {
//                        JSONObject object = new JSONObject(response.body().string());
//                        if (object.getInt(Constants.kStatus) == Constants.kSuccessCode) {
//                            JSONArray arr = object.getJSONArray(Constants.kData);
//                            Gson gson = new Gson();
//                            introList.clear();
//                            for (int i = 0; i < arr.length(); i++) {
//                                introList.add(gson.fromJson(arr.get(i).toString(), AppIntro.class));
//                            }
//                            adapter.notifyDataSetChanged();
//                        }
//                        else {
//                            Functions.showToast(getContext(), object.getString(Constants.kMsg), FancyToast.ERROR);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Functions.showToast(getContext(), e.getLocalizedMessage(), FancyToast.ERROR);
//                    }
//                }
//                else {
//                    Functions.showToast(getContext(), getString(R.string.error_occured), FancyToast.ERROR);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                if (t instanceof UnknownHostException) {
//                    Functions.showToast(getContext(), getString(R.string.check_internet_connection), FancyToast.ERROR);
//                }
//                else {
//                    Functions.showToast(getContext(), t.getLocalizedMessage(), FancyToast.ERROR);
//                }
//            }
//        });
//    }
}