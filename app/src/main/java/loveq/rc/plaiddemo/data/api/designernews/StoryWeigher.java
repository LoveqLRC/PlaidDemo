package loveq.rc.plaiddemo.data.api.designernews;

import java.util.List;

import loveq.rc.plaiddemo.data.PlaidItemSorting;
import loveq.rc.plaiddemo.data.api.designernews.model.Story;

/**
 * Created by rc on 2017/12/23.
 * Description:
 */

public class StoryWeigher implements PlaidItemSorting.PlaidItemGroupWeigher<Story>  {
    @Override
    public void weigh(List<Story> stories) {
        float maxVotes = 0f;
        float maxComments = 0f;
        for (Story story : stories) {
            maxVotes = Math.max(maxVotes, story.vote_count);
            maxComments = Math.max(maxComments, story.comment_count);
        }
        for (Story story : stories) {
            float weight = 1f - ((((float) story.comment_count) / maxComments) +
                    ((float) story.vote_count / maxVotes)) / 2f;
            story.weight = story.page + weight;
        }
    }
}
