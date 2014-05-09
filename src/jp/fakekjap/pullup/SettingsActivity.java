package jp.fakekjap.pullup;

import android.os.Bundle;
import android.view.Menu;
import jp.fakejap.R;

import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
 
public class SettingsActivity extends ListActivity {
    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist = null;
    private ApplicationAdapter listadaptor = null;
//    public static final String PREFS_NAME = "FakeJap_PREFS";

 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      
        
        
        setContentView(R.layout.activity_stream);
 
        packageManager = getPackageManager();
 
        new LoadApplications().execute();
    }
    
    
   
    
    public void setPullUpApp(String appName)
    {
        SharedPreferences settings = getSharedPreferences(PullUp.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PullUp.PREFS_NAME,appName);
        // Commit the edits!
        editor.commit();
    }
 
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
 
        return true;
    }
 
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;
 
//        switch (item.getItemId()) {
////        case R.id.menu_about: {
////            displayAboutDialog();
// 
//            break;
//        }
//        default: {
//            result = super.onOptionsItemSelected(item);
// 
//            break;
//        }
//        }
// 
        return result;
    }
     
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
 
        ApplicationInfo app = applist.get(position);
        setPullUpApp(app.packageName);
        Toast.makeText(this,"Set as " +app.packageName ,
                Toast.LENGTH_LONG).show();
        
    }
 
    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    applist.add(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
 
        return applist;
    }
 
    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;
 
        @Override
        protected Void doInBackground(Void... params) {
            applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            listadaptor = new ApplicationAdapter(SettingsActivity.this,
                    R.layout.list_row, applist);
 
            return null;
        }
 
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
 
        @Override
        protected void onPostExecute(Void result) {
            setListAdapter(listadaptor);
            progress.dismiss();
            super.onPostExecute(result);
        }
 
        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(SettingsActivity.this, null,
                    "Loading application info...");
            super.onPreExecute();
        }
 
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
