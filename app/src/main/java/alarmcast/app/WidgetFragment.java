package alarmcast.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

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
public class WidgetFragment extends Fragment implements DlgWidgetPicker.OnDialogComplete,AdapterView.OnItemClickListener {
    protected static final String SAVE_FOUR_WIDGETS = "four_widgets";
    protected static final String SAVE_THREE_WIDGETS = "three_widgets";
    protected static final String SAVE_TWO_WIDGETS = "two_widgets";

    protected static final String SAVE_TEMP_WIDGETS = "widgets";

    protected AdapterWidget adapterWidget;
    protected ArrayList<Widget> widgets;

    @Override
    public void onDialogComplete(int ndx, Widget selectedWidget) {
        widgets.set(ndx, selectedWidget);
        adapterWidget.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVE_TEMP_WIDGETS, widgets);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(SAVE_TEMP_WIDGETS))
            widgets = savedInstanceState.getParcelableArrayList(SAVE_TEMP_WIDGETS);
    }




    public ArrayList<Widget> loadWidgets(String saveLoc) {
        GsonBuilder gsonBilder = new GsonBuilder();
        gsonBilder.registerTypeAdapter(Widget.class, new JsonAdapterWidget());
        Gson gson = gsonBilder.create();

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String jsonString = sharedPref.getString(saveLoc, null);

        ArrayList<Widget> widgets = gson.fromJson(jsonString, new TypeToken<ArrayList<Widget>>() {
        }.getType());

        if (widgets == null) {
            widgets = new ArrayList<Widget>();

            for (int i = 0; i < 4; i++)
                widgets.add(new EmptyWidget());
        }
        return widgets;
    }
    public void saveWidgets(String saveLoc) {
        GsonBuilder gsonBilder = new GsonBuilder();
        gsonBilder.registerTypeAdapter(Widget.class, new JsonAdapterWidget());
        Gson gson = gsonBilder.create();
        String jsonString = gson.toJson(widgets, new TypeToken<ArrayList<Widget>>(){}.getType());

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        sharedPref.edit().putString(saveLoc, jsonString).commit();
    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, View view, int position, long id) {
        DlgWidgetPicker.newInstance(position,this).show(getActivity().getSupportFragmentManager(),null);
    }
}
