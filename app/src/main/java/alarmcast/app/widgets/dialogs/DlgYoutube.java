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
import alarmcast.app.widgets.Widget;
import alarmcast.app.widgets.YoutubeWidget;

/**
 * Created by charles on 6/7/14.
 */
public class DlgYoutube extends DialogFragment {
    private YoutubeWidget curWidget;

    public static DlgYoutube newInstance(YoutubeWidget curWidget) {
        DlgYoutube dyt = new DlgYoutube();
        dyt.curWidget = curWidget;
        return dyt;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dlg_youtube_setting, null))
                // Add action buttons
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        final Dialog d = (Dialog)dialog;
                        Editable ytURL = ((EditText) d.findViewById(R.id.yt_url)).getText();
                        if (ytURL != null)
                            curWidget.setYtURL(ytURL.toString());

                    }
                })
                .setNegativeButton("Cancel", null);

        Dialog d = builder.create();
        d.setCanceledOnTouchOutside(true);

        return builder.create();
    }

}
