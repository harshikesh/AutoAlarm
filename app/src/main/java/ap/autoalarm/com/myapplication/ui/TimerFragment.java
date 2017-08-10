package ap.autoalarm.com.myapplication.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ap.autoalarm.com.myapplication.R;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by harshikesh.kumar on 04/01/16.
 */
public class TimerFragment extends BaseFragment implements View.OnClickListener {

  private boolean mStarted=false;
  Timer mTimer;
  TextView text1;
  TextView text2;
  TextView text4;
  int hour;
  int minutes;
  int mSeconds=0;
  int mMilliSecond=0;
   Handler mHandler = new Handler();
  TimerTask timerTask;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view=inflater.inflate(R.layout.fragment_timer, container, false);

     text1=(TextView)view.findViewById(R.id.Text1);
     text2=(TextView)view.findViewById(R.id.Text2);
     text4=(TextView)view.findViewById(R.id.Text4);
    Button button1=(Button)view.findViewById(R.id.button1);
    button1.setOnClickListener(this);
    return view;
  }


 private void startTimer()
  {
    if(!mStarted) {
      mTimer= new Timer();
      initializeTimerTask();
      mTimer.schedule(timerTask, 0, 10);
    }
    mStarted=true;
  }
  private void stopTimer() {
    if (mTimer != null) {
      mTimer.cancel();
    }
    mTimer=null;
    mStarted=false;
    mMilliSecond=0;
    minutes=0;
    mSeconds=0;
    hour=0;
  }
  private void pauseTimer()
  {
    if (mTimer != null) {
      mTimer.cancel();
    }
    mTimer=null;
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
              mSeconds++;
              if(mSeconds==60) {
                mSeconds=0;
                minutes++;
                if (minutes == 60) {
                  minutes = 0;
                  hour++;
                }
              }
            }
            text1.setText(String.valueOf(hour));
            text2.setText(String.valueOf(minutes));
            text4.setText(String.valueOf(mSeconds));
          }
        });
      }
    };
  }
  @Override public void onClick(View v) {

    switch (v.getId())
    {
      case R.id.button1:

        if(!mStarted) {
          startTimer();
        }else
        {
          stopTimer();
        }
        break;
    }

  }
}
