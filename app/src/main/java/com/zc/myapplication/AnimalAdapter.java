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

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {
    private List<AnimalBean> animalList;
    private Context context;
    private OnItemClickListener listener;

    public AnimalAdapter(Context context, List<AnimalBean> list ,OnItemClickListener listener) {
        animalList = list;
        this.context = context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        AnimalViewHolder animalViewHolder = new AnimalViewHolder(itemView);


//        ViewGroup.LayoutParams params = animalViewHolder.itemView.getLayoutParams();
//        params.height = new Random().nextInt(400) + 400;
//        animalViewHolder.itemView.setLayoutParams(params);
        Log.i(AnimalAdapter.class.getSimpleName(),"onCreateViewHolder");

        return animalViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AnimalViewHolder viewHolder, final int i) {
        Log.i(AnimalAdapter.class.getSimpleName(),"onBindViewHolder");
        AnimalBean animalBean = animalList.get(i);
        viewHolder.tvAnimal.setText(animalBean.getTitle());
        viewHolder.ivAnimal.setImageResource(animalBean.getIcon());
        if(listener!=null){
            viewHolder.tvAnimal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(viewHolder.tvAnimal,i);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return animalList.size();
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

    public void addItem(int i, AnimalBean animalBean) {
        animalList.add(i, animalBean);
        notifyItemInserted(i);
    }

    public void removeItem(int i) {
        animalList.remove(i);
        notifyItemRemoved(i);
    }

    interface OnItemClickListener{
        public void onItemClick(View view,int i);
    }
}
