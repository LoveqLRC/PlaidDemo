package loveq.rc.plaiddemo.data.api.designernews;

import loveq.rc.plaiddemo.data.api.EnvelopePayload;
import loveq.rc.plaiddemo.data.api.designernews.model.Story;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by rc on 2017/12/25.
 * Description:
 */

public interface DesignerNewsService {
    String ENDPOINT = "https://www.designernews.co/";


    @EnvelopePayload("story")
    @POST("api/v1/stories/{id}/upvote")
    Call<Story> upvoteStory(@Path("id") long storyId);
}
