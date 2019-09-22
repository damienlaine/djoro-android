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
import com.thermlabs.djoro.app.controllers.SiteListener;
import com.thermlabs.djoro.app.model.TempMode;
import com.thermlabs.djoro.app.model.site.TempControlMode;
import com.thermlabs.djoro.app.request.UpdateThermpointResultPayload;

public class SetTemperatureDialogFragment extends DialogFragment {

    private NumberPicker temperaturePicker;

    private Spinner tempModeSpinner;

    private Activity mActivity;

    private float currentTemperature;

    //Used as argument in the bundle
    public static final String CURRENT_TEMPERATURE = "currentTemperature";



    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        currentTemperature = Float.valueOf(args.getString(CURRENT_TEMPERATURE));
    }

    public interface TemperatureDialogListener {
        public void onTempDialogPositiveClick(TempMode newMode, float newMeasuredTemperature);
        public void onTempDialogNegativeClick();
    }

    // Use this instance of the interface to deliver action events
    TemperatureDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;

        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (TemperatureDialogListener) activity;
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

        View customView = inflater.inflate(R.layout.dialog_set_temperature, null);

        float minValue = 7.0f;
        float maxValue = 30.0f;
        float step = 0.5f;
        int nbElements = Math.round((maxValue - minValue) / step) + 1;
        final String[] valueSet = new String[nbElements];
        int index = 0;
        int currentTemperatureIndex = 0;
        for (float i = minValue; i <= maxValue; i += step) {
            if (i == currentTemperature) {
                currentTemperatureIndex = index;
                Log.d("temperature", String.format("currentTemperattureindex == %d", currentTemperatureIndex));
            }

            valueSet[index++] = String.valueOf(i);
            Log.d("DialogTemp", String.format("%f ", i));
        }
        temperaturePicker = (NumberPicker) customView.findViewById(R.id.dialog_set_temperature_choice_number_picker);
        temperaturePicker.setDisplayedValues(valueSet);
        temperaturePicker.setMinValue(0);
        temperaturePicker.setMaxValue(valueSet.length - 1);
        temperaturePicker.setValue(currentTemperatureIndex);

        tempModeSpinner = (Spinner) customView.findViewById(R.id.temp_mode_spinner);

        tempModeSpinner.setAdapter(new ArrayAdapter<TempControlMode>(this.getActivity(), android.R.layout.simple_list_item_1, TempControlMode.values()));

        builder.setView(customView)
                .setMessage("Nouvelle température")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        float newTemperature = Float.valueOf(valueSet[temperaturePicker.getValue()]);
                        TempControlMode mode = (TempControlMode)tempModeSpinner.getSelectedItem();
                        ((DjoroApplication)getActivity().getApplication()).getTempController().updateThermpoint(
                                new TempMode(mode, newTemperature),
                                new UpdateThermpointSiteListener());
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    public class UpdateThermpointSiteListener implements SiteListener<UpdateThermpointResultPayload> {

        @Override
        public void onSuccess(UpdateThermpointResultPayload result) {
            Log.d("SetTemperature", result.toString());
        }

        @Override
        public void onFailure(Exception exception) {
            Toast.makeText(mActivity, "failure", Toast.LENGTH_SHORT).show();
            mListener.onTempDialogNegativeClick();
        }
    }


}
