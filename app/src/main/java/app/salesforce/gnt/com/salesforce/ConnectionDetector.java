package app.salesforce.gnt.com.salesforce;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by PC-05 on 5/14/2017.
 */

public class ConnectionDetector {

   Context context;

    public ConnectionDetector(Context context){
        this.context = context;
    }

    public boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager!= null){
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if(info != null){

                if (info.getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }

            }
        }
        return false;
    }
}
