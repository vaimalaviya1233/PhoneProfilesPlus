package sk.henrichg.phoneprofilesplus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.Settings;

import androidx.core.graphics.ColorUtils;
import androidx.palette.graphics.Palette;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class ProfileStatic {

    public static String getIconIdentifier(String icon)
    {
        //Log.e("ProfileStatic.getIconIdentifier", "_icon="+_icon);
        String value;
        try {
            String[] splits = icon.split("\\|");
            value = splits[0];
        } catch (Exception e) {
            value = "ic_profile_default";
        }
        return value;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean getIsIconResourceID(String icon)
    {
        boolean value;
        try {
            String[] splits = icon.split("\\|");
            value = splits[1].equals("1");

        } catch (Exception e) {
            value = true;
        }
        return value;
    }

    static int getVolumeRingtoneValue(String volumeRingtone)
    {
        int value;
        try {
            String[] splits = volumeRingtone.split("\\|");
            value = Integer.parseInt(splits[0]);
        } catch (Exception e) {
            value = 0;
        }
        return value;
    }

    static boolean getVolumeChange(String volume)
    {
        int value;
        try {
            String[] splits = volume.split("\\|");
            value = Integer.parseInt(splits[1]);
        } catch (Exception e) {
            value = 1;
        }
        return value == 0; // in preference dialog is checked=No change
    }

    static int getDeviceBrightnessValue(String _deviceBrightness)
    {
        int maximumValue = 100;
        int defaultValue = 50;
        int value;
        try {
            String[] splits = _deviceBrightness.split("\\|");
            value = Integer.parseInt(splits[0]);
            if ((value < 0) || (value > maximumValue))
                value = defaultValue;
        } catch (Exception e) {
            value = defaultValue;
        }
        return value;
    }

    static boolean getDeviceBrightnessChange(String _deviceBrightness)
    {
        int value;
        try {
            String[] splits = _deviceBrightness.split("\\|");
            value = Integer.parseInt(splits[1]);
        } catch (Exception e) {
            value = 1;
        }
        return value == 0; // in preference dialog is checked=No change
    }

    static boolean getDeviceBrightnessAutomatic(String _deviceBrightness)
    {
        int value;
        try {
            String[] splits = _deviceBrightness.split("\\|");
            value = Integer.parseInt(splits[2]);
        } catch (Exception e) {
            value = 1;
        }
        return value == 1;
    }

    static boolean getDeviceBrightnessChangeLevel(String _deviceBrightness)
    {
        int value;
        try {
            String[] splits = _deviceBrightness.split("\\|");
            value = Integer.parseInt(splits[4]);
        } catch (Exception e) {
            value = 1;
        }
        return value == 1;
    }


//    private static int getMinimumScreenBrightnessSetting (Context context)
//    {
//        return context.getResources().getInteger(com.android.internal.R.integer.config_screenBrightnessSettingMinimum);
//
        /*
        final Resources res = Resources.getSystem();
        int id = res.getIdentifier("config_screenBrightnessSettingMinimum", "integer", "android"); // API17+
        if (id == 0)
            id = res.getIdentifier("config_screenBrightnessDim", "integer", "android"); // lower API levels
        if (id != 0)
        {
            try {
                return res.getInteger(id);
            }
            catch (Exception ignored) {}
        }
        return 0;
        */
//    }

//    static int getMaximumScreenBrightnessSetting (Context context)
//    {
//        return context.getResources().getInteger(com.android.internal.R.integer.config_screenBrightnessSettingMaximum);
        /*
        final Resources res = Resources.getSystem();
        final int id = res.getIdentifier("config_screenBrightnessSettingMaximum", "integer", "android");  // API17+
        if (id != 0)
        {
            try {
                int value = res.getInteger(id);
                if (value > 255)
                    value = 255;
                return value;
            }
            catch (Resources.NotFoundException e) {
                // ignore
            }
        }
        return 255;
        */
//    }

    ////// from AOSP and changed for PPP
//    private static final int GAMMA_SPACE_MAX_256 = 1023;
    //private static final int GAMMA_SPACE_MAX_1024 = 4095;

    // Hybrid Log Gamma constant values
//    private static final float _R = 0.5f;
//    private static final float _A = 0.17883277f;
//    private static final float _B = 0.28466892f;
//    private static final float _C = 0.55991073f;

    /*
        private static float convertLinearToGamma(float val, float min, float max) {
            // For some reason, HLG normalizes to the range [0, 12] rather than [0, 1]
            final float normalizedVal = MathUtils.norm(min, max, val) * 12;
            final float ret;
            if (normalizedVal <= 1f) {
                ret = MathUtils.sqrt(normalizedVal) * _R;
            } else {
                ret = _A * MathUtils.log(normalizedVal - _B) + _C;
            }
            //int spaceMax = GAMMA_SPACE_MAX_256;
            //if (PPApplication.romIsOnePlus)
            //    spaceMax = GAMMA_SPACE_MAX_1024;
            //return Math.round(MathUtils.lerp(0, GAMMA_SPACE_MAX_256, ret));
            return MathUtils.lerp(0, GAMMA_SPACE_MAX_256, ret);
        }

        private static float convertGammaToLinear(float val, float min, float max) {
            //int spaceMax = GAMMA_SPACE_MAX_256;
            //if (PPApplication.romIsOnePlus)
            //    spaceMax = GAMMA_SPACE_MAX_1024;
            final float normalizedVal = MathUtils.norm(0, GAMMA_SPACE_MAX_256, val);
            final float ret;
            if (normalizedVal <= _R) {
                ret = MathUtils.sq(normalizedVal / _R);
            } else {
                ret = MathUtils.exp((normalizedVal - _C) / _A) + _B;
            }
            // HLG is normalized to the range [0, 12], so we need to re-normalize to the range [0, 1]
            // in order to derive the correct setting value.
            //return Math.round(MathUtils.lerp(min, max, ret / 12));
            return MathUtils.lerp(min, max, ret / 12);
        }

        private static float getPercentage(float value, float min, float max) {
            if (value > max) {
                return 1.0f;
            }
            if (value < min) {
                return 0.0f;
            }
            //return ((float)value - min) / (max - min);
            return (value - min) / (max - min);
        }
    */
    private static int getBrightnessPercentage_A9(int settingsValue/*, int minValue, int maxValue*/) {
        /*final float value;
        float _settingsValue = settingsValue;
        if (PPApplication.romIsOnePlus)
            _settingsValue = settingsValue / 4; // convert from 1024 to 256

        value = convertLinearToGamma(_settingsValue, minValue, maxValue);
        //int spaceMax = GAMMA_SPACE_MAX_256;
        //if (PPApplication.romIsOnePlus)
        //    spaceMax = GAMMA_SPACE_MAX_1024;
        int percentage = Math.round(getPercentage(value, 0, GAMMA_SPACE_MAX_256) * 100);*/

        int _settingsValue = settingsValue;
        if (PPApplication.deviceIsOnePlus)
            _settingsValue = Math.round(settingsValue / 4f); // convert from 1024 to 256
        else
        if (PPApplication.deviceIsXiaomi && PPApplication.romIsMIUI)
            _settingsValue = Math.round(settingsValue / 16f); // convert from 4096 to 256
        //noinspection UnnecessaryLocalVariable
        int percentage = BrightnessLookup.lookup(_settingsValue, true);
        /*if (PPApplication.logEnabled()) {
            PPApplication.logE("getBrightnessPercentage_A9", "settingsValue=" + settingsValue);
            PPApplication.logE("getBrightnessPercentage_A9", "percentage=" + percentage);
        }*/
        return percentage;
    }

    private static int getBrightnessValue_A9(int percentage/*, int minValue, int maxValue*/) {
        //int spaceMax = GAMMA_SPACE_MAX_256;
        //if (PPApplication.romIsOnePlus)
        //    spaceMax = GAMMA_SPACE_MAX_1024;
        //int value = Math.round((GAMMA_SPACE_MAX_256+1) / 100f * (float)(percentage + 1));
        /*float value = (GAMMA_SPACE_MAX_256+1) / 100f * (float)(percentage + 1);
        float systemValue = convertGammaToLinear(value, minValue, maxValue);
        if (PPApplication.romIsOnePlus)
            systemValue = systemValue * 4; // convert from 256 to 1024
        PPApplication.logE("getBrightnessValue_A9", "float systemValue="+systemValue);

        int maximumValue = 255;
        if (PPApplication.romIsOnePlus)
            maximumValue = 1023;
        if (systemValue > maximumValue)
            systemValue = maximumValue;*/

        int systemValue = BrightnessLookup.lookup(percentage, false);
        if (PPApplication.deviceIsOnePlus)
            systemValue = systemValue * 4; // convert from 256 to 1024
        else
        if (PPApplication.deviceIsXiaomi && PPApplication.romIsMIUI)
            systemValue = systemValue * 16; // convert from 256 to 4096

        /*if (PPApplication.logEnabled()) {
            PPApplication.logE("getBrightnessValue_A9", "percentage=" + percentage);
            PPApplication.logE("getBrightnessValue_A9", "systemValue=" + systemValue);
        }*/

        return Math.round(systemValue);
    }

    ///////////////

    static int convertPercentsToBrightnessManualValue(int percentage, Context context)
    {
        int maximumValue;// = getMaximumScreenBrightnessSetting();
        int minimumValue;// = getMinimumScreenBrightnessSetting();

        //PPApplication.logE("convertPercentsToBrightnessManualValue", "maximumValue="+getMaximumScreenBrightnessSetting(context.getApplicationContext()));
        //PPApplication.logE("convertPercentsToBrightnessManualValue", "minimumValue="+getMinimumScreenBrightnessSetting(context.getApplicationContext()));

        //if (maximumValue-minimumValue > 255) {
        minimumValue = 0;
        maximumValue = 255;
        if (PPApplication.deviceIsOnePlus && (Build.VERSION.SDK_INT >= 28))
            maximumValue = 1023;
        else
        if (PPApplication.deviceIsXiaomi && PPApplication.romIsMIUI && (Build.VERSION.SDK_INT >= 28))
            maximumValue = 4095;
        //}

        int value;

        if (percentage == Profile.BRIGHTNESS_ADAPTIVE_BRIGHTNESS_NOT_SET) {
            // brightness is not set, change it to default manual brightness value
            int defaultValue = 128;
            if (PPApplication.deviceIsOnePlus && (Build.VERSION.SDK_INT >= 28))
                defaultValue = 512;
            else
            if (PPApplication.deviceIsXiaomi && PPApplication.romIsMIUI && (Build.VERSION.SDK_INT >= 28))
                defaultValue = 2048;

            if ((Build.VERSION.SDK_INT > 28) &&
                    (!(PPApplication.deviceIsSamsung && PPApplication.romIsGalaxy)) &&
                    (!PPApplication.deviceIsOnePlus) &&
                    (!PPApplication.deviceIsLenovo)) {
                //PPApplication.logE("convertPercentsToBrightnessManualValue", "getBrightnessValue_A9 called - SDK_INT > 28");
                defaultValue = getBrightnessValue_A9(50/*, minimumValue, maximumValue*/);
            }
            else
            if ((Build.VERSION.SDK_INT == 28) && Build.MODEL.contains("Nexus")) {// Nexus may be LG, Samsung, Huawei, ...
                //PPApplication.logE("convertPercentsToBrightnessManualValue", "getBrightnessValue_A9 called - SDK_INT == 28 and Nexus");
                defaultValue = getBrightnessValue_A9(50/*, minimumValue, maximumValue*/);
            }
            else
            if ((Build.VERSION.SDK_INT == 28) &&
                    (!(PPApplication.deviceIsSamsung && PPApplication.romIsGalaxy)) &&
                    (!PPApplication.deviceIsLG)/* && (!PPApplication.romIsOnePlus)*/) {
                //PPApplication.logE("convertPercentsToBrightnessManualValue", "getBrightnessValue_A9 called - SDK_INT == 28 and !Samsung and !LG");
                defaultValue = getBrightnessValue_A9(50/*, minimumValue, maximumValue*/);
            }
            /*else {
                PPApplication.logE("convertPercentsToBrightnessManualValue", "getBrightnessValue_A9 NOT called");
            }*/
            value = Settings.System.getInt(context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS, defaultValue);
        }
        else {
            /*if (PPApplication.logEnabled()) {
                try {
                    int oldValue = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
                    PPApplication.logE("convertPercentsToBrightnessManualValue", "oldValue=" + oldValue);
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }
            }*/
            if ((Build.VERSION.SDK_INT > 28) &&
                    (!(PPApplication.deviceIsSamsung && PPApplication.romIsGalaxy)) &&
                    (!PPApplication.deviceIsOnePlus) &&
                    (!PPApplication.deviceIsLenovo)) {
                //PPApplication.logE("convertPercentsToBrightnessManualValue", "getBrightnessValue_A9 called - SDK_INT > 28");
                value = getBrightnessValue_A9(percentage/*, minimumValue, maximumValue*/);
            }
            else
            if ((Build.VERSION.SDK_INT == 28) && Build.MODEL.contains("Nexus")) {// Nexus may be LG, Samsung, Huawei, ...
                //PPApplication.logE("convertPercentsToBrightnessManualValue", "getBrightnessValue_A9 called - SDK_INT == 28 and Nexus");
                value = getBrightnessValue_A9(percentage/*, minimumValue, maximumValue*/);
            }
            else
            if ((Build.VERSION.SDK_INT == 28) &&
                    (!(PPApplication.deviceIsSamsung && PPApplication.romIsGalaxy)) &&
                    (!PPApplication.deviceIsLG)/* && (!PPApplication.romIsOnePlus)*/) {
                //PPApplication.logE("convertPercentsToBrightnessManualValue", "getBrightnessValue_A9 called - SDK_INT == 28 and !Samsung and !LG");
                value = getBrightnessValue_A9(percentage/*, minimumValue, maximumValue*/);
            }
            else {
                //PPApplication.logE("convertPercentsToBrightnessManualValue", "getBrightnessValue_A9 NOT called");
                value = Math.round((float) (maximumValue - minimumValue) / 100 * percentage) + minimumValue;
            }
        }

        //PPApplication.logE("convertPercentsToBrightnessManualValue", "value="+value);
        return value;
    }

    static long convertBrightnessToPercents(int value/*, int maxValue, int minValue*/)
    {
        long percentage;
        if (value == Profile.BRIGHTNESS_ADAPTIVE_BRIGHTNESS_NOT_SET)
            percentage = value; // keep BRIGHTNESS_ADAPTIVE_BRIGHTNESS_NOT_SET
        else {
            if ((Build.VERSION.SDK_INT > 28) &&
                    (!(PPApplication.deviceIsSamsung && PPApplication.romIsGalaxy)) &&
                    (!PPApplication.deviceIsOnePlus) &&
                    (!PPApplication.deviceIsLenovo)) {
                //PPApplication.logE("convertBrightnessToPercents", "getBrightnessPercentage_A9 called - SDK_INT > 28");
                percentage = getBrightnessPercentage_A9(value/*, minValue, maxValue*/);
            }
            else
            if ((Build.VERSION.SDK_INT == 28) && Build.MODEL.contains("Nexus")) {// Nexus may be LG, Samsung, Huawei, ...
                //PPApplication.logE("convertBrightnessToPercents", "getBrightnessPercentage_A9 called - SDK_INT == 28 and Nexus");
                percentage = getBrightnessPercentage_A9(value/*, minValue, maxValue*/);
            }
            else
            if ((Build.VERSION.SDK_INT == 28) &&
                    (!(PPApplication.deviceIsSamsung && PPApplication.romIsGalaxy)) &&
                    (!PPApplication.deviceIsLG)) {
                //PPApplication.logE("convertBrightnessToPercents", "getBrightnessPercentage_A9 called - SDK_INT == 28 and !Samsung and !LG");
                percentage = getBrightnessPercentage_A9(value/*, minValue, maxValue*/);
            }
            else {
                //PPApplication.logE("convertBrightnessToPercents", "getBrightnessPercentage_A9 NOT called");
                //if (maximumValue-minimumValue > 255) {
                //int minimumValue = 0;
                int maximumValue = 255;
                if (PPApplication.deviceIsOnePlus && (Build.VERSION.SDK_INT >= 28))
                    maximumValue = 1023;
                else
                if (PPApplication.deviceIsXiaomi && PPApplication.romIsMIUI && (Build.VERSION.SDK_INT >= 28))
                    maximumValue = 4095;
                //}

                percentage = Math.round((float) (value/* - minValue*/) / (maximumValue/* - minValue*/) * 100.0);
            }
        }

        return percentage;
    }

    static float convertPercentsToBrightnessAdaptiveValue(int percentage, Context context)
    {
        float value;

        if (percentage == Profile.BRIGHTNESS_ADAPTIVE_BRIGHTNESS_NOT_SET)
            // brightness is not set, change it to default adaptive brightness value
            value = Settings.System.getFloat(context.getContentResolver(),
                    Settings.System.SCREEN_AUTO_BRIGHTNESS_ADJ, 0f);
        else {
            boolean exponentialLevel = false;
            if ((Build.VERSION.SDK_INT > 28) &&
                    (!(PPApplication.deviceIsSamsung && PPApplication.romIsGalaxy)) &&
                    (!PPApplication.deviceIsOnePlus) &&
                    (!PPApplication.deviceIsLenovo)) {
                //PPApplication.logE("convertPercentsToBrightnessAdaptiveValue", "exponentialLevel=true - SDK_INT > 28");
                exponentialLevel = true;
            }
            else
            if ((Build.VERSION.SDK_INT == 28) && Build.MODEL.contains("Nexus")) {// Nexus may be LG, Samsung, Huawei, ...
                //PPApplication.logE("convertPercentsToBrightnessAdaptiveValue", "exponentialLevel=true - SDK_INT == 28 and Nexus");
                exponentialLevel = true;
            }
            else
            if ((Build.VERSION.SDK_INT == 28) &&
                    (!(PPApplication.deviceIsSamsung && PPApplication.romIsGalaxy)) &&
                    (!PPApplication.deviceIsLG)/* && (!PPApplication.romIsOnePlus)*/) {
                //PPApplication.logE("convertPercentsToBrightnessAdaptiveValue", "exponentialLevel=true - SDK_INT == 28 and !Samsung and !LG");
                exponentialLevel = true;
            }
            /*else {
                PPApplication.logE("convertPercentsToBrightnessAdaptiveValue", "exponentialLevel=false");
            }*/

            if (!exponentialLevel)
                value = (percentage - 50) / 50f;
            else {
//                int maximumValue;// = getMaximumScreenBrightnessSetting();
//                int minimumValue;// = getMinimumScreenBrightnessSetting();

                //PPApplication.logE("convertPercentsToBrightnessAdaptiveValue", "maximumValue="+getMaximumScreenBrightnessSetting(context.getApplicationContext()));
                //PPApplication.logE("convertPercentsToBrightnessAdaptiveValue", "minimumValue="+getMinimumScreenBrightnessSetting(context.getApplicationContext()));

                //if (maximumValue-minimumValue > 255) {
//                minimumValue = 0;
//                maximumValue = 255;
//                if (PPApplication.romIsOnePlus)
//                    maximumValue = 1023;
                //}

                if (PPApplication.deviceIsOnePlus)
                    value = (getBrightnessValue_A9(percentage/*, minimumValue, maximumValue*/) - 512) / 512f;
                else
                if (PPApplication.deviceIsXiaomi && PPApplication.romIsMIUI)
                    value = (getBrightnessValue_A9(percentage/*, minimumValue, maximumValue*/) - 2048) / 2048f;
                else
                    value = (getBrightnessValue_A9(percentage/*, minimumValue, maximumValue*/) - 128) / 128f;
            }
        }

        return value;
    }

    /*
    static boolean getGenerateNotificationGenerate(String _generateNotification)
    {
        int value;
        try {
            String[] splits = _generateNotification.split("\\|");
            value = Integer.parseInt(splits[0]);
        } catch (Exception e) {
            value = 0;
        }
        return value == 1;
    }
    */

    static int getGenerateNotificationIconType(String _generateNotification)
    {
        int value;
        try {
            String[] splits = _generateNotification.split("\\|");
            value = Integer.parseInt(splits[1]);
        } catch (Exception e) {
            value = 1;
        }
        return value;
    }

    static String getGenerateNotificationTitle(String _generateNotification)
    {
        String value;
        try {
            String[] splits = _generateNotification.split("\\|");
            value = splits[2];
        } catch (Exception e) {
            value = "";
        }
        return value;
    }

    static String getGenerateNotificationBody(String _generateNotification)
    {
        String value;
        try {
            String[] splits = _generateNotification.split("\\|");
            value = splits[3];
        } catch (Exception e) {
            value = "";
        }
        return value;
    }

    static Bitmap increaseProfileIconBrightnessForPreference(Bitmap iconBitmap, ProfileIconPreferenceX preference) {
        //if (ApplicationPreferences.applicationIncreaseBrightnessForProfileIcon) {
        if (preference != null) {
            boolean nightModeOn = (preference.prefContext.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                    == Configuration.UI_MODE_NIGHT_YES;

            if (nightModeOn) {
                int iconColor;
                if (preference.isImageResourceID)
                {
                    if (preference.useCustomColor)
                        iconColor = preference.customColor;
                    else
                        iconColor = getIconDefaultColor(preference.imageIdentifier);
                } else {
                    //iconColor = BitmapManipulator.getDominantColor(_iconBitmap);
                    Palette palette = Palette.from(iconBitmap).generate();
                    iconColor = palette.getDominantColor(0xff1c9cd7);
                }
                if (ColorUtils.calculateLuminance(iconColor) < Profile.MIN_PROFILE_ICON_LUMINANCE) {
                    if (iconBitmap != null) {
                        return BitmapManipulator.setBitmapBrightness(iconBitmap, Profile.BRIGHTNESS_VALUE_FOR_DARK_MODE);
                    } else {
                        int iconResource = getIconResource(preference.imageIdentifier);
                        Bitmap bitmap = BitmapManipulator.getBitmapFromResource(iconResource, true, preference.prefContext);
                        return BitmapManipulator.setBitmapBrightness(bitmap, Profile.BRIGHTNESS_VALUE_FOR_DARK_MODE);
                    }
                }
            }
        }
        //}
        return null;
    }

    static int getImageResourcePosition(String imageIdentifier/*, Context context*/) {
        /*for (int pos = 0; pos < Profile.profileIconId.length; pos++) {
            String resName = context.getResources().getResourceEntryName(Profile.profileIconId[pos]);
            if (resName.equals(imageIdentifier))
                return pos;
        }*/
        if (Profile.profileIconIdMap.get(imageIdentifier) != null) {
            int iconResource = getIconResource(imageIdentifier);
            for (int pos = 0; pos < Profile.profileIconId.length; pos++) {
                if (Profile.profileIconId[pos] == iconResource)
                    return pos;
            }
        }
        return 0;
    }

    static String getImageResourceName(int position) {
        int iconResource = Profile.profileIconId[position];
        //noinspection rawtypes
        for(Map.Entry entry: Profile.profileIconIdMap.entrySet()){
            if (entry.getValue().equals(iconResource)) {
                return entry.getKey().toString();
            }
        }
        return "ic_profile_default";
    }

    static int getIconDefaultColor(String imageIdentifier/*, Context context*/) {
        return Profile.profileIconColor[getImageResourcePosition(imageIdentifier/*, context*/)];
    }

    @SuppressLint("SimpleDateFormat")
    static String timeDateStringFromTimestamp(Context applicationContext, long timestamp){
        String timeDate;
        String timestampDate = android.text.format.DateFormat.getDateFormat(applicationContext).format(new Date(timestamp));
        Calendar calendar = Calendar.getInstance();
        String currentDate = android.text.format.DateFormat.getDateFormat(applicationContext).format(new Date(calendar.getTimeInMillis()));
        String androidDateTime;
        if (timestampDate.equals(currentDate))
            androidDateTime=android.text.format.DateFormat.getTimeFormat(applicationContext).format(new Date(timestamp));
        else
            androidDateTime=android.text.format.DateFormat.getDateFormat(applicationContext).format(new Date(timestamp))+" "+
                    android.text.format.DateFormat.getTimeFormat(applicationContext).format(new Date(timestamp));
        String javaDateTime = DateFormat.getDateTimeInstance().format(new Date(timestamp));
        String AmPm="";
        if(!Character.isDigit(androidDateTime.charAt(androidDateTime.length()-1))) {
            if(androidDateTime.contains(new SimpleDateFormat().getDateFormatSymbols().getAmPmStrings()[Calendar.AM])){
                AmPm=" "+new SimpleDateFormat().getDateFormatSymbols().getAmPmStrings()[Calendar.AM];
            }else{
                AmPm=" "+new SimpleDateFormat().getDateFormatSymbols().getAmPmStrings()[Calendar.PM];
            }
            androidDateTime=androidDateTime.replace(AmPm, "");
        }
        if(!Character.isDigit(javaDateTime.charAt(javaDateTime.length()-1))){
            javaDateTime=javaDateTime.replace(" "+new SimpleDateFormat().getDateFormatSymbols().getAmPmStrings()[Calendar.AM], "");
            javaDateTime=javaDateTime.replace(" "+new SimpleDateFormat().getDateFormatSymbols().getAmPmStrings()[Calendar.PM], "");
        }
        javaDateTime=javaDateTime.substring(javaDateTime.length()-3);
        timeDate=androidDateTime.concat(javaDateTime);
        return timeDate.concat(AmPm);
    }

    // this change old, no longer used SHARED_PROFILE_VALUE to "Not used" value
    static Profile removeSharedProfileParameters(Profile profile)
    {
        final int SHARED_PROFILE_VALUE = 99;
        final String CONNECTTOSSID_SHAREDPROFILE = "^default_profile^";

        if (profile != null)
        {
            //Profile sharedProfile = getProfileFromSharedPreferences(context, PPApplication.SHARED_PROFILE_PREFS_NAME);

            Profile mappedProfile = new Profile(
                    profile._id,
                    profile._name,
                    profile._icon,
                    profile._checked,
                    profile._porder,
                    profile._volumeRingerMode,
                    profile._volumeRingtone,
                    profile._volumeNotification,
                    profile._volumeMedia,
                    profile._volumeAlarm,
                    profile._volumeSystem,
                    profile._volumeVoice,
                    profile._soundRingtoneChange,
                    profile._soundRingtone,
                    profile._soundNotificationChange,
                    profile._soundNotification,
                    profile._soundAlarmChange,
                    profile._soundAlarm,
                    profile._deviceAirplaneMode,
                    profile._deviceWiFi,
                    profile._deviceBluetooth,
                    profile._deviceScreenTimeout,
                    profile._deviceBrightness,
                    profile._deviceWallpaperChange,
                    profile._deviceWallpaper,
                    profile._deviceMobileData,
                    profile._deviceMobileDataPrefs,
                    profile._deviceGPS,
                    profile._deviceRunApplicationChange,
                    profile._deviceRunApplicationPackageName,
                    profile._deviceAutoSync,
                    profile._showInActivator,
                    profile._deviceAutoRotate,
                    profile._deviceLocationServicePrefs,
                    profile._volumeSpeakerPhone,
                    profile._deviceNFC,
                    profile._duration,
                    profile._afterDurationDo,
                    profile._volumeZenMode,
                    profile._deviceKeyguard,
                    profile._vibrationOnTouch,
                    profile._deviceWiFiAP,
                    profile._devicePowerSaveMode,
                    profile._askForDuration,
                    profile._deviceNetworkType,
                    profile._notificationLed,
                    profile._vibrateWhenRinging,
                    profile._deviceWallpaperFor,
                    profile._hideStatusBarIcon,
                    profile._lockDevice,
                    profile._deviceConnectToSSID,
                    profile._applicationDisableWifiScanning,
                    profile._applicationDisableBluetoothScanning,
                    profile._durationNotificationSound,
                    profile._durationNotificationVibrate,
                    profile._deviceWiFiAPPrefs,
                    profile._applicationDisableLocationScanning,
                    profile._applicationDisableMobileCellScanning,
                    profile._applicationDisableOrientationScanning,
                    profile._headsUpNotifications,
                    profile._deviceForceStopApplicationChange,
                    profile._deviceForceStopApplicationPackageName,
                    profile._activationByUserCount,
                    profile._deviceNetworkTypePrefs,
                    profile._deviceCloseAllApplications,
                    profile._screenDarkMode,
                    profile._dtmfToneWhenDialing,
                    profile._soundOnTouch,
                    profile._volumeDTMF,
                    profile._volumeAccessibility,
                    profile._volumeBluetoothSCO,
                    profile._afterDurationProfile,
                    profile._alwaysOnDisplay,
                    profile._screenOnPermanent,
                    profile._volumeMuteSound,
                    profile._deviceLocationMode,
                    profile._applicationDisableNotificationScanning,
                    profile._generateNotification,
                    profile._cameraFlash,
                    profile._deviceNetworkTypeSIM1,
                    profile._deviceNetworkTypeSIM2,
                    profile._deviceMobileDataSIM1,
                    profile._deviceMobileDataSIM2,
                    profile._deviceDefaultSIMCards,
                    profile._deviceOnOffSIM1,
                    profile._deviceOnOffSIM2,
                    profile._soundRingtoneChangeSIM1,
                    profile._soundRingtoneSIM1,
                    profile._soundRingtoneChangeSIM2,
                    profile._soundRingtoneSIM2,
                    profile._soundNotificationChangeSIM1,
                    profile._soundNotificationSIM1,
                    profile._soundNotificationChangeSIM2,
                    profile._soundNotificationSIM2,
                    profile._soundSameRingtoneForBothSIMCards,
                    profile._deviceLiveWallpaper,
                    profile._vibrateNotifications,
                    profile._deviceWallpaperFolder,
                    profile._applicationDisableGloabalEventsRun,
                    profile._deviceVPNSettingsPrefs,
                    profile._endOfActivationType,
                    profile._endOfActivationTime,
                    profile._applicationDisablePeriodicScanning,
                    profile._deviceVPN
            );

            if (profile._volumeRingerMode == SHARED_PROFILE_VALUE)
                mappedProfile._volumeRingerMode = 0;
            if (profile._volumeZenMode == SHARED_PROFILE_VALUE)
                mappedProfile._volumeZenMode = 0;
            if (profile.getVolumeRingtoneSharedProfile())
                mappedProfile._volumeRingtone = Profile.defaultValuesString.get(Profile.PREF_PROFILE_VOLUME_RINGTONE);
            if (profile.getVolumeNotificationSharedProfile())
                mappedProfile._volumeNotification = Profile.defaultValuesString.get(Profile.PREF_PROFILE_VOLUME_NOTIFICATION);
            if (profile.getVolumeAlarmSharedProfile())
                mappedProfile._volumeAlarm = Profile.defaultValuesString.get(Profile.PREF_PROFILE_VOLUME_ALARM);
            if (profile.getVolumeMediaSharedProfile())
                mappedProfile._volumeMedia = Profile.defaultValuesString.get(Profile.PREF_PROFILE_VOLUME_MEDIA);
            if (profile.getVolumeSystemSharedProfile())
                mappedProfile._volumeSystem = Profile.defaultValuesString.get(Profile.PREF_PROFILE_VOLUME_SYSTEM);
            if (profile.getVolumeVoiceSharedProfile())
                mappedProfile._volumeVoice = Profile.defaultValuesString.get(Profile.PREF_PROFILE_VOLUME_VOICE);
            if (profile._soundRingtoneChange == SHARED_PROFILE_VALUE)
                mappedProfile._soundRingtoneChange = 0;
            if (profile._soundNotificationChange == SHARED_PROFILE_VALUE)
                mappedProfile._soundNotificationChange = 0;
            if (profile._soundAlarmChange == SHARED_PROFILE_VALUE)
                mappedProfile._soundAlarmChange = 0;
            if (profile._deviceAirplaneMode == SHARED_PROFILE_VALUE)
                mappedProfile._deviceAirplaneMode = 0;
            if (profile._deviceAutoSync == SHARED_PROFILE_VALUE)
                mappedProfile._deviceAutoSync = 0;
            if (profile._deviceMobileData == SHARED_PROFILE_VALUE)
                mappedProfile._deviceMobileData = 0;
            if (profile._deviceMobileDataPrefs == SHARED_PROFILE_VALUE)
                mappedProfile._deviceMobileDataPrefs = 0;
            if (profile._deviceWiFi == SHARED_PROFILE_VALUE)
                mappedProfile._deviceWiFi = 0;
            if (profile._deviceBluetooth == SHARED_PROFILE_VALUE)
                mappedProfile._deviceBluetooth = 0;
            if (profile._deviceGPS == SHARED_PROFILE_VALUE)
                mappedProfile._deviceGPS = 0;
            if (profile._deviceLocationServicePrefs == SHARED_PROFILE_VALUE)
                mappedProfile._deviceLocationServicePrefs = 0;
            if (profile._deviceScreenTimeout == SHARED_PROFILE_VALUE)
                mappedProfile._deviceScreenTimeout = 0;
            if (profile.getDeviceBrightnessSharedProfile())
                mappedProfile._deviceBrightness = Profile.defaultValuesString.get(Profile.PREF_PROFILE_DEVICE_BRIGHTNESS);
            if (profile._deviceAutoRotate == SHARED_PROFILE_VALUE)
                mappedProfile._deviceAutoRotate = 0;
            if (profile._deviceRunApplicationChange == SHARED_PROFILE_VALUE)
                mappedProfile._deviceRunApplicationChange = 0;
            if (profile._deviceWallpaperChange == SHARED_PROFILE_VALUE)
                mappedProfile._deviceWallpaperChange = 0;
            if (profile._volumeSpeakerPhone == SHARED_PROFILE_VALUE)
                mappedProfile._volumeSpeakerPhone = 0;
            if (profile._deviceNFC == SHARED_PROFILE_VALUE)
                mappedProfile._deviceNFC = 0;
            if (profile._deviceKeyguard == SHARED_PROFILE_VALUE)
                mappedProfile._deviceKeyguard = 0;
            if (profile._vibrationOnTouch == SHARED_PROFILE_VALUE)
                mappedProfile._vibrationOnTouch = 0;
            if (profile._deviceWiFiAP == SHARED_PROFILE_VALUE)
                mappedProfile._deviceWiFiAP = 0;
            if (profile._devicePowerSaveMode == SHARED_PROFILE_VALUE)
                mappedProfile._devicePowerSaveMode = 0;
            if (profile._deviceNetworkType == SHARED_PROFILE_VALUE)
                mappedProfile._deviceNetworkType = 0;
            if (profile._notificationLed == SHARED_PROFILE_VALUE)
                mappedProfile._notificationLed = 0;
            if (profile._vibrateWhenRinging == SHARED_PROFILE_VALUE)
                mappedProfile._vibrateWhenRinging = 0;
            if (profile._vibrateNotifications == SHARED_PROFILE_VALUE)
                mappedProfile._vibrateNotifications = 0;
            if (profile._lockDevice == SHARED_PROFILE_VALUE)
                mappedProfile._lockDevice = 0;
            if ((profile._deviceConnectToSSID != null) && (profile._deviceConnectToSSID.equals(CONNECTTOSSID_SHAREDPROFILE)))
                mappedProfile._deviceConnectToSSID = Profile.defaultValuesString.get(Profile.PREF_PROFILE_DEVICE_CONNECT_TO_SSID);
            if (profile._applicationDisableWifiScanning == SHARED_PROFILE_VALUE)
                mappedProfile._applicationDisableWifiScanning = 0;
            if (profile._applicationDisableBluetoothScanning == SHARED_PROFILE_VALUE)
                mappedProfile._applicationDisableBluetoothScanning = 0;
            if (profile._deviceWiFiAPPrefs == SHARED_PROFILE_VALUE)
                mappedProfile._deviceWiFiAPPrefs = 0;
            if (profile._applicationDisableLocationScanning == SHARED_PROFILE_VALUE)
                mappedProfile._applicationDisableLocationScanning = 0;
            if (profile._applicationDisableMobileCellScanning == SHARED_PROFILE_VALUE)
                mappedProfile._applicationDisableMobileCellScanning = 0;
            if (profile._applicationDisableOrientationScanning == SHARED_PROFILE_VALUE)
                mappedProfile._applicationDisableOrientationScanning = 0;
            if (profile._headsUpNotifications == SHARED_PROFILE_VALUE)
                mappedProfile._headsUpNotifications = 0;
            if (profile._deviceForceStopApplicationChange == SHARED_PROFILE_VALUE)
                mappedProfile._deviceForceStopApplicationChange = 0;
            if (profile._deviceNetworkTypePrefs == SHARED_PROFILE_VALUE)
                mappedProfile._deviceNetworkTypePrefs = 0;
            if (profile._deviceCloseAllApplications == SHARED_PROFILE_VALUE)
                mappedProfile._deviceCloseAllApplications = 0;
            if (profile._screenDarkMode == SHARED_PROFILE_VALUE)
                mappedProfile._screenDarkMode = 0;
            if (profile._dtmfToneWhenDialing == SHARED_PROFILE_VALUE)
                mappedProfile._dtmfToneWhenDialing = 0;
            if (profile._soundOnTouch == SHARED_PROFILE_VALUE)
                mappedProfile._soundOnTouch = 0;
            if (profile.getVolumeDTMFSharedProfile())
                mappedProfile._volumeDTMF = Profile.defaultValuesString.get(Profile.PREF_PROFILE_VOLUME_DTMF);
            if (profile.getVolumeAccessibilitySharedProfile())
                mappedProfile._volumeAccessibility = Profile.defaultValuesString.get(Profile.PREF_PROFILE_VOLUME_ACCESSIBILITY);
            if (profile.getVolumeBluetoothSCOSharedProfile())
                mappedProfile._volumeBluetoothSCO = Profile.defaultValuesString.get(Profile.PREF_PROFILE_VOLUME_BLUETOOTH_SCO);
            if (profile._alwaysOnDisplay == SHARED_PROFILE_VALUE)
                mappedProfile._alwaysOnDisplay = 0;
            if (profile._screenOnPermanent == SHARED_PROFILE_VALUE)
                mappedProfile._screenOnPermanent = 0;
            if (profile._deviceLocationMode == SHARED_PROFILE_VALUE)
                mappedProfile._deviceLocationMode = 0;
            if (profile._applicationDisableNotificationScanning == SHARED_PROFILE_VALUE)
                mappedProfile._applicationDisableNotificationScanning = 0;
            if (profile.getGenerateNotificationSharedProfile())
                mappedProfile._generateNotification = Profile.defaultValuesString.get(Profile.PREF_PROFILE_GENERATE_NOTIFICATION);
            if (profile._cameraFlash == SHARED_PROFILE_VALUE)
                mappedProfile._cameraFlash = 0;
            if (profile._deviceNetworkTypeSIM1 == SHARED_PROFILE_VALUE)
                mappedProfile._deviceNetworkTypeSIM1 = 0;
            if (profile._deviceNetworkTypeSIM2 == SHARED_PROFILE_VALUE)
                mappedProfile._deviceNetworkTypeSIM2 = 0;
            if (profile._deviceMobileDataSIM1 == SHARED_PROFILE_VALUE)
                mappedProfile._deviceMobileDataSIM1 = 0;
            if (profile._deviceMobileDataSIM2 == SHARED_PROFILE_VALUE)
                mappedProfile._deviceMobileDataSIM2 = 0;
            // !!! do not add other profile aprameters. Shared profile is never used !!!

            mappedProfile._iconBitmap = profile._iconBitmap;
            mappedProfile._preferencesIndicator = profile._preferencesIndicator;

            return mappedProfile;
        }
        else
            return null;
    }

    static PreferenceAllowed isProfilePreferenceAllowed(String preferenceKey, Profile profile,
                                                        SharedPreferences sharedPreferences,
                                                        boolean fromUIThread, Context context) {
//        if (profile != null)
//            PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "profile != null - START");

        if ((profile != null) && (!preferenceKey.equals("-")) && (sharedPreferences == null)) {
            sharedPreferences = context.getApplicationContext().getSharedPreferences("temp_isProfilePreferenceAllowed", Context.MODE_PRIVATE);
            profile.saveProfileToSharedPreferences(sharedPreferences);
            profile = null;
        }

        PreferenceAllowed preferenceAllowed = new PreferenceAllowed();

        preferenceAllowed.notAllowedRoot = false;
        preferenceAllowed.notAllowedG1 = false;

        //noinspection IfStatementWithIdenticalBranches
        if (profile == null) {
            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
            switch (preferenceKey) {
                case Profile.PREF_PROFILE_DEVICE_AIRPLANE_MODE:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_AIRPLANE_MODE(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                case Profile.PREF_PROFILE_DEVICE_WIFI:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_WIFI(preferenceAllowed, null, sharedPreferences, fromUIThread);
                    break;
                case Profile.PREF_PROFILE_DEVICE_BLUETOOTH:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_BLUETOOTH(preferenceAllowed);
                    break;
                case Profile.PREF_PROFILE_DEVICE_MOBILE_DATA:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_MOBILE_DATA(preferenceAllowed, preferenceKey, null, sharedPreferences, fromUIThread, context);
                    break;
                case Profile.PREF_PROFILE_DEVICE_MOBILE_DATA_SIM1:
                case Profile.PREF_PROFILE_DEVICE_MOBILE_DATA_SIM2:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_MOBILE_DATA_DUAL_SIM(preferenceAllowed, preferenceKey, null, sharedPreferences, fromUIThread, context);
                    break;
                case Profile.PREF_PROFILE_DEVICE_MOBILE_DATA_PREFS:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_MOBILE_DATA_PREFS(preferenceAllowed);
                    break;
                case Profile.PREF_PROFILE_DEVICE_GPS:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_GPS(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                case Profile.PREF_PROFILE_DEVICE_LOCATION_MODE:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_LOCATION_MODE(preferenceAllowed, null, context);
                    break;
                case Profile.PREF_PROFILE_DEVICE_NFC:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NFC(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                case Profile.PREF_PROFILE_DEVICE_WIFI_AP:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_WIFI_AP(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                case Profile.PREF_PROFILE_VIBRATE_WHEN_RINGING:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_VIBRATE_WHEN_RINGING(preferenceAllowed, null, sharedPreferences, fromUIThread);
                    break;
                case Profile.PREF_PROFILE_VIBRATE_NOTIFICATIONS:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_VIBRATE_NOTIFICATIONS(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                case Profile.PREF_PROFILE_DEVICE_ADAPTIVE_BRIGHTNESS:
                    // !!! test this only for preference key !!!
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_ADAPTIVE_BRIGHTNESS(preferenceAllowed, sharedPreferences, fromUIThread);
                    break;
                case Profile.PREF_PROFILE_DEVICE_POWER_SAVE_MODE:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_POWER_SAVE_MODE(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                case Profile.PREF_PROFILE_DEVICE_NETWORK_TYPE:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NETWORK_TYPE(preferenceAllowed, preferenceKey, null, sharedPreferences, fromUIThread, context);
                    break;
                case Profile.PREF_PROFILE_DEVICE_NETWORK_TYPE_SIM1:
                case Profile.PREF_PROFILE_DEVICE_NETWORK_TYPE_SIM2:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NETWORK_TYPE_DUAL_SIM(preferenceAllowed, preferenceKey, null, sharedPreferences, fromUIThread, context);
                    break;
                case Profile.PREF_PROFILE_NOTIFICATION_LED:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_NOTIFICATION_LED(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                case Profile.PREF_PROFILE_DEVICE_KEYGUARD:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_KEYGUARD(preferenceAllowed, context);
                    break;
                case Profile.PREF_PROFILE_DEVICE_CONNECT_TO_SSID:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_CONNECT_TO_SSID(preferenceAllowed);
                    break;
                case Profile.PREF_PROFILE_APPLICATION_DISABLE_WIFI_SCANNING:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_APPLICATION_DISABLE_WIFI_SCANNING(preferenceAllowed);
                    break;
                case Profile.PREF_PROFILE_APPLICATION_DISABLE_BLUETOOTH_SCANNING:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_APPLICATION_DISABLE_BLUETOOTH_SCANNING(preferenceAllowed);
                    break;
                case Profile.PREF_PROFILE_DEVICE_WIFI_AP_PREFS:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_WIFI_AP_PREFS(preferenceAllowed);
                    break;
                case Profile.PREF_PROFILE_APPLICATION_DISABLE_MOBILE_CELL_SCANNING:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_APPLICATION_DISABLE_MOBILE_CELL_SCANNING(preferenceAllowed);
                    break;
                case Profile.PREF_PROFILE_APPLICATION_DISABLE_ORIENTATION_SCANNING:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_APPLICATION_DISABLE_ORIENTATION_SCANNING(preferenceAllowed);
                    break;
                case Profile.PREF_PROFILE_HEADS_UP_NOTIFICATIONS:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_HEADS_UP_NOTIFICATIONS(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                case Profile.PREF_PROFILE_DEVICE_NETWORK_TYPE_PREFS:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NETWORK_TYPE_PREFS(preferenceAllowed);
                    break;
                case Profile.PREF_PROFILE_VOLUME_ACCESSIBILITY:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_VOLUME_ACCESSIBILITY(preferenceAllowed, context);
                    break;
                case Profile.PREF_PROFILE_ALWAYS_ON_DISPLAY:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_ALWAYS_ON_DISPLAY(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                case Profile.PREF_PROFILE_SCREEN_DARK_MODE:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_SCREEN_DARK_MODE(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                case Profile.PREF_PROFILE_VOLUME_SPEAKER_PHONE:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_VOLUME_SPEAKER_PHONE(preferenceAllowed, context);
                    break;
                case Profile.PREF_PROFILE_CAMERA_FLASH:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_CAMERA_FLASH(preferenceAllowed);
                    break;
                case Profile.PREF_PROFILE_DEVICE_DEFAULT_SIM_CARDS:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_DEFAULT_SIM_CARDS(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                case Profile.PREF_PROFILE_DEVICE_ONOFF_SIM1:
                case Profile.PREF_PROFILE_DEVICE_ONOFF_SIM2:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_ONOFF_SIM(preferenceAllowed, preferenceKey, null, sharedPreferences, fromUIThread, context);
                    break;
                case Profile.PREF_PROFILE_SOUND_RINGTONE_CHANGE_SIM1:
                case Profile.PREF_PROFILE_SOUND_RINGTONE_CHANGE_SIM2:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_SOUND_RINGTONE_CHANGE_SIM(preferenceAllowed, null, context);
                    break;
                case Profile.PREF_PROFILE_SOUND_NOTIFICATION_CHANGE_SIM1:
                case Profile.PREF_PROFILE_SOUND_NOTIFICATION_CHANGE_SIM2:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_SOUND_NOTIFICATION_CHANGE_SIM(preferenceAllowed, preferenceKey, null, sharedPreferences, fromUIThread, context);
                    break;
                case Profile.PREF_PROFILE_SOUND_SAME_RINGTONE_FOR_BOTH_SIM_CARDS:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_SOUND_SAME_RINGTONE_FOR_BOTH_SIM_CARDS(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                default:
                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
            }
            return preferenceAllowed;

        } else {
            // !!! call only methods with profile parameter

            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;

            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_AIRPLANE_MODE(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Zuhause Tag")) {
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_AIRPLANE_MODE] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_AIRPLANE_MODE] preferenceAllowed.notAllowedG1=" + preferenceAllowed.notAllowedG1);
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_AIRPLANE_MODE] preferenceAllowed.notAllowedRoot=" + preferenceAllowed.notAllowedRoot);
//            }
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_WIFI(preferenceAllowed, profile, sharedPreferences, fromUIThread);
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_BLUETOOTH(preferenceAllowed);
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_MOBILE_DATA(preferenceAllowed, "-", profile, sharedPreferences, fromUIThread, context);
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_MOBILE_DATA_DUAL_SIM(preferenceAllowed, "-", profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Zuhause Tag")) {
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_MOBILE_DATA] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_MOBILE_DATA] preferenceAllowed.notAllowedG1=" + preferenceAllowed.notAllowedG1);
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_MOBILE_DATA] preferenceAllowed.notAllowedRoot=" + preferenceAllowed.notAllowedRoot);
//            }
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_MOBILE_DATA_PREFS(preferenceAllowed);
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_GPS(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Zuhause Tag")) {
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_GPS] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_GPS] preferenceAllowed.notAllowedG1=" + preferenceAllowed.notAllowedG1);
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_GPS] preferenceAllowed.notAllowedRoot=" + preferenceAllowed.notAllowedRoot);
//            }
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_LOCATION_MODE(preferenceAllowed, profile, context);
//            if (profile._name.equals("Zuhause Tag")) {
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_LOCATION_MODE] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_LOCATION_MODE] preferenceAllowed.notAllowedG1=" + preferenceAllowed.notAllowedG1);
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_LOCATION_MODE] preferenceAllowed.notAllowedRoot=" + preferenceAllowed.notAllowedRoot);
//            }
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NFC(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Zuhause Tag")) {
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NFC] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NFC] preferenceAllowed.notAllowedG1=" + preferenceAllowed.notAllowedG1);
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NFC] preferenceAllowed.notAllowedRoot=" + preferenceAllowed.notAllowedRoot);
//            }
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_WIFI_AP(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Zuhause Tag")) {
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_WIFI_AP] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_WIFI_AP] preferenceAllowed.notAllowedG1=" + preferenceAllowed.notAllowedG1);
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_WIFI_AP] preferenceAllowed.notAllowedRoot=" + preferenceAllowed.notAllowedRoot);
//            }
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_VIBRATE_WHEN_RINGING(preferenceAllowed, profile, sharedPreferences, fromUIThread);
//            if (profile._name.equals("Zuhause Tag")) {
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_VIBRATE_WHEN_RINGING] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_VIBRATE_WHEN_RINGING] preferenceAllowed.notAllowedG1=" + preferenceAllowed.notAllowedG1);
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_VIBRATE_WHEN_RINGING] preferenceAllowed.notAllowedRoot=" + preferenceAllowed.notAllowedRoot);
//            }
            if (Build.VERSION.SDK_INT >= 28) {
                PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_VIBRATE_NOTIFICATIONS(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//                if (profile._name.equals("Zuhause Tag")) {
//                    PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_VIBRATE_NOTIFICATIONS] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//                    PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_VIBRATE_NOTIFICATIONS] preferenceAllowed.notAllowedG1=" + preferenceAllowed.notAllowedG1);
//                    PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_VIBRATE_NOTIFICATIONS] preferenceAllowed.notAllowedRoot=" + preferenceAllowed.notAllowedRoot);
//                }
            }
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_POWER_SAVE_MODE(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Zuhause Tag")) {
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_POWER_SAVE_MODE] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_POWER_SAVE_MODE] preferenceAllowed.notAllowedG1=" + preferenceAllowed.notAllowedG1);
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_POWER_SAVE_MODE] preferenceAllowed.notAllowedRoot=" + preferenceAllowed.notAllowedRoot);
//            }
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NETWORK_TYPE(preferenceAllowed, "-", profile, sharedPreferences, fromUIThread, context);
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NETWORK_TYPE_DUAL_SIM(preferenceAllowed, "-", profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Zuhause Tag")) {
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NETWORK_TYPE] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NETWORK_TYPE] preferenceAllowed.notAllowedG1=" + preferenceAllowed.notAllowedG1);
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NETWORK_TYPE] preferenceAllowed.notAllowedRoot=" + preferenceAllowed.notAllowedRoot);
//            }
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_NOTIFICATION_LED(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Zuhause Tag")) {
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_NOTIFICATION_LED] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_NOTIFICATION_LED] preferenceAllowed.notAllowedG1=" + preferenceAllowed.notAllowedG1);
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_NOTIFICATION_LED] preferenceAllowed.notAllowedRoot=" + preferenceAllowed.notAllowedRoot);
//            }
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_KEYGUARD(preferenceAllowed, context);
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_CONNECT_TO_SSID(preferenceAllowed);
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_APPLICATION_DISABLE_WIFI_SCANNING(preferenceAllowed);
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_APPLICATION_DISABLE_BLUETOOTH_SCANNING(preferenceAllowed);
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_WIFI_AP_PREFS(preferenceAllowed);
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_APPLICATION_DISABLE_MOBILE_CELL_SCANNING(preferenceAllowed);
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_APPLICATION_DISABLE_ORIENTATION_SCANNING(preferenceAllowed);
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_HEADS_UP_NOTIFICATIONS(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Zuhause Tag")) {
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_HEADS_UP_NOTIFICATIONS] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_HEADS_UP_NOTIFICATIONS] preferenceAllowed.notAllowedG1=" + preferenceAllowed.notAllowedG1);
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_HEADS_UP_NOTIFICATIONS] preferenceAllowed.notAllowedRoot=" + preferenceAllowed.notAllowedRoot);
//            }
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NETWORK_TYPE_PREFS(preferenceAllowed);
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_VOLUME_ACCESSIBILITY(preferenceAllowed, context);
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_ALWAYS_ON_DISPLAY(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Zuhause Tag")) {
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_ALWAYS_ON_DISPLAY] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_ALWAYS_ON_DISPLAY] preferenceAllowed.notAllowedG1=" + preferenceAllowed.notAllowedG1);
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_ALWAYS_ON_DISPLAY] preferenceAllowed.notAllowedRoot=" + preferenceAllowed.notAllowedRoot);
//            }
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_SCREEN_DARK_MODE(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Zuhause Tag")) {
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_SCREEN_DARK_MODE] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_SCREEN_DARK_MODE] preferenceAllowed.notAllowedG1=" + preferenceAllowed.notAllowedG1);
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_SCREEN_DARK_MODE] preferenceAllowed.notAllowedRoot=" + preferenceAllowed.notAllowedRoot);
//            }
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_VOLUME_SPEAKER_PHONE(preferenceAllowed, context);
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_CAMERA_FLASH(preferenceAllowed);
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_DEFAULT_SIM_CARDS(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Zuhause Tag")) {
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_DEFAULT_SIM_CARDS] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_DEFAULT_SIM_CARDS] preferenceAllowed.notAllowedG1=" + preferenceAllowed.notAllowedG1);
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_DEFAULT_SIM_CARDS] preferenceAllowed.notAllowedRoot=" + preferenceAllowed.notAllowedRoot);
//            }
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_ONOFF_SIM(preferenceAllowed, "-", profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Zuhause Tag")) {
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_ONOFF_SIM] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_ONOFF_SIM] preferenceAllowed.notAllowedG1=" + preferenceAllowed.notAllowedG1);
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_ONOFF_SIM] preferenceAllowed.notAllowedRoot=" + preferenceAllowed.notAllowedRoot);
//            }
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_SOUND_RINGTONE_CHANGE_SIM(preferenceAllowed, profile, context);
//            if (profile._name.equals("Zuhause Tag")) {
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_SOUND_RINGTONE_CHANGE_SIM] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_SOUND_RINGTONE_CHANGE_SIM] preferenceAllowed.notAllowedG1=" + preferenceAllowed.notAllowedG1);
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_SOUND_RINGTONE_CHANGE_SIM] preferenceAllowed.notAllowedRoot=" + preferenceAllowed.notAllowedRoot);
//            }
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_SOUND_NOTIFICATION_CHANGE_SIM(preferenceAllowed, "-", profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Zuhause Tag")) {
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_SOUND_NOTIFICATION_CHANGE_SIM] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_SOUND_NOTIFICATION_CHANGE_SIM] preferenceAllowed.notAllowedG1=" + preferenceAllowed.notAllowedG1);
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_SOUND_NOTIFICATION_CHANGE_SIM] preferenceAllowed.notAllowedRoot=" + preferenceAllowed.notAllowedRoot);
//            }
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_SOUND_SAME_RINGTONE_FOR_BOTH_SIM_CARDS(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Zuhause Tag")) {
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_SOUND_SAME_RINGTONE_FOR_BOTH_SIM_CARDS] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_SOUND_SAME_RINGTONE_FOR_BOTH_SIM_CARDS] preferenceAllowed.notAllowedG1=" + preferenceAllowed.notAllowedG1);
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_SOUND_SAME_RINGTONE_FOR_BOTH_SIM_CARDS] preferenceAllowed.notAllowedRoot=" + preferenceAllowed.notAllowedRoot);
//            }

            if (preferenceAllowed.notAllowedG1 || preferenceAllowed.notAllowedRoot)
                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;

            return preferenceAllowed;
        }
    }

    /*
    static void getActivatedProfileForDuration(Context context)
    {
        synchronized (PPApplication.profileActivationMutex) {
            ApplicationPreferences.prefActivatedProfileForDuration = ApplicationPreferences.
                    getSharedPreferences(context).getLong(PREF_ACTIVATED_PROFILE_FOR_DURATION, 0);
            //return prefActivatedProfileForDuration;
        }
    }
    static void setActivatedProfileForDuration(Context context, long profileId)
    {
        synchronized (PPApplication.profileActivationMutex) {
            SharedPreferences.Editor editor = ApplicationPreferences.getEditor(context);
            editor.putLong(PREF_ACTIVATED_PROFILE_FOR_DURATION, profileId);
            editor.apply();
            ApplicationPreferences.prefActivatedProfileForDuration = profileId;
        }
    }
    */

    static void getActivatedProfileEndDurationTime(Context context)
    {
        synchronized (PPApplication.profileActivationMutex) {
            ApplicationPreferences.prefActivatedProfileEndDurationTime = ApplicationPreferences.
                    getSharedPreferences(context).getLong(Profile.PREF_ACTIVATED_PROFILE_END_DURATION_TIME, 0);
            //return prefActivatedProfileEndDurationTime;
        }
    }

    static void setActivatedProfileEndDurationTime(Context context, long time)
    {
        synchronized (PPApplication.profileActivationMutex) {
            SharedPreferences.Editor editor = ApplicationPreferences.getEditor(context);
            editor.putLong(Profile.PREF_ACTIVATED_PROFILE_END_DURATION_TIME, time);
            editor.apply();
            ApplicationPreferences.prefActivatedProfileEndDurationTime = time;
        }
    }

    static int getIconResource(String identifier) {
        int iconResource = R.drawable.ic_profile_default;
        try {
            if ((identifier != null) && (!identifier.isEmpty())) {
                Object idx = Profile.profileIconIdMap.get(identifier);
                if (idx != null)
                    iconResource = (int) idx;
            }
        } catch (Exception e) {
            PPApplication.recordException(e);
        }
        return iconResource;
    }


}