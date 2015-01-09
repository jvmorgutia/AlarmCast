package alarmcast.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import alarmcast.app.widgets.Widget;
import alarmcast.app.widgets.EmptyWidget;
import alarmcast.app.widgets.JsonWidget;

/**
 * Created by charles on 6/9/14.
 */
public abstract class BaseWidgetFragment extends Fragment implements DlgWidgetPicker.OnDialogComplete {
    private static final String SAVE_TEMP_WIDGETS = "widgets";
    protected ArrayList<Widget> widgets;

    public void onDialogComplete(View v, Widget selectedWidget, int ndx) {
        widgets.set(ndx, selectedWidget);
        initWidgetView(v, selectedWidget, ndx);
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
        gsonBilder.registerTypeAdapter(Widget.class, new JsonWidget());
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
        gsonBilder.registerTypeAdapter(Widget.class, new JsonWidget());
        Gson gson = gsonBilder.create();
        String jsonString = gson.toJson(widgets, new TypeToken<ArrayList<Widget>>(){}.getType());

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        sharedPref.edit().putString(saveLoc, jsonString).apply();
    }



    public void initWidgetView(final View v, final Widget w, final int ndx) {
        TextView tv = (TextView) v.findViewById(R.id.tv_widget_title);
        tv.setText(w.getTitle());

        //TODO: Create imageView for empty widget
        if(w.getImage() != -1) {
            ImageView iv = (ImageView) v.findViewById(R.id.iv_widget);
            iv.setImageResource(w.getImage());
        }

        ImageButton btSetting = (ImageButton)v.findViewById(R.id.bt_widget_setting);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DlgWidgetPicker.newInstance(BaseWidgetFragment.this, v, ndx).show(getActivity().getSupportFragmentManager(),null);
            }
        });

        btSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFrag = w.getDialog();

                if (dialogFrag != null)
                    dialogFrag.show(getActivity().getSupportFragmentManager(), null);
                else {
                    Toast.makeText(getActivity(), getString(R.string.toast_no_setting),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }



}
