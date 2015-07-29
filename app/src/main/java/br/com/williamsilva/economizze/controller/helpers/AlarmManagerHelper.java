package br.com.williamsilva.economizze.controller.helpers;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by william on 23/02/15.
 */
public class AlarmManagerHelper {

    private static AlarmManagerHelper alarmManagerHelper;

    private AlarmManagerHelper(){}

    public synchronized static AlarmManagerHelper getInstance(){

        if(alarmManagerHelper == null){
                alarmManagerHelper = new AlarmManagerHelper();
        }
        return alarmManagerHelper;
    }

    public void gerarAlarmeMainService(Context context){

        if(PendingIntent.getService(context, 0, new Intent("MAIN_SERVICE"), PendingIntent.FLAG_NO_CREATE) == null) {

            Intent intent = new Intent("MAIN_SERVICE");
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
            Log.i("ALARME", "Novo Alarme Criado");
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            c.add(Calendar.SECOND, 3);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        else{
            Log.i("ALARME","Alarme já ativo");
        }

    }
}
