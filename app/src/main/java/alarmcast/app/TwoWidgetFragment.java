package alarmcast.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class TwoWidgetFragment extends WidgetFragment implements AdapterView.OnItemClickListener {

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
        gridview.setAdapter(((MainTabActivity)getActivity()).adapterWidget);

        return v;
    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, View view, int position, long id) {
        DlgWidgetPicker.newInstance(position).show(getActivity().getSupportFragmentManager(),null);
    }
}