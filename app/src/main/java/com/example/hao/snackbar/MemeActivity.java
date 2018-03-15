package com.example.hao.snackbar;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.data.StreamAssetPathFetcher;

public class MemeActivity extends AppCompatActivity {
    public static final String MEME_NAME = "meme name";
    public static final String MEME_IMAGE_ID = "meme_image_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme);

        Intent intent = getIntent();
        String memeName = intent.getStringExtra(MEME_NAME);
        int memeImageId = intent.getIntExtra(MEME_IMAGE_ID , 9);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        ImageView memeImageView = (ImageView) findViewById(R.id.fruit_image_view);
        TextView memeContentText = (TextView) findViewById(R.id.fruit_content_text) ;

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //调用 CollapsingToolbarLayout 的 setTitle() 方法将水果名设置成当前界面的标题，
        // 然后使用 Glide 加载传入的水果图片，并设置到标题栏的 ImageView 上面
        collapsingToolbar.setTitle(memeName);
        Glide.with(this).load(memeImageId).into(memeImageView);

        String memeContent = generateMemeContent(memeName);
        memeContentText.setText(memeContent);
    }
    //generateFruitContent() 方法将水果名循环拼接 500 次，从而生成了一个比较长的字符串，将它设置到了 TextView 上面。
    private String generateMemeContent(String memeName){
        StringBuilder memeContent = new StringBuilder();
        for (int i=0;i<500;i++){
            memeContent.append(memeName);
        }
        return memeContent.toString();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
