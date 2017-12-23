package loveq.rc.plaiddemo.util;

import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.URLSpan;

import in.uncod.android.bypass.style.TouchableUrlSpan;

/**
 * Created by rc on 2017/12/22.
 * Description:
 */

public class HtmlUtils {
    private HtmlUtils() {
    }

    /**
     * Parse the given input using {@link TouchableUrlSpan}s rather than vanilla {@link URLSpan}s
     * so that they respond to touch.
     */
    public static SpannableStringBuilder parseHtml(
            String input,
            ColorStateList linkTextColor,
            @ColorInt int linkHighlightColor) {
        SpannableStringBuilder spanned = fromHtml(input);

        // strip any trailing newlines
        while (spanned.charAt(spanned.length() - 1) == '\n') {
            spanned = spanned.delete(spanned.length() - 1, spanned.length());
        }

        return linkifyPlainLinks(spanned, linkTextColor, linkHighlightColor);
    }

    private static SpannableStringBuilder linkifyPlainLinks(
            CharSequence input,
            ColorStateList linkTextColor,
            @ColorInt int linkHighlightColor) {
        final SpannableString plainLinks = new SpannableString(input); // copy of input

        // Linkify doesn't seem to work as expected on M+
        // TODO: figure out why
        //Linkify.addLinks(plainLinks, Linkify.WEB_URLS);

        final URLSpan[] urlSpans = plainLinks.getSpans(0, plainLinks.length(), URLSpan.class);

        // add any plain links to the output
        final SpannableStringBuilder ssb = new SpannableStringBuilder(input);
        for (URLSpan urlSpan : urlSpans) {
            ssb.removeSpan(urlSpan);
            ssb.setSpan(new TouchableUrlSpan(urlSpan.getURL(), linkTextColor, linkHighlightColor),
                    plainLinks.getSpanStart(urlSpan),
                    plainLinks.getSpanEnd(urlSpan),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return ssb;
    }

    private static SpannableStringBuilder fromHtml(String input) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return (SpannableStringBuilder) Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return (SpannableStringBuilder) Html.fromHtml(input);
        }
    }
}
