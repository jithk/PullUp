package jp.fakekjap.pullup;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class PullUp extends Activity
{

    public static final String PREFS_NAME = "FakeJap_PREFS";
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        startDefaultApp();  
        finish();
        
//        
    }
    
    private void startDefaultApp()
    {
        String appName = getPullUpAppName();
        if(appName == null ||
                appName.isEmpty())
        {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return;
        }
        
        try {
            
            Intent intent = getPackageManager()
                    .getLaunchIntentForPackage(appName); 
            if (null != intent) {
                startActivity(intent);                
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(),
                    Toast.LENGTH_LONG).show();        
            
        }
    }
    
    
    /* methods to save the app name*/
    public String getPullUpAppName()
    {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getString(PREFS_NAME, new String());        
    }
    
}
