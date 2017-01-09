package denuncias.minem.gob.pe.denuncias;

import android.provider.BaseColumns;

/**
 * Created by ronaldvelasquez on 7/01/17.
 */

public class DenunciasContract {
    public static class DenunciaEntry implements BaseColumns {
        public static final String TABLE_NAME = "denuncia";
        public static final String COLUM_NAME_TITLE = "title";
        public static final String COLUM_NAME_PERSON_NAME = "person_name";
        public static final String COLUM_NAME_DESCRIPTION = "description";
        public static final String COLUM_NAME_LATITUDE = "latitude";
        public static final String COLUM_NAME_LONGITUDE = "longitude";
        public static final String COLUM_NAME_PHOTO = "photo";

    }
}
