package com.thermlabs.djoro.app.request;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.jackson.JacksonFactory;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
import com.thermlabs.djoro.app.model.json.savings.SavingProposal;

import roboguice.util.temp.Ln;

public class NewSavingRequest extends GoogleHttpClientSpiceRequest<NewSavingResult> {

    private String baseUrl;
    private SavingProposal saving;

    public NewSavingRequest(String baseUrl, SavingProposal saving){
        super(NewSavingResult.class);
        this.baseUrl = baseUrl;
        this.saving = saving;
    }

    @Override
    public NewSavingResult loadDataFromNetwork() throws Exception {
        JacksonFactory factory = new JacksonFactory();
        JsonHttpContent content = new JsonHttpContent(factory, saving);
        Ln.d("Call web service " + baseUrl);
        Ln.d("request is: " + content.toString());
        HttpRequest request = getHttpRequestFactory()
                .buildPostRequest(new GenericUrl(baseUrl), content);
        request.setParser(new JacksonFactory().createJsonObjectParser());
        return request.execute().parseAs(getResultType());
    }

}
