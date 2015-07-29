package br.com.williamsilva.economizze.model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by william on 23/11/14.
 */
public class Receita implements Serializable {

    private Long id;
    private Long codVerificador;
    private String nome;
    private String data;
    private Double valor;
    private Integer receitaFixa;

    public Receita(Long id, Long codVerificador, String nome, String data, Double valor, Integer receitaFixa) {
        this.id = id;
        this.codVerificador = codVerificador;
        this.nome = nome;
        this.data = data;
        this.valor = valor;
        this.receitaFixa = receitaFixa;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getReceitaFixa() {
        return receitaFixa;
    }

    public void setReceitaFixa(Integer receitaFixa) {
        this.receitaFixa = receitaFixa;
    }
}
