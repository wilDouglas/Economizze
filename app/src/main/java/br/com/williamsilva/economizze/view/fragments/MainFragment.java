package br.com.williamsilva.economizze.view.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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
import br.com.williamsilva.economizze.view.DespesaVencidaActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by William on 19/11/2014.
 */
public class MainFragment extends Fragment {

    @InjectView(R.id.main_fragment_valor_atual)
    protected TextView tValoratual;

    @InjectView(R.id.main_fragment_despesa_vencida)
    protected TextView tDespesaVencida;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(MainFragment.this,view);
        return view;
    }

    @OnClick(R.id.card_view002)
    public void abrirDespesaVencida(View v){
        startActivity(new Intent(view.getContext(), DespesaVencidaActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        Financas fn = new Financas(view.getContext(), new RelogioHelper(RelogioHelper.dataHoje()));
        tValoratual.setText(MoneyHelper.getInstance().converterValorEmReal(fn.getSaldoAtual()));
        tDespesaVencida.setText(MoneyHelper.getInstance().converterValorEmReal(fn.getDespesasVencidas()));
    }
}
