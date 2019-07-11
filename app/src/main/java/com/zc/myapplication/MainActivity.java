package com.zc.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AnimalAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    public AnimalAdapter animalAdapter;
    private List<AnimalBean> animalList;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

//    StaggeredGridLayoutManager manager;
    private LinearLayoutManager manager;

    private int lastItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        refreshLayout = findViewById(R.id.refreshlayout);
        animalList = new ArrayList<>();
        AnimalBean animalBean;

        for (int i = 0; i < 6; i++) {
            animalBean = new AnimalBean("DolphinDolphinDolphinDolphinDolphinDolphin",
                    R.drawable.dolphin);
            animalList.add(animalBean);
            animalBean = new AnimalBean("Fish", R.drawable.fish);
            animalList.add(animalBean);
            animalBean = new AnimalBean("LineLineLineLineLineLineLineLineLineLineLineLineLineLineLineLineLineLineLine",
                    R.drawable.lion);
            animalList.add(animalBean);
            animalBean = new AnimalBean("Elephant", R.drawable.elephant);
            animalList.add(animalBean);
        }

        manager = new LinearLayoutManager(this);
//        manager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        animalAdapter = new AnimalAdapter(this, animalList, this);
        recyclerView.setAdapter(animalAdapter);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

        refreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                lastItemPosition=manager.findLastVisibleItemPosition();
                Log.i(MainActivity.class.getSimpleName(), "lastItemPosition: " + lastItemPosition);
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition==animalList.size()) {
                    animalAdapter.changeMoreStatus(AnimalAdapter.PULL_STATUS_LOADING);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            animalAdapter.addItem(0, new AnimalBean("New animal", R.drawable.ic_launcher_background));
                            animalAdapter.changeMoreStatus(AnimalAdapter.PULL_STATUS_LOAD);
                        }
                    }, 3000);

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                animalAdapter.addItem(0, new AnimalBean("New animal", R.drawable.ic_launcher_background));
//                recyclerView.scrollToPosition(0);
                animalAdapter.notifyDataSetChanged();
                break;
            case R.id.delete:
                animalAdapter.removeItem(0);
                break;
        }
        return true;
    }

    @Override
    public void onItemClick(View view, int i) {
        Toast.makeText(this, animalList.get(i).getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animalAdapter.addItem(0, new AnimalBean("New animal", R.drawable.ic_launcher_background));
                animalAdapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        }, 300);
    }

}
