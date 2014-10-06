package com.flores.nico.wallet;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.flores.nico.database.Debt;
import com.flores.nico.utils.AlarmReceiver;

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DebtFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class DebtFragment extends Fragment {
    private static Date reminderDate;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    public static DebtFragment newInstance() {
        return new DebtFragment();
    }
    public DebtFragment() {
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
        View layout = inflater.inflate(R.layout.fragment_debt, container, false);

        EditText reminder = (EditText) layout.findViewById(R.id.debtFragmentInputReminder);
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                showDatePicker();
            }
        });

        Button saveButton = (Button) layout.findViewById(R.id.debtFragmentBtnSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                saveDebt();
            }
        });
        return layout;
    }

    private void showDatePicker() {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(), "datepicker");
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog
            .OnDateSetListener {

        @Override
        public Dialog onCreateDialog (Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet (DatePicker datePicker, int year, int month, int day) {
            reminderDate = new Date(datePicker.getCalendarView().getDate());
            EditText reminder = (EditText) getActivity().findViewById(R.id.debtFragmentInputReminder);
            reminder.setText(String.format("%1$ta %1$td %1$tb %1$tY", reminderDate));
        }

    }

    private void saveDebt() {
        Toast toast;
        int duration = Toast.LENGTH_SHORT;
        String message;
        Context context = getActivity().getApplicationContext();

        EditText debtAmount = (EditText) getActivity().findViewById(R.id.debtFragmentInputAmount);
        EditText debtDescription = (EditText) getActivity().findViewById(R.id
                .debtFragmentInputDescription);
        if (debtAmount.getText().length() > 0) {
            double amount = Double.parseDouble(debtAmount.getText().toString());
            String description = debtDescription.getText().toString();

            Debt debt = new Debt(amount, false, reminderDate, description);
            debt.save();

            alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmReceiver.class);
            alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(reminderDate);
            calendar.set(Calendar.HOUR_OF_DAY, 9);

//            alarmMgr.set(AlarmManager.RTC, calendar.getTimeInMillis(), alarmIntent);
            alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 30
                    * 1000, alarmIntent);

            resetView();

            message = context.getString(R.string.toast_debt_fragment_debt_saved);
        } else {
            message = context.getString(R.string.toast_debt_fragment_debt_error);
        }
        toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    private void resetView() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new DebtFragment())
                .commit();
    }
}
