package loveq.rc.plaiddemo.data.api.dribbble;

import java.util.List;

import loveq.rc.plaiddemo.data.PlaidItemSorting;
import loveq.rc.plaiddemo.data.api.dribbble.model.Shot;

/**
 * Created by rc on 2017/12/23.
 * Description:
 */

public class ShotWeigher implements PlaidItemSorting.PlaidItemGroupWeigher<Shot> {
    @Override
    public void weigh(List<Shot> shots) {
        float maxLikes = 0f;
        for (Shot shot : shots) {
            maxLikes = Math.max(maxLikes, shot.likes_count);
        }
        for (Shot shot : shots) {
            float weight = 1f - ((float) shot.likes_count / maxLikes);
            shot.weight = shot.page + weight;
        }
    }
}
