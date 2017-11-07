package ifrs.com.criptoupdate.services;

import ifrs.com.criptoupdate.model.Cotacao;
import ifrs.com.criptoupdate.model.Ticker;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by diego.gomes on 03/02/2017.
 */

public interface CotacaoService {

    @GET("btc")
    Call<Cotacao> buscarBtc(@Header("Content-Type") String content_type);

}
