package alarmcast.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by charles on 6/9/14.
 */
public class ThreeWidgetFragment extends BaseWidgetFragment {
    private static final String SAVE_THREE_WIDGETS = "three_widgets";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_three_widget, container, false);

        if(widgets == null)
            widgets = loadWidgets(SAVE_THREE_WIDGETS);

        initWidgetView(v.findViewById(R.id.widget_three_l), widgets.get(0), 0);
        initWidgetView(v.findViewById(R.id.widget_three_tr), widgets.get(1), 1);
        initWidgetView(v.findViewById(R.id.widget_three_br), widgets.get(2), 2);

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        saveWidgets(SAVE_THREE_WIDGETS);
    }
}
