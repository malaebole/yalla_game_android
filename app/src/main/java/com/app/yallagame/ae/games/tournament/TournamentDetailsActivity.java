package com.app.yallagame.ae.games.tournament;

import android.os.Bundle;
import android.view.View;
import com.app.yallagame.ae.base.BaseActivity;
import com.app.yallagame.ae.databinding.ActivityTournamentDetailsBinding;


public class TournamentDetailsActivity extends BaseActivity implements View.OnClickListener {

    private ActivityTournamentDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTournamentDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeStatusbarTransperant();

        binding.btnBack.setOnClickListener(this);
        binding.btnBackText.setOnClickListener(this);
        binding.btnJoinTournament.setOnClickListener(this);
        binding.btnLeaveTournament.setOnClickListener(this);
        binding.btnChat.setOnClickListener(this);
        binding.fixture.setOnClickListener(this);
        binding.details.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnBack || v == binding.btnBackText) {
            finish();
        }
    }
}