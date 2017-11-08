package ifrs.com.criptoupdate.services;

import ifrs.com.criptoupdate.model.response.Cotacao;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by diego.gomes on 03/02/2017.
 */

public interface CotacaoService {

    @GET("btc")
    Call<Cotacao> buscarBtc(@Header("Content-Type") String content_type);

    @GET("ltc")
    Call<Cotacao> buscarLtc(@Header("Content-Type") String content_type);

    @GET("bch")
    Call<Cotacao> buscarBch(@Header("Content-Type") String content_type);

}
