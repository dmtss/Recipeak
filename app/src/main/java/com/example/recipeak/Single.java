package com.example.recipeak;
import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

public class Single {
    private static Single myInstance;
    private RequestQueue myQueue;
    private Context Ctx;

    public Single(Context Ctx){
        this.Ctx=Ctx;
        myQueue= getMyQueue();
    }

    public RequestQueue getMyQueue(){
        if(myQueue == null){
            Cache cache = new DiskBasedCache(Ctx.getCacheDir(), 1024*1024);
            Network network = new BasicNetwork(new HurlStack());
            myQueue = new RequestQueue(cache, network);
            myQueue = Volley.newRequestQueue(Ctx.getApplicationContext());

        }
        return myQueue;
    }

    public static synchronized Single getMyInstance(Context context){
        if (myInstance==null){
            myInstance = new Single(context);
        }
        return myInstance;
    }

    public <T> void addToRequestQueue(Request<T> request){
        myQueue.add(request);

    }

}
