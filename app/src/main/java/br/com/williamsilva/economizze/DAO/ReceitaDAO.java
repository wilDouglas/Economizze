package br.com.williamsilva.economizze.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.williamsilva.economizze.controller.helpers.RelogioHelper;
import br.com.williamsilva.economizze.model.Receita;

/**
 * Created by william on 05/12/14.
 * classe de integração com a base de dados da tabela receita
 */
public class ReceitaDAO {

    private Context context;
    private Receita receita;

    public ReceitaDAO(Context context, Receita receita){
        this.context = context;
        this.receita = receita;
    }

    public ReceitaDAO(Context context){this.context = context;}


    public void cadastrarReceita(){

        ContentValues dados = new ContentValues();
        dados.put("codVerificador",this.receita.getCodVerificador());
        dados.put("nome",this.receita.getNome());
        dados.put("data",this.receita.getData());
        dados.put("valor",this.receita.getValor());
        dados.put("receitaFixa",this.receita.getReceitaFixa());
        DBCRUD.getInstance(context).inserir(dados,"RECEITA");
    }

    public void alterarReceita()
    {
        ContentValues dados = new ContentValues();
        dados.put("nome",this.receita.getNome());
        dados.put("data",this.receita.getData());
        dados.put("valor",this.receita.getValor());
        dados.put("receitaFixa",this.receita.getReceitaFixa());
        DBCRUD.getInstance(context).alterar(dados,"_id = ?",new String[]{ this.receita.getId().toString()},"RECEITA");
    }

    public void excluirReceita(Long id){
        DBCRUD.getInstance(context).deletar("RECEITA",id.toString());
    }

    public List<Receita> listarReceitas(){

        SQLiteDatabase db = DBCRUD.getInstance(context).getDb();
        String[] colunas = new String[]{"_id","codVerificador","nome","data","valor","receitaFixa"};
        ArrayList<Receita> receitas = new ArrayList<>();
        Cursor cursor = db.query("RECEITA",colunas,null,null,null,null,"_id DESC");

        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
               Receita receita = new Receita(cursor.getLong(0),cursor.getLong(1),cursor.getString(2),
                       cursor.getString(3),cursor.getDouble(4),cursor.getInt(5));

                if(new RelogioHelper(RelogioHelper.dataHoje())
                        .validarMesAno(new RelogioHelper(receita.getData()))) {
                    receitas.add(receita);
                }

            }while (cursor.moveToNext());
        }

        return receitas;
    }

    public List<Receita> buscarReceitas(String where, String[] args){

        SQLiteDatabase db = DBCRUD.getInstance(context).getDb();
        String[] colunas = new String[]{"_id","codVerificador","nome","data","valor","receitaFixa"};
        ArrayList<Receita> receitas = new ArrayList<>();
        Cursor cursor = db.query("RECEITA",colunas,where,args,null,null,"_id DESC");

        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                Receita receita = new Receita(cursor.getLong(0),cursor.getLong(1),cursor.getString(2),
                        cursor.getString(3),cursor.getDouble(4),cursor.getInt(5));
                    receitas.add(receita);

            }while (cursor.moveToNext());
        }

        return receitas;
    }

   public Receita buscarReceita(Long id){

        Receita receita = null;
        SQLiteDatabase db = DBCRUD.getInstance(context).getDb();
        String[] colunas = new String[]{"_id","codVerificador","nome","data","valor","receitaFixa"};


       Cursor cursor = db.query("RECEITA",colunas,"_id = ?",new String[] { id.toString() },null,null,"_id DESC");

        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                receita = new Receita(cursor.getLong(0),cursor.getLong(1),cursor.getString(2),
                        cursor.getString(3),cursor.getDouble(4),cursor.getInt(5));
            }while (cursor.moveToNext());
        }


        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }
}
