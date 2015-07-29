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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import br.com.williamsilva.economizze.DAO.DespesaDAO;
import br.com.williamsilva.economizze.R;
import br.com.williamsilva.economizze.controller.DespesaController;
import br.com.williamsilva.economizze.exception.ValidarDadosException;
import br.com.williamsilva.economizze.model.Despesa;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class DespesaActivity extends ActionBarActivity {

    private Integer ano,mes,dia;
    private Despesa despesa;

    @InjectView(R.id.dataDespesa)
    protected TextView tDataDespesa;
    @InjectView(R.id.salvarDespesa)
    protected Button bSalvar;
    @InjectView(R.id.valorDespesa)
    protected EditText eValor;
    @InjectView(R.id.nomeDespesa)
    protected  EditText eNome;
    @InjectView(R.id.despesaPaga)
    protected RadioButton rPago;
    @InjectView(R.id.despesaNaoPaga)
    protected RadioButton rNaoPago;
    @InjectView(R.id.despesaFixa)
    protected CheckBox cDespesaFixa;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa);
        ButterKnife.inject(DespesaActivity.this);

        this.customizarActionBar();
        this.inicializarCalendario();

        //para nao abrir o teclado ao usuario acessar a activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        DespesaController controller = new DespesaController(this);
        despesa = controller.getDespesaSerializada();
        if(despesa != null){
            this.setarElementosView();
        }

    }

    private void setarElementosView() {

        bSalvar.setText(getString(R.string.alterar));
        eValor.setText(despesa.getValor().toString());
        eNome.setText(despesa.getNome());
        tDataDespesa.setText(despesa.getVencimento());

        if(despesa.getDespesaFixa() == 1)
            cDespesaFixa.setChecked(true);

        switch (despesa.getStatus()){
            case 0:
                rNaoPago.setChecked(true);
                break;
            case 1:
                rPago.setChecked(true);
                break;
        }

    }

    public void salvarDespesa(View view){

        try {
            DespesaController controller = new DespesaController(this);

            if(controller.validar()) {
                if (getIntent().getExtras() == null) {
                    controller.create();
                    controller.salvarDespesa();
                    Toast.makeText(this,getString(R.string.salvo_com_sucesso),Toast.LENGTH_LONG).show();
                    finish();

                } else {
                    controller.create();
                    controller.alterarDespesa(despesa.getId());
                    Toast.makeText(this, getString(R.string.alterado_com_sucesso), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }catch (ValidarDadosException e){
            Crouton.makeText(this, e.getMessage(), Style.ALERT).show();
        }
        catch (Exception e){
            Log.i("ERRO DESPESA ACTIVITY", e.getMessage());
        }
    }

    @OnClick(R.id.dataDespesa)
    public void selecionarData(View view) {
        showDialog(view.getId());
    }

    protected Dialog onCreateDialog(int id) {
        if (R.id.dataDespesa == id) {
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
            tDataDespesa.setText(dia + "/" + (mes + 1) + "/" + ano);

        }
    };

    private void customizarActionBar(){

         /* customizando a action bar, alterando a cor do mesmo */
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.vermelho_claro)));

         /*colocando o actionbar centralizado e setando seu titulo */
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        View v  = getSupportActionBar().getCustomView();
        TextView txt = (TextView) v.findViewById(R.id.mytext);

        if(getIntent().getExtras() == null)
            txt.setText(getString(R.string.adicionar_despesa));
        else
            txt.setText(getString(R.string.alterar_despesa));
    }

    private void inicializarCalendario()
    {
        // tratando o campo data
        Calendar calendario = Calendar.getInstance();
        ano = calendario.get(Calendar.YEAR);
        mes = calendario.get(Calendar.MONTH);
        dia = calendario.get(Calendar.DAY_OF_MONTH);

        tDataDespesa.setText(dia + "/" + (mes + 1) + "/" + ano);
        // fim do tratamento
    }
}
