package vn.heallife.duchv.ouralarm.ui.fragment;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import vn.heallife.duchv.ouralarm.AlarmReceiver;
import vn.heallife.duchv.ouralarm.R;
import vn.heallife.duchv.ouralarm.background.model.Alarm;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlarmEdit.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AlarmEdit extends Fragment {

    private OnFragmentInteractionListener mListener;

    private String TAG = "ALARMEDIT";

    private static TextView hour;
    private static TextView minus;
    private EditText message;
    private ImageButton cancelButton;
    private ImageButton saveButton;

    private static AlarmManager alarmManager;
    private static PendingIntent pendingIntent;
    private int position = -1;


    public String getTAG() {
        return TAG;
    }

    public static AlarmEdit newInstance(){
        AlarmEdit alarmEdit = new AlarmEdit();
        return  alarmEdit;
    }
    public static AlarmEdit editInstance(int position){
        AlarmEdit alarmEdit = new AlarmEdit();
        alarmEdit.position = position;
        return  alarmEdit;
    }
    public AlarmEdit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_alarm_edit, container, false);
        hour =(TextView) view.findViewById(R.id.alarm_hour);
        minus = (TextView) view.findViewById(R.id.alarm_minus);
        message = (EditText) view.findViewById(R.id.alarm_message);
        cancelButton = (ImageButton) view.findViewById(R.id.add_cancel);
        saveButton = (ImageButton) view.findViewById(R.id.add_done);

        LinearLayout setTime = (LinearLayout)view.findViewById(R.id.set_date_time);

        if(position!= -1){
            final Alarm alarm = AlarmView.arrAlarm.get(position);
            hour.setText(alarm.getHour());
            message.setText(alarm.getText());
            minus.setText(alarm.getMinus());
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    AlarmView.arrAlarm.get(position).editAlarm(hour.getText().toString(),minus.getText().toString(),message.getText().toString(),true);
                    alarm.editAlarm(hour.getText().toString(),minus.getText().toString(),message.getText().toString(),true);
                    AlarmView.adapter.notifyDataSetChanged();
                    setAlarm(alarm.getHourInt(), alarm.getMinusInt(), alarm.getText(), getActivity(), position);
                    backToAlarmList();
                }
            });
        }else{

            Calendar c = Calendar.getInstance();
            hour.setText(""+c.get(Calendar.HOUR_OF_DAY));
            minus.setText(""+c.get(Calendar.MINUTE));

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Alarm alarm = new Alarm(hour.getText().toString(), minus.getText().toString(), message.getText().toString(), true);
                    AlarmView.arrAlarm.add(alarm);
                    AlarmView.adapter.notifyDataSetChanged();
//                    AlarmReceiver receiver = new AlarmReceiver();
//                    receiver.setAlarm(alarm.getHourInt(),alarm.getMinusInt(),alarm.getText(),getActivity());
//                    AlarmView.arrAlarmReceiver.add(receiver);
                    setAlarm(alarm.getHourInt(), alarm.getMinusInt(), alarm.getText(), getActivity(), -1);
                    backToAlarmList();
                }
            });

        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToAlarmList();
            }
        });
        setTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showPTimePickerDialog(view);
            }
        });



        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private void backToAlarmList(){
        AlarmView alarmView = AlarmView.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, alarmView).commit();
    }

//    private class MyButton implements OnClickListener{
//        @Override
//        public void onClick(View v){
//            switch (v.getId())
//            {
//                case R.id.set_date_time:
//                    showPTimePickerDialog();
//            }
//        }
//
//    }
    private void showPTimePickerDialog(View v){
        DialogFragment fragment = new TimePickerFragment();
        fragment.show(getFragmentManager(),"Time Picker");
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
//            final Calendar c = Calendar.getInstance();
//            int hour = c.get(Calendar.HOUR_OF_DAY);
//            int minute = c.get(Calendar.MINUTE);
            int h = Integer.parseInt(hour.getText().toString());
            int m = Integer.parseInt(minus.getText().toString());

            // Create a new instance of TimePickerDialog and return it
//            TimePickerDialog pickerDialog = new TimePickerDialog(getActivity(),android.R.style.Theme_DeviceDefault, this, h, m,DateFormat.is24HourFormat(getActivity()));
            TimePickerDialog pickerDialog = new TimePickerDialog(getActivity(), this, h, m,DateFormat.is24HourFormat(getActivity()));
//            Log.d(pickerDialog.get);
            return pickerDialog;
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            hour.setText(""+hourOfDay);
            minus.setText(""+minute);
        }
    }


    public static void setAlarm(int h, int m, String text,Context context,@Nullable int position){
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,AlarmReceiver.class);
        intent.putExtra("message", ""+text);

        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        //create Calender
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE,m);
        Log.d("Notifi", "schedule Notifi");
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),1000*60*10,pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
    }

    public static void cancelAlarm(){
        if(alarmManager!= null){
            alarmManager.cancel(pendingIntent);
        }
    }

}
