package br.com.williamsilva.economizze.view;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.williamsilva.economizze.R;
import br.com.williamsilva.economizze.controller.DespesaController;
import br.com.williamsilva.economizze.controller.adapters.AdapterDespesaListView;
import br.com.williamsilva.economizze.model.Despesa;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

public class DespesaVencidaActivity extends ActionBarActivity {

    @InjectView(R.id.lista_despesa_vencida)
    protected ListView lista;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa_vencida);
        ButterKnife.inject(this);
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(R.drawable.ic_despesa);
        this.customizarActionBar();
    }

    @OnItemClick(R.id.lista_despesa_vencida)
    public void efetuarPagamento(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.efetuar_pagamento);
        builder.setMessage(R.string.efetou_pagamento);
        builder.setPositiveButton(R.string.sim,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              Despesa despesa = (Despesa) lista.getItemAtPosition(position);
              despesa.setStatus(1);
              DespesaController controller = new DespesaController(DespesaVencidaActivity.this);
              controller.setDespesa(despesa);
              controller.alterarDespesa(despesa.getId());
              carregarLista();
            }
        });
        builder.setNegativeButton(R.string.nao,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_despesa_vencida, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    @Override
    protected void onResume() {
        super.onResume();
        this.carregarLista();
    }

    private void carregarLista() {
        List<Despesa> despesas = new DespesaController(this).getDespesasVencidas();
        AdapterDespesaListView adapter = new AdapterDespesaListView(despesas,this);
        lista.setAdapter(adapter);
    }

    private void customizarActionBar(){

         /* customizando a action bar, alterando a cor do mesmo */
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.vermelho_escuro)));

         /*colocando o actionbar centralizado e setando seu titulo */
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        View v  = getSupportActionBar().getCustomView();
        TextView txt = (TextView) v.findViewById(R.id.mytext);
        txt.setText(getString(R.string.despesas_em_atraso));
    }
}
