package loveq.rc.plaiddemo.ui.transitions;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.FontRes;
import android.support.annotation.NonNull;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;

import loveq.rc.plaiddemo.R;

/**
 * Created by rc on 2017/12/28.
 * Description:
 */

public class ReflowText extends Transition {
    private static final String EXTRA_REFLOW_DATA = "EXTRA_REFLOW_DATA";
    private static final String PROPNAME_DATA = "plaid:reflowtext:data";
    private static final String PROPNAME_TEXT_SIZE = "plaid:reflowtext:textsize";
    private static final String PROPNAME_BOUNDS = "plaid:reflowtext:bounds";
    private static final String[] PROPERTIES = {PROPNAME_TEXT_SIZE, PROPNAME_BOUNDS};
    private static final int TRANSPARENT = 0;
    private static final int OPAQUE = 255;
    private static final int OPACITY_MID_TRANSITION = (int) (0.8f * OPAQUE);
    private static final float STAGGER_DECAY = 0.8f;

    private int velocity = 700;         // pixels per second
    private long minDuration = 200;     // ms
    private long maxDuration = 400;     // ms
    private long staggerDelay = 40;     // ms
    private long duration;
    // this is hack for preventing view from drawing briefly at the end of the transition :(
    private final boolean freezeFrame;

    public ReflowText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ReflowText);
        velocity = a.getDimensionPixelSize(R.styleable.ReflowText_velocity, velocity);
        minDuration = a.getInt(R.styleable.ReflowText_minDuration, (int) minDuration);
        maxDuration = a.getInt(R.styleable.ReflowText_maxDuration, (int) maxDuration);
        staggerDelay = a.getInt(R.styleable.ReflowText_staggerDelay, (int) staggerDelay);
        freezeFrame = a.getBoolean(R.styleable.ReflowText_freezeFrame, false);
        a.recycle();
    }

    public static void addExtras(@NonNull Intent intent, @NonNull Reflowable reflowableView) {
        intent.putExtra(EXTRA_REFLOW_DATA, new ReflowData(reflowableView));
    }

    @Override
    public void captureStartValues(TransitionValues values) {

    }

    @Override
    public void captureEndValues(TransitionValues values) {

    }

    public interface Reflowable<T extends View> {
        T getView();

        String getText();

        Point getTextPosition();

        int getTextWidth();

        int getTextHeight();

        float getTextSize();

        @ColorInt
        int getTextColor();

        float getLineSpacingAdd();

        float getLineSpacingMult();

        int getBreakStrategy();

        float getLetterSpacing();

        @FontRes
        int getFontResId();

        int getMaxLines();
    }

    private static class ReflowData implements Parcelable {

        final String text;
        final float textSize;
        final @ColorInt
        int textColor;
        final Rect bounds;
        final @FontRes
        int fontResId;
        final float lineSpacingAdd;
        final float lineSpacingMult;
        final Point textPosition;
        final int textHeight;
        final int textWidth;
        final int breakStrategy;
        final float letterSpacing;
        final int maxLines;

        ReflowData(@NonNull Reflowable reflowable) {
            text = reflowable.getText();
            textSize = reflowable.getTextSize();
            textColor = reflowable.getTextColor();
            fontResId = reflowable.getFontResId();
            final View view = reflowable.getView();
            int[] loc = new int[2];
            view.getLocationInWindow(loc);
            bounds = new Rect(loc[0], loc[1], loc[0] + view.getWidth(), loc[1] + view.getHeight());
            textPosition = reflowable.getTextPosition();
            textHeight = reflowable.getTextHeight();
            lineSpacingAdd = reflowable.getLineSpacingAdd();
            lineSpacingMult = reflowable.getLineSpacingMult();
            textWidth = reflowable.getTextWidth();
            breakStrategy = reflowable.getBreakStrategy();
            letterSpacing = reflowable.getLetterSpacing();
            maxLines = reflowable.getMaxLines();
        }

        protected ReflowData(Parcel in) {
            text = in.readString();
            textSize = in.readFloat();
            textColor = in.readInt();
            bounds = (Rect) in.readValue(Rect.class.getClassLoader());
            fontResId = in.readInt();
            lineSpacingAdd = in.readFloat();
            lineSpacingMult = in.readFloat();
            textPosition = (Point) in.readValue(Point.class.getClassLoader());
            textHeight = in.readInt();
            textWidth = in.readInt();
            breakStrategy = in.readInt();
            letterSpacing = in.readFloat();
            maxLines = in.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }
        public static final Creator<ReflowData> CREATOR = new Creator<ReflowData>() {
            @Override
            public ReflowData createFromParcel(Parcel in) {
                return new ReflowData(in);
            }

            @Override
            public ReflowData[] newArray(int size) {
                return new ReflowData[size];
            }
        };


        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(text);
            dest.writeFloat(textSize);
            dest.writeInt(textColor);
            dest.writeValue(bounds);
            dest.writeInt(fontResId);
            dest.writeFloat(lineSpacingAdd);
            dest.writeFloat(lineSpacingMult);
            dest.writeValue(textPosition);
            dest.writeInt(textHeight);
            dest.writeInt(textWidth);
            dest.writeInt(breakStrategy);
            dest.writeFloat(letterSpacing);
            dest.writeInt(maxLines);
        }
    }

}
