package alarmcast.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import alarmcast.app.widgets.JsonAdapterWidget;
import alarmcast.app.widgets.Widget;

/**
 * Created by charles on 6/9/14.
 */
public class WidgetFragment extends Fragment {
    private ArrayList<Widget> widgets;

    @Override
    public void onAttach(Activity parent) {
        super.onAttach(parent);

        widgets = ((MainTabActivity)parent).widgets;
    }

    @Override
    public void onResume() {
        super.onResume();

        widgets = ((MainTabActivity)getActivity()).widgets;
    }
    @Override
    public void onPause() {
        super.onPause();

        GsonBuilder gsonBilder = new GsonBuilder();
        gsonBilder.registerTypeAdapter(Widget.class, new JsonAdapterWidget());
        Gson gson = gsonBilder.create();
        String jsonString = gson.toJson(widgets, new TypeToken<ArrayList<Widget>>(){}.getType());

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        sharedPref.edit().putString(MainTabActivity.SAVE_JSON_WIDGETS, jsonString).commit();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MainTabActivity.SAVE_WIDGETS, widgets);
    }
}
