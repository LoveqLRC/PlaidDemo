package loveq.rc.plaiddemo.ui;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.content.ContextCompat;

import loveq.rc.plaiddemo.R;
import loveq.rc.plaiddemo.data.api.designernews.UpvoteStoryService;
import loveq.rc.plaiddemo.data.api.designernews.model.Story;
import loveq.rc.plaiddemo.util.DrawableUtils;

public class DesignerNewsStory extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer_news_story);
    }

    public static CustomTabsIntent.Builder getCustomTabIntent(@NonNull Context context,
                                                              @NonNull Story story,
                                                              @Nullable CustomTabsSession session) {
        Intent upvoteStory = new Intent(context, UpvoteStoryService.class);
        upvoteStory.setAction(UpvoteStoryService.ACTION_UPVOTE);
        upvoteStory.putExtra(UpvoteStoryService.EXTRA_STORY_ID, story.id);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, upvoteStory, 0);
        return new CustomTabsIntent.Builder(session)
                .setToolbarColor(ContextCompat.getColor(context, R.color.designer_news))
                .setActionButton(DrawableUtils.drawableToBitmap(context,
                        R.drawable.ic_upvote_filled_24dp_white),
                        context.getString(R.string.upvote_story),
                        pendingIntent,
                        false)
                .setShowTitle(true)
                .enableUrlBarHiding()
                .addDefaultShareMenuItem();

    }
}
