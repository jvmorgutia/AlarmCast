package alarmcast.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

import alarmcast.app.widgets.EmptyWidget;
import alarmcast.app.widgets.JsonAdapterWidget;
import alarmcast.app.widgets.Widget;

/**
 * Created by charles on 6/9/14.
 */
public class MainTabActivity extends ActionBarActivity implements ActionBar.TabListener,
        AdapterWidget.OnSettingClick {

    private static final int NUM_TABS = 3;

    private ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);



        MainTabAdapter mAppSectionsPagerAdapter = new MainTabAdapter(getSupportFragmentManager());

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public void onSettingClick(int ndx, Widget curWidget) {
        DialogFragment dialogFrag = curWidget.getDialog();

        if (dialogFrag != null)
            dialogFrag.show(getSupportFragmentManager(), null);
        else {
            Toast.makeText(this, getString(R.string.toast_no_setting),
                    Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    public class MainTabAdapter extends FragmentPagerAdapter {

        public MainTabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new FourWidgetFragment();
                case 1:
                    return new ThreeWidgetFragment();
                case 2:
                    return new TwoWidgetFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_TABS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return getString(R.string.tab_one);
                case 1:
                    return getString(R.string.tab_two);
                case 2:
                    return getString(R.string.tab_three);
            }
            return null;
        }
    }
}
