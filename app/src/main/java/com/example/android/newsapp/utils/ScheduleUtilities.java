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
        if(sInitialized) return; //if the job already initialized, simply return

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job constraintRefreshJob = dispatcher.newJobBuilder()
                //sets what Job service to run for the dispatcher
                .setService(NewsJob.class)
                //set the tag to identify the job
                .setTag(NEWS_JOB_TAG)
                //runtime when a job is eligible to start running, in this case, it's when the user
                //has internet available
                .setConstraints(Constraint.ON_ANY_NETWORK)
                //sets when the job should be executed, in this case, forever
                .setLifetime(Lifetime.FOREVER)
                //tells if the job should reoccur, in this case, yes
                .setRecurring(true)
                //the range where the job should trigger, from a starting period to a respectable amount
                //afterwards; in this case set it to be exactly 1 min (give or take a few secs off)
                .setTrigger(Trigger.executionWindow(SCHEDULE_INTERVAL_MINUTES, SCHEDULE_INTERVAL_MINUTES))
                //asks if this job should replace previous jobs with the same tag (i.e. NEWS_JOB_TAG)
                .setReplaceCurrent(true)
                //builds the job with all the settings specificed above
                .build();

        dispatcher.schedule(constraintRefreshJob);
        sInitialized = true;
    }
}