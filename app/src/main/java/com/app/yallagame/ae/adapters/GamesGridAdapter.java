package com.app.yallagame.ae.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.yallagame.ae.R;
import com.app.yallagame.ae.models.Avatar;
import com.app.yallagame.ae.models.Games;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class GamesGridAdapter extends RecyclerView.Adapter<GamesGridAdapter.ViewHolder> {

    private final Context context;

    private final List<Games> list;
    private ItemClickListener itemClickListener;
    private boolean setManualWidth = false;
    private final boolean isFilter = false;
    private int playersLimit = 0;
    private String selectedId = "";
    private final List<Games> selectedList = new ArrayList<>();


    public GamesGridAdapter(Context context, List<Games> list, boolean setManualWidth, int playersLimit) {
        this.context = context;
        this.list = list;
        this.setManualWidth = setManualWidth;
        this.playersLimit = playersLimit;
    }

    public GamesGridAdapter(Context context, List<Games> list, boolean setManualWidth) {
        this(context, list, setManualWidth, 1000);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public List<Games> getSelectedList() {
        return selectedList;
    }



    public void selectPos(int pos) {
        int index = isExist(String.valueOf(list.get(pos).getId()));
        if (index == -1) {
            selectedList.add(list.get(pos));
        }
        else {
            selectedList.remove(index);
        }
        notifyDataSetChanged();
    }


    private int isExist(String id) {
        int index = -1;
        for (int i = 0; i < selectedList.size(); i++) {
            if (String.valueOf(selectedList.get(i).getId()).equalsIgnoreCase(id)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_vu, parent, false);
//        if (setManualWidth) {
//            setItemWidth(v, parent);
//        }
        return new ViewHolder(v);
    }

    private void setItemWidth(View view, ViewGroup parent) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        params.width = (int)((parent.getMeasuredWidth() / 3) * 0.9);
        view.setLayoutParams(params);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Games info = list.get(position);

        holder.tvName.setText(info.getName());
        Glide.with(context).load(info.getBanner()).into(holder.imgVu);
        // Glide.with(context).load(context.getDrawable(R.drawable.black_gradient_bg)).into(holder.overlayImg);

        if (isExist(String.valueOf(info.getId())) == -1) {
            holder.selected.setImageResource(R.drawable.non_selected_ic);
        }
        else {
            holder.selected.setImageResource(R.drawable.selected_ic);
        }

        holder.mainLay.setOnClickListener(new View.OnClickListener() {
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

        TextView tvName;
        ImageView imgVu, overlayImg;
        ImageButton selected;
        CardView mainLay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            imgVu = itemView.findViewById(R.id.img_vu);
            selected = itemView.findViewById(R.id.selected);
            mainLay = itemView.findViewById(R.id.main_lay);
            overlayImg = itemView.findViewById(R.id.overlay_img);
        }
    }

    public interface ItemClickListener {
        void itemClicked(View view, int pos);
    }
}