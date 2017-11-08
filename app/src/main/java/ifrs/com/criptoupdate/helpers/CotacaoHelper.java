package ifrs.com.criptoupdate.helpers;

import android.content.Context;
import android.util.Log;

import ifrs.com.criptoupdate.app.RestClient;
import ifrs.com.criptoupdate.model.Moeda;
import ifrs.com.criptoupdate.model.response.Cotacao;
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

    public void buscaBtc(final iAsyncObj delegate){
        CotacaoService cotacaoService = RestClient.createService(CotacaoService.class, "admin", "123");
        Call<Cotacao> call = cotacaoService.buscarBtc("application/json");
        call.enqueue(new Callback<Cotacao>() {
            @Override
            public void onResponse(Call<Cotacao> call, Response<Cotacao> response) {
                Log.e("diego", "teste");
                Cotacao cot = response.body();
                cot.setMoeda(Moeda.BITCOIN);
                delegate.processoEncerrado(cot);
            }

            @Override
            public void onFailure(Call<Cotacao> call, Throwable t) {
                Log.e("diego", "teste");
            }
        });

    }

    public void buscaLtc(final iAsyncObj delegate){
        CotacaoService cotacaoService = RestClient.createService(CotacaoService.class, "admin", "123");
        Call<Cotacao> call = cotacaoService.buscarLtc("application/json");
        call.enqueue(new Callback<Cotacao>() {
            @Override
            public void onResponse(Call<Cotacao> call, Response<Cotacao> response) {
                Log.e("diego", "teste");
                Cotacao cot = response.body();
                cot.setMoeda(Moeda.LITECOIN);
                delegate.processoEncerrado(cot);
            }

            @Override
            public void onFailure(Call<Cotacao> call, Throwable t) {
                Log.e("diego", "teste");
            }
        });

    }

    public void buscaBch(final iAsyncObj delegate){
        CotacaoService cotacaoService = RestClient.createService(CotacaoService.class, "admin", "123");
        Call<Cotacao> call = cotacaoService.buscarBch("application/json");
        call.enqueue(new Callback<Cotacao>() {
            @Override
            public void onResponse(Call<Cotacao> call, Response<Cotacao> response) {
                Log.e("diego", "teste");
                Cotacao cot = response.body();
                cot.setMoeda(Moeda.BCASH);
                delegate.processoEncerrado(cot);
            }

            @Override
            public void onFailure(Call<Cotacao> call, Throwable t) {
                Log.e("diego", "teste");
            }
        });

    }

    public void atualizaBtc(Context context){
        this.context = context;
        buscaBtc(this);
    }

    @Override
    public void processoEncerrado(Object obj) {
        if (obj instanceof Cotacao) {
            Cotacao cot = (Cotacao) obj;
            new Notificacao().atualizaCotacao(context, cot);
        }
    }
}
