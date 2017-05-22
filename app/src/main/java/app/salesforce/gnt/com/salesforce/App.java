package app.salesforce.gnt.com.salesforce;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

/**
 * Created by PC-05 on 5/21/2017.
 */

public class App extends Application {
    private static App mInstance;
    public static final String TAG = App.class.getSimpleName();
    private RequestQueue mRequestQueue;

    public App(){

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
    public static synchronized App getmInstance()
    {
        return mInstance;
    }
    public RequestQueue getmRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(com.android.volley.Request<T> request,String tag){
        //set the default tag if the tag is empty

        request.setTag(TextUtils.isEmpty(tag)?TAG:tag);

        VolleyLog.d("Adding request to queue: %s", request.getUrl());

        getmRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(com.android.volley.Request<T> request){

        request.setTag(TAG);
        getmRequestQueue().add(request);

    }

    public void cancelPendingRequest(Object tag){
        if(mRequestQueue!=null){
            mRequestQueue.cancelAll(tag);
        }
    }
}
