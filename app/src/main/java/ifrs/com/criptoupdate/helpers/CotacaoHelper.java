package ifrs.com.criptoupdate.helpers;

import android.content.Context;
import android.util.Log;

import java.util.List;

import ifrs.com.criptoupdate.app.RestClient;
import ifrs.com.criptoupdate.data.CotacaoRepositorio;
import ifrs.com.criptoupdate.model.CotacaoCadastro;
import ifrs.com.criptoupdate.model.EmailEnvio;
import ifrs.com.criptoupdate.model.Moeda;
import ifrs.com.criptoupdate.model.interfaces.iAsyncObj;
import ifrs.com.criptoupdate.model.response.Cotacao;
import ifrs.com.criptoupdate.model.response.Email;
import ifrs.com.criptoupdate.model.response.EmailEnviado;
import ifrs.com.criptoupdate.model.response.Variacao;
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
    private String apiKey = "d46b232a9b37fd3afc552c4e5eea14da";

    public void buscaBtc(final iAsyncObj delegate){
        CotacaoService cotacaoService = RestClient.createService(CotacaoService.class,
                "admin", "123");
        Call<Cotacao> call = cotacaoService.buscarBtc("application/json", apiKey);
        call.enqueue(new Callback<Cotacao>() {
            @Override
            public void onResponse(Call<Cotacao> call, Response<Cotacao> response) {
                Log.e("diego", "teste");
                Cotacao cot = response.body();
                if (cot != null) {
                    cot.setMoeda(Moeda.BITCOIN);
                }
                delegate.processoEncerrado(cot);
            }

            @Override
            public void onFailure(Call<Cotacao> call, Throwable t) {
                Log.e("diego",
                        "teste");
            }
        });
    }

    public void buscaLtc(final iAsyncObj delegate){
        CotacaoService cotacaoService = RestClient.createService(CotacaoService.class,
                "admin", "123");
        Call<Cotacao> call = cotacaoService.buscarLtc("application/x-www-form-urlencoded",
                apiKey);
        call.enqueue(new Callback<Cotacao>() {
            @Override
            public void onResponse(Call<Cotacao> call, Response<Cotacao> response) {
                Log.e("diego", "teste");
                Cotacao cot = response.body();
                if (cot != null)
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
        CotacaoService cotacaoService = RestClient.createService(CotacaoService.class, "admin",
                "123");
        Call<Cotacao> call = cotacaoService.buscarBch("application/x-www-form-urlencoded", apiKey);
        call.enqueue(new Callback<Cotacao>() {
            @Override
            public void onResponse(Call<Cotacao> call, Response<Cotacao> response) {
                Log.e("diego", "teste");
                Cotacao cot = response.body();
                if (cot != null)
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
    public void atualizaLtc(Context context){
        this.context = context;
        buscaLtc(this);
    }
    public void atualizaBch(Context context){
        this.context = context;
        buscaBch(this);
    }

    @Override
    public void processoEncerrado(Object obj) {
        if (obj instanceof Cotacao) {
            Cotacao cot = (Cotacao) obj;
            CotacaoCadastro cad = new CotacaoRepositorio().selectByMoeda(cot.getMoedaEnum());
            boolean alter = validaPercentAlteracao(Double.parseDouble(cot.getTicker().getSell()),
                    cad.getValorVenda(), cad.getPercentual());
            if (alter) {
                realizaEnvioEmail(cad.getToken(),
                        calculaPercentAlteracao(Double.parseDouble(cot.getTicker().getSell()),
                                cad.getValorVenda(), cad.getPercentual()), cot.getMoedaEnum());
            }
            new CotacaoRepositorio().updateValor(cad.getId(),
                    Double.parseDouble(cot.getTicker().getSell()));
            new Notificacao().atualizaCotacao(context, cot);
        }
    }

    private void realizaEnvioEmail(String apiToken, double variacao, int moeda) {
        if (moeda == 1) {
            enviarEmailBtc(this, apiToken, variacao);
        } else if (moeda == 2) {
            enviarEmailLtc(this, apiToken, variacao);
        } else if (moeda == 3) {
            enviarEmailBch(this, apiToken, variacao);
        }
    }

    private double calculaPercentAlteracao(double valorAtual, double valorAntigo, int percentual) {
        double dif = valorAntigo - valorAtual;
        if (dif < 0) {
            dif = dif * -1;
        }
        return dif * 100 / valorAntigo;

    }

    private boolean validaPercentAlteracao(double valorAtual, double valorAntigo, int percentual) {
        //return true;
        boolean retorno;
        double dif = valorAntigo - valorAtual;
        if (dif < 0) {
            dif = dif * -1;
        } else if (dif == 0) {
            return false;
        }
        Log.e("percentual", String.valueOf(percentual));
        double calc = dif * 100 / valorAntigo;
        retorno = calc >= percentual;
        return retorno;
    }

    public void atualizaMoedas(Context context) {
        List<CotacaoCadastro> lista = new CotacaoRepositorio().selectTodosAtivos();
        for (CotacaoCadastro cot : lista
                ) {
            if (cot.getMoedaEnum().equals(Moeda.BITCOIN)) {
                new CotacaoHelper().atualizaBtc(context);
            }
            if (cot.getMoedaEnum().equals(Moeda.BCASH)) {
                new CotacaoHelper().atualizaBch(context);
            }
            if (cot.getMoedaEnum().equals(Moeda.LITECOIN)) {
                new CotacaoHelper().atualizaLtc(context);
            }
        }
    }

    public void enviarEmailBtc(final iAsyncObj delegate, String apiToken, double variacao) {
        CotacaoService cotacaoService = RestClient.createService(CotacaoService.class,
                "admin", "123");
        Variacao variacao1 = new Variacao();
        variacao1.setVariation(variacao);
        Call<EmailEnviado> call = cotacaoService.sendEmailBtc("application/json",
                apiKey, apiToken, variacao1);
        call.enqueue(new Callback<EmailEnviado>() {
            @Override
            public void onResponse(Call<EmailEnviado> call, Response<EmailEnviado> response) {
                Log.e("diego", "teste");
                EmailEnviado cot = response.body();
                //cot.setMoeda(Moeda.BCASH);
                delegate.processoEncerrado(cot);
            }

            @Override
            public void onFailure(Call<EmailEnviado> call, Throwable t) {
                Log.e("diego", "teste");
            }
        });
    }

    public void enviarEmailBch(final iAsyncObj delegate, String apiToken, double variacao) {
        CotacaoService cotacaoService = RestClient.createService(CotacaoService.class, "admin", "123");
        Variacao variacao1 = new Variacao();
        CotacaoCadastro c = new CotacaoRepositorio().selectByMoeda(3);
        variacao1.setVariation(variacao);
        Call<EmailEnviado> call = cotacaoService.sendEmailBch("application/json", apiKey,
                apiToken, variacao1);
        call.enqueue(new Callback<EmailEnviado>() {
            @Override
            public void onResponse(Call<EmailEnviado> call, Response<EmailEnviado> response) {
                Log.e("diego", "teste");
                EmailEnviado cot = response.body();
                //cot.setMoeda(Moeda.BCASH);
                delegate.processoEncerrado(cot);
            }

            @Override
            public void onFailure(Call<EmailEnviado> call, Throwable t) {
                Log.e("diego", "teste");
            }
        });
    }

    public void enviarEmailLtc(final iAsyncObj delegate, String apiToken, double variacao) {
        CotacaoService cotacaoService = RestClient.createService(CotacaoService.class, "admin", "123");
        CotacaoCadastro c = new CotacaoRepositorio().selectByMoeda(2);
        Variacao variacao1 = new Variacao();
        variacao1.setVariation(variacao);
        Call<EmailEnviado> call = cotacaoService.sendEmailLtc("application/json", apiKey, apiToken, variacao1);
        call.enqueue(new Callback<EmailEnviado>() {
            @Override
            public void onResponse(Call<EmailEnviado> call, Response<EmailEnviado> response) {
                Log.e("diego", "teste");
                EmailEnviado cot = response.body();
                //cot.setMoeda(Moeda.BCASH);
                delegate.processoEncerrado(cot);
            }

            @Override
            public void onFailure(Call<EmailEnviado> call, Throwable t) {
                Log.e("diego", "teste");
            }
        });
    }

    public void salvarUsuario(final iAsyncObj delegate, String email) {
        CotacaoService cotacaoService = RestClient.createService(CotacaoService.class, "admin", "123");
        EmailEnvio emailEnvio = new EmailEnvio();
        emailEnvio.setEmail(email);
        Call<Email> call = cotacaoService.saveUser("application/json", apiKey, emailEnvio);
        call.enqueue(new Callback<Email>() {
            @Override
            public void onResponse(Call<Email> call, Response<Email> response) {
                Log.e("diego", "teste");
                Email email = response.body();
                delegate.processoEncerrado(email);
            }

            @Override
            public void onFailure(Call<Email> call, Throwable t) {
                Log.e("diego", "teste");
            }

        });
    }
}
