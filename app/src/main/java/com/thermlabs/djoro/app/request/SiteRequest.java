package com.thermlabs.djoro.app.request;

import java.io.IOException;
import roboguice.util.temp.Ln;
import com.thermlabs.djoro.app.model.json.SiteResult;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.json.jackson.JacksonFactory;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;

public class SiteRequest extends GoogleHttpClientSpiceRequest<SiteResult> {

    private String baseUrl;

    public SiteRequest(String baseUrl) {
        super( SiteResult.class );
        this.baseUrl = baseUrl;
    }

    @Override
    public SiteResult loadDataFromNetwork() throws IOException {
        Ln.d( "Call web service " + baseUrl );
        HttpRequest request = getHttpRequestFactory()//
                .buildGetRequest( new GenericUrl( baseUrl ) );
        request.setParser(new JacksonFactory().createJsonObjectParser());
        return request.execute().parseAs( getResultType() );
    }

}