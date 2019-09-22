package com.thermlabs.djoro.app.controllers;

import android.content.SharedPreferences;
import android.util.Log;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.thermlabs.djoro.app.fragment.HomeCardsFragment;
import com.thermlabs.djoro.app.fragment.SavingCardsFragment;
import com.thermlabs.djoro.app.fragment.TypicalWeekFragment;
import com.thermlabs.djoro.app.model.TempMode;
import com.thermlabs.djoro.app.model.json.Device;
import com.thermlabs.djoro.app.model.json.MoneySaved;
import com.thermlabs.djoro.app.model.json.SiteResult;
import com.thermlabs.djoro.app.model.json.savings.SavingProposal;
import com.thermlabs.djoro.app.model.json.savings.SavingProposalCampaign;
import com.thermlabs.djoro.app.model.json.savings.SavingResult;
import com.thermlabs.djoro.app.model.json.WeekScheduleResult;
import com.thermlabs.djoro.app.model.site.SiteState;
import com.thermlabs.djoro.app.model.site.TempControlMode;
import com.thermlabs.djoro.app.model.site.TempControlState;
import com.thermlabs.djoro.app.model.site.TypicalWeekSchedule;
import com.thermlabs.djoro.app.preferences.PreferencesNames;
import com.thermlabs.djoro.app.request.GetTypicalWeekScheduleRequest;
import com.thermlabs.djoro.app.request.NewSavingRequest;
import com.thermlabs.djoro.app.request.NewSavingResult;
import com.thermlabs.djoro.app.request.NewSavingResultPayload;
import com.thermlabs.djoro.app.request.SavingRequest;
import com.thermlabs.djoro.app.request.SiteRequest;
import com.thermlabs.djoro.app.request.UpdateThermpointRequest;
import com.thermlabs.djoro.app.request.UpdateThermpointResult;
import com.thermlabs.djoro.app.request.UpdateThermpointResultPayload;
import java.util.List;
import com.thermlabs.djoro.app.request.UpdateTypicalWeekScheduleRequest;

public class SiteController {

    private TempControlState tempState;

    private MoneySaved lastMoneySavedValue;

    private TypicalWeekSchedule typicalWeekSchedule;

    private List<SavingProposalCampaign> savings;

    SharedPreferences preferences;

    SpiceManager spiceManager;

    private HomeCardsFragment.HomeCardsListener homeCardsListener;
    private SavingCardsFragment.SavingCardsListener savingCardsListener;

    public SiteController(SharedPreferences preferences, SpiceManager spiceManager) {
        this.tempState = new TempControlState(17.0f, new TempMode(TempControlMode.tempDay, 21.0f));
        this.preferences = preferences;
        this.spiceManager = spiceManager;
        this.typicalWeekSchedule = new TypicalWeekSchedule();
    }

    private String getBaseUrl(){
        return preferences.getString(PreferencesNames.ServerURL, "https://api.djoro.com");
    }

    private SiteRequest getSiteRequest(){
        return new SiteRequest(getBaseUrl() + "/site/1");
    }

    public void setHomeCardsListener(HomeCardsFragment.HomeCardsListener homeCardsListener) {
        this.homeCardsListener = homeCardsListener;
    }

    public void setSavingCardsListener(SavingCardsFragment.SavingCardsListener savingCardsListener) {
        this.savingCardsListener = savingCardsListener;
    }

    /**
     * Get current temperature state
     */
    public TempControlState getCurrentState() {
        return tempState;
    }

    /**
     * Return the last measured temperature
     *
     * @return float the last measured temperature
     */
    public float getCurrentMeasuredTemperature(){
        return tempState.getCurrentMeasuredTemp();
    }

    /**
     * return the last retrieved mode
     *
     * @return TempControlMode the last temperature control mode
     */
    public TempMode getCurrentTempMode() {
        return tempState.getCurrentMode();
    }

    public void refreshSite(final SiteListener<SiteState> listener) {
        spiceManager.execute(
                getSiteRequest(),
                "json",
                DurationInMillis.ALWAYS_EXPIRED,
                new RequestListener<SiteResult>() {
                    @Override
                    public void onRequestFailure(SpiceException spiceException) {
                        listener.onFailure(spiceException);
                    }

                    @Override
                    public void onRequestSuccess(SiteResult siteResult) {
                        SiteState siteState = new SiteState();
                        Device device = siteResult.getPayload().getDevices().get(0);
                        tempState.setCurrentMeasuredTemp(device.getStatus().getMeasuredTemp());
                        TempControlMode currentMode = TempControlMode.valueOf(device.getSchedule().getNow().getThermPointName());
                        tempState.setCurrentMode(new TempMode(currentMode, device.getSchedule().getNow().getTemperature()));
                        siteState.setTempState(tempState);
                        lastMoneySavedValue = device.getMoneySaved();
                        siteState.setSavings(device.getMoneySaved().getAmount());
                        Log.d("refresh", String.format("Money saved = %f", siteState.getSavings()));
                        listener.onSuccess(siteState);
                    }
                });
    }

