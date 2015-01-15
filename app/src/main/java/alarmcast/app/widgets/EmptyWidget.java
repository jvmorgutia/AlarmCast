package alarmcast.app.widgets;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;

/**
 * Created by charles on 6/7/14.
 */
public class EmptyWidget extends Widget {
    public final static String TITLE_NONE = "None Selected";
    private final static int IMAGE_NONE = -1;

    public EmptyWidget() {
        super(TITLE_NONE,IMAGE_NONE);
    }
    public EmptyWidget getCopy() {
        return new EmptyWidget();
    }

    @Override
    public DialogFragment getDialog() {
        return null;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel,i);
    }
    @Override
    public int describeContents() {
        return 0;
    }
    public static final Parcelable.Creator<EmptyWidget> CREATOR
            = new Parcelable.Creator<EmptyWidget>() {
        public EmptyWidget createFromParcel(Parcel in) {
            return new EmptyWidget(in);
        }

        public EmptyWidget[] newArray(int size) {
            return new EmptyWidget[size];
        }
    };
    private EmptyWidget(Parcel in) {
        super(in);
    }
}
