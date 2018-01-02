package loveq.rc.plaiddemo.ui;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import loveq.rc.plaiddemo.R;
import loveq.rc.plaiddemo.data.DataLoadingSubject;
import loveq.rc.plaiddemo.data.PlaidItem;
import loveq.rc.plaiddemo.data.PlaidItemSorting;
import loveq.rc.plaiddemo.data.api.designernews.StoryWeigher;
import loveq.rc.plaiddemo.data.api.designernews.model.Story;
import loveq.rc.plaiddemo.data.api.dribbble.ShotWeigher;
import loveq.rc.plaiddemo.data.api.dribbble.model.Shot;
import loveq.rc.plaiddemo.data.api.producthunt.PostWeigher;
import loveq.rc.plaiddemo.ui.recyclerview.Divided;
import loveq.rc.plaiddemo.ui.widget.BaselineGridTextView;
import loveq.rc.plaiddemo.util.customtabs.CustomTabActivityHelper;


/**
 * Created by rc on 2017/12/21.
 * Description:
 */

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements DataLoadingSubject.DataLoadingCallbacks,
        ListPreloader.PreloadModelProvider<Shot> {

    static final int REQUEST_CODE_VIEW_SHOT = 5407;

    private static final int TYPE_DESIGNER_NEWS_STORY = 0;
    private static final int TYPE_DRIBBBLE_SHOT = 1;
    private static final int TYPE_PRODUCT_HUNT_POST = 2;
    private static final int TYPE_LOADING_MORE = -1;
    // we need to hold on to an activity ref for the shared element transitions :/
    final Activity host;
    private final LayoutInflater layoutInflater;
    private final PlaidItemSorting.PlaidItemComparator comparator;
    private final boolean pocketIsInstalled;
    private final @Nullable
    DataLoadingSubject dataLoading;
    private final int columns;
    private final ColorDrawable[] shotLoadingPlaceholders;
    private final ViewPreloadSizeProvider<Shot> shotPreloadSizeProvider;
    private final @ColorInt
    int initialGifBadgeColor;
    private List<PlaidItem> items;
    private boolean showLoadingMore = false;

    private ShotWeigher shotWeigher;
    private StoryWeigher storyWeigher;
    private PostWeigher postWeigher;

    FeedAdapter(Activity hostActivity,
                @Nullable DataLoadingSubject dataLoading,
                int columns,
                boolean pocketInstalled, ViewPreloadSizeProvider<Shot> shotPreloadSizeProvider) {
        this.host = hostActivity;
        this.dataLoading = dataLoading;
        if (dataLoading != null) {
            dataLoading.registerCallback(this);
        }
        this.columns = columns;
        this.pocketIsInstalled = pocketInstalled;
        this.shotPreloadSizeProvider = shotPreloadSizeProvider;
        layoutInflater = LayoutInflater.from(host);
        comparator = new PlaidItemSorting.PlaidItemComparator();
        items = new ArrayList<>();
        setHasStableIds(true);

        // get the dribbble shot placeholder colors & badge color from the theme
        final TypedArray a = host.obtainStyledAttributes(R.styleable.DribbbleFeed);
        final int loadingColorArrayId =
                a.getResourceId(R.styleable.DribbbleFeed_shotLoadingPlaceholderColors, 0);
        if (loadingColorArrayId != 0) {
            int[] placeholderColors = host.getResources().getIntArray(loadingColorArrayId);
            shotLoadingPlaceholders = new ColorDrawable[placeholderColors.length];
            for (int i = 0; i < placeholderColors.length; i++) {
                shotLoadingPlaceholders[i] = new ColorDrawable(placeholderColors[i]);
            }
        } else {
            shotLoadingPlaceholders = new ColorDrawable[]{new ColorDrawable(Color.DKGRAY)};
        }

        final int initialGifBadgeColorId =
                a.getResourceId(R.styleable.DribbbleFeed_initialBadgeColor, 0);
        initialGifBadgeColor = initialGifBadgeColorId != 0 ?
                ContextCompat.getColor(host, initialGifBadgeColorId) : 0x40ffffff;
        a.recycle();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_DESIGNER_NEWS_STORY:
                return createDesignerNewsStoryHolder(parent);
            case TYPE_DRIBBBLE_SHOT:
                return createDribbbleShotHolder(parent);
            case TYPE_PRODUCT_HUNT_POST:
                return createProductHuntStoryHolder(parent);
            case TYPE_LOADING_MORE:
                return new LoadingMoreHolder(
                        layoutInflater.inflate(R.layout.infinite_loading, parent, false));
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private RecyclerView.ViewHolder createDesignerNewsStoryHolder(ViewGroup parent) {
        final DesignerNewsStoryHolder holder = new DesignerNewsStoryHolder(layoutInflater.inflate(
                R.layout.designer_news_story_item, parent, false), pocketIsInstalled);
        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Story story = (Story) getItem(holder.getAdapterPosition());
                        CustomTabActivityHelper.openCustomTab(host,
                                DesignerNewsStory.getCustomTabIntent(host, story, null).build(),
                                Uri.parse(story.url));
                    }
                }
        );

        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return holder;
    }


    private RecyclerView.ViewHolder createDribbbleShotHolder(ViewGroup parent) {

        return null;
    }

    private RecyclerView.ViewHolder createProductHuntStoryHolder(ViewGroup parent) {

        return null;
    }

    static class DesignerNewsStoryHolder extends RecyclerView.ViewHolder implements Divided {

        @BindView(R.id.story_title)
        BaselineGridTextView title;
        @BindView(R.id.story_comments)
        TextView comments;
        @BindView(R.id.pocket)
        ImageButton pocket;

        DesignerNewsStoryHolder(View itemView, boolean pocketIsInstalled) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            pocket.setVisibility(pocketIsInstalled ? View.VISIBLE : View.GONE);
        }
    }

    static class LoadingMoreHolder extends RecyclerView.ViewHolder {

        ProgressBar progress;

        LoadingMoreHolder(View itemView) {
            super(itemView);
            progress = (ProgressBar) itemView;
        }

    }

    public static SharedElementCallback createSharedElementReenterCallback(Context context) {
        final String shotTransitionName = context.getString(R.string.transition_shot);
        final String shotBackgroundTransitionName =
                context.getString(R.string.transition_shot_background);
        return new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                if (sharedElements.size() != names.size()) {
                    View sharedShot = sharedElements.get(shotTransitionName);
                    if (sharedShot != null) {
                        sharedElements.put(shotBackgroundTransitionName, sharedShot);
                    }
                }
            }
        };
    }

    @Override
    public void dataStartedLoading() {

    }

    @Override
    public void dataFinishedLoading() {

    }

    @NonNull
    @Override
    public List<Shot> getPreloadItems(int position) {
        return null;
    }

    @Nullable
    @Override
    public RequestBuilder getPreloadRequestBuilder(Shot item) {
        return null;
    }


    private PlaidItem getItem(int position) {
        if (position < 0 || position >= items.size()) return null;
        return items.get(position);
    }
}
