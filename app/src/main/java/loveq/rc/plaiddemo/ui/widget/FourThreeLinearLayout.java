package loveq.rc.plaiddemo.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by rc on 2017/12/23.
 * Description:
 */

public class FourThreeLinearLayout extends ForegroundLinearLayout {
    public FourThreeLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightMeasureSpec) {
        int fourThreeHeight = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthSpec) * 3 / 4,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthSpec, fourThreeHeight);
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }
}
