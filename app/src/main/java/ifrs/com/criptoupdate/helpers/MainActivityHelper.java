package ifrs.com.criptoupdate.helpers;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import ifrs.com.criptoupdate.services.AtualizaCotacaoService;
import ifrs.com.criptoupdate.views.MainActivity;


/**
 * Created by diego.gomes on 06/03/2017.
 */

public class MainActivityHelper {

    private static final int JOB_ID = 1;

    public void verificaService(final MainActivity activity) {
        JobScheduler scheduler = (JobScheduler) activity.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.cancelAll();

        ComponentName serviceName = new ComponentName(activity, AtualizaCotacaoService.class);
        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, serviceName)
                //.setPeriodic(120000)
                .setPeriodic(600000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                //.setRequiresDeviceIdle(true)
                .build();

        scheduler.schedule(jobInfo);
        /*if (result == JobScheduler.RESULT_SUCCESS) {
            Toast.makeText(activity, "Criei o job", Toast.LENGTH_LONG).show();
        }*/
    }


}
