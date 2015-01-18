package alarmcast.app.widgets;


import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;

import com.google.android.gms.maps.model.LatLng;

import alarmcast.app.R;
import alarmcast.app.utils.Location;
import alarmcast.app.widgets.dialogs.DlgMap;

/**
 * Created by charles on 6/6/14.
 */
public class MapWidget extends Widget {
    public final static String TITLE_MAP = "Traffic";
    private final static int IMAGE_MAP = R.drawable.map;

    private LatLng start;
    public String startStr;

    private LatLng end;
    public String endStr;

    private MapWidget(MapWidget toClone) {
        super(toClone);
        if(start != null) {
            this.start = new LatLng(toClone.start.latitude, toClone.start.longitude);
        }
        if(end != null ) {
            this.end = new LatLng(toClone.end.latitude, toClone.end.longitude);
        }
        this.startStr = toClone.startStr;
        this.endStr = toClone.endStr;
    }
    @Override
    public MapWidget getCopy() {
        return new MapWidget(this);
    }

    public MapWidget() {
        super(TITLE_MAP, IMAGE_MAP);

    }
    public void setStart(String s, Context c) {
        startStr = s;
        new Location(c).fromAddress(s, new Location.LocationFinder() {
            @Override
            public void onLocationFound(LatLng loc) {
                start = loc;
            }
        });
    }
    public void setEnd(String s, Context c) {
        endStr = s;
        new Location(c).fromAddress(s, new Location.LocationFinder() {
            @Override
            public void onLocationFound(LatLng loc) {
                end = loc;
            }
        });
    }

    @Override
    public DialogFragment getDialog(WidgetListener wl) {
        return DlgMap.newInstance(this, wl);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof MapWidget) {
            MapWidget mOther = (MapWidget) o;
            if(startStr == null && endStr == null) {
                return mOther.startStr == null && mOther.endStr == null;
            }
            if(startStr == null) {
                return endStr.equals(mOther.endStr);
            }
            else if(endStr == null) {
                return startStr.equals(mOther.startStr);
            }
            else {
                return startStr.equals(mOther.startStr) && endStr.equals(mOther.endStr);
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
