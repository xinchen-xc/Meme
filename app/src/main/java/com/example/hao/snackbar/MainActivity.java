package com.example.hao.snackbar;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.hao.snackbar.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {
    CoordinatorLayout container;
    private DrawerLayout mDrawerLayout;
    private List<Meme> memeList = new ArrayList<>();
    private MemeAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        //侧滑美化  NavigationView
        NavigationView navView = (NavigationView)findViewById(R.id.nav_view);

        //隐藏actionbar，使用了 Toolbar ，让它的外观与功能都和 ActionBar一致
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //drawerlayout滑动按钮
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }

        //侧滑美化  NavigationView
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        //悬浮按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            //Snackbar使用，
            //coordinator监控子控件，滑动
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this,"aaaaaaaaaaaaaaaaaaa",Toast.LENGTH_LONG).show();
                Snackbar.make(view, "delete!!!!!!!!!!", Snackbar.LENGTH_INDEFINITE)
                        .setAction("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View view1) {
                                Toast.makeText(MainActivity.this, "删除失败", Toast.LENGTH_LONG).show();
                            }
                        }).show();
            }
        });

        //Glide加载图片
        initMemes();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MemeAdapter(memeList);
        recyclerView.setAdapter(adapter);

        //刷新 SwipeRefreshLayout
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        //设置进度条的颜色
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
                refreshMemes();
            }
        });
    }

    //toolbar上添加action按钮
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toobar_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(this, "You cticked Backup", Toast.LENGTH_SHORT).
                        show();
                break;
            case R.id.delete:
                Toast.makeText(this, "You cticked Delete", Toast.LENGTH_SHORT).
                        show();
                break;
            case R.id.settings:
                Toast.makeText(this, "You cticked Settings", Toast.LENGTH_SHORT).
                        show();
                break;
            //drawerlayout滑动按钮
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                    break;
            default:
        }
        return true;
    }

    //Glide加载图片
    private Meme[] memes = {
            new Meme("气愤",R.drawable.angry),new Meme("吃惊",R.drawable.astonished),
            new Meme("沮丧",R.drawable.depressed),new Meme("开心",R.drawable.happy),
            new Meme("恐怖",R.drawable.horrible),new Meme("大笑",R.drawable.laughing),
            new Meme("失落",R.drawable.lost),new Meme("紧张",R.drawable.nervous),
    };
    public void initMemes(){
        memeList.clear();
        for(int i = 0;i<50;i++){
            Random random = new Random();
            int index = random.nextInt(memes.length);
            memeList.add(memes[index]);
        }
    }

    //刷新 SwipeRefreshLayout
    private void refreshMemes(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //本地刷新操作速度非常快，如果不将线程沉睡的话，刷新立刻就结束了
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initMemes();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
