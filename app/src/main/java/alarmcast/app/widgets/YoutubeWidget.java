package alarmcast.app.widgets;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;

import alarmcast.app.R;
import alarmcast.app.widgets.dialogs.DlgYoutube;

/**
 * Created by charles on 6/6/14.
 */
public class YoutubeWidget extends Widget {
    public final static String TITLE_YOUTUBE = "Youtube";
    private final static int IMAGE_YOUTUBE = R.drawable.youtube;

    public String ytURL;

    private YoutubeWidget(YoutubeWidget toClone) {
        super(toClone);
        this.ytURL = toClone.ytURL;
    }
    @Override
    public YoutubeWidget getCopy() {
        return new YoutubeWidget(this);
    }

    public YoutubeWidget() {
        super(TITLE_YOUTUBE, IMAGE_YOUTUBE);
    }


    public void setYtURL(String s) {
        ytURL = s;
    }

    @Override
    public DialogFragment getDialog(WidgetListener wl) {
        return DlgYoutube.newInstance(this, wl);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof YoutubeWidget) {
            YoutubeWidget ytOther = (YoutubeWidget) o;
            if(ytURL == null) {
                return ytOther.ytURL == null;
            }
            else {
                return ytURL.equals(ytOther.ytURL);
            }
        }
        return false;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel,i);
        parcel.writeString(ytURL);

    }
    @Override
    public int describeContents() {
        return 0;
    }
    public static final Parcelable.Creator<YoutubeWidget> CREATOR
            = new Parcelable.Creator<YoutubeWidget>() {
        public YoutubeWidget createFromParcel(Parcel in) {
            return new YoutubeWidget(in);
        }

        public YoutubeWidget[] newArray(int size) {
            return new YoutubeWidget[size];
        }
    };
    private YoutubeWidget(Parcel in) {
        super(in);
        ytURL = in.readString();
    }
}
