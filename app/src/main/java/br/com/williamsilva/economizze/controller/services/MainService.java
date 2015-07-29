package br.com.williamsilva.economizze.controller.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import br.com.williamsilva.economizze.R;
import br.com.williamsilva.economizze.controller.DespesaController;
import br.com.williamsilva.economizze.controller.ReceitaController;
import br.com.williamsilva.economizze.controller.helpers.RelogioHelper;
import br.com.williamsilva.economizze.model.Financas;
import br.com.williamsilva.economizze.view.DespesaVencidaActivity;
import br.com.williamsilva.economizze.view.MainActivity;

/**
 * Created by william on 04/02/15.
 */
public class MainService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Worker worker = new Worker();
        worker.start();
        return super.onStartCommand(intent, flags, startId);

    }

    public void notificarDespesasVencidas(Context context){
        Financas fn = new Financas(context,new RelogioHelper(RelogioHelper.dataHoje()));

        if(fn.getDespesasVencidas() <= 0) return;

        try{
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(context,som);
            ringtone.play();
        }catch (Exception e){

        }

        NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(context,0,new Intent(context, DespesaVencidaActivity.class),0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker("Despesas em atraso");
        builder.setContentTitle("Despesas em atraso");
        builder.setContentText("Você possui despesas em atraso!");
        builder.setSmallIcon(R.drawable.ic_despesa);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_despesa));
        builder.setContentIntent(p);

        Notification n = builder.build();
        n.vibrate = new long[]{150,300,150,600};
        //n.flags = Notification.FLAG_AUTO_CANCEL; auto cancela a notificacao ao usuario clicar no mesmo!
        nm.notify(R.drawable.ic_despesa,n);

    }

    class Worker extends Thread{

        @Override
        public void run() {
            Log.i("MainService","Execução em background efetuada com sucesso!");
            DespesaController.fixarDespesas(getApplicationContext());
            ReceitaController.fixarReceita(getApplicationContext());
            notificarDespesasVencidas(getApplicationContext());
        }
    }



}
