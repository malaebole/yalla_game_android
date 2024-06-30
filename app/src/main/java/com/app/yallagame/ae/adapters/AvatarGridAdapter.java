package com.app.yallagame.ae.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.yallagame.ae.R;
import com.app.yallagame.ae.models.Avatar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class AvatarGridAdapter extends RecyclerView.Adapter<AvatarGridAdapter.ViewHolder> {

    private final Context context;

    private final List<Avatar> list;
    private ItemClickListener itemClickListener;
    private boolean setManualWidth = false;
    private int playersLimit = 0;
    private int selectedId;



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

    public void isSelected(int selectedId){
        this.selectedId = selectedId;
        notifyDataSetChanged();
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
        Avatar info = list.get(position);

        if (selectedId == info.getId()){

            Glide.with(context)
                    .load(info.getSrc())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.imgVu);
            holder.tick.setImageResource(R.drawable.selected_ic);

        }else{

            Glide.with(context).load(info.getSrc()).into(holder.imgVu);
            holder.tick.setImageResource(R.drawable.non_selected_ic);

        }

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.itemClicked(v, holder.getAdapterPosition());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgVu;
        ImageButton tick;
        RelativeLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgVu = itemView.findViewById(R.id.img_vu);
            tick = itemView.findViewById(R.id.tick);
            mainLayout = itemView.findViewById(R.id.rel_main_data);

        }
    }

    public interface ItemClickListener {
        void itemClicked(View view, int pos);
    }
}