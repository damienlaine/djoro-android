package com.thermlabs.djoro.app.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.thermlabs.djoro.app.DjoroApplication;
import com.thermlabs.djoro.app.R;
import com.thermlabs.djoro.app.model.json.savings.Program;
import com.thermlabs.djoro.app.controllers.SiteListener;
import com.thermlabs.djoro.app.model.json.savings.SavingProposalSchedule;
import com.thermlabs.djoro.app.model.site.TempControlMode;
import com.thermlabs.djoro.app.request.NewSavingResultPayload;
import com.thermlabs.djoro.tools.DateToolbox;

import java.util.Date;

public class SetAwayDurationDialogFragment extends DialogFragment {

    private Spinner durationPeriodSpinner;
    private NumberPicker durationNumberPicker;
    private Activity mActivity;

    public interface SetAwayDialogListener {
        public void onDialogPositiveClick();
        public void onDialogNegativeClick();
    }

    // Use this instance of the interface to deliver action events
    SetAwayDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;

        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (SetAwayDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View customView = inflater.inflate(R.layout.dialog_set_away_duration, null);
        durationPeriodSpinner = (Spinner) customView.findViewById(R.id.dialog_set_away_duration_choice_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                customView.getContext(),
                R.array.set_away_duration_unit,
                android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        durationPeriodSpinner.setAdapter(adapter);

        //Limited to 24 hours or 24 days.
        durationNumberPicker = (NumberPicker) customView.findViewById(R.id.dialog_set_away_duration_choice_number_picker);
        durationNumberPicker.setMinValue(1);
        durationNumberPicker.setMaxValue(24);

        builder.setView(customView)
               .setMessage("Durée de l'absence ?")
               .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        Date now = new Date();
                        int duration = durationNumberPicker.getValue();
                        Date endDate;
                        switch(durationPeriodSpinner.getSelectedItemPosition()) {
                            case 0: //Hours
                                endDate = DateToolbox.addHours(now, duration);
                                break;
                            case 1: //Days
                                endDate = DateToolbox.addDays(now, duration);
                                break;
                            default:
                                //TODO Give more information in the exception
                                //TODO Create DJORO exception instead of using unchecked exception like the following one.
                                throw new RuntimeException("Bad duration unit must be hours or days but is something else");
                        }

                        Program program = new Program(now, endDate, TempControlMode.tempAway);
                        SavingProposalSchedule setAwaySaving = new SavingProposalSchedule(program);

                        Log.d("Setaway", setAwaySaving.toString());

                        ((DjoroApplication) getActivity().getApplication()).getTempController().newSaving(
                                setAwaySaving,
                                new SetAwaySiteListener());
                    }
               })
               .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
               });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    public class SetAwaySiteListener implements SiteListener<NewSavingResultPayload> {

        @Override
        public void onSuccess(NewSavingResultPayload newSavingPayload) {
            Toast.makeText(mActivity, "success", Toast.LENGTH_SHORT).show();
            mListener.onDialogPositiveClick();
        }

        @Override
        public void onFailure(Exception exception) {
            Toast.makeText(mActivity, "failure", Toast.LENGTH_SHORT).show();
            mListener.onDialogNegativeClick();
        }
    }

}
