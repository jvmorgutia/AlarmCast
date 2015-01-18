package alarmcast.app.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

//TODO: Create method to get user's current location
public class Location {

    private Geocoder geocoder;

    public interface LocationFinder {
        public void onLocationFound(LatLng loc);
    }

    public Location(Context c) {
        geocoder = new Geocoder(c, Locale.getDefault());
    }

    public void fromAddress(String input,  LocationFinder lf) {
        new FromAddress(lf).execute(input);
    }

    private class FromAddress extends AsyncTask<String, Void, LatLng> {
        private LocationFinder lf;
        private FromAddress(LocationFinder lf) {
            this.lf = lf;
        }
        @Override
        protected LatLng doInBackground(String... params) {
            List<Address> addresses;
            try {
                addresses = geocoder.getFromLocationName(params[0], 1);
                if(addresses != null && addresses.size() > 0 && addresses.get(0) != null)
                    return new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());

            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(LatLng result) {
            lf.onLocationFound(result);
        }
    }
}
