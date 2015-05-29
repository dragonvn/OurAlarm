package vn.heallife.duchv.ouralarm.ui;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import vn.heallife.duchv.ouralarm.Config;
import vn.heallife.duchv.ouralarm.R;
import vn.heallife.duchv.ouralarm.background.model.Alarm;
import vn.heallife.duchv.ouralarm.ui.fragment.AlarmDelete;
import vn.heallife.duchv.ouralarm.ui.fragment.AlarmEdit;
import vn.heallife.duchv.ouralarm.ui.fragment.AlarmView;

/**
 * Created by duchv on 2/13/15.
 */
public class AlarmListAdapter extends ArrayAdapter<Alarm> {

    private Context context;
    private int layoutId;
    private ArrayList<Alarm> alarms;
    private int processId;

    public AlarmListAdapter(Context context, int layoutId, ArrayList<Alarm> alarms, int processId) {
        super(context, layoutId, alarms);
        this.context = context;
        this.alarms = alarms;
        this.layoutId = layoutId;
        this.processId = processId;

    }

    @Override
    public Alarm getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        /**
         * không làm:
         * if(convertView==null)
         * {
         * LayoutInflater inflater=
         * context.getLayoutInflater();
         * convertView=inflater.inflate(layoutId, null);
         * }
         * Lý do là ta phải xử lý xóa phần tử Checked, nếu dùng If thì
         * nó lại checked cho các phần tử khác sau khi xóa vì convertView
         * lưu lại trạng thái trước đó
         */
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        convertView = inflater.inflate(R.layout.alarm_list_item,null);
        convertView = inflater.inflate(layoutId,null);
        final TextView timeText = (TextView)convertView.findViewById(R.id.alarm_item_time);
        final TextView message = (TextView)convertView.findViewById(R.id.alarm_item_message);
        final Alarm alarm = alarms.get(position);
        message.setText(alarm.getText());
        timeText.setText(alarm.getHour()+ ":"+alarm.getMinus());
        if(processId == Config.NEW_EDIT_ALARM){

            final Switch enable = (Switch) convertView.findViewById(R.id.alarm_item_switch);
            enable.setChecked(alarm.isEnable());
            if(alarms.size()> 0&&position>=0){
                enable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(alarm.isEnable()){
                            AlarmEdit.cancelAlarm();
                        }else{
                            AlarmEdit.setAlarm(alarm.getHourInt(),alarm.getMinusInt(),alarm.getText(),getContext(),position);
                        }
                        alarm.setEnable(!alarm.isEnable());
                    }
                });
            }
        }else if(processId == Config.DELETE_ALARM)
        {
            final CheckBox deleteCheck = (CheckBox) convertView.findViewById(R.id.delete_alarm_checked);
            if(alarms.size()> 0&&position>=0){
                deleteCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlarmDelete.getInstance().setDeleteEnable();
                    }
                });
            }


        }

        message.setText(alarm.getText());
        timeText.setText(alarm.getHour()+ ":"+alarm.getMinus());

        return convertView;

    }
}
