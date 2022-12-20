package com.example.test;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class slowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slowchart);


        PieChart pieChart = findViewById(R.id.pieChart);


        ArrayList<PieEntry> fast = new ArrayList<>();

        fast.add(new PieEntry(292,"환경부"));
        fast.add(new PieEntry(259,"GS"));
        fast.add(new PieEntry(292,"한국전력"));
        fast.add(new PieEntry(280,"제주전기자동차"));
        fast.add(new PieEntry(255,"한국전기자동차"));
        fast.add(new PieEntry(292,"에스트래픽"));
        fast.add(new PieEntry(309,"SK"));
        fast.add(new PieEntry(279,"차지비"));
        fast.add(new PieEntry(292,"에버온"));
        fast.add(new PieEntry(290,"제주도청"));
        fast.add(new PieEntry(250,"대영채비"));
        fast.add(new PieEntry(292,"지엔텔"));
        fast.add(new PieEntry(390,"이카플러그"));
        fast.add(new PieEntry(255,"스타코프"));
        fast.add(new PieEntry(255,"한국전기차"));
        fast.add(new PieEntry(300,"보타리"));
        fast.add(new PieEntry(275,"태슬라"));
        fast.add(new PieEntry(290,"타디스테크놀로지"));
        fast.add(new PieEntry(292,"현대오일뱅크"));
        fast.add(new PieEntry(292,"클린인텍스"));
        fast.add(new PieEntry(240,"LG"));
        fast.add(new PieEntry(253,"휴맥스"));
        fast.add(new PieEntry(255,"레드이앤지"));




        PieDataSet pieDataSet = new PieDataSet(fast,"급속");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("fast");
        pieChart.animate();
    }
}
