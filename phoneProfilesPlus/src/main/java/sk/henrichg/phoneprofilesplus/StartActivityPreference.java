package sk.henrichg.phoneprofilesplus;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

public class StartActivityPreference extends Preference {

    private final Context prefContext;

    public StartActivityPreference(Context context)
    {
        super(context);

        prefContext = context;

        setWidgetLayoutResource(R.layout.preference_widget_start_activity);
    }

    public StartActivityPreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        prefContext = context;

        setWidgetLayoutResource(R.layout.preference_widget_start_activity);
    }

    //@Override
    @Override
    public void onBindViewHolder(@NonNull PreferenceViewHolder holder)
    {
        super.onBindViewHolder(holder);

        ImageView imageView = (ImageView) holder.findViewById(R.id.start_activity_preference_imageView1);

        imageView.setImageResource(R.drawable.ic_start_activity_preference_icon);
        if (!isEnabled()) {
            int disabledColor = ContextCompat.getColor(prefContext, R.color.activityDisabledTextColor);
            imageView.setColorFilter(disabledColor, android.graphics.PorterDuff.Mode.MULTIPLY);
        }
        else
            imageView.setColorFilter(null);
    }

}
