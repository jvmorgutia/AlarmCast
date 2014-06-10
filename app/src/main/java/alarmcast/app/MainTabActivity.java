package alarmcast.app;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import alarmcast.app.widgets.EmptyWidget;
import alarmcast.app.widgets.JsonAdapterWidget;
import alarmcast.app.widgets.Widget;

/**
 * Created by charles on 6/9/14.
 */
public class MainTabActivity extends FragmentActivity implements ActionBar.TabListener,
        AdapterWidget.OnSettingClick, DlgWidgetPicker.OnDialogComplete {
    private static final int NUM_TABS = 2;

    protected static final String SAVE_WIDGETS = "widgets";
    protected static final String SAVE_JSON_WIDGETS = "json_widgets";

    protected ArrayList<Widget> widgets;

    protected AdapterWidget adapterWidget;

    private MainTabAdapter mAppSectionsPagerAdapter;
    private ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);

        if (savedInstanceState == null || !savedInstanceState.containsKey(SAVE_WIDGETS)) {

            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            String jsonString = sharedPref.getString(SAVE_JSON_WIDGETS, null);

            GsonBuilder gsonBilder = new GsonBuilder();
            gsonBilder.registerTypeAdapter(Widget.class, new JsonAdapterWidget());
            Gson gson = gsonBilder.create();

            widgets = gson.fromJson(jsonString, new TypeToken<ArrayList<Widget>>() {
            }.getType());

            if (widgets == null) {
                widgets = new ArrayList<Widget>();

                for (int i = 0; i < 4; i++)
                    widgets.add(new EmptyWidget());
            }
        } else {
            widgets = savedInstanceState.getParcelableArrayList(SAVE_WIDGETS);
        }

        adapterWidget = new AdapterWidget(this, R.layout.gv_item_widget, widgets);


        mAppSectionsPagerAdapter = new MainTabAdapter(getSupportFragmentManager());

        final ActionBar actionBar = getActionBar();

        actionBar.setHomeButtonEnabled(false);

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
            Toast.makeText(this, "No settings found for this item.",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDialogComplete(int ndx, Widget selectedWidget) {
        widgets.set(ndx,selectedWidget);
        adapterWidget.notifyDataSetChanged();
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
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
            }
            return null;
        }
    }
}
