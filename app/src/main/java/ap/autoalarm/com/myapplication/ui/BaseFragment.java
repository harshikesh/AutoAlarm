package ap.autoalarm.com.myapplication.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by harshikesh.kumar on 04/01/16.
 */
public class BaseFragment extends Fragment {


  protected void showSnackbar(View view, String message) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
  }

  protected boolean isNetworkAvailable() {
    ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
  }

}
