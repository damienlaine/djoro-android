package com.thermlabs.djoro.app.request;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.jackson.JacksonFactory;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
import com.thermlabs.djoro.app.model.TempMode;

import org.apache.commons.io.IOUtils;

import roboguice.util.temp.Ln;

public class UpdateThermpointRequest extends GoogleHttpClientSpiceRequest<UpdateThermpointResult> {

    private String baseUrl;
    private TempMode mode;

    public UpdateThermpointRequest(String baseUrl, TempMode mode){
        super(UpdateThermpointResult.class);
        this.baseUrl = baseUrl;
        this.mode = mode;
    }

    @Override
    public UpdateThermpointResult loadDataFromNetwork() throws Exception {
        JacksonFactory factory = new JacksonFactory();
        JsonHttpContent content = new JsonHttpContent(factory, mode);
        Ln.d("Call web service " + baseUrl);
        Ln.d("request is: " + content.toString());
        HttpRequest request = getHttpRequestFactory()
                .buildPutRequest(new GenericUrl(baseUrl), content);
        request.setParser(new JacksonFactory().createJsonObjectParser());
        return request.execute().parseAs(getResultType());
    }

}
