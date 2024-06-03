package com.app.yallagame.ae.activities;


import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.app.yallagame.ae.adapters.NotificationListAdapter;
import com.app.yallagame.ae.base.BaseActivity;
import com.app.yallagame.ae.databinding.ActivityNotificationsBinding;
import com.app.yallagame.ae.models.NotificationList;
import com.app.yallagame.ae.util.Constants;
import com.app.yallagame.ae.util.Functions;
import com.baoyz.actionsheet.ActionSheet;
import java.util.ArrayList;
import java.util.List;


public class NotificationsActivity extends BaseActivity implements View.OnClickListener {

    private ActivityNotificationsBinding binding;
    private final List<NotificationList> oleNotificationList = new ArrayList<>();
    private NotificationListAdapter adapter;
    private final String clubId = "";
//    private RankClubAdapter oleRankClubAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.titleBar.toolbarTitle.setText("Notification");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerVu.setLayoutManager(layoutManager);
        adapter = new NotificationListAdapter(getContext(), oleNotificationList);
        adapter.setItemClickListener(clickListener);
        binding.recyclerVu.setAdapter(adapter);

//        LinearLayoutManager ageLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        binding.clubRecyclerVu.setLayoutManager(ageLayoutManager);
//        oleRankClubAdapter = new RankClubAdapter(getContext(), AppManager.getInstance().clubs, 0, false);
//        oleRankClubAdapter.setOnItemClickListener(clubClickListener);
//        binding.clubRecyclerVu.setAdapter(oleRankClubAdapter);

//        if (Functions.getPrefValue(getContext(), Constants.kUserType).equalsIgnoreCase(Constants.kOwnerType)) {
//            binding.clubRecyclerVu.setVisibility(View.VISIBLE);
//            if (AppManager.getInstance().clubs.size() > 0) {
//                clubId = AppManager.getInstance().clubs.get(0).getId();
//                getNotifications(true);
//            }
//        }
//        else {
//            binding.clubRecyclerVu.setVisibility(View.GONE);
//        }

        binding.titleBar.backBtn.setOnClickListener(this);
        binding.btnDelete.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
       // if (!Functions.getPrefValue(getContext(), Constants.kUserType).equalsIgnoreCase(Constants.kOwnerType)) {
          //  getNotifications(oleNotificationList.isEmpty());
       // }
    }

