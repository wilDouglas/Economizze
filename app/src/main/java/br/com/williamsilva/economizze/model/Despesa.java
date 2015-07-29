package br.com.williamsilva.economizze.model;

import android.util.Log;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by william on 23/11/14.
 */
public class Despesa implements Serializable {

    private Long id;
    private Long codVerificador;
    private String nome;
    private Double valor;
    private String vencimento;
    private Integer status;
    private Integer despesaFixa;

    public Despesa(Long id, Long codVerificador, String nome, Double valor, String vencimento, Integer status,
                   Integer despesaFixa) {
        this.id = id;
        this.codVerificador = codVerificador;
        this.nome = nome;
        this.valor = valor;
        this.vencimento = vencimento;
        this.status = status;
        this.despesaFixa = despesaFixa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodVerificador() {
        return codVerificador;
    }

    public void setCodVerificador(Long codVerificador) {
        this.codVerificador = codVerificador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDespesaFixa() {
        return despesaFixa;
    }

    public void setDespesaFixa(Integer despesaFixa) {
        this.despesaFixa = despesaFixa;
    }
}
