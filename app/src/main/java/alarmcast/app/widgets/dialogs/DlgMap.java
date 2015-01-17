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
import alarmcast.app.widgets.MapWidget;
import alarmcast.app.widgets.Widget;

public class DlgMap extends DialogFragment {
    private MapWidget mCurWidget;
    private Widget.WidgetListener wl;
    public static DlgMap newInstance(MapWidget curWidget, Widget.WidgetListener wl) {
        DlgMap dyt = new DlgMap();
        dyt.mCurWidget = curWidget;
        dyt.wl = wl;

        return dyt;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.dlg_map_setting, null);

        EditText tvStart = (EditText)  view.findViewById(R.id.tv_map_start);
        if(mCurWidget.startStr != null) {
            tvStart.setText(mCurWidget.startStr);
        }

        EditText tvEnd = (EditText)  view.findViewById(R.id.tv_map_end);
        if(mCurWidget.endStr != null) {
            tvEnd.setText(mCurWidget.endStr);
        }

        builder.setView(view)

                .setPositiveButton(getActivity().getString(R.string.dlg_accept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog d = (Dialog) dialog;

                        Editable etStart = ((EditText) d.findViewById(R.id.tv_map_start)).getText();
                        if (etStart != null)
                            mCurWidget.setStart(etStart.toString(), getActivity());

                        Editable etEnd = ((EditText) d.findViewById(R.id.tv_map_end)).getText();
                        if (etEnd != null)
                            mCurWidget.setEnd(etEnd.toString(), getActivity());

                        wl.onWidgetClicked(mCurWidget);

                    }
                })
                .setNegativeButton(getActivity().getString(R.string.dlg_cancel), null);

        Dialog d = builder.create();
        d.setCanceledOnTouchOutside(true);

        return builder.create();
    }
}
