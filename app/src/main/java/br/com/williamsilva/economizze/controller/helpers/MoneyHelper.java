package br.com.williamsilva.economizze.controller.helpers;

import java.text.DecimalFormat;

/**
 * Created by william on 03/02/15.
 */
public class MoneyHelper {

    private DecimalFormat df = new DecimalFormat("#0.00");
    private static MoneyHelper moneyHelper;

    private MoneyHelper(){}
    public synchronized static MoneyHelper getInstance(){

        if(moneyHelper == null) {
            moneyHelper = new MoneyHelper();
        }
        return moneyHelper;
    }
    public String converterValorEmReal(Double valor){
        return "R$ " + df.format(valor);
    }
}
