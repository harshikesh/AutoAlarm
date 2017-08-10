package ap.autoalarm.com.myapplication.DataBase;

/**
 * Created by harshikesh.kumar on 25/01/16.
 */
public class AlarmInfo {

  public int getId() {
    return mId;
  }

  public void setId(int id) {
    mId = id;
  }

  public String getTime() {
    return mTime;
  }

  public void setTime(String time) {
    mTime = time;
  }

  public String getDays() {
    return mDays;
  }

  public void setDays(String days) {
    mDays = days;
  }

  public int getIsOn() {
    return isOn;
  }

  public void setIsOn(int isOn) {
    this.isOn = isOn;
  }

  int mId;
  String mTime;
  //0 is for sunday
  String  mDays;
  int isOn;
  String name ="";

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
