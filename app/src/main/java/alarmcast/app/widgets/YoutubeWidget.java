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

    private String ytURL;

    public YoutubeWidget(String ytURL) {
        super(TITLE_YOUTUBE, IMAGE_YOUTUBE);
        this.ytURL = ytURL;
    }

    public YoutubeWidget() {
        super(TITLE_YOUTUBE, IMAGE_YOUTUBE);
    }


    public void setYtURL(String s) {
        ytURL = s;
    }

    @Override
    public DialogFragment getDialog() {
        return DlgYoutube.newInstance(this);
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
