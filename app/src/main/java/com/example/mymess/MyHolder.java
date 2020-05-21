package com.example.mymess;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class MyHolder extends ViewHolder implements View.OnClickListener {

    ImageView m_imageView;
    TextView m_textViewTitle,m_textViewCuisine, m_textViewMess, m_textViewTags, m_textViewNumberOfTagsMatching, m_textViewNumberOfTagsMatchingwithMess;
    ItemClickListener itemClickListener;

    MyHolder(@NonNull View itemView) {
        super(itemView);
        m_imageView = itemView.findViewById(R.id.imageIv);
        m_textViewTitle = itemView.findViewById(R.id.titleTv);
        m_textViewCuisine = itemView.findViewById(R.id.cuisineTv);
        m_textViewMess = itemView.findViewById(R.id.messTv);
        m_textViewTags = itemView.findViewById(R.id.tagsTv);
        m_textViewNumberOfTagsMatching = itemView.findViewById(R.id.numtagsTv);
        m_textViewNumberOfTagsMatchingwithMess = itemView.findViewById(R.id.nummessTagsTv);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClickListener(v, getLayoutPosition());
    }
    public void setItemClickListener(ItemClickListener ic){
        this.itemClickListener = ic;
    }
}
