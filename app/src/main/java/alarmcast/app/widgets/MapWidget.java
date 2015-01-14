package alarmcast.app.widgets;


import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;

import com.google.android.gms.maps.model.LatLng;

import alarmcast.app.R;
import alarmcast.app.widgets.dialogs.DlgMap;

/**
 * Created by charles on 6/6/14.
 */
public class MapWidget extends Widget {
    public final static String TITLE_MAP = "Traffic";
    private final static int IMAGE_MAP = R.drawable.map;

    private LatLng start;
    private LatLng end;

    public MapWidget(LatLng start, LatLng end) {
        super(TITLE_MAP, IMAGE_MAP);
        this.start = start;
        this.end = end;
    }
    public MapWidget() {
        super(TITLE_MAP, IMAGE_MAP);

    }
    public void setStart(String s, Context c) {
        start = Widget.toLatLng(s, c);
    }
    public void setEnd(String s, Context c) {
        start = Widget.toLatLng(s, c);
    }

    @Override
    public DialogFragment getDialog() {
        return DlgMap.newInstance(this);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof MapWidget) {
            MapWidget mOther = (MapWidget) o;
            if(start == null && end == null) {
                return mOther.start == null && mOther.end == null;
            }
            if(start == null) {
                return end.equals(mOther.end);
            }
            else if(end == null) {
                return start.equals(mOther.start);
            }
            else {
                return start.equals(mOther.start) && end.equals(mOther.end);
            }
        }
        return false;

    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel,i);
        if(start != null) {
            parcel.writeDouble(start.latitude);
            parcel.writeDouble(start.longitude);
        }
        if(end != null) {
            parcel.writeDouble(end.latitude);
            parcel.writeDouble(end.longitude);
        }
    }
    @Override
    public int describeContents() {
        return 0;
    }
    public static final Parcelable.Creator<MapWidget> CREATOR
            = new Parcelable.Creator<MapWidget>() {
        public MapWidget createFromParcel(Parcel in) {
            return new MapWidget(in);
        }

        public MapWidget[] newArray(int size) {
            return new MapWidget[size];
        }
    };
    private MapWidget(Parcel in) {
        super(in);
        start = new LatLng(in.readDouble(),in.readDouble());
        end = new LatLng(in.readDouble(),in.readDouble());
    }
}
