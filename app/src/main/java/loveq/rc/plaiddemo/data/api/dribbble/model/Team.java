package loveq.rc.plaiddemo.data.api.dribbble.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

import loveq.rc.plaiddemo.util.ParcelUtils;

/**
 * Created by rc on 2017/12/22.
 * Description:
 */

public class Team implements Parcelable {
    public final long id;
    public final String name;
    public final String username;
    public final String html_url;
    public final String avatar_url;
    public final String bio;
    public final String location;
    public final Map<String, String> links;

    public Team(long id,
                String name,
                String username,
                String html_url,
                String avatar_url,
                String bio,
                String location,
                Map<String, String> links) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.html_url = html_url;
        this.avatar_url = avatar_url;
        this.bio = bio;
        this.location = location;
        this.links = links;
    }
    protected Team(Parcel in) {
        id = in.readLong();
        name = in.readString();
        username = in.readString();
        html_url = in.readString();
        avatar_url = in.readString();
        bio = in.readString();
        location = in.readString();
        links = ParcelUtils.readStringMap(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(html_url);
        dest.writeString(avatar_url);
        dest.writeString(bio);
        dest.writeString(location);
        ParcelUtils.writeStringMap(links, dest);
    }



    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel source) {
            return new Team(source);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };
}
