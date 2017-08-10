package ap.autoalarm.com.myapplication.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import ap.autoalarm.com.myapplication.DataBase.AlarmInfo;
import ap.autoalarm.com.myapplication.DataBase.DbHelper;
import ap.autoalarm.com.myapplication.R;
import ap.autoalarm.com.myapplication.Util.Constants;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by harshikesh.kumar on 04/01/16.
 */
public class AlarmFragment extends BaseFragment {

  private ListView mListView;
  private Activity mActivity;
  private DbHelper mDbhelper;
  private List<AlarmInfo> mListAlarmInfo = new ArrayList<AlarmInfo>();
  private AlarmListAdapter mAdapter;
  private FloatingActionButton mFloatingButton;
  private int mRequestCode = 1;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mActivity = (Activity)getActivity();
    mDbhelper = DbHelper.getInstance((Context)mActivity);
    // Inflate the layout for this fragment
    View view= inflater.inflate(R.layout.fragment_alarm, container, false);
    mListView= (ListView)view.findViewById(R.id.list_alarm);
    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        navigateToCreateAlarmFragment(mListAlarmInfo.get(position));
      }
    });
    mFloatingButton = (FloatingActionButton)view.findViewById(R.id.add_alarm_button);
    mFloatingButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        navigateToCreateAlarmFragment(null);
      }
    });

    return view;
  }

  void navigateToCreateAlarmFragment(AlarmInfo alarmInfo)
  {
    //DialogHandler dialogHandler = new DialogHandler();
    //dialogHandler.show(getFragmentManager(), "time picker");

    Intent setAlarmIntent = new Intent(mActivity,SetAlarmActivity.class);
    if(null != alarmInfo)
    {
      setAlarmIntent.putExtra(Constants.SETALARM_NAME,alarmInfo.getName());
      setAlarmIntent.putExtra(Constants.SETALARM_TIME,alarmInfo.getId());
    }
    //Fragment
    startActivityForResult(setAlarmIntent,mRequestCode);

  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == mRequestCode)
    {

    }
  }

  @Override public void onResume() {
    super.onResume();
    new FetchAllAlarmTask().execute();
  }

  class FetchAllAlarmTask extends AsyncTask<Void,Void,Void>
 {

   @Override protected Void doInBackground(Void... params) {
     setUpListView();
     return null;
   }

   @Override protected void onPostExecute(Void id) {
     super.onPostExecute(id);
     mAdapter = new AlarmListAdapter();
     mListView.setAdapter(mAdapter);

   }
 }
  private void setUpListView()
  {
    mListAlarmInfo = mDbhelper.findAllAlarm();
  }

  class AlarmListAdapter extends BaseAdapter
  {

    AlarmListAdapter()
    {

    }

    @Override public int getCount() {
      if(mListAlarmInfo == null)
      {
        return 0;
      }
      return mListAlarmInfo.size();
    }

    @Override public Object getItem(int position) {
      return null;
    }

    @Override public long getItemId(int position) {
      return position;
    }

    @Override public View getView(int position, android.view.View convertView, ViewGroup parent) {

      ViewHolder viewHolder;
      if(convertView == null)
      {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        convertView= inflater.inflate(R.layout.item_alarm, parent, false);
         viewHolder = new ViewHolder();
        convertView.setTag(viewHolder);
      }else
      {
        viewHolder = (ViewHolder)convertView.getTag();
      }
      viewHolder.alarmText = (TextView)convertView.findViewById(R.id.text_alarm_time);
      viewHolder.imagebutton = (ImageView)convertView.findViewById(R.id.image_button);
      if(mListAlarmInfo != null) {
        viewHolder.alarmText.setText(mListAlarmInfo.get(position).getTime());
      }
      return convertView;
    }
  }
  private class ViewHolder
  {
    TextView alarmText;
    ImageView imagebutton;

  }

}
