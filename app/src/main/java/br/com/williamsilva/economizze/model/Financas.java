package br.com.williamsilva.economizze.model;

import android.content.Context;

import java.util.List;

import br.com.williamsilva.economizze.DAO.DespesaDAO;
import br.com.williamsilva.economizze.DAO.ReceitaDAO;
import br.com.williamsilva.economizze.controller.helpers.RelogioHelper;

/**
 * Created by William on 12/12/2014.
 */
public class Financas {

    private Context context;
    private RelogioHelper relogio;

    private Double saldoTotal;
    private Double saldoAtual;
    private Double totalDespesas;
    private Double despesasPagas;
    private Double despesasNaoPagas;
    private Double despesasVencidas;


    public Financas(Context context, RelogioHelper relogio){
        this.context = context;
        this.relogio = relogio;
        this.setSaldoTotal();
        this.setTotalDespesas();
        this.setDespesasPagas();
        this.setDespesasNaoPagas();
        this.setSaldoAtual();
        this.setDespesasVencidas();
    }


    private void setSaldoTotal()
    {
        double saldototal = 0d;
        ReceitaDAO dao = new ReceitaDAO(context, null);
        List<Receita> receitas = dao.buscarReceitas(null,null);

        for(Receita receita : receitas){

            if(new RelogioHelper(receita.getData()).validarMesAno(relogio)){
                saldototal = saldototal +  receita.getValor();
            }
        }

        this.saldoTotal = saldototal;
    }

    private void setDespesasPagas(){

        Double despesasPagas = 0d;
        DespesaDAO dao = new DespesaDAO(null,context);
        List<Despesa> despesas = dao.buscarDespesas(null,null);

        for(Despesa despesa : despesas){
            if(new RelogioHelper(despesa.getVencimento()).validarMesAno(relogio) && despesa.getStatus().equals(1)){
                despesasPagas += despesa.getValor();
            }
        }

      this.despesasPagas = despesasPagas;

    }

    public void setDespesasNaoPagas(){

        Double despesasNaoPagas = 0d;
        DespesaDAO dao = new DespesaDAO(null,context);
        List<Despesa> despesas = dao.buscarDespesas(null,null);

        for(Despesa despesa : despesas){
            if(new RelogioHelper(despesa.getVencimento()).validarMesAno(relogio) && despesa.getStatus().equals(0)){
                despesasNaoPagas += despesa.getValor();
            }
        }

        this.despesasNaoPagas = despesasNaoPagas;
    }

    private void setSaldoAtual()
    {
        this.saldoAtual =  this.saldoTotal - this.despesasPagas;
    }

    private void setTotalDespesas(){

        Double totalDespesas = 0d;
        DespesaDAO dao = new DespesaDAO(null,context);
        List<Despesa> despesas = dao.buscarDespesas(null,null);

        for(Despesa despesa : despesas){
            if(new RelogioHelper(despesa.getVencimento()).validarMesAno(relogio)){
                totalDespesas = totalDespesas + despesa.getValor();
            }
        }

        this.totalDespesas = totalDespesas;
    }

    public void setDespesasVencidas(){
        DespesaDAO dao = new DespesaDAO(null,context);
        List<Despesa> despesas = dao.buscarDespesas(null,null);
        Double despesasVencidas = 0d;

        for(Despesa despesa : despesas){
            if(!new RelogioHelper(despesa.getVencimento()).validarVenciamento() && despesa.getStatus().equals(0)){
                despesasVencidas = despesasVencidas +  despesa.getValor();
            }
        }
        this.despesasVencidas = despesasVencidas;
    }

    public Double getSaldoTotal() {
        return saldoTotal;
    }

    public Double getSaldoAtual() {
        return saldoAtual;
    }

    public Double getTotalDespesas() {
        return totalDespesas;
    }

    public Double getDespesasPagas() {
        return despesasPagas;
    }

    public Double getDespesasNaoPagas() {
        return despesasNaoPagas;
    }

    public Double getDespesasVencidas() {
        return despesasVencidas;
    }
}
