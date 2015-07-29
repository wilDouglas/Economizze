package br.com.williamsilva.economizze.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by william on 05/12/14.
 */
public class DBCRUD {

    private SQLiteDatabase db;
    private static DBCRUD dbcrud;

    private DBCRUD(Context context){
        db = DataBase.getInstance(context).getWritableDatabase();
    }

    public static DBCRUD getInstance(Context context){

        if(dbcrud == null){
            dbcrud = new DBCRUD(context);
        }
        return dbcrud;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void inserir(ContentValues values,String table){
        try {
            db.insert(table, null, values);
            Log.i("Inserir", "Inserido com Sucesso!");
        }catch (SQLiteException erro){
            Log.i("Inserir",erro.getMessage());
        }
    }

    public void alterar(ContentValues values,String where, String[] dados, String table){
        try {
            db.update(table, values, where, dados);
            Log.i("Alterar", "Alterado com Sucesso!");
        }catch (SQLiteException erro){
            Log.i("Alterar",erro.getMessage());
        }
    }

    public void deletar(String table, String id){
        try {
            db.delete(table, "_id = ?", new String[]{id});
            Log.i("Deletar","Deletado com sucesso!");
        }catch (SQLiteException erro){
            Log.i("Deletar",erro.getMessage());
        }
    }

    public static Long gerarCodigoVeririficador(Context context, String table) {
        Long cod = Long.MIN_VALUE;
        Cursor cursor = DBCRUD.getInstance(context).getDb().rawQuery("SELECT max(codVerificador) as max FROM " + table,null);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                cod = cursor.getLong(0);
                cod++;
            }while (cursor.moveToNext());
        }
        Log.i("Código verificador: ", cod.toString());
        return cod;
    }

  /*  public Cursor buscar(String table,String where,String whereArg, String[] colunas, String ordem){
        try {
            return db.query(table, colunas, where, new String[]{whereArg}, null, null, ordem);
        }catch (SQLiteException erro){
            Log.i("Buscar",erro.getMessage());
            return  null;
        }
    }*/
}
