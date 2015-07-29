package br.com.williamsilva.economizze.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.williamsilva.economizze.DAO.DBCRUD;
import br.com.williamsilva.economizze.DAO.DespesaDAO;
import br.com.williamsilva.economizze.R;
import br.com.williamsilva.economizze.controller.helpers.RelogioHelper;
import br.com.williamsilva.economizze.controller.helpers.ValidatorHelper;
import br.com.williamsilva.economizze.exception.ValidarDadosException;
import br.com.williamsilva.economizze.model.Despesa;
import br.com.williamsilva.economizze.view.DespesaActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by william on 23/11/14.
 */
public class DespesaController {

    private Activity activity;
    private Context contexto;
    private Despesa despesa;

    @InjectView(R.id.valorDespesa)
    protected EditText eValor;
    @InjectView(R.id.nomeDespesa)
    protected EditText eNome;
    @InjectView(R.id.dataDespesa)
    protected TextView tDataDespesa;
    @InjectView(R.id.despesaFixa)
    protected CheckBox cDespesaFixa;
    @InjectView(R.id.statusDespesa)
    protected RadioGroup rStatus;

    public DespesaController(DespesaActivity activity) {
        this.activity = activity;
        this.contexto = activity;
        ButterKnife.inject(DespesaController.this, this.activity);
    }

    public DespesaController(Context context) {
        this.contexto = context;
    }

    /*
    validando os campos da activity
     */
    public Boolean validar() {

        if (ValidatorHelper.getInstance().validateNotNull
                (eNome, activity.getString(R.string.erro_nome_nao_digitado)).equals(false) ||
                ValidatorHelper.getInstance().validateNotNull
                        (eValor, activity.getString(R.string.erro_valor_nao_digitado)).equals(false)) {
            return false;
        } else if (ValidatorHelper.getInstance().validateMaxLength
                (eNome, 20, activity.getString(R.string.valor_max20_string)).equals(false)) {
            return false;
        }

        return true;
    }

    public void create(){

        int despesaFixa = 0, statusDespesa = 0;

        if (cDespesaFixa.isChecked())
            despesaFixa = 1;
        if (rStatus.getCheckedRadioButtonId() == R.id.despesaPaga)
            statusDespesa = 1;

        this.despesa =  new Despesa(null, DBCRUD.gerarCodigoVeririficador(contexto, "DESPESA"), eNome.getText().toString(), Double.parseDouble(eValor.getText().toString()),
                tDataDespesa.getText().toString(), statusDespesa, despesaFixa);
    }


    /*
    retorna uma despesa puxando os dados do formulario da activity
     */
    public Despesa getDespesa() {

       return this.despesa;
    }

    public void setDespesa(Despesa despesa) {

        this.despesa = despesa;
    }

    /*
    Método que fixa a despesa do mes anterior no mes atual
     */
    public static void fixarDespesas(Context contexto) {

        DespesaDAO dao = new DespesaDAO(contexto);
        List<Despesa> despesas = dao.buscarDespesas(null, null);
        RelogioHelper dataHoje = new RelogioHelper(RelogioHelper.dataHoje());
        RelogioHelper dataBanco = null;

        for (Despesa despesa : despesas) {

            dataBanco = new RelogioHelper(despesa.getVencimento());


            if (!dataHoje.validarMesAno(dataBanco)) {
                int rs = dataHoje.getMes() - dataBanco.getMes(),
                        esseAno = dataHoje.getAno(), anoBanco = dataBanco.getAno();

                if (rs == 1 && esseAno == anoBanco && despesa.getDespesaFixa().equals(1)) {

                    if (verificaDespesaDuplicada(despesa.getCodVerificador(), contexto)) {
                        dataBanco.setMes(dataHoje.getMes());
                        despesa.setVencimento(dataBanco.toString());
                        despesa.setStatus(0);
                        dao.setDespesa(despesa);
                        dao.cadastrarDespesa();
                    }
                } else if (rs < 0 && esseAno > anoBanco && despesa.getDespesaFixa().equals(1)) {

                    if (verificaDespesaDuplicada(despesa.getCodVerificador(), contexto)) {
                        dataBanco.setMes(1);
                        dataBanco.setAno(dataHoje.getAno());
                        despesa.setVencimento(dataBanco.toString());
                        despesa.setStatus(0);
                        dao.setDespesa(despesa);
                        dao.cadastrarDespesa();
                    }
                }
            }

        }
    }

    /*
    Verifica se já foi adicionada a despesa no mes atual, se existir ele retorna falso dizendo
    que o mesmo ja existe e não sera necessario adicionar a despesa no mes atual.
     */
    private static Boolean verificaDespesaDuplicada(Long codVerificador, Context contexto) {

        Boolean rs = true;
        DespesaDAO dao = new DespesaDAO(contexto);

        List<Despesa> despesas = dao.buscarDespesas("codVerificador = ?", new String[]{codVerificador.toString()});

        if (despesas.size() > 0) {
            for (Despesa despesa : despesas) {

                if (new RelogioHelper(RelogioHelper.dataHoje())
                        .validarMesAno(new RelogioHelper(despesa.getVencimento()))) {
                    rs = false;
                }
            }
        }

        return rs;
    }

    public boolean verificaNomeDespesaDuplicada(String nome, Long id) {

        DespesaDAO dao = new DespesaDAO(contexto);
        List<Despesa> despesas = dao.buscarDespesas("nome = ?", new String[]{nome});

        if (despesas.size() <= 0) {
            return true;
        }

        if (id == null || id <= 0) {
            for (Despesa despesa : despesas) {
                if (despesa.getNome().equals(nome) &&
                        new RelogioHelper(RelogioHelper.dataHoje()).validarMesAno(new RelogioHelper(despesa.getVencimento()))) {
                    throw new ValidarDadosException(activity.getString(R.string.nome_existente));
                }
            }
        } else {
            for (Despesa despesa : despesas) {
                if (despesa.getNome().equals(nome) && !despesa.getId().equals(id) &&
                        new RelogioHelper(RelogioHelper.dataHoje()).validarMesAno(new RelogioHelper(despesa.getVencimento()))) {
                    throw new ValidarDadosException(activity.getString(R.string.nome_existente));
                }
            }
        }
        return true;
    }

    public Despesa getDespesaSerializada() {

        Intent intent = activity.getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                return (Despesa) bundle.get("DESPESA");
            }
        }
        return  null;
    }

    public void salvarDespesa(){

        if (this.verificaNomeDespesaDuplicada(despesa.getNome(), null)) {
            new DespesaDAO(despesa, contexto).cadastrarDespesa();
        }

    }

    public void alterarDespesa(Long id){

        despesa.setId(id);
        if (this.verificaNomeDespesaDuplicada(despesa.getNome(), despesa.getId())) {
            new DespesaDAO(despesa, activity).alterarDespesa();
        }
    }

    public List<Despesa> getDespesasVencidas() {

        DespesaDAO dao = new DespesaDAO(null,contexto);
        List<Despesa> despesas = dao.buscarDespesas(null,null);
        List<Despesa> despesasVencidas = new ArrayList<>();

        for(Despesa despesa : despesas){

            if(!new RelogioHelper(despesa.getVencimento()).validarVenciamento() && despesa.getStatus().equals(0)){
                despesasVencidas.add(despesa);
            }
        }
        return despesasVencidas;
    }
}
