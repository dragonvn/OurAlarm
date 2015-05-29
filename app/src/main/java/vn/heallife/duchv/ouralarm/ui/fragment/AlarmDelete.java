package vn.heallife.duchv.ouralarm.ui.fragment;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;

import vn.heallife.duchv.ouralarm.Config;
import vn.heallife.duchv.ouralarm.R;
import vn.heallife.duchv.ouralarm.background.model.Alarm;
import vn.heallife.duchv.ouralarm.ui.AlarmListAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmDelete extends Fragment {

    private String TAG ="DELETE_ALARM_FRAGMENT";

    public String getTAG() {
        return TAG;
    }
    private static AlarmDelete alarmDelete;

    public static AlarmDelete getInstance(){
        if(alarmDelete == null){
               alarmDelete = new AlarmDelete();
        }
        return alarmDelete;
    }

    public AlarmDelete() {
        // Required empty public constructor
    }
    ListView alarmList;
    Button cancel;
    Button delete;
    ArrayList<Alarm> alamArray = AlarmView.arrAlarm;
    ArrayAdapter<Alarm> adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alarm_delete, container, false);
        if(view == null){
            return view;
        }
        cancel = (Button) view.findViewById(R.id.delete_cancel);
        delete = (Button) view.findViewById(R.id.delete_done);
        delete.setEnabled(false);
        alarmList = (ListView) view.findViewById(R.id.delete_alarm_list);
        adapter = new AlarmListAdapter(getActivity(),R.layout.alarm_delete_item,alamArray, Config.DELETE_ALARM);
        alarmList.setAdapter(adapter);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDO
                backToAlarmList();
            }
        });
        delete .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkDelete()) {
                    AlarmEdit.cancelAlarm();
                    deleteProcessing();
                    backToAlarmList();
                }
            }
        });



        return view;
    }
    private void showFragment(int id,Fragment fragment, String fragmentTag){
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.replace(id,fragment,fragmentTag);
        ft.addToBackStack(fragmentTag);
        ft.commit();
    }
    private void backToAlarmList(){
        AlarmView alarmView = AlarmView.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, alarmView).commit();
    }
    private void deleteProcessing(){
        for(int i = alarmList.getChildCount()-1; i>=0;i--){
            View v = alarmList.getChildAt(i);
            CheckBox checked = (CheckBox)v.findViewById(R.id.delete_alarm_checked);
            if(checked.isChecked()){
                alamArray.remove(i);
            }
        }
        adapter.notifyDataSetChanged();
    }
//    public void checkDelete(){
//        for(int i = alarmList.getChildCount()-1; i>=0;i--){
//            View v = alarmList.getChildAt(i);
//            CheckBox checked = (CheckBox)v.findViewById(R.id.delete_alarm_checked);
//            if(checked.isChecked()){
//                delete.setEnabled(true);
//            }else{
//                if(i==0){
//                    delete.setEnabled(false);
//                }
//            }
//        }
//    }

    private boolean checkDelete(){
        for(int i = alarmList.getChildCount()-1; i>=0;i--){
            View v = alarmList.getChildAt(i);
            CheckBox checked = (CheckBox)v.findViewById(R.id.delete_alarm_checked);
            if(checked.isChecked()) {
                return true;
            }
        }
        return false;
    }

    public void setDeleteEnable(){
            delete.setEnabled(checkDelete());

    }
}
