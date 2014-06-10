package alarmcast.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class TwoWidgetFragment extends WidgetFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_two_widget, container, false);

        GridView gridview = (GridView) v.findViewById(R.id.gv_two_widget_picker);
        //Remove scrollbar from GridView
        gridview.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return event.getAction() == MotionEvent.ACTION_MOVE;
            }
        });

        gridview.setOnItemClickListener(this);
        if(widgets == null)
            widgets = loadWidgets(SAVE_TWO_WIDGETS);

        adapterWidget = new AdapterWidget(getActivity(), R.layout.gv_item_widget, widgets);

        gridview.setAdapter(adapterWidget);

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();

        saveWidgets(SAVE_TWO_WIDGETS);
    }
}