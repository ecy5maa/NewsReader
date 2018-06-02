package com.androidtutorialpoint.newsreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.assad.newsreader.R;


public class MainActivity extends AppCompatActivity  {

    public OnDataPass dataPasser;
    public  void setDataPasser(OnDataPass _dataPasser){
        this.dataPasser=_dataPasser;
    }
    private TabLayout tabLayout;
    private ViewPager viewPager;
    int current=0;
    int max=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(SPUser.getValue(MainActivity.this,SPUser.COUNTREY_NAMEB)+" Breaking News");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //setOnDataPass(dataPasser);
        ///getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        addTabs(viewPager);
        max=viewPager.getChildCount();
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
               // if(dataPasser!=null)
                 //   dataPasser.onDataPass(position);

                if(dataPasser!=null)
                    dataPasser.onDataPass(position);
            }


            @Override
            public void onPageScrollStateChanged(int state) {

              // mListener.doYourWork(state);
             //   MainActivity.this.setOnDataPass(viewPager.getCurrentItem());
              // if(dataPasser!=null)
                //dataPasser.onDataPass(state);
            }
        });
    }
    private void addTabs(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Tablayout_in_Android(), "Business");
        adapter.addFrag(new Tablayout_in_Android(), "Entertainment");
        adapter.addFrag(new Tablayout_in_Android(), "Health");
        adapter.addFrag(new Tablayout_in_Android(), "Science");
        adapter.addFrag(new Tablayout_in_Android(), "Sports");
        adapter.addFrag(new Tablayout_in_Android(), "Technology");
        viewPager.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id==R.id.action_setting){
            Intent intent = new Intent(MainActivity.this, CountreySelection.class);
            startActivity(intent);
        }
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }
}
