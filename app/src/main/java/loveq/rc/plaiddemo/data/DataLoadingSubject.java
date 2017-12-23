package loveq.rc.plaiddemo.data;

/**
 * Created by rc on 2017/12/22.
 * Description:
 */

public interface DataLoadingSubject {
    boolean isDataLoading();

    void registerCallback(DataLoadingCallbacks callbacks);

    void unregisterCallback(DataLoadingCallbacks callbacks);

    interface DataLoadingCallbacks {
        void dataStartedLoading();

        void dataFinishedLoading();
    }
}