    public void updateThermpoint(TempMode updatedMode, final SiteListener<UpdateThermpointResultPayload> siteListener) {

        UpdateThermpointRequest request =
                new UpdateThermpointRequest(getBaseUrl() + "/site/1/device/0/thermpoint", updatedMode);

        spiceManager.execute(
                request,
                "json",
                DurationInMillis.ALWAYS_EXPIRED,
                new RequestListener<UpdateThermpointResult> () {

                    @Override
                    public void onRequestFailure( SpiceException spiceException ) {
                        siteListener.onFailure(spiceException);
                    }

                    @Override
                    public void onRequestSuccess( final UpdateThermpointResult result) {
                        Log.d("DurationDialog", String.format("request successful: %s", result.getPayload()));
                        siteListener.onSuccess(result.getPayload());
                        //TODO A second request to the server might be avoid if the new state is given in the UpdateThermpointResult
                        refreshSite(homeCardsListener);
                    }
                }
        );
    }

    public TypicalWeekSchedule getTypicalWeekSchedule() {
        return typicalWeekSchedule;
    }

    public void setTypicalWeekSchedule(TypicalWeekSchedule typicalWeekSchedule) {
        this.typicalWeekSchedule = typicalWeekSchedule;
    }

    public TypicalWeekSchedule refreshTypicalWeekSchedule(final TypicalWeekFragment.TypicalWeekListener listener) {
        GetTypicalWeekScheduleRequest request = new GetTypicalWeekScheduleRequest(
                getBaseUrl() + "/site/1/device/0/week");

        spiceManager.execute(
                request,
                "json",
                DurationInMillis.ALWAYS_EXPIRED,
                new RequestListener<WeekScheduleResult> () {

                    @Override
                    public void onRequestFailure( SpiceException spiceException ) {
                        listener.onFailure(spiceException);
                    }

                    @Override
                    public void onRequestSuccess( final WeekScheduleResult result) {
                        Log.d("DurationDialog", String.format("request successful: %s", result.getPayload()));
                        setTypicalWeekSchedule(new TypicalWeekSchedule(result.getPayload()));
                        listener.onSuccess(result);
                    }
                }
        );

        return typicalWeekSchedule;
    }


    public void updateTypicalWeekSchedule(TypicalWeekSchedule newTypicalWeekSchedule,
                                          final TypicalWeekFragment.TypicalWeekListener listener)
    {
        UpdateTypicalWeekScheduleRequest request = new UpdateTypicalWeekScheduleRequest(
                getBaseUrl() + "/site/1/device/0/week",
                newTypicalWeekSchedule);
        spiceManager.execute(
                request,
                "json",
                DurationInMillis.ALWAYS_EXPIRED,
                new RequestListener<WeekScheduleResult> () {
                    @Override
                    public void onRequestFailure( SpiceException spiceException ) {
                        listener.onFailure(spiceException);
                    }

                    @Override
                    public void onRequestSuccess( final WeekScheduleResult result) {
                        Log.d("DurationDialog", String.format("request successful: %s", result.getPayload()));
                        listener.onSuccess(result);
                        setTypicalWeekSchedule(new TypicalWeekSchedule(result.getPayload()));
                    }
                }
            );
        }


    protected void setSavings(List<SavingProposalCampaign> savings){
        this.savings = savings;
    }

    public void newSaving(SavingProposal saving, final SiteListener<NewSavingResultPayload> siteListener) {

        NewSavingRequest request =
                new NewSavingRequest(getBaseUrl() + "/site/1/device/0/saving", saving);

        spiceManager.execute(
                request,
                "apjson",
                DurationInMillis.ALWAYS_EXPIRED,
                new RequestListener< NewSavingResult > () {

                    @Override
                    public void onRequestFailure( SpiceException spiceException ) {
                        siteListener.onFailure(spiceException);
                    }

                    @Override
                    public void onRequestSuccess( final NewSavingResult result) {
                        Log.d("DurationDialog", String.format("request successful: %s", result.getPayload()));

                        siteListener.onSuccess(result.getPayload());
                        //TODO A second request to the server might be avoid if the new state is given in the NewSavingResult
                        refreshSite(homeCardsListener);
                    }
                }
        );
    }

    public List<SavingProposalCampaign> getSavings(){
        return this.savings;
    }

    public void refreshSavings(final SiteListener<List<SavingProposalCampaign>> listener){
        SavingRequest request = new SavingRequest(getBaseUrl() + "/site/1/device/0/saving");
        spiceManager.execute(
                request,
                "json",
                DurationInMillis.ALWAYS_EXPIRED,
                new RequestListener<SavingResult>() {
                    @Override
                    public void onRequestFailure(SpiceException spiceException) {
                        Log.d("saving", "Failed to retrieve saving from server", spiceException);
                        listener.onFailure(spiceException);
                    }

                    @Override
                    public void onRequestSuccess(final SavingResult savingResult) {
                        listener.onSuccess(savingResult.getPayload());
                        setSavings(savingResult.getPayload());
                    }
                });
    }
}
