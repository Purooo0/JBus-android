package com.AdheliaPutriMaylaniJBusBR.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.Bus;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.City;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.Station;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.request.BaseApiService;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MainActivity is the main screen of the JBus application, displaying a paginated list of buses.
 * It also includes navigation controls for pagination and options in the action bar.
 *
 * @author Adhelia Putri Maylani
 */
public class MainActivity extends AppCompatActivity {
    private Button[] btns;
    private int currentPage = 0;
    private int pageSize = 5;
    private int listSize;
    private int noOfPages;
    private List<Bus> listBus = new ArrayList<>();
    private BaseApiService mApiService;
    private Context mContext;
    private Button prevButton = null;
    private Button nextButton = null;
    private ListView busListView = null;
    private HorizontalScrollView pageScroll = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("JBus");

        // Connect components with their IDs
        prevButton = findViewById(R.id.prev_page);
        nextButton = findViewById(R.id.next_page);
        pageScroll = findViewById(R.id.page_number_scroll);
        busListView = findViewById(R.id.bus_list_view);

        // Create a sample BusArrayAdapter and set it to the ListView
        BusArrayAdapter newBusArrayAdapter = new BusArrayAdapter(this, (ArrayList<Bus>) Bus.sampleBusList(5));
        ListView newListView = findViewById(R.id.bus_list_view);
        newListView.setAdapter(newBusArrayAdapter);

        listBus = Bus.sampleBusList(20);
        listSize = listBus.size();

        mContext = this;
        mApiService = UtilsApi.getApiService();

        // Construct the pagination footer
        paginationFooter();
        goToPage(currentPage);

        // Listeners for prevButton and nextButton
        prevButton.setOnClickListener(v -> {
            currentPage = currentPage != 0? currentPage-1 : 0;
            goToPage(currentPage);
        });

        nextButton.setOnClickListener(v -> {
            currentPage = currentPage != noOfPages -1? currentPage+1 : currentPage;
            goToPage(currentPage);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    private void paginationFooter() {
        int val = listSize % pageSize;
        val = val == 0 ? 0:1;
        noOfPages = listSize / pageSize + val;

        LinearLayout ll = findViewById(R.id.btn_layout);
        btns = new Button[noOfPages];
        if (noOfPages <= 6) {
            ((FrameLayout.LayoutParams) ll.getLayoutParams()).gravity = Gravity.CENTER;
        }

        for (int i = 0; i < noOfPages; i++) {
            btns[i]=new Button(this);
            btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btns[i].setText(""+(i+1));
            btns[i].setTextColor(getResources().getColor(R.color.blue_700));

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150, 150);
            ll.addView(btns[i], lp);

            final int j = i;
            btns[j].setOnClickListener(v -> {
                currentPage = j;
                goToPage(j);
            });
        }
    }

    private void goToPage(int index) {
        for (int i = 0; i< noOfPages; i++) {
            if (i == index) {
                btns[index].setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
                btns[i].setTextColor(getResources().getColor(R.color.blue_700));
                scrollToItem(btns[index]);
                viewPaginatedList(listBus, currentPage);
            } else {
                btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btns[i].setTextColor(getResources().getColor(R.color.blue_700));
            }
        }
    }

    private void scrollToItem(Button item) {
        int scrollX = item.getLeft() - (pageScroll.getWidth() - item.getWidth()) / 2;
        pageScroll.smoothScrollTo(scrollX, 0);
    }

    /**
     * Overrides the onOptionsItemSelected method to handle item selection in the action bar.
     *
     * @param item The selected MenuItem.
     * @return True if the item is handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.search_menu) {
            // Display the search result (to be implemented)
            return true;
        } else if (itemId == R.id.person_menu) {
            // Move to the profile activity
            Intent intent = new Intent(this, AboutMeActivity.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.payment_menu) {
            // Move to the payment activity (to be implemented)
            return true;
        } else return super.onOptionsItemSelected(item);
    }

    private void viewPaginatedList(List<Bus> listBus, int page) {
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, listBus.size());
        List<Bus> paginatedList = listBus.subList(startIndex, endIndex);

        ArrayList<Bus> paginatedArrayList = new ArrayList<>(paginatedList);
        BusArrayAdapter numberArrayAdapt = new BusArrayAdapter(this, new ArrayList<>(paginatedList));
        ListView numberListView = findViewById(R.id.bus_list_view);
        numberListView.setAdapter(numberArrayAdapt);
    }
}