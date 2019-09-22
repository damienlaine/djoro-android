package com.thermlabs.djoro.app.cards;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thermlabs.djoro.app.MainActivity;
import com.thermlabs.djoro.app.R;
import com.thermlabs.djoro.app.dialog.SetAwayDurationDialogFragment;
import com.thermlabs.djoro.app.dialog.SetTemperatureDialogFragment;
import com.thermlabs.djoro.app.model.TempMode;
import java.util.Locale;
import info.hoang8f.widget.FButton;
import it.gmariotti.cardslib.library.internal.Card;

public class TempControlCard extends Card {

    protected FButton setAwayButton;
    protected ImageView cardIcon;
    protected TextView cardTitle;
    protected RelativeLayout titleContainerLayout;
    protected TextView targetTemperature;
    protected TextView measuredTemperature;
    protected FragmentManager fragmentManager;
    protected MainActivity mActivity;

    public TempControlCard(Context context, FragmentManager fragmentManager) {
        super(context, R.layout.card_temp_control_inner_main);
        this.fragmentManager = fragmentManager;
        this.mActivity = (MainActivity)context;
        init();
    }

    private void init() {
        //Add ClickListener
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Bundle bundle = new Bundle();
                TextView textTemp = (TextView)view.findViewById(R.id.card_temp_control_inner_target_temperature);
                bundle.putCharSequence(SetTemperatureDialogFragment.CURRENT_TEMPERATURE, textTemp.getText());
                SetTemperatureDialogFragment dialog = new SetTemperatureDialogFragment();
                dialog.setArguments(bundle);
                dialog.show(fragmentManager, "SetTemperatureDialog");
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        //Retrieve elements
        cardIcon = (ImageView)this.mInnerView.findViewById(R.id.card_temp_control_title_icon);
        cardTitle = (TextView)this.mInnerView.findViewById(R.id.card_temp_control_title);
        titleContainerLayout = (RelativeLayout)this.mInnerView.findViewById(R.id.card_temp_control_title_container);
        targetTemperature = (TextView)this.mInnerView.findViewById(R.id.card_temp_control_inner_target_temperature);
        measuredTemperature = (TextView)this.mInnerView.findViewById(R.id.card_temp_control_inner_measured_temperature_value);
        setAwayButton = (FButton)this.mInnerView.findViewById(R.id.card_temp_control_set_away_button);
    }

    private void setActionButtonToSetAway() {
        setAwayButton.setText(R.string.SetAwayButton);
        setAwayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of the dialog fragment and show it
                SetAwayDurationDialogFragment dialog = new SetAwayDurationDialogFragment();
                dialog.show(fragmentManager, "NoticeDialogFragment");
            }
        });
    }

    private void setActionButtonToIamBack() {
        setAwayButton.setText(R.string.IAmBackButton);
        setAwayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of the dialog fragment and show it

            }
        });
    }

    public void setTargetTemperatureTextView(float newTemperature) {
        String newTempAsString = String.format(Locale.US, "%.1f", newTemperature);

        Log.d("targetTemperature: ", newTempAsString);
        String parts[] = newTempAsString.split("[.]");
        Log.d("target temperature", String.format("parts[] length = %d",parts.length));

        if (targetTemperature != null)
            targetTemperature.setText(parts[0]);
    }

    public void setMeasuredTemperatureTextView(float newTemperature) {
        String newTempAsString = String.format(Locale.US, "%.2f", newTemperature);

        if (measuredTemperature != null)
            measuredTemperature.setText(newTempAsString);
    }

    public void switchToMode(TempMode newMode) {
        switch(newMode.getMode()){
            case tempAway:
                cardTitle.setText(R.string.card_temp_control_title_mode_away);
                cardTitle.setTextColor(
                        getContext().getResources().getColor(R.color.card_temp_control_title_text_away));
                cardIcon.setImageResource(R.drawable.ic_away);
                titleContainerLayout.setBackgroundColor(
                        getContext().getResources().getColor(R.color.card_temp_control_title_container_away));

                setActionButtonToIamBack();
                break;
            case tempNight:
                cardTitle.setText(R.string.card_temp_control_title_mode_night);
                cardTitle.setTextColor(
                        getContext().getResources().getColor(R.color.card_temp_control_title_text_night));
                cardIcon.setImageResource(R.drawable.ic_night);
                titleContainerLayout.setBackgroundColor(
                        getContext().getResources().getColor(R.color.card_temp_control_title_container_night));
                setActionButtonToSetAway();
                break;
            case tempDay:
                cardTitle.setText(R.string.card_temp_control_title_mode_comfort);
                cardTitle.setTextColor(
                        getContext().getResources().getColor(R.color.card_temp_control_title_text_comfort));
                cardIcon.setImageResource(R.drawable.ic_comfort);
                titleContainerLayout.setBackgroundColor(
                        getContext().getResources().getColor(R.color.card_temp_control_title_container_comfort));
                setActionButtonToSetAway();
                break;
            case boostTempDay:
                cardTitle.setText(R.string.card_temp_control_title_mode_boost);
                cardTitle.setTextColor(
                        getContext().getResources().getColor(R.color.card_temp_control_title_text_boost));
                cardIcon.setImageResource(R.drawable.ic_boost);
                titleContainerLayout.setBackgroundColor(
                        getContext().getResources().getColor(R.color.card_temp_control_title_container_boost));
                setActionButtonToSetAway();
                break;
            default:
                assert(false);
                break;
        }
        setTargetTemperatureTextView(newMode.getTargetTemp());
    }

}
