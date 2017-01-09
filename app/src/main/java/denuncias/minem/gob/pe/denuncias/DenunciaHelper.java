package denuncias.minem.gob.pe.denuncias;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by ronaldvelasquez on 7/01/17.
 */

public class DenunciaHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DENUNCIAS";

    private static final String TEXT_TYPE = " TEXT";
    private static final String REAL_TYPE = " REAL";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_DENUNCIA =
            "CREATE TABLE " + DenunciasContract.DenunciaEntry.TABLE_NAME +
                    " (" + DenunciasContract.DenunciaEntry._ID + INT_TYPE + " PRIMARY KEY, "
                    + DenunciasContract.DenunciaEntry.COLUM_NAME_TITLE + TEXT_TYPE + COMMA_SEP
                    + DenunciasContract.DenunciaEntry.COLUM_NAME_PERSON_NAME + TEXT_TYPE + COMMA_SEP
                    + DenunciasContract.DenunciaEntry.COLUM_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP
                    + DenunciasContract.DenunciaEntry.COLUM_NAME_PHOTO + TEXT_TYPE + COMMA_SEP
                    + DenunciasContract.DenunciaEntry.COLUM_NAME_LATITUDE + REAL_TYPE + COMMA_SEP
                    + DenunciasContract.DenunciaEntry.COLUM_NAME_LONGITUDE + REAL_TYPE + " )";


    public DenunciaHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_DENUNCIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
