package ap.autoalarm.com.myapplication.ui.widget;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import ap.autoalarm.com.myapplication.DataBase.AlarmInfo;
import ap.autoalarm.com.myapplication.DataBase.DbHelper;
import ap.autoalarm.com.myapplication.R;
import ap.autoalarm.com.myapplication.ui.AlarmReceiver;
import java.util.Calendar;

public class DialogHandler extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    Context mContext;
    private int mHour;
    private int mMinute;
    EditText tvTitle;

    public DialogHandler()
    {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        mContext = (Context)getActivity();
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog dialog= new TimePickerDialog(new ContextThemeWrapper(getActivity(), R.style.AppTheme), this, hour, minute, false);
        // Create a new instance of TimePickerDialog and return it
        tvTitle = new EditText(getActivity());
        tvTitle.setText("TimePickerDialog Title");
        tvTitle.setBackgroundColor(Color.parseColor("#EEE8AA"));
        tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        dialog.setCustomTitle(tvTitle);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE​);
        return dialog;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        mHour=hourOfDay;
        mMinute = minute;
        setNewAlarm();
        Toast.makeText(mContext, "your time is selected", Toast.LENGTH_LONG).show();
    }

    private void setNewAlarm() {
        AlarmManager alarmMgr;
        PendingIntent alarmIntent;
        alarmMgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);

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
        //alarmInfo.setName(mEditText.getText().toString());
        alarmInfo.setIsOn(1);
        alarmInfo.setDays("1000000");
        DbHelper dbHelper = DbHelper.getInstance(mContext);
        dbHelper.addNewAlarm(alarmInfo);
    }


}