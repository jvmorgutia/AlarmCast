package alarmcast.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TwoWidgetFragment extends BaseWidgetFragment {
    private static final String SAVE_TWO_WIDGETS = "two_widgets";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_two_widget, container, false);

        if(widgets == null)
            widgets = loadWidgets(SAVE_TWO_WIDGETS);

        initWidgetView(v.findViewById(R.id.widget_two_l), widgets.get(0), 0);
        initWidgetView(v.findViewById(R.id.widget_two_r), widgets.get(1), 1);
        initFabView(v);

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();

        saveWidgets(SAVE_TWO_WIDGETS);
    }
}