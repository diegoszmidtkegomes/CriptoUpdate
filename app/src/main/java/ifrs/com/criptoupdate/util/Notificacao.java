package ifrs.com.criptoupdate.util;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import ifrs.com.criptoupdate.R;
import ifrs.com.criptoupdate.model.Moeda;
import ifrs.com.criptoupdate.model.response.Cotacao;
import ifrs.com.criptoupdate.views.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by diego on 07/11/2017.
 */

public class Notificacao {

    public void exibeCotacao(Activity act, Cotacao cot ){
        NotificationManager notificationManager = (NotificationManager)
                act.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(act);
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        Intent intent = new Intent(act , MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(act, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(act.getResources(), R.mipmap.ic_launcher));

        if(cot.getMoeda().equals(Moeda.BITCOIN)){
            builder.setContentTitle("BTC: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getSell())));
            builder.setContentText("Maior: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getHigh())) + " | Menor: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getLow())));
            builder.setSubText("Vol: " + cot.getTicker().getVol() + " | Último: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getLast())));
            notificationManager.notify(90, builder.build());
        }
        else if(cot.getMoeda().equals(Moeda.LITECOIN)){
            builder.setContentTitle("LTC: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getSell())));
            builder.setContentText("Maior: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getHigh())) + " | Menor: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getLow())));
            builder.setSubText("Vol: " + cot.getTicker().getVol() + " | Último: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getLast())));
            notificationManager.notify(91, builder.build());
        }
        else if(cot.getMoeda().equals(Moeda.BCASH)){
            builder.setContentTitle("BCASH: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getSell())));
            builder.setContentText("Maior: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getHigh())) + " | Menor: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getLow())));
            builder.setSubText("Vol: " + cot.getTicker().getVol() + " | Último: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getLast())));
            notificationManager.notify(92, builder.build());
        }
    }

    public void atualizaCotacao(Context context, Cotacao cot){
        NotificationManager mNotificationManager = (NotificationManager)  context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        Intent intent = new Intent(context , MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        builder.setContentIntent(pendingIntent);

        if(cot.getMoeda().equals(Moeda.BITCOIN)){
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
            builder.setContentTitle("BTC: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getSell())));
            builder.setContentText("Maior: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getHigh())) + " | Menor: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getLow())));
            builder.setSubText("Vol: " + cot.getTicker().getVol() + " | Último: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getLast())));
            mNotificationManager.notify(90, builder.build());
        }
        else if(cot.getMoeda().equals(Moeda.LITECOIN)){
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
            builder.setContentTitle("LTC: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getSell())));
            builder.setContentText("Maior: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getHigh())) + " | Menor: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getLow())));
            builder.setSubText("Vol: " + cot.getTicker().getVol() + " | Último: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getLast())));
            mNotificationManager.notify(91, builder.build());
        }
         else if(cot.getMoeda().equals(Moeda.BCASH)){
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
            builder.setContentTitle("BCASH: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getSell())));
            builder.setContentText("Maior: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getHigh())) + " | Menor: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getLow())));
            builder.setSubText("Vol: " + cot.getTicker().getVol() + " | Último: " + String.format("%.2f", Double.parseDouble(cot.getTicker().getLast())));
            mNotificationManager.notify(92, builder.build());
        }
    }
}
