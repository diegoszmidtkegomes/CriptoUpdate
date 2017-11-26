package ifrs.com.criptoupdate.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
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
            new CotacaoHelper().atualizaMoedas(getApplicationContext());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;

    }

    /*private void atualizaMoedas() {
        List<CotacaoCadastro> lista = new CotacaoRepositorio().selectTodosAtivos();
        for (CotacaoCadastro cot: lista
                ) {
            if(cot.getMoedaEnum().equals(Moeda.BITCOIN)){
                new CotacaoHelper().atualizaBtc(getApplicationContext());
            }
            if(cot.getMoedaEnum().equals(Moeda.BCASH)){
                new CotacaoHelper().atualizaBch(this);
            }
            if(cot.getMoedaEnum().equals(Moeda.LITECOIN)){
                new CotacaoHelper().atualizaLtc(this);
            }
        }

    }*/

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
