package com.app.yallagame.ae.fragments;

import android.graphics.Rect;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.yallagame.ae.R;
import com.app.yallagame.ae.activities.HomeActivity;
import com.app.yallagame.ae.base.BaseFragment;
import com.app.yallagame.ae.databinding.FragmentChatBinding;
import com.app.yallagame.ae.util.AppManager;


public class ChatFragment extends BaseFragment implements View.OnClickListener {

    private final boolean isPlayed = false;
    //  private final List<OlePlayerMatch> upcomingList = new ArrayList<>();
    // private final List<OlePlayerMatch> playedList = new ArrayList<>();
    //  private OleMatchListAdapter adapter;
    private FragmentChatBinding binding;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.imgVuMenu.setImageResource(R.drawable.menu_ic);
        binding.tvTitle.setTextColor(getResources().getColor(R.color.whiteColor));
        binding.imgVuNotif.setImageResource(R.drawable.noti_ic);


//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        binding.recyclerVu.setLayoutManager(layoutManager);
//        adapter = new OleMatchListAdapter(getContext(), upcomingList);
//        adapter.setItemClickListener(clickListener);
//        binding.recyclerVu.setAdapter(adapter);

        //upcomingClicked();


        binding.relMenu.setOnClickListener(this);
        binding.relNotif.setOnClickListener(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    private final OleMatchListAdapter.ItemClickListener clickListener = new OleMatchListAdapter.ItemClickListener() {
//        @Override
//        public void itemClicked(View view, int pos) {
//            OlePlayerMatch olePlayerMatch;
//            if (isPlayed) {
//                olePlayerMatch = playedList.get(pos);
//            }
//            else {
//                olePlayerMatch = upcomingList.get(pos);
//            }
//            if (olePlayerMatch.getCreatedBy().getId().equalsIgnoreCase(Functions.getPrefValue(getContext(), Constants.kUserID))) {
//                if (olePlayerMatch.getBookingType().equalsIgnoreCase(Constants.kNormalBooking)) {
//                    Intent intent = new Intent(getContext(), OleNormalBookingDetailActivity.class);
//                    intent.putExtra("booking_id", olePlayerMatch.getBookingId());
//                    startActivity(intent);
//                }
//                else if (olePlayerMatch.getBookingType().equalsIgnoreCase(Constants.kFriendlyGame)) {
//                    Intent intent = new Intent(getContext(), OleGameBookingDetailActivity.class);
//                    intent.putExtra("booking_id", olePlayerMatch.getBookingId());
//                    startActivity(intent);
//                }
//                else {
//                    if (Functions.getPrefValue(getContext(), Constants.kAppModule).equalsIgnoreCase(Constants.kPadelModule)) {
//                        Intent intent = new Intent(getContext(), OlePadelMatchBookingDetailActivity.class);
//                        intent.putExtra("booking_id", olePlayerMatch.getBookingId());
//                        startActivity(intent);
//                    }
//                    else {
//                        Intent intent = new Intent(getContext(), OleMatchBookingDetailActivity.class);
//                        intent.putExtra("booking_id", olePlayerMatch.getBookingId());
//                        startActivity(intent);
//                    }
//                }
//            }
//            else {
//                if (olePlayerMatch.getBookingType().equalsIgnoreCase(Constants.kFriendlyGame)) {
//                    Intent intent = new Intent(getContext(), OleGameDetailActivity.class);
//                    intent.putExtra("booking_id", olePlayerMatch.getBookingId());
//                    startActivity(intent);
//                }
//                else {
//                    if (Functions.getPrefValue(getContext(), Constants.kAppModule).equalsIgnoreCase(Constants.kPadelModule)) {
//                        Intent intent = new Intent(getContext(), OlePadelMatchDetailActivity.class);
//                        intent.putExtra("booking_id", olePlayerMatch.getBookingId());
//                        startActivity(intent);
//                    }
//                    else {
//                        Intent intent = new Intent(getContext(), OleMatchDetailActivity.class);
//                        intent.putExtra("booking_id", olePlayerMatch.getBookingId());
//                        startActivity(intent);
//                    }
//                }
//            }
//        }
//
//        @Override
//        public void joinClicked(View view, int pos) {
//
//        }
//
//        @Override
//        public void acceptClicked(View view, int pos) {
//
//        }
//
//        @Override
//        public void challengeClicked(View view, int pos) {
//
//        }
//    };

    @Override
    public void onResume() {
        super.onResume();
        setBadgeValue();
//        if (isPlayed) {
//            getMatchListAPI(playedList.isEmpty());
//        }
//        else {
//            getMatchListAPI(upcomingList.isEmpty());
//        }
    }

    public void setBadgeValue() {
        if (AppManager.getInstance().notificationCount > 0) {
            binding.toolbarBadge.setVisibility(View.VISIBLE);
            binding.toolbarBadge.setNumber(AppManager.getInstance().notificationCount);
        }
        else  {
            binding.toolbarBadge.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.relMenu) {
            menuClicked();
        }
        else if (v == binding.relNotif) {
            notifClicked();
        }
    }

    private void menuClicked() {
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).menuClicked();
        }
    }

    private void notifClicked() {
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).notificationsClicked();
        }
    }

//    private void upcomingClicked() {
//        isPlayed = false;
//        adapter.setDataSource(upcomingList);
//    }

//    private void playedClicked() {
//        isPlayed = true;
//        adapter.setDataSource(playedList);
//    }

}
