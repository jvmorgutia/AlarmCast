package alarmcast.app.widgets;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;

import com.google.android.gms.maps.model.LatLng;

import alarmcast.app.R;
import alarmcast.app.widgets.dialogs.DlgWeather;

/**
 * Created by charles on 6/6/14.
 */
public class WeatherWidget extends Widget {
    public final static String TITLE_WEATHER = "Weather";
    private final static int IMAGE_WEATHER = R.drawable.weather;

    private LatLng location;
    public String locationStr;

    public WeatherWidget(LatLng location) {
        super(TITLE_WEATHER, IMAGE_WEATHER);
        this.location = location;

    }
    private WeatherWidget(WeatherWidget toClone) {
        super(toClone);
        if(location != null) {
            this.location = new LatLng(toClone.location.latitude, toClone.location.longitude);
        }
        this.locationStr = toClone.locationStr;
    }
    @Override
    public WeatherWidget getCopy() {
        return new WeatherWidget(this);
    }

    public WeatherWidget() {
        super(TITLE_WEATHER, IMAGE_WEATHER);
    }

    public void setLocation(String s, Context c) {
        locationStr = s;
        location = toLatLng(s,c);
    }
    @Override
    public DialogFragment getDialog() {
        return DlgWeather.newInstance(this);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof WeatherWidget) {
            WeatherWidget wOther = (WeatherWidget) o;
            if(location == null) {
                return wOther.location == null;
            }
            else {
                return location.equals(wOther.location);
            }
        }
        return false;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel,i);

        if(location != null) {
            parcel.writeDouble(location.latitude);
            parcel.writeDouble(location.longitude);
        }
    }
    @Override
    public int describeContents() {
        return 0;
    }
    public static final Parcelable.Creator<WeatherWidget> CREATOR
            = new Parcelable.Creator<WeatherWidget>() {
        public WeatherWidget createFromParcel(Parcel in) {
            return new WeatherWidget(in);
        }

        public WeatherWidget[] newArray(int size) {
            return new WeatherWidget[size];
        }
    };
    private WeatherWidget(Parcel in) {
        super(in);
        location = new LatLng(in.readDouble(),in.readDouble());
    }

}
