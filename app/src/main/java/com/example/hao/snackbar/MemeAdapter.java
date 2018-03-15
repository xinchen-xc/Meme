package com.example.hao.snackbar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Hao on 2017/12/20.
 */

public class MemeAdapter extends RecyclerView.Adapter<MemeAdapter.ViewHolder> {
    private Context mContext;
    private List<Meme> mMemeList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView memeImage;
        TextView memeName;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            memeImage = (ImageView)view.findViewById(R.id.fruit_image);
            memeName = (TextView)view.findViewById(R.id.fruit_name);
        }
    }

    public MemeAdapter(List<Meme> memeList){
        mMemeList = memeList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.meme,parent,false);

        //可折叠式标题栏CollapsingToolbarLayout ，
        //处理 RecyclerView 的点击事件，打开MemeActivity
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Meme meme = mMemeList.get(position);
                Intent intent = new Intent(mContext,MemeActivity.class);
                intent.putExtra(MemeActivity.MEME_NAME,meme.getName());
                intent.putExtra(MemeActivity.MEME_IMAGE_ID,meme.getImageId());
                mContext.startActivity(intent);
            }
        });
        return holder;
//        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MemeAdapter.ViewHolder holder, int position) {
        Meme meme = mMemeList.get(position);
        holder.memeName.setText(meme.getName());
        Glide.with(mContext).load(meme.getImageId()).into(holder.memeImage);
    }
//首先调用 Glide.with() 方法并传人一个 Context 、Activity 或Fragment 参数，
//然后调用 load ()方法去加载图片，可以是一个 URL 地址，也可以是一个本地路径，或者是一个资源 id ，
// 最后调用 into ()方法将图片设置到具体某一个 ImageView 中就可以了。

    @Override
    public int getItemCount() {
        return mMemeList.size();
    }

}
