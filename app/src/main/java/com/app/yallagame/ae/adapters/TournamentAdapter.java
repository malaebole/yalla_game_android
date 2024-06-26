package com.app.yallagame.ae.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.yallagame.ae.R;
import com.app.yallagame.ae.models.NotificationList;
import com.app.yallagame.ae.models.Tournament;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.List;

public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.ViewHolder> {

    private final Context context;
    private final List<Tournament> list;
    private ItemClickListener itemClickListener;

    public TournamentAdapter(Context context, List<Tournament> list) {
        this.context = context;
        this.list = list;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tournament_vu, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        NotificationList notification = list.get(position);
//        holder.tvDesc.setText(notification.getMessage());
//        holder.tvDate.setText(notification.getTime());
//        if (notification.getIsRead().equalsIgnoreCase("1")) {
//            holder.indicatorVu.setVisibility(View.GONE);
//        }
//        else {
//            holder.indicatorVu.setVisibility(View.VISIBLE);
//        }
//
//        binderHelper.bind(holder.swipeLayout, String.valueOf(position));
//
//        holder.main.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                itemClickListener.OnItemClick(v, holder.getAdapterPosition());
//            }
//        });
//
//        holder.deleteLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                itemClickListener.OnDeleteClick(v, holder.getAdapterPosition());
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

//        TextView tvDesc, tvDate;
//        RelativeLayout indicatorVu, main;
//        SwipeRevealLayout swipeLayout;
//        FrameLayout deleteLayout;

        ViewHolder(View itemView) {
            super(itemView);

//            indicatorVu = itemView.findViewById(R.id.indicator_vu);
//            main = itemView.findViewById(R.id.main);
//            tvDesc = itemView.findViewById(R.id.tv_desc);
//            tvDate = itemView.findViewById(R.id.tv_date);
//            swipeLayout = itemView.findViewById(R.id.swipe_layout);
//            deleteLayout = itemView.findViewById(R.id.delete_layout);

        }
    }

    public interface ItemClickListener {
        void ItemClick(View v, int pos);
    }
}