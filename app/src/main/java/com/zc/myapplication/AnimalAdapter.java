package com.zc.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class AnimalAdapter extends RecyclerView.Adapter {
    private List<AnimalBean> animalList;
    private Context context;
    private OnItemClickListener listener;

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 1;

    private int pull_load_status;
    public static final int PULL_STATUS_LOAD = 0;
    public static final int PULL_STATUS_LOADING = 1;


    public AnimalAdapter(Context context, List<AnimalBean> list, OnItemClickListener listener) {
        animalList = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int viewType) {
        Log.i(AnimalAdapter.class.getSimpleName(), "onCreateViewHolder");
        if (viewType == TYPE_NORMAL) {
            return new AnimalViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false));
        } else {
            return new FooterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.footer_item, viewGroup, false));
        }

//        ViewGroup.LayoutParams params = animalViewHolder.itemView.getLayoutParams();
//        params.height = new Random().nextInt(400) + 400;
//        animalViewHolder.itemView.setLayoutParams(params);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        Log.i(AnimalAdapter.class.getSimpleName(), "onBindViewHolder");

        if (viewHolder instanceof AnimalViewHolder) {
            AnimalBean animalBean = animalList.get(i);
            ((AnimalViewHolder) viewHolder).tvAnimal.setText(animalBean.getTitle());
            ((AnimalViewHolder) viewHolder).ivAnimal.setImageResource(animalBean.getIcon());
            if (listener != null) {
                ((AnimalViewHolder) viewHolder).tvAnimal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(((AnimalViewHolder) viewHolder).tvAnimal, i);
                    }
                });

            }
        } else {
            switch (pull_load_status) {
                case PULL_STATUS_LOAD:
                    ((FooterViewHolder) viewHolder).tvFooter.setText("上拉加载更多");
                    break;
                case PULL_STATUS_LOADING:
                    ((FooterViewHolder) viewHolder).tvFooter.setText("加载中...");
                    break;
            }
        }
    }



    @Override
    public int getItemCount() {
        return animalList.size()+1;
    }

    public class AnimalViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView tvAnimal;
        ImageView ivAnimal;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.tvAnimal = itemView.findViewById(R.id.tv_animal);
            this.ivAnimal = itemView.findViewById(R.id.iv_animal);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView tvFooter;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvFooter = itemView.findViewById(R.id.tv_footer);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < animalList.size()) {
            return TYPE_NORMAL;
        } else
            return TYPE_FOOTER;
    }

    public void changeMoreStatus(int type){
        pull_load_status=type;
        notifyDataSetChanged();
    }

    public void addItem(int i, AnimalBean animalBean) {
        animalList.add(i, animalBean);
        notifyItemInserted(i);
    }

    public void removeItem(int i) {
        animalList.remove(i);
        notifyItemRemoved(i);
    }

    interface OnItemClickListener {
        public void onItemClick(View view, int i);
    }
}