//    OleRankClubAdapter.OnItemClickListener clubClickListener = new OleRankClubAdapter.OnItemClickListener() {
//        @Override
//        public void OnItemClick(View v, int pos) {
//            oleRankClubAdapter.setSelectedIndex(pos);
//            clubId = AppManager.getInstance().clubs.get(pos).getId();
//            getNotifications(true);
//        }
//    };

    NotificationListAdapter.OnItemClickListener clickListener = new NotificationListAdapter.OnItemClickListener() {
        @Override
        public void OnItemClick(View v, int pos) {
            NotificationList notification = oleNotificationList.get(pos);
            if (!notification.getIsRead().equalsIgnoreCase("1")) {
               // readNotificationAPI(notification.getId());
                notification.setIsRead("1");
                adapter.notifyItemChanged(pos);
            }
            clickedItem(notification);
        }

        @Override
        public void OnDeleteClick(View v, int pos) {
            deleteItem(pos);
        }
    };

    private void deleteItem(int pos) {
        ActionSheet.createBuilder(getContext(), getSupportFragmentManager())
                .setCancelButtonTitle("Cancel")
                .setOtherButtonTitles("Delete notification")
                .setCancelableOnTouchOutside(true)
                .setListener(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
                        adapter.binderHelper.closeLayout(String.valueOf(pos));
                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        if (index == 0) {
                           // deleteNotificationAPI(true, oleNotificationList.get(pos).getId(), pos);
                        }
                    }
                }).show();
    }

    private void clickedItem(NotificationList notification) {
//        if (notification.getType().equalsIgnoreCase(Constants.kNewBookingNotification) ||
//                notification.getType().equalsIgnoreCase(Constants.kConfirmBookingByPlayerNotification) ||
//                notification.getType().equalsIgnoreCase(Constants.kCancelBookingByPlayerNotification)) {
//            if (Functions.getPrefValue(getContext(), Constants.kUserType).equalsIgnoreCase(Constants.kOwnerType)) {
//                if (notification.getBookingField().equalsIgnoreCase("padel")) {
//                    Intent intent = new Intent(getContext(), OlePadelBookingDetailActivity.class);
//                    intent.putExtra("booking_id", notification.getBookingId());
//                    startActivity(intent);
//                }
//                else {
//                    Intent intent = new Intent(getContext(), OleBookingDetailActivity.class);
//                    intent.putExtra("booking_id", notification.getBookingId());
//                    startActivity(intent);
//                }
//            }
//       }
//        else if (notification.getType().equalsIgnoreCase(Constants.kConfirmBookingByOwnerNotification) ||
//                notification.getType().equalsIgnoreCase(Constants.kCancelBookingByOwnerNotification) ||
//                notification.getType().equalsIgnoreCase(Constants.kBookingReminderNotification) ||
//                notification.getType().equalsIgnoreCase(Constants.kAcceptInvitationNotification) ||
//                notification.getType().equalsIgnoreCase(Constants.kRejectInvitationNotification) ||
//                notification.getType().equalsIgnoreCase(Constants.kCancelInvitationByReceiverNotification) ||
//                notification.getType().equalsIgnoreCase(Constants.kPublicChallengeCanceledByPlayerNotification) ||
//                notification.getType().equalsIgnoreCase(Constants.kNewGameRequestNotification) ||
//                notification.getType().equalsIgnoreCase(Constants.kInviteMorePlayersNotification) ||
//                notification.getType().equalsIgnoreCase(Constants.kPlayerCanceledAcceptedMatchNotification) ||
//                notification.getType().equalsIgnoreCase(Constants.kNewChallengeRequestNotification) ||
//                notification.getType().equalsIgnoreCase(Constants.kPrivateChallengeAcceptedNotification) ||
//                notification.getType().equalsIgnoreCase(Constants.kPrivateChallengeCanceledNotification)){
//            if (Functions.getPrefValue(getContext(), Constants.kUserType).equalsIgnoreCase(Constants.kPlayerType)) {
//                if (notification.getBookingType().equalsIgnoreCase(Constants.kNormalBooking)) {
//                    Intent intent = new Intent(getContext(), OleNormalBookingDetailActivity.class);
//                    intent.putExtra("booking_id", notification.getBookingId());
//                    startActivity(intent);
//                }
//                else if (notification.getBookingType().equalsIgnoreCase(Constants.kFriendlyGame)) {
//                    Intent intent = new Intent(getContext(), OleGameBookingDetailActivity.class);
//                    intent.putExtra("booking_id", notification.getBookingId());
//                    startActivity(intent);
//                }
//                else {
//                    Intent intent = new Intent(getContext(), OleMatchBookingDetailActivity.class);
//                    intent.putExtra("booking_id", notification.getBookingId());
//                    startActivity(intent);
//                }
//            }
//        }
//        else if (notification.getType().equalsIgnoreCase(Constants.kBookingAvailableNotification) ||
//                notification.getType().equalsIgnoreCase(Constants.kNewOfferNotification)) {
//            Intent intent = new Intent(getContext(), OleBookingActivity.class);
//            if (Functions.getPrefValue(getContext(), Constants.kUserType).equalsIgnoreCase(Constants.kPlayerType)) {
//                intent.putExtra("field_id", notification.getFieldId());
//            }
//            else {
//                intent.putExtra("field_id", "");
//            }
//            Gson gson = new Gson();
//            intent.putExtra("club", gson.toJson(notification.getClub()));
//            startActivity(intent);
//        }
//        else if (notification.getType().equalsIgnoreCase(Constants.kBookingCompleteNotification)) {
//            if (notification.getBookingType().equalsIgnoreCase(Constants.kFriendlyGame)) {
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                Fragment prev = getSupportFragmentManager().findFragmentByTag("RatingPagerDialogFragment");
//                if (prev != null) {
//                    fragmentTransaction.remove(prev);
//                }
//                fragmentTransaction.addToBackStack(null);
//                OleRatingPagerDialogFragment dialogFragment = new OleRatingPagerDialogFragment(notification.getBookingId());
//                dialogFragment.show(fragmentTransaction, "RatingPagerDialogFragment");
//            }
//            else {
//                Intent rateIntent = new Intent(getContext(), OleEmployeeRateActivity.class);
//                rateIntent.putExtra("booking_id", notification.getBookingId());
//                rateIntent.putExtra("club_id", notification.getClub().getId());
//                rateIntent.putExtra("is_rated", notification.getIsRated());
//                startActivity(rateIntent);
//            }
//        }
//        else if (notification.getType().equalsIgnoreCase(Constants.kAppUpdateNotification)) {
//            final String appPackageName = getPackageName();
//            try {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//            } catch (android.content.ActivityNotFoundException anfe) {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//            }
//        }
//        else if (notification.getType().equalsIgnoreCase(Constants.kCreatorCanceledAcceptedMatchNotification)) {
//            if (Functions.getPrefValue(getContext(), Constants.kUserType).equalsIgnoreCase(Constants.kPlayerType)) {
//                if (notification.getBookingType().equalsIgnoreCase(Constants.kFriendlyGame)) {
//                    Intent intent = new Intent(getContext(), OleGameDetailActivity.class);
//                    intent.putExtra("booking_id", notification.getBookingId());
//                    startActivity(intent);
//                } else {
//                    Intent intent = new Intent(getContext(), OleMatchDetailActivity.class);
//                    intent.putExtra("booking_id", notification.getBookingId());
//                    startActivity(intent);
//                }
//            }
//        }
//        else if (notification.getType().equalsIgnoreCase(Constants.kPublicChallengeAcceptedNotification) ||
//                notification.getType().equalsIgnoreCase(Constants.kPublicChallengeCanceledNotification) ||
//                notification.getType().equalsIgnoreCase(Constants.kNewInvitationRequestNotification)) {
//            if (Functions.getPrefValue(getContext(), Constants.kUserType).equalsIgnoreCase(Constants.kPlayerType)) {
//                Intent intent = new Intent(getContext(), OleMatchDetailActivity.class);
//                intent.putExtra("booking_id", notification.getBookingId());
//                startActivity(intent);
//            }
//        }
//        else if (notification.getType().equalsIgnoreCase(Constants.kGameRequestAcceptedNotification) ||
//                notification.getType().equalsIgnoreCase(Constants.kGameRequestCanceledNotification) ||
//                notification.getType().equalsIgnoreCase(Constants.kJoinFriendlyGameNotification) ||
//                notification.getType().equalsIgnoreCase(Constants.kNewCaptainGameNotification)) {
//            if (Functions.getPrefValue(getContext(), Constants.kUserType).equalsIgnoreCase(Constants.kPlayerType)) {
//                Intent intent = new Intent(getContext(), OleGameDetailActivity.class);
//                intent.putExtra("booking_id", notification.getBookingId());
//                startActivity(intent);
//            }
//        }
//        else if (notification.getType().equalsIgnoreCase(Constants.kShoppingNotification)) {
//            if (Functions.getPrefValue(getContext(), Constants.kUserType).equalsIgnoreCase(Constants.kPlayerType)) {
//                Intent intent = new Intent(getContext(), ShopOrderDetailActivity.class);
//                intent.putExtra("order_id", notification.getOrderId());
//                intent.putExtra("is_from_checkout", false);
//                startActivity(intent);
//            }
//        }
//        else if (notification.getType().equalsIgnoreCase(Constants.kInvitationPadel) ||
//                notification.getType().equalsIgnoreCase(Constants.kInvitationRejectedByPlayer) ||
//                notification.getType().equalsIgnoreCase(Constants.kInvitationAcceptedByPlayer)) {
//            if (Functions.getPrefValue(getContext(), Constants.kAppModule).equalsIgnoreCase(Constants.kPadelModule)) {
//                Intent intent = new Intent(getContext(), OlePadelNormalBookingDetailActivity.class);
//                intent.putExtra("booking_id", notification.getBookingId());
//                startActivity(intent);
//            }
//        }
//        else if (notification.getType().equalsIgnoreCase(Constants.kPadelChallengeRejected) ||
//                notification.getType().equalsIgnoreCase(Constants.kAcceptedChallengeCanceledByCreator) ||
//                notification.getType().equalsIgnoreCase(Constants.kPadelChallengeAccepted) ||
//                notification.getType().equalsIgnoreCase(Constants.kPartnerForChallenge)) {
//            if (Functions.getPrefValue(getContext(), Constants.kAppModule).equalsIgnoreCase(Constants.kPadelModule)) {
//                Intent intent = new Intent(getContext(), OlePadelMatchDetailActivity.class);
//                intent.putExtra("booking_id", notification.getBookingId());
//                startActivity(intent);
//            }
//        }
//        else if (notification.getType().equalsIgnoreCase(Constants.kChallengePadel) ||
//                notification.getType().equalsIgnoreCase(Constants.kPadelChallengeCanceled) ||
//                notification.getType().equalsIgnoreCase(Constants.kAcceptedChallengeCanceledByPlayer)) {
//            if (Functions.getPrefValue(getContext(), Constants.kAppModule).equalsIgnoreCase(Constants.kPadelModule)) {
//                Intent intent = new Intent(getContext(), OlePadelMatchBookingDetailActivity.class);
//                intent.putExtra("booking_id", notification.getBookingId());
//                startActivity(intent);
//            }
//        }
//        else if (notification.getType().equalsIgnoreCase(Constants.kLevelCompleted)) {
//            Intent intent = new Intent(getContext(), OlePlayerProfileActivity.class);
//            intent.putExtra("player_id", notification.getSender().getId());
//            startActivity(intent);
//        }
//        else if (notification.getType().equalsIgnoreCase(Constants.kCompletePayment)) {
//            if (Functions.getPrefValue(getContext(), Constants.kAppModule).equalsIgnoreCase(Constants.kPadelModule)) {
//                if (notification.getBookingType().equalsIgnoreCase(Constants.kNormalBooking)) {
//                    Intent intent = new Intent(getContext(), OlePadelNormalBookingDetailActivity.class);
//                    intent.putExtra("booking_id", notification.getBookingId());
//                    startActivity(intent);
//                }
//                else {
//                    Intent intent = new Intent(getContext(), OlePadelMatchBookingDetailActivity.class);
//                    intent.putExtra("booking_id", notification.getBookingId());
//                    startActivity(intent);
//                }
//            }
//        }
//        else if (notification.getType().equalsIgnoreCase(Constants.kYouWonTheMatch)) {
//            getMatchResultAPI(false, notification.getBookingId());
//        }
//        else if (notification.getType().equalsIgnoreCase(Constants.kYouWonTheMatchPadel)) {
//            getMatchResultAPI(true, notification.getBookingId());
//        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.titleBar.backBtn) {
            finish();
        }
        else if (v == binding.btnDelete) {
            deleteClicked();
        }
    }

    private void deleteClicked() {
        if (oleNotificationList.size() == 0) {
            return;
        }
        ActionSheet.createBuilder(getContext(), getSupportFragmentManager())
                .setCancelButtonTitle("Cancel")
                .setOtherButtonTitles("Read All Notifications", "Delete all notifications")
                .setCancelableOnTouchOutside(true)
                .setListener(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        if (index == 0) {
                           // readAllNotificationAPI(true);
                        }
                        else if (index == 1) {
                            //deleteAllNotificationAPI(true);
                        }
                    }
                }).show();
    }
}
