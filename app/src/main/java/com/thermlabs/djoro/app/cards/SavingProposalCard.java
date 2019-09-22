package com.thermlabs.djoro.app.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thermlabs.djoro.app.MainActivity;
import com.thermlabs.djoro.app.R;
import com.thermlabs.djoro.app.model.json.savings.SavingProposal;
import com.thermlabs.djoro.app.model.json.savings.SavingProposalCampaign;

import java.util.Locale;

import it.gmariotti.cardslib.library.internal.Card;

public class SavingProposalCard extends Card {

    protected SavingProposal savingProposal;
    protected ImageView image;
    protected TextView title;
    protected TextView content;
    protected TextView amount;
    protected TextView currency;

    private static final String TAG="SavingProposalCard";

    public SavingProposalCard(Context context, SavingProposal proposal) {
        super(context, R.layout.card_saving_proposal_inner_main);
        this.savingProposal = proposal;
        init();
    }

    public SavingProposalCard(Context context, int innerLayout, SavingProposal proposal) {
        super(context, innerLayout);
        this.savingProposal = proposal;
        init();
    }

    private void init() {
        //Add ClickListener
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
            //TODO How to give parameters to a new fragment ? Hint Use setArguments and Bundle !
            MainActivity activity = ((MainActivity)getContext());
            activity.openFragment(activity.selectFragment(MainActivity.CASE_SET_AWAY_PERIOD));
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        //Retrieve elements
        title = (TextView)this.mInnerView.findViewById(R.id.card_saving_proposal_title);
        content = (TextView)this.mInnerView.findViewById(R.id.card_saving_proposal_content);
        amount = (TextView)this.mInnerView.findViewById(R.id.card_saving_proposal_inner_amount);
        image = (ImageView)this.mInnerView.findViewById(R.id.card_saving_proposal_type_image);
        currency = (TextView)this.mInnerView.findViewById(R.id.card_saving_proposal_inner_currency);


        refreshView();
    }

    public void refreshView(){

        switch(savingProposal.getType()) {
            case CAMPAIGN:
                SavingProposalCampaign s = (SavingProposalCampaign)savingProposal;
                amount.setText(String.format(Locale.US, "%.2f", s.getAmount()));
                title.setText(s.getTitle());
                content.setText(s.getContent());
                currency.setText(s.getCurrency().getSymbol());
                image.setImageResource(R.drawable.tools);
                break;
            case SCHEDULE:
                image.setImageResource(R.drawable.calendar);
                break;
            case CALENDAR:
                image.setImageResource(R.drawable.calendar);
                break;
            case HOLIDAYS:
                image.setImageResource(R.drawable.skiing);
                break;
            case COMFORT:
            case TEMPERATURE:
            case WEEKEND:
            default:
                image.setImageResource(R.drawable.moon);
        }
    }





}
