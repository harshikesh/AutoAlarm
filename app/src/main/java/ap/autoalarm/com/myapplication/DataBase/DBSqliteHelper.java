package ap.autoalarm.com.myapplication.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by harshikesh.kumar on 25/01/16.
 */
public class DBSqliteHelper extends SQLiteOpenHelper {


  public DBSqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
      int version) {
    super(context, name, factory, version);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    db.execSQL(DataBaseConstants.CREATE_ALARM_ITEM_TABLE);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + DataBaseConstants.TABLE_ALARMS);
    onCreate(db);
  }
}
