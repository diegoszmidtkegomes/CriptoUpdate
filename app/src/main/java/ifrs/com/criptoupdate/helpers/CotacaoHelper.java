package ifrs.com.criptoupdate.helpers;

import android.content.Context;
import android.util.Log;

import ifrs.com.criptoupdate.app.RestClient;
import ifrs.com.criptoupdate.model.Cotacao;
import ifrs.com.criptoupdate.model.Ticker;
import ifrs.com.criptoupdate.model.interfaces.iAsyncObj;
import ifrs.com.criptoupdate.services.CotacaoService;
import ifrs.com.criptoupdate.util.Notificacao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by diego on 07/11/2017.
 */

public class CotacaoHelper implements iAsyncObj {

    Context context;

    public void buscaCtc(final iAsyncObj delegate){
        CotacaoService cotacaoService = RestClient.createService(CotacaoService.class, "admin", "123");
        Call<Cotacao> call = cotacaoService.buscarBtc("application/json");
        call.enqueue(new Callback<Cotacao>() {
            @Override
            public void onResponse(Call<Cotacao> call, Response<Cotacao> response) {
                Log.e("diego", "teste");
                Cotacao cot = response.body();
                delegate.processoEncerrado(cot);
            }

            @Override
            public void onFailure(Call<Cotacao> call, Throwable t) {
                Log.e("diego", "teste");
            }
        });

    }

    public void atualizaCtc(Context context){
        this.context = context;
        buscaCtc(this);
    }

    @Override
    public void processoEncerrado(Object obj) {
        if (obj instanceof Cotacao) {
            Cotacao cot = (Cotacao) obj;
            new Notificacao().atualizaCotacao(context, cot);
        }
    }
}
