package sk.henrichg.phoneprofilesplus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationsPreferncesActivity extends AppCompatActivity {

    private boolean activityStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);

        activityStarted = true;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if (activityStarted) {
            GlobalGUIRoutines.setTheme(this, true, true/*, false*/, false, false, false, false);

            SingleSelectListDialog dialog = new SingleSelectListDialog(
                    getString(R.string.notifications_preferences_notification_type),
                    R.array.notificationPreferencesNotificationTypeArray,
                    SingleSelectListDialog.NOT_USE_RADIO_BUTTONS,
                    (dialog1, which) -> {
                        switch (which) {
                            case 0:
                                try {
                                    Intent intent = new Intent(this, PhoneProfilesPrefsActivity.class);
                                    intent.putExtra(PhoneProfilesPrefsActivity.EXTRA_SCROLL_TO, "categoryAppNotificationRoot");
                                    //noinspection deprecation
                                    startActivityForResult(intent, 100);
                                } catch (Exception e) {
                                    finish();
                                }
                                break;
                            case 1:
                                try {
                                    Intent intent = new Intent(this, PhoneProfilesPrefsActivity.class);
                                    intent.putExtra(PhoneProfilesPrefsActivity.EXTRA_SCROLL_TO, "categoryProfileListNotificationRoot");
                                    //noinspection deprecation
                                    startActivityForResult(intent, 100);
                                } catch (Exception e) {
                                    finish();
                                }
                                break;
                            default:
                        }
                    },
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            finish();
                        }
                    },
                    false,
                    this);

            if (!isFinishing())
                dialog.show();

        }
        else {
            if (!isFinishing())
                finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(0, 0);
    }

}
