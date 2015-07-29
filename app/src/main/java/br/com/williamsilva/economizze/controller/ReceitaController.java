package br.com.williamsilva.economizze.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import br.com.williamsilva.economizze.DAO.DBCRUD;
import br.com.williamsilva.economizze.DAO.ReceitaDAO;
import br.com.williamsilva.economizze.R;
import br.com.williamsilva.economizze.controller.helpers.RelogioHelper;
import br.com.williamsilva.economizze.controller.helpers.ValidatorHelper;
import br.com.williamsilva.economizze.exception.ValidarDadosException;
import br.com.williamsilva.economizze.model.Receita;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by William on 07/12/2014.
 */
public class ReceitaController {

    private Activity activity;
    private Context context;

    @InjectView(R.id.dataReceita)
    protected TextView bDataReceita;

    @InjectView(R.id.nomeReceita)
    protected EditText eNome;

    @InjectView(R.id.valorReceita)
    protected EditText eValor;

    @InjectView(R.id.receitaFixa)
    protected CheckBox cReceitaFixa;

    public ReceitaController(Activity activity) {
        this.activity = activity;
        ButterKnife.inject(this,activity);
    }

    public Boolean validar(){

        if(ValidatorHelper.getInstance().validateNotNull(eNome,activity.getString(R.string.erro_nome_nao_digitado)).equals(false) ||
                ValidatorHelper.getInstance().validateNotNull(eValor,activity.getString(R.string.erro_valor_nao_digitado)).equals(false))
            return false;
        else if(ValidatorHelper.getInstance().validateMaxLength(eNome,20,activity.getString(R.string.valor_max20_string)).equals(false))
            return false;
        else
            return true;
    }

    public Receita getReceita()
    {
        int receitaFixa = 0;

        if(cReceitaFixa.isChecked()) {
            receitaFixa = 1;
        }

        return new Receita(null,DBCRUD.gerarCodigoVeririficador(activity,"RECEITA"),eNome.getText().toString(),bDataReceita.getText().toString(),
                Double.parseDouble(eValor.getText().toString()),receitaFixa);
    }

    public static void fixarReceita(Context contexto){

        ReceitaDAO dao =  new ReceitaDAO(contexto);

        List<Receita> receitas = dao.buscarReceitas(null,null);
        RelogioHelper dataHoje = new RelogioHelper(RelogioHelper.dataHoje());
        RelogioHelper dataBanco = null;

        for(Receita receita : receitas) {
            dataBanco = new RelogioHelper(receita.getData());

            if (!dataHoje.validarMesAno(dataBanco)) {
                int rs = dataHoje.getMes() - dataBanco.getMes(),
                esseAno = dataHoje.getAno(), anoBanco = dataBanco.getAno();

                if( rs == 1 && esseAno == anoBanco && receita.getReceitaFixa().equals(1) ){

                    if(verificaReceitaDuplicada(receita.getCodVerificador().toString(),contexto)){
                        dataBanco.setMes(dataHoje.getMes());
                        receita.setData(dataBanco.toString());
                        dao.setReceita(receita);
                        dao.cadastrarReceita();
                    }

                }else if(rs < 0 && esseAno > anoBanco && receita.getReceitaFixa().equals(1)){

                    if(verificaReceitaDuplicada(receita.getCodVerificador().toString(), contexto)) {
                        dataBanco.setMes(1);
                        dataBanco.setAno(dataHoje.getAno());
                        receita.setData(dataBanco.toString());
                        dao.setReceita(receita);
                        dao.cadastrarReceita();
                    }
                }
            }
        }
    }

    private static Boolean verificaReceitaDuplicada(String codVerificador, Context contexto) {
        Boolean rs = true;
        ReceitaDAO dao = new ReceitaDAO(contexto);
        List<Receita> receitas = dao.buscarReceitas("codVerificador = ?",new String[] {codVerificador});

        if(receitas.size() > 0){
            for(Receita receita : receitas){
                if (new RelogioHelper(RelogioHelper.dataHoje())
                        .validarMesAno(new RelogioHelper(receita.getData()))) {
                    rs = false;
                }
            }
        }
        return rs;
    }

    public Boolean verificaNomeReceitaDuplicada(String nome, Long id){

        ReceitaDAO dao = new ReceitaDAO(activity);
        List<Receita> receitas  = dao.buscarReceitas("nome = ?",new String[] {nome});

        if(receitas.size() <= 0) {
            return true;
        }

            if(id == null || id <= 0) {
                for (Receita receita : receitas) {
                    if (receita.getNome().equals(nome) &&
                            new RelogioHelper(RelogioHelper.dataHoje()).validarMesAno(new RelogioHelper(receita.getData()))) {
                        throw new ValidarDadosException(activity.getString(R.string.nome_existente));
                    }
                }
            }
            else{
                for(Receita receita : receitas){
                    if(receita.getNome().equals(nome) && !receita.getId().equals(id) &&
                            new RelogioHelper(RelogioHelper.dataHoje()).validarMesAno(new RelogioHelper(receita.getData()))){
                        throw new ValidarDadosException(activity.getString(R.string.nome_existente));
                    }
                }
            }

        return true;
    }

    public void salvarReceita(){

        Receita receita = this.getReceita();

        if(this.verificaNomeReceitaDuplicada(receita.getNome(), null)) {
            new ReceitaDAO(activity, receita).cadastrarReceita();
        }

    }

    public void alterarReceita(Long id){

        Receita receita = getReceita();
        receita.setId(id);
        if(this.verificaNomeReceitaDuplicada(receita.getNome(), receita.getId())) {
            new ReceitaDAO(activity, receita).alterarReceita();
        }

    }

    public Receita getReceitaSerializado(){

        Intent intent = activity.getIntent();
        if(intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                return (Receita) bundle.get("RECEITA");
            }
        }
        return null;
    }
}
