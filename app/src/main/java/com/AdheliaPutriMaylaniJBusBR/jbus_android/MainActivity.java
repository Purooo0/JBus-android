package com.AdheliaPutriMaylaniJBusBR.jbus_android;

import com.AdheliaPutriMaylaniJBusBR.jbus_android.R.id;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.Bus;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import android.view.Gravity;
import android.widget.Button;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button[] btns;
    private int currentPage = 0;
    private int pageSize = 12;
    private int listSize;
    private int noOfPages;
    private List<Bus> listBus = new ArrayList<>();
    private Button nextButton = null;
    private Button prevButton = null;
    private ListView busListView = null;
    private HorizontalScrollView pageScroll = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listBus = Bus.sampleBusList(20);
        BusArrayAdapter adapter = new BusArrayAdapter(this, (ArrayList<Bus>) listBus);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        prevButton = findViewById(R.id.prev_page);
        nextButton = findViewById(R.id.next_page);
        pageScroll = findViewById(R.id.page_number_scroll);

        listSize = listBus.size();

        paginationFooter();
        goToPage(currentPage);

        prevButton.setOnClickListener(v -> {
            currentPage = currentPage != 0? currentPage-1 : 0;
            goToPage(currentPage);
        });

        nextButton.setOnClickListener(v -> {
            currentPage = currentPage != noOfPages -1? currentPage+1 : currentPage;
            goToPage(currentPage);
        });
    }

    private void paginationFooter(){
        int val = listSize % pageSize;
        val = val == 0 ? 0:1;
        noOfPages = listSize / pageSize + val;

        LinearLayout ll = findViewById(R.id.btn_layout);
        btns = new Button[noOfPages];
        if(noOfPages <= 6){
            ((FrameLayout.LayoutParams) ll.getLayoutParams()).gravity = Gravity.CENTER;
        }

        for(int i = 0; i < noOfPages; i++){
            btns[i] = new Button(this);
            btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btns[i].setText(""+ (i + 1));
            btns[i].setTextColor(getResources().getColor(R.color.black));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100, 100);
            ll.addView(btns[i], lp);

            final int j = i;
            btns[j].setOnClickListener(v -> {
                currentPage = j;
                goToPage(j);
            });
        }
    }

    private void goToPage(int index){
        for(int i = 0; i < noOfPages; i++){
            if(i == index){
                btns[i].setTextColor(getResources().getColor(android.R.color.white));
                scrollToItem(btns[index]);
                viewPaginatedList(listBus, currentPage);
            } else {
                btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btns[i].setTextColor(getResources().getColor(android.R.color.black));
            }
        }
    }

    private void scrollToItem(Button item){
        int scrollX = item.getLeft() - (pageScroll.getWidth() - item.getWidth()) / 2;
        pageScroll.smoothScrollTo(scrollX, 0);
    }

    private void viewPaginatedList(List<Bus> listBus, int page){
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, listBus.size());
        List<Bus> paginatedList = listBus.subList(startIndex, endIndex);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    public boolean onOptionItemSelected(@NonNull MenuItem item){
        int itemId = item.getItemId();
        if(item.getItemId() == id.person_menu){
            Intent aboutmeIntent = new Intent(this, AboutMeActivity.class);
            startActivity(aboutmeIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}