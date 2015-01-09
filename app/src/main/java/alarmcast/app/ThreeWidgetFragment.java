package alarmcast.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by charles on 6/9/14.
 */
public class ThreeWidgetFragment extends WidgetFragment {
    private static final String SAVE_THREE_WIDGETS = "three_widgets";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_three_widget, container, false);
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();

        saveWidgets(SAVE_THREE_WIDGETS);
    }
}
