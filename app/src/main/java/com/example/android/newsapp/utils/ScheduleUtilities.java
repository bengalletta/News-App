package com.example.android.newsapp.utils;

        import android.content.Context;
        import android.support.annotation.NonNull;
        import com.example.android.newsapp.tasks.NewsJob;
        import com.firebase.jobdispatcher.Constraint;
        import com.firebase.jobdispatcher.Driver;
        import com.firebase.jobdispatcher.FirebaseJobDispatcher;
        import com.firebase.jobdispatcher.GooglePlayDriver;
        import com.firebase.jobdispatcher.Job;
        import com.firebase.jobdispatcher.Lifetime;
        import com.firebase.jobdispatcher.Trigger;


public class ScheduleUtilities {
    private static final int SCHEDULE_INTERVAL_MINUTES = 60;
    private static final String NEWS_JOB_TAG = "news_job_tag";

    private static boolean sInitialized;

    synchronized public static void scheduleRefresh(@NonNull final Context context){
        if(sInitialized) return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job constraintRefreshJob = dispatcher.newJobBuilder()
                //sets what Job service to run
                .setService(NewsJob.class)
                //set the tag
                .setTag(NEWS_JOB_TAG)
                //runtime when a job available if there is internet
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(SCHEDULE_INTERVAL_MINUTES, SCHEDULE_INTERVAL_MINUTES))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(constraintRefreshJob);
        sInitialized = true;
    }
}