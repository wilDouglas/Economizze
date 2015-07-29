package br.com.williamsilva.economizze.controller.broadcastReceivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import br.com.williamsilva.economizze.R;
import br.com.williamsilva.economizze.controller.DespesaController;
import br.com.williamsilva.economizze.controller.ReceitaController;
import br.com.williamsilva.economizze.controller.helpers.AlarmManagerHelper;

/**
 * Created by william on 24/01/15.
 */
public class MainReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("MainReceiver","Execução em background efetuada com sucesso!");
        new Worker(context).start();
    }

    class Worker extends Thread{
        private Context context;

        public Worker(Context context){
            this.context = context;
        }

        @Override
        public void run() {
            super.run();
            AlarmManagerHelper.getInstance().gerarAlarmeMainService(context);
        }
    }
}
