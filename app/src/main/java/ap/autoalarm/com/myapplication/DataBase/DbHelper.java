package ap.autoalarm.com.myapplication.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import ap.autoalarm.com.myapplication.Util.Constants;
import java.util.ArrayList;
import java.util.List;

import static ap.autoalarm.com.myapplication.DataBase.DataBaseConstants.COLUMN_ALARM_NAME;
import static ap.autoalarm.com.myapplication.DataBase.DataBaseConstants.COLUMN_DAYS;
import static ap.autoalarm.com.myapplication.DataBase.DataBaseConstants.COLUMN_ID;
import static ap.autoalarm.com.myapplication.DataBase.DataBaseConstants.COLUMN_IS_ON;
import static ap.autoalarm.com.myapplication.DataBase.DataBaseConstants.COLUMN_TIME;
import static ap.autoalarm.com.myapplication.DataBase.DataBaseConstants.TABLE_ALARMS;

/**
 * Created by harshikesh.kumar on 25/01/16.
 */
public class DbHelper {

  private static DbHelper dbHelper;
  private Context mContext;

  private DbHelper(Context context) {
    mContext= context;
  }
  public static DbHelper getInstance(Context context) {

    if(dbHelper == null)
    {
      dbHelper = new DbHelper(context);
    }
    return dbHelper;
  }

  public void addNewAlarm(AlarmInfo alarmInfo) {

    ContentValues values = new ContentValues();
    values.put(COLUMN_ID, alarmInfo.getId());
    values.put(COLUMN_TIME, alarmInfo.getTime());
    values.put(COLUMN_DAYS, alarmInfo.getDays());
    values.put(COLUMN_IS_ON, alarmInfo.getIsOn());
    values.put(COLUMN_ALARM_NAME, alarmInfo.getName());

    DBSqliteHelper sqliteHelper= new DBSqliteHelper(mContext,DataBaseConstants.DATABASE_NAME,null,DataBaseConstants.DATABASE_VERSION);
    SQLiteDatabase db = sqliteHelper.getWritableDatabase();

    long totalrows=db.insert(DataBaseConstants.TABLE_ALARMS, null, values);
    if(totalrows == -1)
    {

    }
    Log.d(Constants.LOG ," total number of rows inserted :"+totalrows+"");
    db.close();
  }

  public List<AlarmInfo> findAllAlarm() {
    String query = "Select * FROM " + TABLE_ALARMS ;

    DBSqliteHelper sqliteHelper= new DBSqliteHelper(mContext,DataBaseConstants.DATABASE_NAME,null,DataBaseConstants.DATABASE_VERSION);
    SQLiteDatabase db = sqliteHelper.getWritableDatabase();

    Cursor cursor = db.rawQuery(query, null);

    List<AlarmInfo> listAlarmInfo=new ArrayList<>();

    if (cursor.moveToFirst()) {
      cursor.moveToFirst();
      do{
        AlarmInfo alarmInfo = new AlarmInfo();
        alarmInfo.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
        alarmInfo.setDays(cursor.getString(cursor.getColumnIndex(COLUMN_DAYS)));
        alarmInfo.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_TIME)));
        alarmInfo.setName(cursor.getString(cursor.getColumnIndex(COLUMN_ALARM_NAME)));
        alarmInfo.setIsOn(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_IS_ON))));
        listAlarmInfo.add(alarmInfo);
      }while(cursor.moveToNext());
      cursor.close();
    } else {
      listAlarmInfo = null;
    }
    db.close();
    return listAlarmInfo;
  }

}
