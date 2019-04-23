package com.yenen.ahmet.myapplication;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.yenen.ahmet.customviewranking.listener.ChangingViewListener;
import com.yenen.ahmet.customviewranking.core.CustomViewRanking;
import com.yenen.ahmet.customviewranking.core.CustomViewRankingConst;
import com.yenen.ahmet.customviewranking.listener.DelayedAllViewListener;
import com.yenen.ahmet.customviewranking.listener.ItemClickListener;


public class MainActivity extends AppCompatActivity implements ItemClickListener, DelayedAllViewListener, ChangingViewListener {

    private int i = 1;
    private CustomViewRanking viewSorting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LinearLayout container = findViewById(R.id.container);
        viewSorting = new CustomViewRanking(container, this);
        viewSorting.setItemClickListener(this).setContinueScrol(true)
                .setAnimationType(CustomViewRankingConst.SlideBottom)
                .setDuration(2000).setDelayedAllViewListener(this)
                .setChangingViewListener(this);

        addViews(viewSorting);
        final Button btnShort = findViewById(R.id.btnShort);
        btnShort.setOnClickListener(v -> {
            viewSorting.refreshViewSequentially();
            //click(viewSorting);
        });
    }

    private void addViews(CustomViewRanking viewSorting){
        viewSorting.addViewToList(5, R.layout.deneme1);
        viewSorting.addViewToList(2, R.layout.deneme4);
        viewSorting.addViewToList(1, R.layout.deneme3);
        viewSorting.addViewToList(4, R.layout.deneme5);
        viewSorting.addViewToList(3, R.layout.deneme2);

        viewSorting.addViewToList(6, R.layout.deneme1);
        viewSorting.addViewToList(7, R.layout.deneme4);
        viewSorting.addViewToList(8, R.layout.deneme3);
        viewSorting.addViewToList(9, R.layout.deneme5);
        viewSorting.addViewToList(10, R.layout.deneme2);
        viewSorting.addViewToList(11, R.layout.deneme1);
        viewSorting.addViewToList(12, R.layout.deneme4);
        viewSorting.addViewToList(13, R.layout.deneme3);
        viewSorting.addViewToList(14, R.layout.deneme5);
        viewSorting.addViewToList(15, R.layout.deneme2);
        viewSorting.addViewToList(16, R.layout.deneme1);
        viewSorting.addViewToList(17, R.layout.deneme4);
        viewSorting.addViewToList(18, R.layout.deneme3);
        viewSorting.addViewToList(19, R.layout.deneme5);
        viewSorting.addViewToList(20, R.layout.deneme2);
    }

    private void click(CustomViewRanking viewSorting) {
        switch (i) {
            case 1:
                viewSorting.addViewList(5, R.layout.deneme1);
                break;
            case 2:
                viewSorting.addViewList(2, R.layout.deneme4);
                break;
            case 3:
                viewSorting.addViewList(1, R.layout.deneme3);
                break;
            case 4:
                viewSorting.addViewList(4, R.layout.deneme5);
                break;
            case 5:
                viewSorting.addViewList(3, R.layout.deneme2);
                break;
        }
        i++;

    }

    @Override
    public void onClick(int position, View view){
        Log.e("view", String.valueOf(position));
        viewSorting.removeAllViewsDelayed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(viewSorting!=null){
            viewSorting.unBind();
            viewSorting=null;
        }
    }

    @Override
    public void onEnd(boolean isAdd) {
        Log.e("End",String.valueOf(isAdd));
    }

    @Override
    public void onStart(boolean isAdd) {
        if(!isAdd){
            viewSorting.setPostDelayedMilis((short) 500);
            viewSorting.setDuration(700);
        }
        Log.e("Start",String.valueOf(isAdd));
    }

    @Override
    public void onChange(int rowNumber, boolean isAdd, int position) {
        if(!isAdd){
            if(rowNumber %2==1){
                viewSorting.setAnimationType(CustomViewRankingConst.SlideLeft);
            }else{
                viewSorting.setAnimationType(CustomViewRankingConst.SlideRight);
            }
        }
    }
}
