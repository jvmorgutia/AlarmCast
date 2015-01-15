package alarmcast.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FourWidgetFragment extends BaseWidgetFragment {
    public static final String SAVE_WIDGETS = "four_widgets";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_four_widget, container, false);
        if (widgets == null)
            widgets = loadWidgets(SAVE_WIDGETS);

        initWidgetView(v.findViewById(R.id.widget_four_tl), widgets.get(0), 0);
        initWidgetView(v.findViewById(R.id.widget_four_tr), widgets.get(1), 1);
        initWidgetView(v.findViewById(R.id.widget_four_bl), widgets.get(2), 2);
        initWidgetView(v.findViewById(R.id.widget_four_br), widgets.get(3), 3);
        initFabView(v);
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        saveWidgets(SAVE_WIDGETS);
    }
}
