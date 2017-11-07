package ifrs.com.criptoupdate.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.SharedPreferences;
import android.util.Log;

import ifrs.com.criptoupdate.helpers.CotacaoHelper;


/**
 * Created by diego on 05/03/2017.
 */

public class AtualizaCotacaoService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.e("diego", "no service");
        try {
            new CotacaoHelper().atualizaCtc(getApplicationContext());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;

    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
