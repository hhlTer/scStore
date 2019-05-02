package com.test.store.conroller.web;

import com.google.gson.Gson;
import com.test.store.conroller.utils.HttpFixerUriBuilderParameters;
import com.test.store.model.domain.FixerResponceEntity;
import com.test.store.model.domain.Order;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.stereotype.Service;

import static com.test.store.conroller.utils.HttpFixerUriBuilderParameters.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service(value = "fixerRequest")
public class FixerRequest<T extends FixerResponceEntity> implements Request<T> {

    private final String API_KEY = "ad2d0508d1b0963413b98ccf22225399";

    private List<NameValuePair> parameters;
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private HttpContext context = new BasicHttpContext();

    @Override
    public T GET(Map<String, String> initParameters, Class<T> clazz) {
        this.parameters = initParameters.entrySet().stream()
                .map(p -> new BasicNameValuePair(p.getKey(), p.getValue()))
                .collect(Collectors.toCollection(ArrayList::new));

        try {
            String responseString = getResponseResult();
            return new Gson().fromJson(responseString, clazz);
        } catch (URISyntaxException initE) {
            initE.printStackTrace();
        } catch (IOException initE) {
            initE.printStackTrace();
        }
        return null;
    }

    private String getResponseResult() throws URISyntaxException, IOException {
        URI uri = new URIBuilder()
                .setScheme(SCHEME.value())
                .setHost(HOST.value())
                .setPath(PATH_LATEST.value())
                .setParameters(parameters)
                .setParameter(ACCES_KEY_PARAMETER.value(), API_KEY)
                .build();
        HttpGet localHttpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(localHttpGet, context);

        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder sb = new StringBuilder();
        return br.lines().collect(Collectors.joining("\\n"));
    }
}
