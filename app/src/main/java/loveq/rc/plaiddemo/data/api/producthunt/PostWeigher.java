package loveq.rc.plaiddemo.data.api.producthunt;

import java.util.List;

import loveq.rc.plaiddemo.data.PlaidItemSorting;
import loveq.rc.plaiddemo.data.api.producthunt.model.Post;

/**
 * Created by rc on 2017/12/23.
 * Description:
 */

public class PostWeigher implements PlaidItemSorting.PlaidItemGroupWeigher<Post> {

    @Override
    public void weigh(List<Post> posts) {
        float maxVotes = 0f;
        float maxComments = 0f;
        for (Post post : posts) {
            maxVotes = Math.max(maxVotes, post.votes_count);
            maxComments = Math.max(maxComments, post.comments_count);
        }
        for (Post post : posts) {
            float weight = 1f - ((((float) post.comments_count) / maxComments) +
                    ((float) post.votes_count / maxVotes)) / 2f;
            post.weight = post.page + weight;
        }
    }
}
