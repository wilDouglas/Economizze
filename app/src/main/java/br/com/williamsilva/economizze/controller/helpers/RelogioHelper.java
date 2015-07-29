package br.com.williamsilva.economizze.controller.helpers;

import android.util.Log;

import java.util.Calendar;

import br.com.williamsilva.economizze.R;

/**
 * Created by William on 07/12/2014.
 */
public class RelogioHelper {

    private Integer dia,mes,ano;
    private static RelogioHelper relogioHelper;

    //intancia o calendario da classe nativa Calendar
    private Calendar calendario = Calendar.getInstance();



    public RelogioHelper(String data) {

        //Divide as datas em dia, mes e ano
        String[] arrayData = data.split("/");

        try {
            dia = Integer.parseInt(arrayData[0]);
            mes = Integer.parseInt(arrayData[1]);
            ano = Integer.parseInt(arrayData[2]);
        }catch (NumberFormatException e){
            Log.i("RelogioHelper",e.getMessage());
            e.printStackTrace();
        }


		/* seto o dia, mes e ano, e no caso do mes ultilizo o mes-1 pois a classe
		 * calendar conta os meses a partir do 0. Então segue abaixo um exemplo.
		 *
		 * 0 = Janeiro;
		 * 1 = Fevereiro;
		 * 2 = Março;
		 * 3 = Abril;
		 * 4 = Maio
		 * 5 = Junho;
		 * 6 = Julho;
		 * 7 = Agosto;
		 * 8 = Setembro;
		 * 9 = Outubro;
		 * 10 = Novembro;
		 * 11 = Dezembro;
		 */
        calendario.set(Calendar.YEAR, ano);
        calendario.set(Calendar.MONTH, mes-1);
        calendario.set(Calendar.DAY_OF_MONTH, dia);

    }
    public Integer getDia() {
        return dia;
    }
    public void setDia(int dia) {
        this.dia = dia;
    }
    public Integer getMes() {
        return mes;
    }
    public void setMes(int mes) {
        this.mes = mes;
    }
    public Integer getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }
    public Calendar getCalendario() {
        return calendario;
    }
    public void setCalendario(Calendar calendario) {
        this.calendario = calendario;
    }

    public static String dataHoje()
    {
        Integer dia,mes,ano;
        Calendar calendario = Calendar.getInstance();
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH);
        ano = calendario.get(Calendar.YEAR);
        return dia + "/" + (mes+1) + "/" + ano;
    }

    public static String horarioDeAgora()
    {
        Integer hora,minuto,segundo;
        Calendar calendario = Calendar.getInstance();

        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minuto = calendario.get(Calendar.MINUTE);
        segundo = calendario.get(Calendar.SECOND);
        return hora + ":" + minuto + ":" + segundo;
    }

    public String toString()
    {
        return dia + "/" + mes + "/" + ano;
    }

    public Integer returnMesName() {



        switch (this.mes) {
            case 1:
                return R.string.janeiro;
            case 2:
                return R.string.fevereiro;
            case 3:
                return R.string.março;
            case 4:
                return R.string.abril;
            case 5:
                return R.string.maio;
            case 6:
                return R.string.junho;
            case 7:
                return R.string.julho;
            case 8:
                return R.string.agosto;
            case 9:
                return R.string.setembro;
            case 10:
                return R.string.outubro;
            case 11:
                return R.string.novembro;
            case 12:
                return R.string.dezembro;
            default:
                return null;
        }
    }


    public Boolean validarMesAno(RelogioHelper dataDoMesSelecionado){

        if(dataDoMesSelecionado.getMes().equals(this.mes) &&
                dataDoMesSelecionado.getAno().equals(this.ano)){
            return true;
        }

        return  false;
    }

    public Boolean validarVenciamento(){
        RelogioHelper relogio = new RelogioHelper(RelogioHelper.dataHoje());

        if(calendario.before(relogio.getCalendario()))
        {
            return false;
        }

        return true;
    }

}

