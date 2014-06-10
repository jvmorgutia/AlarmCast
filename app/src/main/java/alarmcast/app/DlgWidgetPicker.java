package alarmcast.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

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

    public static interface OnDialogComplete {
        public abstract void onDialogComplete(int ndx,Widget selectedWidget);
    }

    public static DlgWidgetPicker newInstance(int ndx) {
        DlgWidgetPicker dwp = new DlgWidgetPicker();
        dwp.ndx = ndx;
        return dwp;
    }

    @Override
    public void onAttach(Activity activity) {
        try {
            this.mListener = (OnDialogComplete)activity;
            super.onAttach(activity);
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnDialogComplete");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dlg_title_widgets)
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
        mListener.onDialogComplete(ndx,selectedWidget);
    }


}
