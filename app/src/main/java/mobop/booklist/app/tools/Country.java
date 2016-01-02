package mobop.booklist.app.tools;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.Locale;

public class Country {

    private Country() {

    }
    private static String country = null;
    // source : http://stackoverflow.com/a/19415296
    public synchronized static String getUserCountry(Context context) {
        if (country == null) {
            country = load(context);
        }
        return country;
    }
    private static String load(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                return simCountry.toUpperCase(Locale.FRENCH);
            }
            else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    return networkCountry.toUpperCase(Locale.FRENCH);
                }
            }
        }
        catch (Exception e) { }
        return context.getResources().getConfiguration().locale.getCountry().toUpperCase(Locale.FRENCH);
    }


}
