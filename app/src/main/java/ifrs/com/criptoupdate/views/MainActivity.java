package ifrs.com.criptoupdate.views;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import ifrs.com.criptoupdate.R;
import ifrs.com.criptoupdate.helpers.CotacaoHelper;
import ifrs.com.criptoupdate.helpers.MainActivityHelper;
import ifrs.com.criptoupdate.model.Cotacao;
import ifrs.com.criptoupdate.model.interfaces.iAsyncObj;
import ifrs.com.criptoupdate.util.Notificacao;

public class MainActivity extends AppCompatActivity implements iAsyncObj {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new CotacaoHelper().buscaCtc(this);
        new MainActivityHelper().verificaService(this);
    }

    @Override
    public void processoEncerrado(Object obj) {
        if (obj instanceof Cotacao) {
            Cotacao cot = (Cotacao) obj;
            new Notificacao().exibeCotacao(this, cot);
        }
    }
}
