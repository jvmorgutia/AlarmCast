package alarmcast.app.widgets.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.widget.EditText;

import alarmcast.app.R;
import alarmcast.app.widgets.WeatherWidget;

//TODO: Implement functionality for use current
public class DlgWeather extends DialogFragment{
    private WeatherWidget mCurWidget;

    public static DlgWeather newInstance(WeatherWidget curWidget) {
        DlgWeather dyt = new DlgWeather();
        dyt.mCurWidget = curWidget;

        return dyt;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dlg_weather_setting, null))

                .setPositiveButton(getActivity().getString(R.string.dlg_accept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog d = (Dialog) dialog;

                        Editable location = ((EditText) d.findViewById(R.id.location)).getText();
                        if (location != null)
                            mCurWidget.setLocation(location.toString(), getActivity());
                    }
                })
                .setNeutralButton(getActivity().getString(R.string.dlg_use_current), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton(getActivity().getString(R.string.dlg_cancel), null);

        Dialog d = builder.create();
        d.setCanceledOnTouchOutside(true);

        return builder.create();
    }
}
