package loveq.rc.plaiddemo.data.api.designernews;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import loveq.rc.plaiddemo.data.api.designernews.model.Story;
import loveq.rc.plaiddemo.data.prefs.DesignerNewsPrefs;
import retrofit2.Call;
import retrofit2.Response;


public class UpvoteStoryService extends IntentService {
    public static final String ACTION_UPVOTE = "ACTION_UPVOTE";
    public static final String EXTRA_STORY_ID = "EXTRA_STORY_ID";

    public UpvoteStoryService() {
        super("UpvoteStoryService");
    }

    public static void startActionUpvote(@NonNull Context context, long storyId) {
        final Intent intent = new Intent(context, UpvoteStoryService.class);
        intent.setAction(ACTION_UPVOTE);
        intent.putExtra(EXTRA_STORY_ID, storyId);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPVOTE.equals(action)) {
                handleActionUpvote(intent.getLongExtra(EXTRA_STORY_ID, 0L));
            }
        }
    }

    private void handleActionUpvote(long storyId) {
        if (storyId == 0L) return;
        final DesignerNewsPrefs designerNewsPrefs = DesignerNewsPrefs.get(this);
        if (!designerNewsPrefs.isLoggedIn()) {
            // TODO prompt for login
            return;
        }

        final Call<Story> upvoteStoryCall = designerNewsPrefs.getApi().upvoteStory(storyId);
        try {
            final Response<Story> response = upvoteStoryCall.execute();
            // int newVotesCount = response.body().vote_count;
            // TODO report success
        } catch (Exception e) {
            // TODO report failure
        }
    }
}
