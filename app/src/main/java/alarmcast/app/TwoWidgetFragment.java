package alarmcast.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class TwoWidgetFragment extends WidgetFragment {
    private static final String SAVE_TWO_WIDGETS = "two_widgets";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_three_widget, container, false);
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();

        saveWidgets(SAVE_TWO_WIDGETS);
    }
}