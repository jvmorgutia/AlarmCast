package alarmcast.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import alarmcast.app.widgets.CalendarWidget;
import alarmcast.app.widgets.MapWidget;
import alarmcast.app.widgets.WeatherWidget;
import alarmcast.app.widgets.Widget;
import alarmcast.app.widgets.YoutubeWidget;

/**
 * Created by charles on 6/7/14.
 */
public class DlgWidgetPicker extends DialogFragment implements DialogInterface.OnClickListener {
    private OnDialogComplete mListener;
    private Widget selectedWidget;
    private int ndx;
    private View v;

    public static interface OnDialogComplete {
        public abstract void onDialogComplete(View v, Widget selectedWidget, int ndx);
    }

    public static DlgWidgetPicker newInstance(OnDialogComplete mListener, View v, int ndx) {
        DlgWidgetPicker dwp = new DlgWidgetPicker();
        dwp.mListener = mListener;
        dwp.ndx = ndx;
        dwp.v = v;
        return dwp;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dlg_picker_main, null);

        builder.setCustomTitle(view)
               .setItems(R.array.widgets, this);

        Dialog d = builder.create();
        d.setCanceledOnTouchOutside(true);

        return d;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int position) {
        switch(position) {
            case 0:
                selectedWidget = new CalendarWidget();
                break;
            case 1:
                selectedWidget = new MapWidget();
                break;
            case 2:
                selectedWidget = new YoutubeWidget();
                break;
            case 3:
                selectedWidget = new WeatherWidget();
                break;

        }
        mListener.onDialogComplete(v, selectedWidget, ndx);
    }


}
