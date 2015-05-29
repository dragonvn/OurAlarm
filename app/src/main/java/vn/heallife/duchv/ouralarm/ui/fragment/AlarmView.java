package vn.heallife.duchv.ouralarm.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import vn.heallife.duchv.ouralarm.AlarmReceiver;
import vn.heallife.duchv.ouralarm.Config;
import vn.heallife.duchv.ouralarm.R;
import vn.heallife.duchv.ouralarm.background.model.Alarm;
import vn.heallife.duchv.ouralarm.ui.AlarmListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlarmView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlarmView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlarmView extends Fragment {


    public String getTAG() {
        return TAG;
    }

    private final String TAG = "ALARMVIEW";

    public static ArrayList<Alarm> arrAlarm = new ArrayList<Alarm>();
    public static ArrayAdapter<Alarm> adapter = null;
    public static ArrayList<AlarmReceiver> arrAlarmReceiver = new ArrayList<AlarmReceiver>();
    ImageButton addAlarm;
    ImageButton removeAlarm;
    ListView listAlarm;





    public static AlarmView newInstance() {
        AlarmView fragment = new AlarmView();

        return fragment;
    }
    public AlarmView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_alarm_view, container, false);
        if(v == null){
            return v;
        }

        addAlarm = (ImageButton) v.findViewById(R.id.add_alarm);
        removeAlarm = (ImageButton) v.findViewById(R.id.remove_alarm);
        listAlarm = (ListView) v.findViewById(R.id.alarm_list);
        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmEdit alarmEdit = AlarmEdit.newInstance();
                showFragment(R.id.container,alarmEdit,alarmEdit.getTAG());

            }
        });
        removeAlarm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlarmDelete alarmDelete = AlarmDelete.getInstance();
                showFragment(R.id.container,alarmDelete,alarmDelete.getTAG());
            }
        });
        adapter = new AlarmListAdapter(getActivity(),R.layout.alarm_list_item,arrAlarm, Config.NEW_EDIT_ALARM);
        listAlarm.setAdapter(adapter);
        listAlarm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlarmEdit alarmEdit = AlarmEdit.editInstance(position);
                showFragment(R.id.container,alarmEdit,alarmEdit.getTAG());

            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

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
//        mListener = null;
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

    private void showFragment(int id,Fragment fragment, String fragmentTag){
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.replace(id,fragment,fragmentTag);
        ft.addToBackStack(fragmentTag);
        ft.commit();
    }

}
