package com.thermlabs.djoro.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thermlabs.djoro.app.DjoroApplication;
import com.thermlabs.djoro.app.R;
import com.thermlabs.djoro.app.cards.SavingProposalCard;
import com.thermlabs.djoro.app.controllers.SiteController;
import com.thermlabs.djoro.app.controllers.SiteListener;
import com.thermlabs.djoro.app.model.json.savings.SavingProposal;
import com.thermlabs.djoro.app.model.json.savings.SavingProposalCampaign;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

public class SavingCardsFragment extends Fragment {

    private SiteController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_savings_cards, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        this.controller = ((DjoroApplication)getActivity().getApplication()).getTempController();
        this.controller.setSavingCardsListener(new SavingCardsListener());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.changevalue, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<SavingProposalCampaign> savings = controller.getSavings();
        if (savings != null){
            displayCards(savings);
        } else {
            controller.refreshSavings(new SavingCardsListener());
        }
    }

    public void refreshCards(){
        controller.refreshSavings(new SavingCardsListener());
    }

    public void displayCards(List<SavingProposalCampaign> savings){

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(), genCards(savings));

        CardListView listView = (CardListView) getActivity().findViewById(R.id.saving_proposal_list_view);

        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }
    }

    private List<Card> genCards(List<SavingProposalCampaign> savings) {
        ArrayList<Card> cards = new ArrayList<Card>();

        for (SavingProposal saving: savings) {
            SavingProposalCard card = new SavingProposalCard(getActivity(), saving);
            cards.add(card);
        }

        return cards;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                refreshCards();
                Toast.makeText(getActivity(), "Refreshed savings", Toast.LENGTH_SHORT).show();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public final class SavingCardsListener implements SiteListener<List<SavingProposalCampaign>> {

        @Override
        public void onSuccess(List<SavingProposalCampaign> savings) {
            Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
            displayCards(savings);
        }

        @Override
        public void onFailure(Exception exception) {
            Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();
        }
    }

}
