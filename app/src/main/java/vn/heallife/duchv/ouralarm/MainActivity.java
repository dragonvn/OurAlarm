package vn.heallife.duchv.ouralarm;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import vn.heallife.duchv.ouralarm.ui.fragment.AlarmView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.container, new SplashScreenFragment()).commit();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    AlarmView alarmView = AlarmView.newInstance();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container, alarmView).commit();
                }
            },400);

            //not use splash screen
//            AlarmView alarmView = AlarmView.newInstance();
//            FragmentTransaction ft = getFragmentManager().beginTransaction();
//            ft.add(R.id.container, alarmView).commit();
        }
        getContentResolver().registerContentObserver(Settings.System.getUriFor
                        (Settings.System.ACCELEROMETER_ROTATION),
                true,contentObserver);

//        RelativeLayout wc = (RelativeLayout) findViewById(R.id.top);
//        wc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                AlarmView alarmView = AlarmView.newInstance();
//                ft.addToBackStack(alarmView.getTAG());
//                ft.replace(R.id.container,alarmView);
//                ft.commit();
//            }
//        });
//        OpenUDID.syncContext(this);
//        final String openUDID = OpenUDID.getOpenUDIDInContext();
//
//        Log.d("TEST", "Nothing happen...");
//        Push.InitWithSettings(MainActivity.this, openUDID, "http://192.168.1.111/test.php");



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(contentObserver);
    }

    private ContentObserver contentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            int i = android.provider.Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION,0);
            System.out.print(android.provider.Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0));
            System.out.print(android.provider.Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 1));
            // show a toast here
        }
    };

//    public void OnClick(View v){
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        AlarmView alarmView = AlarmView.newInstance();
//        ft.replace(R.id.container,alarmView);
//        ft.addToBackStack(alarmView.getTAG());
//        ft.commit();
//    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
//    public static class PlaceholderFragment extends Fragment {
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            return rootView;
//        }
//
//    }
    public static class SplashScreenFragment extends Fragment{
        public  SplashScreenFragment(){

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main,container,false);
            return  rootView;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
