package alarmcast.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import alarmcast.app.widgets.Widget;

/**
 * Created by charles on 6/6/14.
 */
public class AdapterWidget extends ArrayAdapter<Widget> {
    private OnSettingClick settingListener;

    private ArrayList<Widget> widgets;

    public interface OnSettingClick {
        public abstract void onSettingClick(int ndx,Widget selectedWidget);
    }

    public AdapterWidget(Context context, int textViewResourceId, ArrayList<Widget> objects) {
        super(context, textViewResourceId, objects);

        try {
            this.settingListener = (OnSettingClick)context;
            this.widgets = objects;

        }
        catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnSettingClick");
        }
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.widget, parent,false);
        }

        int height = parent.getHeight();
        int width = parent.getWidth();

        switch(parent.getId()) {

            case R.id.gv_four_widget_picker:
                if (height > 0) {
                    ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                    layoutParams.height = height  / 2 - 8;
                }
                break;

            case R.id.gv_three_widget_picker:
                if (height > 0) {
                    ViewGroup.LayoutParams layoutParams = v.getLayoutParams();

                    if(position == 0)
                        layoutParams.width = width;

                    else if(position == 1)
                        v.setY(-9000);

                    layoutParams.height = height / 2 - 8;
                }
                break;

            case R.id.gv_two_widget_picker:
                if (height > 0) {
                    ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                    layoutParams.height = height;
                }
                break;
        }

        final Widget curWidget = widgets.get(position);
        if (curWidget != null) {
            TextView tv = (TextView) v.findViewById(R.id.tv_widget_title);
            tv.setText(curWidget.getTitle());

            //TODO: Create imageView for empty widget
            if(curWidget.getImage() != -1) {
                ImageView iv = (ImageView) v.findViewById(R.id.iv_widget);
                iv.setImageResource(curWidget.getImage());
            }

            ImageButton btSetting = (ImageButton)v.findViewById(R.id.bt_widget_setting);

            btSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    settingListener.onSettingClick(position,curWidget);
                }
            });
        }

        return v;
    }
}
