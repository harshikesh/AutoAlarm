package ap.autoalarm.com.myapplication.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ap.autoalarm.com.myapplication.R;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by harshikesh.kumar on 05/01/16.
 */
public class MyCountDownTimer extends Fragment implements View.OnClickListener {


  private boolean mStarted=false;
  Timer mTimer;
  TextView textHour;
  TextView textMinutes;
  TextView textSeconds;
  EditText mEditText;
  Button startButton;
  Button stopButton;
  int mHour;
  int mMinutes;
  int mSeconds=0;
  int mMilliSecond=0;
  Handler mHandler = new Handler();
  TimerTask timerTask;
  private int mTotalTime;

  public MyCountDownTimer()
  {

  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view= inflater.inflate(R.layout.fragment_main, container, false);
    mEditText=(EditText)view.findViewById(R.id.edit_time);
    textHour=(TextView)view.findViewById(R.id.hour);
    textMinutes=(TextView)view.findViewById(R.id.min);
    textSeconds=(TextView)view.findViewById(R.id.sec);

    startButton=(Button)view.findViewById(R.id.startTimer);
    stopButton=(Button)view.findViewById(R.id.sTopTimer);

    startButton.setOnClickListener(this);
    stopButton.setOnClickListener(this);
    return view;
  }

  private void startCountDown()
  {
    mTotalTime = mHour*60*60+mMinutes*60+mSeconds;
    new CountDownTimer(mTotalTime*1000, 1000) { // adjust the milli seconds here

      public void onTick(long millisUntilFinished) {
        textHour.setText(""+String.format("%d hour,%d min, %d sec",
            TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
            TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished)-TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
      }

      public void onFinish() {
        textSeconds.setText("done!");
      }
    }.start();
    //if(!mStarted) {
    //  mTimer= new Timer();
    //  initializeTimerTask();
    //  mTimer.schedule(timerTask, 0, 10);
    //}
    mStarted=true;
  }

  private void stopCountDown()
  {
    if(mTimer!=null)
    {
      mTimer.cancel();
      mTimer=null;
    }
    mStarted=false;
  }
  public void initializeTimerTask() {

    timerTask = new TimerTask() {
      public void run() {

        //use a handler to run a toast that shows the current timestamp
        mHandler.post(new Runnable() {
          public void run() {
            //get the current timeStamp
            mMilliSecond++;
            if(mMilliSecond==100)
            {
              mMilliSecond=0;
              mSeconds--;
              if(mSeconds==0) {

                mMinutes--;
                if (mMinutes == 0) {
                  mHour--;
                }
                if(mHour==0 && mMinutes==0 && mSeconds==0)
                {
                  mTimer.cancel();
                  mTimer=null;
                  mStarted=false;
                }
                mSeconds=60;
              }
            }
            textHour.setText(String.valueOf(mHour));
            textMinutes.setText(String.valueOf(mMinutes));
            textSeconds.setText(String.valueOf(mSeconds));
          }
        });
      }
    };
  }

  @Override public void onClick(View v) {
    switch(v.getId())
    {
      case R.id.startTimer:
        String text=mEditText.getText().toString();
        mHour=Integer.parseInt(text.substring(0,2));
        mMinutes=Integer.parseInt(text.substring(2,4));
        mSeconds=Integer.parseInt(text.substring(4,6));
        startCountDown();
        break;
      case R.id.sTopTimer:
        stopCountDown();
        break;
    }
  }
}
