/*
 * Ce document intitulé «  UlrichDbContract.java  » du package sqlitegenerator proposé par HCurti$ et Ulrich TIAYO
 * issus du SEED(www.seed-innov.com) est mis à disposition sous les termes de la licence Creative Commons. 
 * Vous pouvez copier, modifier des copies de cette page, dans les conditions fixées par la licence, 
 * tant que cette note apparaît clairement.
 */


package Ajouter votre package ici
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by SqlIteCodeGenerator for Android on 07 août 2017.
 */
public class DataBaseNameContract {

    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.setToNow();
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

	/* le contenu de cette classe definit les colones de la table tab_1 */
	public static final class tab_1Entry implements BaseColumns {
		//
		public static final String TABLE_NAME = "tab_1";
		// 
		public static final String COLUMN_A = "a";

		// 
		public static final String COLUMN_B = "b";

		// 
		public static final String COLUMN_C = "c";
	}
	/* le contenu de cette classe definit les colones de la table tab_2 */
	public static final class tab_2Entry implements BaseColumns {
		//
		public static final String TABLE_NAME = "tab_2";
		// 
		public static final String COLUMN_A = "a";

		// 
		public static final String COLUMN_B = "b";

		// 
		public static final String COLUMN_C = "c";
	}

}