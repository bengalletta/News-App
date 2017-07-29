package com.example.android.newsapp.tasks;

        import android.os.AsyncTask;
        import android.widget.Toast;
        import com.firebase.jobdispatcher.JobService;
        import com.firebase.jobdispatcher.JobParameters;

public class NewsJob extends JobService {
    private AsyncTask mBackgroundTask;

    //initial method when the job gets called which will run on the main thread
    @Override
    public boolean onStartJob(final JobParameters job){
        mBackgroundTask = new AsyncTask() {

            @Override
            protected void onPreExecute(){
                //Notifies the user that the activity has automatically refreshed
                Toast.makeText(NewsJob.this, "News has been refreshed.", Toast.LENGTH_SHORT).show();
                super.onPreExecute();
            }
            @Override
            protected Object doInBackground(Object[] params) {
                FetchNews.fetchArticles(NewsJob.this);
                return null;
            }

            @Override
            protected void onPostExecute(Object o){
                //indicates job completion
                jobFinished(job, false);
                super.onPostExecute(o);
            }
        };
        mBackgroundTask.execute();
        return true;
    }

    //stops the job within constraints specified
    @Override
    public boolean onStopJob(JobParameters job){
        if (mBackgroundTask != null){
            mBackgroundTask.cancel(false);
        }
        return true;
    }
}