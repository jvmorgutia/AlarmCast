package alarmcast.app.widgets;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by charles on 6/6/14.
 */
public abstract class Widget implements Parcelable {
    private String title;
    private int image;

    public interface WidgetListener {
        public void onWidgetClicked(Widget selectedWidget);
    }

    public abstract DialogFragment getDialog(WidgetListener wl);

    public abstract Widget getCopy();

    protected Widget(String title, int image) {
        this.title = title;
        this.image = image;
    }
    protected Widget(Widget toClone) {
        this.title = toClone.title;
        this.image = toClone.image;
    }


    public String getTitle() {return title;}
    public int getImage() {return image;}

    @Override
    public boolean equals(Object other) {
        if(other instanceof Widget) {
            Widget oWidget = (Widget) other;
            return oWidget.title.equals(title) && oWidget.image == image;
        }
        return false;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeInt(image);
    }

    protected Widget(Parcel in) {
        title = in.readString();
        image = in.readInt();
    }


}
