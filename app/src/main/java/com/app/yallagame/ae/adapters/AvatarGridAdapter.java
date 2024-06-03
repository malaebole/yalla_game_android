package com.app.yallagame.ae.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.yallagame.ae.R;
import com.app.yallagame.ae.models.Avatar;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class AvatarGridAdapter extends RecyclerView.Adapter<AvatarGridAdapter.ViewHolder> {

    private final Context context;

    private final List<Avatar> list;
    private ItemClickListener itemClickListener;
    private boolean setManualWidth = false;
    private final boolean isFilter = false;
    private int playersLimit = 0;



    public AvatarGridAdapter(Context context, List<Avatar> list, boolean setManualWidth, int playersLimit) {
        this.context = context;
        this.list = list;
        this.setManualWidth = setManualWidth;
        this.playersLimit = playersLimit;
    }

    public AvatarGridAdapter(Context context, List<Avatar> list, boolean setManualWidth) {
        this(context, list, setManualWidth, 1000);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.avatar_vu, parent, false);
        if (setManualWidth) {
            setItemWidth(v, parent);
        }
        return new ViewHolder(v);
    }

    private void setItemWidth(View view, ViewGroup parent) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        params.width = (int)((parent.getMeasuredWidth() / 3) * 0.9);
        view.setLayoutParams(params);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Avatar info;
//        if (isFilter){
//            info = filteredPlayers.get(position);
//        }else{
//            info = list.get(position);
//        }
//        String[] arr = info.getNickName().split(" ");
//        if (arr.length > 0) {
//            holder.tvName.setText(arr[0]);
//        }
//        else {
//            holder.tvName.setText(info.getNickName());
//        }
//
//        holder.tvPhone.setText(info.getPhone());
//        holder.emojiVu.setVisibility(View.VISIBLE);
//        Glide.with(context).load(info.getEmojiUrl()).into(holder.emojiVu);
//        Glide.with(context).load(info.getBibUrl()).placeholder(R.drawable.shirtl).into(holder.shirt);
//
//        holder.addPlayer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (itemClickListener != null) {
//                    itemClickListener.itemClicked(v, holder.getAdapterPosition());
//                }
//            }
//        });


    }

    @Override
    public int getItemCount() {

        return list.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvPhone;
        ImageView emojiVu, shirt;
        ImageButton addPlayer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//
//            tvName = itemView.findViewById(R.id.tv_name);
//            tvPhone = itemView.findViewById(R.id.tv_phone);
//            emojiVu = itemView.findViewById(R.id.emoji_img_vu);
//            shirt = itemView.findViewById(R.id.shirt);
//            addPlayer = itemView.findViewById(R.id.add_player);
        }
    }

    public interface ItemClickListener {
        void itemClicked(View view, int pos);
    }
}