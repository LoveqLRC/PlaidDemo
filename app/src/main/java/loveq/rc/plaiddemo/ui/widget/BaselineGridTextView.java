package loveq.rc.plaiddemo.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.annotation.FontRes;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;

import loveq.rc.plaiddemo.R;

/**
 * Created by rc on 2017/12/20.
 * Description:
 */

public class BaselineGridTextView extends AppCompatTextView {

    private final float FOUR_DIP;
    private float lineHeightMultiplierHint = 1f;
    private float lineHeightHint = 0f;
    private boolean maxLinesByHeight = false;
    private int extraTopPadding = 0;
    private int extraBottomPadding = 0;
    private @FontRes
    int fontResId = 0;


    public BaselineGridTextView(Context context) {
        this(context, null);
    }

    public BaselineGridTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public BaselineGridTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BaselineGridTextView, defStyleAttr, 0);
        // first check TextAppearance for line height & font attributes
        if (array.hasValue(R.styleable.BaselineGridTextView_android_textAppearance)) {
            int textAppearanceId =
                    array.getResourceId(R.styleable.BaselineGridTextView_android_textAppearance,
                            android.R.style.TextAppearance);
            TypedArray ta = context.obtainStyledAttributes(
                    textAppearanceId, R.styleable.BaselineGridTextView);
            parseTextAttrs(ta);
            ta.recycle();
        }
        // then check view attrs
        parseTextAttrs(array);
        maxLinesByHeight = array.getBoolean(R.styleable.BaselineGridTextView_maxLinesByHeight, false);
        array.recycle();

        FOUR_DIP = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        computeLineHeight();
    }

    /**
     * 确保行高是4dp的倍数
     */
    private void computeLineHeight() {
        final Paint.FontMetrics fm = getPaint().getFontMetrics();
        final float fontHeight = Math.abs(fm.ascent - fm.descent) + fm.leading;
        final float desiredLineHeight = (lineHeightHint > 0)
                ? lineHeightHint
                : lineHeightMultiplierHint * fontHeight;

        final int baselineAlignedLineHeight =
                (int) ((FOUR_DIP * (float) Math.ceil(desiredLineHeight / FOUR_DIP)) + 0.5f);
        setLineSpacing(baselineAlignedLineHeight - fontHeight, 1f);
    }

    private void parseTextAttrs(TypedArray a) {
        if (a.hasValue(R.styleable.BaselineGridTextView_lineHeightMultiplierHint)) {
            lineHeightMultiplierHint =
                    a.getFloat(R.styleable.BaselineGridTextView_lineHeightMultiplierHint, 1f);
        }
        if (a.hasValue(R.styleable.BaselineGridTextView_lineHeightHint)) {
            lineHeightHint = a.getDimensionPixelSize(
                    R.styleable.BaselineGridTextView_lineHeightHint, 0);
        }
        if (a.hasValue(R.styleable.BaselineGridTextView_android_fontFamily)) {
            fontResId = a.getResourceId(R.styleable.BaselineGridTextView_android_fontFamily, 0);
        }
    }

    public @FontRes
    int getFontResId() {
        return fontResId;
    }

    @Override
    public int getCompoundPaddingTop() {
        // include extra padding to place the first line's baseline on the grid
        return super.getCompoundPaddingTop() + extraTopPadding;
    }

    @Override
    public int getCompoundPaddingBottom() {
        // include extra padding to make the height a multiple of 4dp
        return super.getCompoundPaddingBottom() + extraBottomPadding;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        extraTopPadding = 0;
        extraBottomPadding = 0;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        height += ensureBaselineOnGrid();
        height += ensureHeightGridAligned(height);
        setMeasuredDimension(getMeasuredWidth(), height);
        checkMaxLines(height, MeasureSpec.getMode(heightMeasureSpec));
    }

    private void checkMaxLines(int height, int heightMode) {
        if (!maxLinesByHeight || heightMode != MeasureSpec.EXACTLY) return;

        int textHeight = height - getCompoundPaddingTop() - getCompoundPaddingBottom();
        int completeLines = (int) Math.floor(textHeight / getLineHeight());
        setMaxLines(completeLines);
    }

    /**
     * 确保高度是4的倍数
     */
    private int ensureHeightGridAligned(int height) {
        float gridOverhang = height % FOUR_DIP;
        if (gridOverhang != 0) {
            extraBottomPadding = (int) (FOUR_DIP - Math.ceil(gridOverhang));
        }
        return extraBottomPadding;
    }

    /**
     * 确保第一行文本坐在4dp网格
     * @return
     */
    private int ensureBaselineOnGrid() {
        float baseline = getBaseline();
        float gridAlign = baseline % FOUR_DIP;
        if (gridAlign != 0) {
            extraTopPadding = (int) (FOUR_DIP - Math.ceil(gridAlign));
        }
        return extraTopPadding;
    }
}
