package com.example.emina.bihamk;

import android.support.v4.view.PagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class FActivity extends FragmentActivity {
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f);
        viewpager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter padapter = new com.example.emina.bihamk.PagerAdapter( getSupportFragmentManager());
        viewpager.setAdapter(padapter);
    }
}