package sk.henrichg.phoneprofilesplus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceDialogFragmentCompat;

public class InfoDialogPreferenceFragment extends PreferenceDialogFragmentCompat {

    private InfoDialogPreference preference;
    private Context context;

    @SuppressLint("InflateParams")
    @Override
    protected View onCreateDialogView(@NonNull Context context)
    {
        preference = (InfoDialogPreference) getPreference();
        preference.fragment = this;
        this.context = context;

        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.dialog_info_preference, null, false);
    }

    @Override
    protected void onBindDialogView(@NonNull View view) {
        super.onBindDialogView(view);

        final TextView infoTextView = view.findViewById(R.id.info_pref_dialog_info_text);

        String _infoText = preference.infoText;

        int tagIndex = 0;

        String beginTag = "<II"+tagIndex+" [";

        int importantInfoTagBeginIndex = _infoText.indexOf(beginTag);
        int importantInfoTagEndIndex = _infoText.indexOf("]>");

        if ((importantInfoTagBeginIndex != -1) && (importantInfoTagEndIndex != -1)) {
            String importantInfoTagDataString = _infoText.substring(importantInfoTagBeginIndex + beginTag.length(), importantInfoTagEndIndex);

            beginTag = "<II" + tagIndex + " [" + importantInfoTagDataString + "]>";
            String endTag = "<II" + tagIndex + "/>";

            importantInfoTagBeginIndex = _infoText.indexOf(beginTag);
            importantInfoTagEndIndex = _infoText.indexOf(endTag);

            if ((importantInfoTagBeginIndex != -1) && (importantInfoTagEndIndex != -1)) {

                _infoText = _infoText.replace(beginTag, "");
                _infoText = _infoText.replace(endTag, "");

                final String _tagType = beginTag.substring(1, 3);
                final String _importantInfoTagDataString = importantInfoTagDataString;

                Spannable sbt = new SpannableString(_infoText);
                /*sbt.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                        importantInfoTagBeginIndex, importantInfoTagEndIndex-beginTag.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/
                /*sbt.setSpan(new RelativeSizeSpan(1.05f),
                        importantInfoTagBeginIndex, importantInfoTagEndIndex-beginTag.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(ds.linkColor);    // you can use custom color
                        ds.setUnderlineText(false);    // this remove the underline
                    }

                    @Override
                    public void onClick(@NonNull View textView) {

                        String[] splits = _importantInfoTagDataString.split(",");
                        int page = Integer.parseInt(splits[0]);

                        int fragment = Integer.parseInt(splits[1]);
                        // 0 = System
                        // 1 = Profiles
                        // 2 = Events

                        int resource = Integer.parseInt(splits[2]);

                        if (_tagType.equals("II")) {
                            Intent intentLaunch = new Intent(context, ImportantInfoActivityForceScroll.class);
                            intentLaunch.putExtra(ImportantInfoActivity.EXTRA_SHOW_QUICK_GUIDE, page == 1);
                            intentLaunch.putExtra(ImportantInfoActivityForceScroll.EXTRA_SHOW_FRAGMENT, fragment);
                            intentLaunch.putExtra(ImportantInfoActivityForceScroll.EXTRA_SCROLL_TO, resource);
                            startActivity(intentLaunch);
                        }

                        if (getDialog() != null)
                            getDialog().cancel();
                    }
                };
                sbt.setSpan(clickableSpan,
                        importantInfoTagBeginIndex, importantInfoTagEndIndex - beginTag.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                infoTextView.setText(sbt);
                infoTextView.setClickable(true);
                infoTextView.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
        else {
            if (preference.isHtml) {
                infoTextView.setText(StringFormatUtils.fromHtml(preference.infoText, true, false, 0, 0));
                infoTextView.setClickable(true);
                infoTextView.setMovementMethod(LinkMovementMethod.getInstance());
            }
            else
                infoTextView.setText(preference.infoText);
        }
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        preference.fragment = null;
    }
}