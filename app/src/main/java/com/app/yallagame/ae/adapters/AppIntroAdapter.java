package com.app.yallagame.ae.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.yallagame.ae.R;
import com.app.yallagame.ae.models.AppIntro;
import com.bumptech.glide.Glide;

import java.util.List;

public class AppIntroAdapter extends RecyclerView.Adapter<AppIntroAdapter.ViewHolder> {

    private final Context context;
    private final List<AppIntro> list;

    public AppIntroAdapter(Context context, List<AppIntro> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_intro, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppIntro intro = list.get(position);
//        Glide.with(context).load(intro.getPhoto()).into(holder.imageView);
        Glide.with(context)
                .load(intro.getPhoto())
                .centerInside()
                .into(holder.imageView);
        holder.tvTitle.setText(intro.getName());
        holder.tvDesc.setText(intro.getDetails());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tvTitle, tvDesc;

        ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_vu);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
        }
    }
}