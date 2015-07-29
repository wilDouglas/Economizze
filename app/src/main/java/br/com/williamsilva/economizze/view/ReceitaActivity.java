package br.com.williamsilva.economizze.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import br.com.williamsilva.economizze.DAO.ReceitaDAO;
import br.com.williamsilva.economizze.R;
import br.com.williamsilva.economizze.controller.ReceitaController;
import br.com.williamsilva.economizze.exception.ValidarDadosException;
import br.com.williamsilva.economizze.model.Receita;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class ReceitaActivity extends ActionBarActivity {

    private Integer ano, mes, dia;
    private Receita receita;

    @InjectView(R.id.dataReceita)
    protected TextView bDataReceita;
    @InjectView(R.id.salvarReceita)
    protected Button bSalvar;
    @InjectView(R.id.nomeReceita)
    protected EditText eNome;
    @InjectView(R.id.valorReceita)
    protected EditText eValor;
    @InjectView(R.id.receitaFixa)
    protected CheckBox cReceitaFixa;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita);
        ButterKnife.inject(ReceitaActivity.this);

        //para nao abrir o teclado ao usuario acessar a activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.customizarActionBar();
        this.inicializarCalendario();


        ReceitaController controller = new ReceitaController(this);
        receita = controller.getReceitaSerializado();
            if(receita != null){
                setarElementosView();
            }

    }


    private void setarElementosView() {
        bSalvar.setText(getString(R.string.alterar));
        eNome.setText(receita.getNome());
        eValor.setText(receita.getValor().toString());
        bDataReceita.setText(receita.getData());

        if(receita.getReceitaFixa() == 1)
            cReceitaFixa.setChecked(true);
    }

    public void salvarReceita(View view){

        try {
            ReceitaController controller = new ReceitaController(this);

            if (controller.validar()) {

                if (getIntent().getExtras() == null) {
                    controller.salvarReceita();
                    Toast.makeText(this, getString(R.string.salvo_com_sucesso), Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    controller.alterarReceita(receita.getId());
                    Toast.makeText(this, getString(R.string.alterado_com_sucesso), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }catch (ValidarDadosException e){
            Crouton.makeText(this,e.getMessage(), Style.ALERT).show();
            return;
        }catch (Exception e){
            Log.i("ERRO RECEITA ACTIVITY",e.getMessage());
        }
    }

    @OnClick(R.id.dataReceita)
    public void selecionarData(View view) {
        showDialog(view.getId());
    }

    protected Dialog onCreateDialog(int id) {
        if (R.id.dataReceita == id) {
            return new DatePickerDialog(this, listener, ano, mes, dia);
        }
        return null;
    }

    // quando a data selecionada e alterada atraves do click
    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            ano = year;
            mes = monthOfYear;
            dia = dayOfMonth;
            bDataReceita.setText(dia + "/" + (mes + 1) + "/" + ano);

        }
    };

    private void inicializarCalendario(){
        // tratando o campo data
        Calendar calendario = Calendar.getInstance();
        ano = calendario.get(Calendar.YEAR);
        mes = calendario.get(Calendar.MONTH);
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        bDataReceita.setText(dia + "/" + (mes + 1) + "/" + ano);
        // fim do tratamento
    }

    private void customizarActionBar(){
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.verde_escuro)));
          /*colocando o actionbar centralizado e setando seu titulo */
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        View v  = getSupportActionBar().getCustomView();
        TextView txt = (TextView) v.findViewById(R.id.mytext);

        if(getIntent().getExtras() == null)
            txt.setText(getString(R.string.adicionar_receita));
        else
            txt.setText(getString(R.string.alterar_receita));
    }
}
