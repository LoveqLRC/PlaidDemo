package loveq.rc.plaiddemo.util;

import android.content.res.ColorStateList;
import android.support.annotation.ColorInt;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import in.uncod.android.bypass.style.TouchableUrlSpan;
import loveq.rc.plaiddemo.ui.span.PlayerSpan;
import okhttp3.HttpUrl;

/**
 * Created by rc on 2017/12/22.
 * Description:
 */

public class DribbbleUtils {
    private DribbbleUtils() {
    }

    public static Spanned parseDribbbleHtml(
            String input,
            ColorStateList linkTextColor,
            @ColorInt int linkHighlightColor) {
        SpannableStringBuilder ssb = HtmlUtils.parseHtml(input, linkTextColor, linkHighlightColor);

        TouchableUrlSpan[] urlSpans = ssb.getSpans(0, ssb.length(), TouchableUrlSpan.class);
        for (TouchableUrlSpan urlSpan : urlSpans) {
            int start = ssb.getSpanStart(urlSpan);
            if (ssb.subSequence(start, start + 1).toString().equals("@")) {
                int end = ssb.getSpanEnd(urlSpan);
                ssb.removeSpan(urlSpan);
                HttpUrl url = HttpUrl.parse(urlSpan.getURL());
                long playerId = -1l;
                String playerUsername = null;
                try {
                    playerId = Long.parseLong(url.pathSegments().get(0));
                } catch (NumberFormatException nfe) {
                    playerUsername = url.pathSegments().get(0);
                }
                ssb.setSpan(new PlayerSpan(urlSpan.getURL(),
                                ssb.subSequence(start + 1, end).toString(),
                                playerId,
                                playerUsername,
                                linkTextColor,
                                linkHighlightColor),
                        start,
                        end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return ssb;
    }
}
