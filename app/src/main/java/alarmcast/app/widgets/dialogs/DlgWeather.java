package alarmcast.app.widgets.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import alarmcast.app.R;
import alarmcast.app.widgets.WeatherWidget;
import alarmcast.app.widgets.Widget;

//TODO: Implement functionality for use current
public class DlgWeather extends DialogFragment{
    private WeatherWidget mCurWidget;
    private Widget.WidgetListener wl;

    public static DlgWeather newInstance(WeatherWidget curWidget, Widget.WidgetListener wl) {
        DlgWeather dyt = new DlgWeather();
        dyt.mCurWidget = curWidget;
        dyt.wl = wl;
        return dyt;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dlg_weather_setting, null);

        EditText tvLoc = (EditText) view.findViewById(R.id.tv_weather_location);
        if(mCurWidget.locationStr != null) {
            tvLoc.setText(mCurWidget.locationStr);
        }

        builder.setView(view)
                .setPositiveButton(getActivity().getString(R.string.dlg_accept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog d = (Dialog) dialog;

                        Editable location = ((EditText) d.findViewById(R.id.tv_weather_location)).getText();
                        if (location != null)
                            mCurWidget.setLocation(location.toString(), getActivity());
                        wl.onWidgetClicked(mCurWidget);
                    }
                })
                .setNeutralButton(getActivity().getString(R.string.dlg_use_current), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //TODO: mCurWidget.location = new Location.getCurrent();
                    }
                })
                .setNegativeButton(getActivity().getString(R.string.dlg_cancel), null);

        Dialog d = builder.create();
        d.setCanceledOnTouchOutside(true);

        return builder.create();
    }
}
