package ap.autoalarm.com.myapplication.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import ap.autoalarm.com.myapplication.DataBase.AlarmInfo;
import ap.autoalarm.com.myapplication.DataBase.DbHelper;
import ap.autoalarm.com.myapplication.R;
import ap.autoalarm.com.myapplication.Util.Constants;
import ap.autoalarm.com.myapplication.Util.Utility;
import java.util.Calendar;

/**
 * Created by harshikesh.kumar on 26/01/16.
 */

public class SetAlarmActivity extends AppCompatActivity implements View.OnClickListener{

  private TimePicker mTimePicker;
  private int mHour;
  private int mMinute;
  private EditText mEditText;
  private String mName;
  private TextView mText1;
  private TextView mText2;
  private TextView mText3;
  private TextView mText4;
  private TextView mText5;
  private TextView mText6;
  private TextView mText7;
  private int mWeekDays;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setalarm);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    mTimePicker = (TimePicker) findViewById(R.id.time_picker);
    mEditText = (EditText) findViewById(R.id.alarm_name);
    mText1=(TextView)findViewById(R.id.mon);
    mText2=(TextView)findViewById(R.id.tue);
    mText3=(TextView)findViewById(R.id.wed);
    mText4=(TextView)findViewById(R.id.thu);
    mText5=(TextView)findViewById(R.id.fri);
    mText6=(TextView)findViewById(R.id.sat);
    mText7=(TextView)findViewById(R.id.sun);

    mText1.setOnClickListener(this);
    mText2.setOnClickListener(this);
    mText3.setOnClickListener(this);
    mText4.setOnClickListener(this);
    mText5.setOnClickListener(this);
    mText6.setOnClickListener(this);
    mText7.setOnClickListener(this);

    int time = getIntent().getIntExtra(Constants.SETALARM_TIME, 0406);
    mName = getIntent().getStringExtra(Constants.SETALARM_NAME);
    String a =Utility.getDecimalFromBinary(0b10101);
    Log.d(Constants.LOG,"decimal  :"+a);
    String b =Utility.getBinaryFromDecimal(21);
    Log.d(Constants.LOG,"binary  :"+b);
   // mWeekDays = getIntent().getIntExtra(Constants.SETALARM_DAYS,0);
    mMinute = time % 100;
    mHour = time / 100;
    mTimePicker.setCurrentHour(mHour);
    mTimePicker.setCurrentMinute(mMinute);

  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_create_alarm, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_save) {
      mHour = mTimePicker.getCurrentHour();
      mMinute = mTimePicker.getCurrentMinute();
      setNewAlarm();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void setNewAlarm() {

    AlarmManager alarmMgr;
    PendingIntent alarmIntent;
    alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    Intent intent = new Intent(this, AlarmReceiver.class);
    alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

    // Set the alarm to start at 8:30 a.m.
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.set(Calendar.HOUR_OF_DAY, mHour);
    calendar.set(Calendar.MINUTE, mMinute);

    // setRepeating() lets you specify a precise custom interval--in this case,
    // 20 minutes.
    alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 20,
        alarmIntent);
    AlarmInfo alarmInfo = new AlarmInfo();
    alarmInfo.setId(mHour * 100 + mMinute);
    if (mMinute >= 10) {
      alarmInfo.setTime(mHour + ":" + mMinute);
    } else {
      alarmInfo.setTime(mHour + ":0" + mMinute);
    }
    alarmInfo.setName(mEditText.getText().toString());
    alarmInfo.setIsOn(1);
    alarmInfo.setDays("0000000");
    DbHelper dbHelper = DbHelper.getInstance(this);
    dbHelper.addNewAlarm(alarmInfo);
    setResult(1);
    finish();
  }

  @Override public void onClick(View v) {

    switch(v.getId())
    {
      case R.id.mon:
        mText1.setBackground(getResources().getDrawable(R.drawable.blue_circular));
        break;
      case R.id.tue:
        break;
      case R.id.wed:
        break;
      case R.id.thu:
        break;
      case R.id.fri:
        break;
      case R.id.sat:
        break;
      case R.id.sun:
        break;


    }
  }
}
