package alarmcast.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import alarmcast.app.widgets.EmptyWidget;
import alarmcast.app.widgets.JsonWidget;
import alarmcast.app.widgets.Widget;

public abstract class BaseWidgetFragment extends Fragment implements DlgWidgetPicker.OnDialogComplete {
    public static final String SAVE_CASTABLE_WIDGETS = "cast_widgets";
    private static final String SAVE_TEMP_WIDGETS = "widgets";
    private static ArrayList<Widget> widgetsCastable;

    protected ArrayList<Widget> widgets;
    private FloatingActionButton fab;

    public void onDialogComplete(View v, Widget selectedWidget, int ndx) {
        widgets.set(ndx, selectedWidget);
        initWidgetView(v, selectedWidget, ndx);
        new CompareWidgets().execute();
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
        widgetsCastable = loadWidgets(SAVE_CASTABLE_WIDGETS);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            new CompareWidgets().execute();
        }
    }

    public ArrayList<Widget> loadWidgets(String saveLoc) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Widget.class, new JsonWidget());
        Gson gson = gsonBuilder.create();

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String jsonString = sharedPref.getString(saveLoc, null);

        ArrayList<Widget> widgets = gson.fromJson(jsonString, new TypeToken<ArrayList<Widget>>() {
        }.getType());

        if (widgets == null) {
            widgets = new ArrayList<>();

            switch(saveLoc) {
                case FourWidgetFragment.SAVE_WIDGETS:
                    emptyWidgets(widgets,4);
                    break;
                case ThreeWidgetFragment.SAVE_WIDGETS:
                    emptyWidgets(widgets,3);
                    break;
                case TwoWidgetFragment.SAVE_WIDGETS:
                    emptyWidgets(widgets,2);
                    break;
            }
        }
        return widgets;
    }
    private void emptyWidgets(ArrayList<Widget> widgets, int size) {
        for( int i = 0; i < size; i++) {
            widgets.add(new EmptyWidget());
        }
    }
    public void saveWidgets(String saveLoc) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Widget.class, new JsonWidget());
        Gson gson = gsonBuilder.create();
        String jsonString = gson.toJson(widgets, new TypeToken<ArrayList<Widget>>(){}.getType());

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        sharedPref.edit().putString(saveLoc, jsonString).apply();
    }

    public void initFabView(View parent) {
        fab = (FloatingActionButton) parent.findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BaseWidgetFragment.this.getActivity(),R.string.tst_save_widgets,Toast.LENGTH_LONG).show();
                saveWidgets(SAVE_CASTABLE_WIDGETS);
                widgetsCastable = new ArrayList<>();
                for(Widget w : widgets) {
                    widgetsCastable.add(w.getCopy());
                }
                fab.setVisibility(View.GONE);
            }
        });
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

    class CompareWidgets extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            if(widgetsCastable != null && widgets != null && widgetsCastable.size() == widgets.size()) {
                for(int i = 0; i < widgets.size(); i++) {
                    if(!widgets.get(i).equals(widgetsCastable.get(i))) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(!result) {
                fab.setVisibility(View.VISIBLE);
            }
            else {
                fab.setVisibility(View.GONE);
            }
        }
    }
}
