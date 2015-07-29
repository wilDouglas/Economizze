package br.com.williamsilva.economizze.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william on 05/12/14.
 */
    class DataBase  extends SQLiteOpenHelper {

    private static DataBase dataBase;
    private static final String nameDataBase = "ECONOMIZZEBD";
    private static final int versaoDataBase = 1;
    private static final String[] createTable = new String[] {
            "CREATE TABLE `RECEITA` ( " +
                    " `_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    " `codVerificador` INTEGER," +
                    " `nome` TEXT, " +
                    " `data` TEXT, " +
                    " `valor` REAL, " +
                    " `receitaFixa` INTEGER " +
                    ");",
            "CREATE TABLE `DESPESA` ( " +
                    " `_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    " `codVerificador` INTEGER, " +
                    " `nome` TEXT, " +
                    " `valor` REAL, " +
                    " `vencimento` TEXT, " +
                    " `status` INTEGER, " +
                    " `despesaFixa` INTEGER " +
                    ");"
    };


    private DataBase(Context context){
        super(context,nameDataBase,null,versaoDataBase);
    }

    public synchronized static DataBase getInstance(Context context) {

        if(dataBase == null){
            dataBase = new DataBase(context);
        }
        return dataBase;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        for (int i = 0; i < createTable.length; i++){
            db.execSQL(createTable[i]);
            Log.i("SQL", createTable[i]);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE continuação......"); tambem pode ser um alterTable algo do genero
        //onCreate(db);
    }
}