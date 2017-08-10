package ap.autoalarm.com.myapplication.DataBase;

/**
 * Created by harshikesh.kumar on 25/01/16.
 */
public abstract class DataBaseConstants {

  public static final int DATABASE_VERSION = 7;
  public static final String DATABASE_NAME = "alarm_database.db";
  public static final String TABLE_ALARMS = "alarm_item_table";

  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_TIME = "time";
  public static final String COLUMN_DAYS = "days";
  public static final String COLUMN_IS_ON = "is_on";
  public static final String COLUMN_ALARM_NAME = "alarm_name";

  public static String CREATE_ALARM_ITEM_TABLE = "CREATE TABLE " +
      TABLE_ALARMS+ "("
      + COLUMN_ID + " INTEGER PRIMARY KEY,"
      + COLUMN_TIME+ " TEXT,"
      + COLUMN_DAYS + " TEXT,"
      + COLUMN_IS_ON + " INTEGER,"
      + COLUMN_ALARM_NAME + " TEXT"
      + ")";

}
