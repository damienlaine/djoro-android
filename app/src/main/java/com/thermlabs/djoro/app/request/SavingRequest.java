package com.thermlabs.djoro.app.request;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.json.jackson.JacksonFactory;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
import com.thermlabs.djoro.app.model.json.savings.SavingResult;

import java.io.IOException;

import roboguice.util.temp.Ln;

public class SavingRequest extends GoogleHttpClientSpiceRequest<SavingResult> {

    private String baseUrl;

    public SavingRequest(String baseUrl) {
        super( SavingResult.class );
        this.baseUrl = baseUrl;
    }

    @Override
    public SavingResult loadDataFromNetwork() throws IOException {
        Ln.d( "Call web service " + baseUrl );
        HttpRequest request = getHttpRequestFactory()//
                .buildGetRequest( new GenericUrl( baseUrl ) );
        request.setParser(new JacksonFactory().createJsonObjectParser());
        return request.execute().parseAs( getResultType() );
    }

}