package ifrs.com.criptoupdate.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by diego on 08/01/2017.
 */
public class ConexaoInternet {

    private Context context;

    /**
     * Instantiates a new Conexao internet.
     *
     * @param context the context
     */
    public ConexaoInternet(Context context) {
        this.context = context;
    }

    /**
     * Retorna se o app está conectado na internet
     *
     * @return boolean boolean
     */
    public boolean possuiConexao() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
