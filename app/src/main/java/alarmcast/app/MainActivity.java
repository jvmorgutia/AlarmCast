package alarmcast.app;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import alarmcast.app.widgets.EmptyWidget;
import alarmcast.app.widgets.Widget;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, DlgWidgetPicker.OnDialogComplete, AdapterWidget.OnSettingClick {
    private static final String SAVE_WIDGETS = "widgets";

    private ArrayList<Widget> widgets;
    private AdapterWidget adapterWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) findViewById(R.id.gv_widget_picker);
        //Remove scrollbar from GridView
        gridview.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                return event.getAction() == MotionEvent.ACTION_MOVE;
            }
        });

        gridview.setOnItemClickListener(this);

        if(savedInstanceState == null || !savedInstanceState.containsKey(SAVE_WIDGETS)) {
            widgets = new ArrayList<Widget>();
            for (int i = 0; i < 4; i++)
                widgets.add(new EmptyWidget());
        }
        else {
            widgets = savedInstanceState.getParcelableArrayList(SAVE_WIDGETS);
        }

        adapterWidget = new AdapterWidget(this, R.layout.gv_item_widget,widgets);
        gridview.setAdapter(adapterWidget);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, View view, int position, long id) {
        DlgWidgetPicker.newInstance(position).show(getSupportFragmentManager(),null);
    }

    @Override
    public void onDialogComplete(int ndx, Widget selectedWidget) {
        widgets.set(ndx,selectedWidget);
        adapterWidget.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVE_WIDGETS, widgets);
    }

    @Override
    public void onSettingClick(int ndx, Widget curWidget) {
        DialogFragment dialogFrag = curWidget.getDialog();

        if(dialogFrag != null)
            dialogFrag.show(getSupportFragmentManager(),null);
        else {
            Toast.makeText(this, "No settings found for this item.",
                    Toast.LENGTH_LONG).show();
        }
    }
}
