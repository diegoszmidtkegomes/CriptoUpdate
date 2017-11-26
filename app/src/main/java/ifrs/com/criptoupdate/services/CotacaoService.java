package ifrs.com.criptoupdate.services;

import ifrs.com.criptoupdate.model.EmailEnvio;
import ifrs.com.criptoupdate.model.response.Cotacao;
import ifrs.com.criptoupdate.model.response.Email;
import ifrs.com.criptoupdate.model.response.EmailEnviado;
import ifrs.com.criptoupdate.model.response.Variacao;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by diego.gomes on 03/02/2017.
 */

public interface CotacaoService {

    @GET("moeda/btc")
    Call<Cotacao> buscarBtc(@Header("Content-Type") String content_type, @Header("x-api-key") String apiKey);

    @GET("moeda/ltc")
    Call<Cotacao> buscarLtc(@Header("Content-Type") String content_type, @Header("x-api-key") String apiKey);

    @GET("moeda/bch")
    Call<Cotacao> buscarBch(@Header("Content-Type") String content_type, @Header("x-api-key") String apiKey);

    @POST("saveUser")
    Call<Email> saveUser(@Header("Content-Type") String content_type, @Header("x-api-key") String apiKey, @Body EmailEnvio email);

    @POST("send-email/btc")
    Call<EmailEnviado> sendEmailBtc(@Header("Content-Type") String content_type,
                                    @Header("x-api-key") String apiKey,
                                    @Header("x-api-token") String apiToken,
                                    @Body Variacao variacao);

    @POST("send-email/ltc")
    Call<EmailEnviado> sendEmailLtc(@Header("Content-Type") String content_type,
                                    @Header("x-api-key") String apiKey,
                                    @Header("x-api-token") String apiToken,
                                    @Body Variacao variacao);

    @POST("send-email/bch")
    Call<EmailEnviado> sendEmailBch(@Header("Content-Type") String content_type,
                                    @Header("x-api-key") String apiKey,
                                    @Header("x-api-token") String apiToken,
                                    @Body Variacao variacao);

}
