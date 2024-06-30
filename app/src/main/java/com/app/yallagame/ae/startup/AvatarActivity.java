package com.app.yallagame.ae.startup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.yallagame.ae.R;
import com.app.yallagame.ae.adapters.AvatarGridAdapter;
import com.app.yallagame.ae.adapters.GamesGridAdapter;
import com.app.yallagame.ae.base.BaseActivity;
import com.app.yallagame.ae.databinding.ActivityAvatarBinding;
import com.app.yallagame.ae.models.Avatar;
import com.app.yallagame.ae.models.Country;
import com.app.yallagame.ae.models.Games;
import com.app.yallagame.ae.util.AppManager;
import com.app.yallagame.ae.util.Constants;
import com.app.yallagame.ae.util.Functions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvatarActivity extends BaseActivity implements View.OnClickListener {

    private ActivityAvatarBinding binding;
    private final List<Avatar> avatarList = new ArrayList<>();
    private AvatarGridAdapter adapter;
    private String avatarId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAvatarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeStatusbarTransperant();

        getAllAvatars(true);

        GridLayoutManager avatarGridLayoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
        binding.avatarRecyclerVu.setLayoutManager(avatarGridLayoutManager);
        adapter = new AvatarGridAdapter(getContext(), avatarList, false);
        adapter.setItemClickListener(avatarItemClickListener);
        binding.avatarRecyclerVu.setAdapter(adapter);


        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent();
                intent.putExtra("is_selected", false);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        binding.btnConfirm.setOnClickListener(this);
        binding.topBar.setOnClickListener(this);

    }

    AvatarGridAdapter.ItemClickListener avatarItemClickListener = new AvatarGridAdapter.ItemClickListener() {
        @Override
        public void itemClicked(View view, int pos) {
            adapter.isSelected(avatarList.get(pos).getId());
            avatarId = String.valueOf(avatarList.get(pos).getId());

        }
    };

    @Override
    public void onClick(View v) {
        if (v == binding.topBar){
            Intent intent = new Intent();
            intent.putExtra("is_selected", false);
            setResult(RESULT_OK, intent);
            finish();
        }
         else if (v == binding.btnConfirm) {
            if (avatarId.isEmpty()){
                Functions.showToast(getContext(), "Please select avatar.", FancyToast.ERROR);
                return;
            }
            for (int i=0; i<avatarList.size(); i++){
                if (avatarId.equalsIgnoreCase(String.valueOf(avatarList.get(i).getId()))){
                    Intent intent = new Intent();
                    intent.putExtra("is_selected", true);
                    intent.putExtra("avatar", avatarList.get(i).getSrc());
                    intent.putExtra("avatar_id", avatarList.get(i).getId());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        }
    }

    @NonNull
    @Override
    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        return super.getOnBackInvokedDispatcher();
    }

    private void getAllAvatars(boolean isLoader) {
        KProgressHUD hud = isLoader ? Functions.showLoader(getContext()) : null;
        Call<ResponseBody> call = AppManager.getInstance().apiInterface.getAllAvatars();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Functions.hideLoader(hud);
                if (response.body() != null) {
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getInt(Constants.kStatus) == Constants.kSuccessCode) {
                            JSONArray avatar = object.getJSONArray(Constants.kData);
                            Gson gson = new Gson();
                            avatarList.clear();
                            for (int i = 0; i < avatar.length(); i++) {
                                avatarList.add(gson.fromJson(avatar.get(i).toString(), Avatar.class));
                            }
                            adapter.notifyDataSetChanged();
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