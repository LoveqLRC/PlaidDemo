package loveq.rc.plaiddemo.data.api.dribbble.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by rc on 2017/12/22.
 * Description:
 */

public class Images implements Parcelable {
    private static final int[] NORMAL_IMAGE_SIZE = new int[]{400, 300};
    private static final int[] TWO_X_IMAGE_SIZE = new int[]{800, 600};
    public final String hidpi;
    public final String normal;
    public final String teaser;

    public Images(String hidpi, String normal, String teaser) {
        this.hidpi = hidpi;
        this.normal = normal;
        this.teaser = teaser;
    }

    protected Images(Parcel in) {
        this.hidpi = in.readString();
        this.normal = in.readString();
        this.teaser = in.readString();
    }

    public String best() {
        return !TextUtils.isEmpty(hidpi) ? hidpi : normal;
    }
    public int[] bestSize() {
        return !TextUtils.isEmpty(hidpi) ? TWO_X_IMAGE_SIZE : NORMAL_IMAGE_SIZE;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hidpi);
        dest.writeString(this.normal);
        dest.writeString(this.teaser);
    }


    public static final Creator<Images> CREATOR = new Creator<Images>() {
        @Override
        public Images createFromParcel(Parcel source) {
            return new Images(source);
        }

        @Override
        public Images[] newArray(int size) {
            return new Images[size];
        }
    };
}
