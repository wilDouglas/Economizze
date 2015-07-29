package br.com.williamsilva.economizze.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.williamsilva.economizze.controller.helpers.RelogioHelper;
import br.com.williamsilva.economizze.model.Despesa;

/**
 * Created by William on 07/12/2014.
 */
public class DespesaDAO {
    private Context context;
    private Despesa despesa;

    public DespesaDAO(Despesa despesa, Context context) {
        this.despesa = despesa;
        this.context = context;
    }

    public DespesaDAO(Context context){
        this.context = context;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public void cadastrarDespesa() {
        ContentValues dados = new ContentValues();
        dados.put("nome",despesa.getNome());
        dados.put("codVerificador",despesa.getCodVerificador());
        dados.put("valor",despesa.getValor());
        dados.put("vencimento",despesa.getVencimento());
        dados.put("status",despesa.getStatus());
        dados.put("despesaFixa",despesa.getDespesaFixa());
        DBCRUD.getInstance(context).inserir(dados,"DESPESA");
    }

    public void alterarDespesa(){
        ContentValues dados = new ContentValues();
        dados.put("nome",despesa.getNome());
        dados.put("valor",despesa.getValor());
        dados.put("vencimento",despesa.getVencimento());
        dados.put("status",despesa.getStatus());
        dados.put("despesaFixa",despesa.getDespesaFixa());
        DBCRUD.getInstance(context).alterar(dados,"_id = ?",new String[]{ despesa.getId().toString()}
                ,"DESPESA");
    }

    public void excluirDespesa(Long id){
        DBCRUD.getInstance(context).deletar("DESPESA",id.toString());
    }

    public List<Despesa> listarDespesa(){

        List<Despesa> despesas = new ArrayList<>();
        SQLiteDatabase db = DBCRUD.getInstance(context).getDb();
        String[] colunas = new String[] {"_id","codVerificador","nome","valor","vencimento","status","despesaFixa"};
        Cursor cursor = db.query("DESPESA",colunas,null,null,null,null,"_id DESC");

        if(cursor.getCount() > 0) {

            cursor.moveToFirst();

            do {

                Despesa despesa = new Despesa(cursor.getLong(0),cursor.getLong(1), cursor.getString(2), cursor.getDouble(3),
                        cursor.getString(4), cursor.getInt(5), cursor.getInt(6));

               if(new RelogioHelper(RelogioHelper.dataHoje())
                        .validarMesAno(new RelogioHelper(despesa.getVencimento()))) {
                    despesas.add(despesa);
                }

            } while (cursor.moveToNext());
        }

        return despesas;
    }

    public List<Despesa> buscarDespesas(String where,String[] args){
        List<Despesa> despesas = new ArrayList<>();
        SQLiteDatabase db = DBCRUD.getInstance(context).getDb();
        String[] colunas = new String[] {"_id","codVerificador","nome","valor","vencimento","status","despesaFixa"};
        Cursor cursor = db.query("DESPESA",colunas,where,args,null,null,"_id DESC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                despesas.add(new Despesa(cursor.getLong(0),cursor.getLong(1), cursor.getString(2), cursor.getDouble(3),
                        cursor.getString(4), cursor.getInt(5), cursor.getInt(6)));
            } while (cursor.moveToNext());
        }
        return despesas;
    }
}
