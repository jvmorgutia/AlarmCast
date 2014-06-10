package alarmcast.app.widgets.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.widget.EditText;

import alarmcast.app.R;
import alarmcast.app.widgets.MapWidget;

/**
 * Created by charles on 6/7/14.
 */
public class DlgMap extends DialogFragment {
    private MapWidget curWidget;

    public static DlgMap newInstance(MapWidget curWidget) {
        DlgMap dyt = new DlgMap();
        dyt.curWidget = curWidget;

        return dyt;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();


        builder.setView(inflater.inflate(R.layout.dlg_map_setting, null))

                .setPositiveButton(getActivity().getString(R.string.dlg_accept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog d = (Dialog) dialog;

                        Editable etStart = ((EditText) d.findViewById(R.id.start)).getText();
                        if (etStart != null)
                            curWidget.setStart(etStart.toString(), getActivity());

                        Editable etEnd = ((EditText) d.findViewById(R.id.end)).getText();
                        if (etEnd != null)
                            curWidget.setEnd(etEnd.toString(),getActivity());

                    }
                })
                .setNegativeButton(getActivity().getString(R.string.dlg_cancel), null);

        Dialog d = builder.create();
        d.setCanceledOnTouchOutside(true);

        return builder.create();
    }
}
