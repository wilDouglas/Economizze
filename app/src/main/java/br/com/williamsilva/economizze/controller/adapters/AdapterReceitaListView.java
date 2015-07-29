package br.com.williamsilva.economizze.controller.adapters;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import br.com.williamsilva.economizze.R;
import br.com.williamsilva.economizze.controller.helpers.MoneyHelper;
import br.com.williamsilva.economizze.model.Receita;

/**
 * Created by William on 07/12/2014.
 */
public class AdapterReceitaListView extends BaseAdapter {

    private List<Receita> receitas;
    private Activity activity;

    public AdapterReceitaListView(List<Receita> receitas, Activity activity) {
        this.receitas = receitas;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return receitas.size();
    }

    @Override
    public Object getItem(int position) {
        return receitas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return receitas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Receita receita = receitas.get(position);
        LayoutInflater inflater = activity.getLayoutInflater();
        View linha = inflater.inflate(R.layout.item_list,null);

        TextView nome = (TextView) linha.findViewById(R.id.name_list);
        nome.setText(receita.getNome());

        TextView valor = (TextView) linha.findViewById(R.id.valor_list);
        valor.setText(MoneyHelper.getInstance().converterValorEmReal(receita.getValor()));

        TextView data = (TextView) linha.findViewById(R.id.data_list);
        data.setText(receita.getData());


        return linha;
    }
}
