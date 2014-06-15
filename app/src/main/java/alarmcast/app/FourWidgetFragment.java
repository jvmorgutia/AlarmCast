package alarmcast.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


public class FourWidgetFragment extends WidgetFragment {
    private static final String SAVE_FOUR_WIDGETS = "four_widgets";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_four_widget, container, false);

        GridView gridview = (GridView) v.findViewById(R.id.gv_four_widget_picker);
        //Remove scrollbar from GridView
        gridview.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return event.getAction() == MotionEvent.ACTION_MOVE;
            }
        });
        gridview.setOnItemClickListener(this);
        if(widgets == null)
            widgets = loadWidgets(SAVE_FOUR_WIDGETS);

        adapterWidget = new AdapterWidget(getActivity(), R.layout.gv_item_widget, widgets);

        gridview.setAdapter(adapterWidget);

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();

        saveWidgets(SAVE_FOUR_WIDGETS);
    }
}
