package com.thermlabs.djoro.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.thermlabs.djoro.app.DjoroApplication;
import com.thermlabs.djoro.app.R;
import com.thermlabs.djoro.app.cards.SavingsReportCard;
import com.thermlabs.djoro.app.cards.TempControlCard;
import com.thermlabs.djoro.app.controllers.SiteController;
import com.thermlabs.djoro.app.controllers.SiteListener;
import com.thermlabs.djoro.app.model.TempMode;
import com.thermlabs.djoro.app.model.site.SiteState;

import it.gmariotti.cardslib.library.view.CardView;

public class HomeCardsFragment extends Fragment {

    protected ScrollView mScrollView;

    private SavingsReportCard savingsReportCard;
    private TempControlCard tempControlCard;
    private SiteController tempCtrl;
    private HomeCardsListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home_cards, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        listener = new HomeCardsListener();

        //Get a reference to the temperature controller on activity creation.
        this.tempCtrl = ((DjoroApplication)getActivity().getApplication()).getTempController();
        //register the homecardslistener to the Site Controller
        this.tempCtrl.setHomeCardsListener(listener);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mScrollView = (ScrollView) getActivity().findViewById(R.id.fragment_home_cards_scrollview);

        initCards();

        //Getting the temperature values from the Temperature controller
        //tempControlCard.setMeasuredTemperatureTextView(tempCtrl.getCurrentMeasuredTemperature());
        //switchToMode(tempCtrl.getCurrentTempMode());
        refreshCards();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.changevalue, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                refreshCards();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initCards() {
        initSavingsReportCard();
        initTempControlCard();
    }

    private void initSavingsReportCard() {

        //Create a Card
        savingsReportCard = new SavingsReportCard(getActivity(), R.layout.card_savings_report_inner_main);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.cardSavingsReport);
        cardView.setCard(savingsReportCard);
    }

    private void initTempControlCard() {

        //Create a Card
        tempControlCard = new TempControlCard(getActivity(), getFragmentManager());

        //Set the card inner text
        tempControlCard.setTitle(getString(R.string.card_savings_report_inner_title));

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.cardControlTemp);
        cardView.setCard(tempControlCard);
    }

    public void switchToMode(TempMode newMode){
        tempControlCard.switchToMode(newMode);
    }

    public void updateCurrentMeasuredTemp(float newMeasuredTemp) {
        tempControlCard.setMeasuredTemperatureTextView(newMeasuredTemp);
    }

    private void refreshCards(){
        tempCtrl.refreshSite(listener);
    }

    public HomeCardsListener getListener() {
        return listener;
    }

    public final class HomeCardsListener implements SiteListener<SiteState> {

        @Override
        public void onSuccess(SiteState siteState) {
            if (getActivity() != null) {
                Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
            }
            savingsReportCard.setSavingsValue(siteState.getSavings());
            tempControlCard.setMeasuredTemperatureTextView(siteState.getTempState().getCurrentMeasuredTemp());
            switchToMode(siteState.getTempState().getCurrentMode());
        }

        @Override
        public void onFailure(Exception exception) {
            if (getActivity() != null) {
                Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
