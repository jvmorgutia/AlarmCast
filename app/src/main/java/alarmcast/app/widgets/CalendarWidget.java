package alarmcast.app.widgets;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;

import alarmcast.app.R;

/**
 * Created by charles on 6/6/14.
 */
public class CalendarWidget extends Widget {
    public final static String TITLE_CALENDAR = "Calendar";
    private final static int IMAGE_CALENDAR = R.drawable.calendar;

    public CalendarWidget() {
        super(TITLE_CALENDAR,IMAGE_CALENDAR);
    }


    //TODO: Make calendar dialog
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
    public static final Parcelable.Creator<CalendarWidget> CREATOR
            = new Parcelable.Creator<CalendarWidget>() {
        public CalendarWidget createFromParcel(Parcel in) {
            return new CalendarWidget(in);
        }

        public CalendarWidget[] newArray(int size) {
            return new CalendarWidget[size];
        }
    };
    private CalendarWidget(Parcel in) {
        super(in);
    }

}
