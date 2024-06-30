package com.app.yallagame.ae.startup;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.app.yallagame.ae.databinding.ActivityGamesBinding;
import com.app.yallagame.ae.models.Avatar;
import com.app.yallagame.ae.models.Country;
import com.app.yallagame.ae.models.Games;
import com.app.yallagame.ae.models.UserInfo;
import com.app.yallagame.ae.util.AppManager;
import com.app.yallagame.ae.util.Constants;
import com.app.yallagame.ae.util.Functions;
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

public class GamesActivity extends BaseActivity implements View.OnClickListener {

    private ActivityGamesBinding binding;
    private final List<Games> gamesList = new ArrayList<>();
    private GamesGridAdapter gamesAdapter;
    private String gender = "", dob = "", phone = "",  avatar = "",  country = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGamesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeStatusbarTransperant();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            gender = bundle.getString("gender", "");
            dob = bundle.getString("dob", "");
            phone = bundle.getString("phone", "");
            avatar = bundle.getString("avatar", "");
            country = bundle.getString("country", "");

        }


        getGames(true);

        GridLayoutManager avatarGridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        binding.gamesRecyclerVu.setLayoutManager(avatarGridLayoutManager);
        gamesAdapter = new GamesGridAdapter(getContext(), gamesList, false);
        gamesAdapter.setItemClickListener(itemClickListener);
        binding.gamesRecyclerVu.setAdapter(gamesAdapter);

        binding.btnComplete.setOnClickListener(this);
        binding.topBar.setOnClickListener(this);

    }

    GamesGridAdapter.ItemClickListener itemClickListener = new GamesGridAdapter.ItemClickListener() {
        @Override
        public void itemClicked(View view, int pos) {
            gamesAdapter.selectPos(pos);
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
        else if (v == binding.btnComplete) {

            List<Games> list = gamesAdapter.getSelectedList();
            if (list.size() < 3) {
                Functions.showToast(getContext(), "Please select at least 3 games.", FancyToast.ERROR);
                return;
            }

            List<Integer> selectedGameIds = new ArrayList<>();
            for (Games game : list) {
                selectedGameIds.add(game.getId());
            }

            String gamesJson = new Gson().toJson(selectedGameIds);
            completeProfile(gender, dob, phone, avatar, country, gamesJson);

        }
    }

    private void getGames(boolean isLoader) {
        KProgressHUD hud = isLoader ? Functions.showLoader(getContext()) : null;
        Call<ResponseBody> call = AppManager.getInstance().apiInterface.getGames();
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
                            gamesList.clear();
                            for (int i = 0; i < avatar.length(); i++) {
                                gamesList.add(gson.fromJson(avatar.get(i).toString(), Games.class));
                            }
                            gamesAdapter.notifyDataSetChanged();
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

    private void completeProfile(String gender, String date_of_birth, String phone, String avatar, String country, String games) {
//        KProgressHUD hud = Functions.showLoader(getContext(), "Image processing");
//        Call<ResponseBody> call = AppManager.getInstance().apiInterface.completeProfile(gender, date_of_birth, phone, avatar, country, games);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Functions.hideLoader(hud);
//                if (response.body() != null) {
//                    try {
//                        JSONObject object = new JSONObject(response.body().string());
//                        if (object.getInt(Constants.kStatus) == Constants.kSuccessCode) {
//                            Functions.showToast(getContext(), object.getString(Constants.kMsg), FancyToast.SUCCESS);
//                            JSONObject data = object.getJSONObject(Constants.kData);
//
//
//                        }
//                        else {
//                            Functions.showToast(getContext(), object.getString(Constants.kMsg), FancyToast.ERROR);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Functions.showToast(getContext(), e.getLocalizedMessage(), FancyToast.ERROR);
//                    }
//                } else if (response.errorBody() != null) {
//                    try {
//                        JSONObject errorObject = new JSONObject(response.errorBody().string());
//                        Functions.showToast(getContext(), errorObject.getString(Constants.kMsg), FancyToast.ERROR);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Functions.showToast(getContext(), getString(R.string.error_occured), FancyToast.ERROR);
//                    }
//                } else {
//                    Functions.showToast(getContext(), getString(R.string.error_occured), FancyToast.ERROR);
//                }
//
//            }
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Functions.hideLoader(hud);
//                if (t instanceof UnknownHostException) {
//                    Functions.showToast(getContext(), getString(R.string.check_internet_connection), FancyToast.ERROR);
//                } else {
//                    Functions.showToast(getContext(), t.getLocalizedMessage(), FancyToast.ERROR);
//                }
//            }
//        });
    }

}