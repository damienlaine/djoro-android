package com.thermlabs.djoro.app.cards;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.thermlabs.djoro.app.MainActivity;
import com.thermlabs.djoro.app.R;

import java.util.Locale;

import info.hoang8f.widget.FButton;
import it.gmariotti.cardslib.library.internal.Card;

public class SavingsReportCard extends Card {

    protected int count = 0;
    protected TextView amount;
    protected TextView cents;
    protected TextView currency;
    protected FButton saveMore;

    public SavingsReportCard(Context context) {
        super(context, R.layout.card_savings_report_inner_main);
    }

    public SavingsReportCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    private void init() {



        //Add ClickListener
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getContext(), "Click Listener card=" + count, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        //Retrieve elements
        amount = (TextView)this.mInnerView.findViewById(R.id.card_savings_report_inner_amount);
        cents = (TextView)this.mInnerView.findViewById(R.id.card_savings_report_inner_cents);
        currency = (TextView)this.mInnerView.findViewById(R.id.card_savings_report_inner_currency);
        saveMore = (FButton)this.mInnerView.findViewById(R.id.card_savings_report_inner_save_more_button);

        saveMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = ((MainActivity)getContext());
                activity.openFragment(activity.selectFragment(MainActivity.CASE_SAVE_MORE));
            }
        });

        amount.setText("--");
        cents.setText("--");
    }

    public void setSavingsValue(float newValue) {
        /* In the following expression I have forced the Locale to US.
         * If the local is french, the separator is a comma, if the locale is US, the separator is a dot.
         * By forcing the locale, I fix the separator to be a dot and then it is easily splitable.
         */
        String newValueAsString = String.format(Locale.US, "%.2f", newValue);

        Log.d("savings", newValueAsString);
        String parts[] = newValueAsString.split("[.]");
        Log.d("savings", String.format("parts[] length = %d",parts.length));
        if (amount != null)
            amount.setText(parts[0]);

        if (cents != null) {
            try {
                cents.setText(parts[1]);
            } catch (IndexOutOfBoundsException ex) {
                Log.d("savingsReport", ex.toString());
                cents.setText("00");
            }
        }

    }

}
