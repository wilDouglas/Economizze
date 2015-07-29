package br.com.williamsilva.economizze.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

import br.com.williamsilva.economizze.R;
import br.com.williamsilva.economizze.controller.helpers.CustomViewHelper;
import br.com.williamsilva.economizze.controller.helpers.MoneyHelper;
import br.com.williamsilva.economizze.controller.helpers.RelogioHelper;
import br.com.williamsilva.economizze.model.Financas;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by William on 21/11/2014.
 */
public class FinancasFragments extends Fragment {

    private int dia = 0,mes = 0,ano = 0;
    private View view;

    @InjectView(R.id.historicoFinancas)
    TextView historicoFinancas;

    @InjectView(R.id.saldoTotal)
    TextView saldoTotal;

    @InjectView(R.id.totalDespesa)
    TextView totalDespesa;

    @InjectView(R.id.despesasPagas)
    TextView despesasPagas;

    @InjectView(R.id.despesaAPagar)
    TextView despesaAPagar;

    @InjectView(R.id.saldoAtual)
    TextView saldoAtual;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_financas, container, false);

        Calendar calendario = Calendar.getInstance();
        dia = calendario.DAY_OF_MONTH;
        mes = (calendario.get(calendario.MONTH) +1);
        ano = calendario.get(calendario.YEAR);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ButterKnife.inject(FinancasFragments.this,view);
       setFinancas(dia + "/" + mes + "/" + ano);

    }

    @OnClick(R.id.voltarMes)
    protected void voltarMes(TextView tx){
        setFinancas(gerenciarDatasMin());

    }

    @OnClick(R.id.avancarMes)
    protected void  avancarMes(TextView tx){
        setFinancas(gerenciarDatasMax());
    }



    public void setFinancas(String data) {
        RelogioHelper relogio = new RelogioHelper(data);
        Financas financas = new Financas(view.getContext(), relogio);


        historicoFinancas.setText(getString(relogio.returnMesName()) + " de " + relogio.getAno());

        saldoTotal.setText(MoneyHelper.getInstance().converterValorEmReal(financas.getSaldoTotal()));
        totalDespesa.setText(MoneyHelper.getInstance().converterValorEmReal(financas.getTotalDespesas()));
        saldoAtual.setText(MoneyHelper.getInstance().converterValorEmReal(financas.getSaldoAtual()));
        despesaAPagar.setText(MoneyHelper.getInstance().converterValorEmReal(financas.getDespesasNaoPagas()));
        despesasPagas.setText(MoneyHelper.getInstance().converterValorEmReal(financas.getDespesasPagas()));

        CustomViewHelper customizar = new CustomViewHelper(view.getContext());
        customizar.customizarTextViewValorNegativo(financas.getSaldoAtual(), saldoAtual);

    }

    private String gerenciarDatasMax(){

        if(mes <= 11) {
            mes++;
        }
        else {
            ano++;
            mes = 1;
        }

        return "1/" + mes + "/"+ ano;
    }

    private String gerenciarDatasMin(){

        if(mes == 1)
        {
            mes = 12;
            ano--;
        }
        else
        {
            mes--;
        }
        return "1/" + mes + "/"+ ano;
    }
}
