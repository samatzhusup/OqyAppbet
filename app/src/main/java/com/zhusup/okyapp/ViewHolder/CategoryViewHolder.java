package com.zhusup.okyapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.zhusup.okyapp.Interface.ItemClickListener;
import com.zhusup.okyapp.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textView;
    public ImageView imageView;
    private ItemClickListener itemClickListener;

    public CategoryViewHolder(View itemView) {
        super(itemView);

        textView= (TextView) itemView.findViewById(R.id.menu_title);
        imageView= (ImageView) itemView.findViewById(R.id.menu_image);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
             itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
