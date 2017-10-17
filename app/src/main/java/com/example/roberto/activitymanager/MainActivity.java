package com.example.roberto.activitymanager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityManager am;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String log="";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        tv.setMovementMethod(new ScrollingMovementMethod());
        am = (ActivityManager)getSystemService(Activity.ACTIVITY_SERVICE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.services) {
            String log="";
            List<ActivityManager.RunningServiceInfo> services = am.getRunningServices(Integer.MAX_VALUE);
            int size = services.size();
            log+="There are "+Integer.toString(size)+" services running:\n\n";
            for (ActivityManager.RunningServiceInfo s : services)
                log+=s.service.getClassName()+"\n";
            tv.setText(log);
            return true;
        }
        if (id==R.id.Apps){
            String log="";
            List<ActivityManager.RunningAppProcessInfo> processes = am.getRunningAppProcesses();
            int size = processes.size();
            log+="There are "+Integer.toString(size)+" Apps running:\n\n";
            for (ActivityManager.RunningAppProcessInfo p: processes)
                log+=p.processName.toString()+"\n";
            tv.setText(log);
            return true;
        }

        if (id==R.id.intent){
            String log="";

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            PackageManager pm = getPackageManager();

            List<ResolveInfo> infoList = pm.queryIntentActivities(intent, 0);

            Iterator<ResolveInfo> infoIterator = infoList.iterator();
            int howmany = infoList.size();
            log+="There are "+ howmany + "installed apps:\n";
            while (infoIterator.hasNext()){

                final ResolveInfo info = infoIterator.next();
                CharSequence activityLabel = info.loadLabel(pm);
                log+=activityLabel+"\n";
            }
            tv.setText(log);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
