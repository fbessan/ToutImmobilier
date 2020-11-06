package besco.corporation.toutimmobilier;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import besco.corporation.toutimmobilier.Functions.Functions;
import me.relex.circleindicator.CircleIndicator;

public class DetailActivity extends AppCompatActivity {



    ViewPager viewPager;
    private int[] layouts1;
    MyViewPagerAdapter myViewPagerAdapter;
    String[] arry;

    Context mContext = DetailActivity.this;

    //private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        final CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);


        layouts1 = new int[]{

                R.layout.sliding_product_detail_1,
                R.layout.sliding_product_detail_1,
                R.layout.sliding_product_detail_1,
                R.layout.sliding_product_detail_1

        };

        arry = getResources().getStringArray(R.array.steps);
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setCurrentItem(0);
        indicator.setViewPager(viewPager);



        ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // addBottomDots(position);
                //circle_indicator.setText(arry[position]);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        };


        Functions functions = new Functions();






    }

    private class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts1[position], container, false);
            container.addView(view);

            return view;
        }


        @Override
        public int getCount() {
            return layouts1.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //mDrawerLayout.openDrawer(GravityCompat.START);
                //Toast.makeText(getApplicationContext(),"Trash",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DetailActivity.this, MainActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
