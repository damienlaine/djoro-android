package com.thermlabs.djoro.app.request;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.jackson.JacksonFactory;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
import com.thermlabs.djoro.app.model.json.JSONWeekSchedule;
import com.thermlabs.djoro.app.model.json.WeekScheduleResult;
import com.thermlabs.djoro.app.model.site.TypicalWeekSchedule;

import roboguice.util.temp.Ln;

public class UpdateTypicalWeekScheduleRequest extends GoogleHttpClientSpiceRequest<WeekScheduleResult> {

    private String baseUrl;
    private JSONWeekSchedule newJSONWeekSchedule;

    public UpdateTypicalWeekScheduleRequest(String baseUrl, TypicalWeekSchedule weekSchedule) {
        super(WeekScheduleResult.class);

        this.baseUrl = baseUrl;
        try {
            this.newJSONWeekSchedule = JSONWeekSchedule.fromModel(weekSchedule);
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public WeekScheduleResult loadDataFromNetwork() throws Exception {
        JacksonFactory factory = new JacksonFactory();
        JsonHttpContent content = new JsonHttpContent(factory, newJSONWeekSchedule);
        Ln.d("Call web service " + baseUrl);
        Ln.d("request is: " + content.toString());
        HttpRequest request = getHttpRequestFactory()
                .buildPutRequest(new GenericUrl(baseUrl), content);
        request.setParser(new JacksonFactory().createJsonObjectParser());
        return request.execute().parseAs(getResultType());
    }
}
