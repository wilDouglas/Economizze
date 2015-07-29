package br.com.williamsilva.economizze.controller.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import br.com.williamsilva.economizze.R;
import br.com.williamsilva.economizze.controller.helpers.MoneyHelper;
import br.com.williamsilva.economizze.model.Despesa;

/**
 * Created by william on 05/12/14.
 */
public class AdapterDespesaListView extends BaseAdapter {

    private List<Despesa> despesas;
    private Activity activity;

    public AdapterDespesaListView(List<Despesa> despesas, Activity activity) {

        this.despesas = despesas;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return despesas.size();
    }

    @Override
    public Object getItem(int position) {
        return despesas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return despesas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Despesa despesa = despesas.get(position);
        LayoutInflater layoutInflater = activity.getLayoutInflater();

        View linha = layoutInflater.inflate(R.layout.item_list,null);

        TextView nome = (TextView) linha.findViewById(R.id.name_list);
        nome.setText(despesa.getNome());

        TextView valor = (TextView) linha.findViewById(R.id.valor_list);
        valor.setText(MoneyHelper.getInstance().converterValorEmReal(despesa.getValor()));

        TextView data = (TextView) linha.findViewById(R.id.data_list);
        data.setText(despesa.getVencimento());

        return linha;

    }
}
