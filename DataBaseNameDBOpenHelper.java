/*
 * Ce document intitulé «  UlrichDbContract.java  » du package sqlitegenerator proposé par HCurti$ et Ulrich TIAYO
 * issus du SEED(www.seed-innov.com) est mis à disposition sous les termes de la licence Creative Commons. 
 * Vous pouvez copier, modifier des copies de cette page, dans les conditions fixées par la licence, 
 * tant que cette note apparaît clairement.
 */


package Ajouter votre package iciimport android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SqlIteCodeGenerator for Android on 07 août 2017.
 */
public class DataBaseNameDBOpenHelper extends SQLiteOpenHelper {


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "DataBaseName";

    public DataBaseNameDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {// creation de la table tab_1.
        final String SQL_CREATE_TAB_1_TABLE = "CREATE TABLE " + DataBaseNameContract.tab_1Entry.TABLE_NAME + " (" +
		DataBaseNameContract.tab_1Entry.COLUMN_A  +" null, " +
		DataBaseNameContract.tab_1Entry.COLUMN_B  +" null, " +
		DataBaseNameContract.tab_1Entry.COLUMN_C  +" null " + 
 " );";	}
// creation de la table tab_2.
        final String SQL_CREATE_TAB_2_TABLE = "CREATE TABLE " + DataBaseNameContract.tab_2Entry.TABLE_NAME + " (" +
		DataBaseNameContract.tab_2Entry.COLUMN_A  +" null, " +
		DataBaseNameContract.tab_2Entry.COLUMN_B  +" null, " +
		DataBaseNameContract.tab_2Entry.COLUMN_C  +" null " + 
 " );";	}
