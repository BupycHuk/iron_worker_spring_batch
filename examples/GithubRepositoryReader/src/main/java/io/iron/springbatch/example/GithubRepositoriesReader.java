package io.iron.springbatch.example;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class GithubRepositoriesReader extends AbstractItemCountingItemStreamItemReader<Repository> {

    private String query;
    private Iterator<JsonNode> items;
    ObjectMapper mapper;

    @Override
    protected Repository doRead() throws Exception {
        if (items.hasNext()) {
            return mapper.readValue(items.next(), Repository.class);
        } else {
            return null;
        }
    }

    @Override
    protected void doOpen() throws Exception {

        /* JSON provider */
        URL url = new URL("https://api.github.com/search/repositories?q=" + URLEncoder.encode(query, "UTF-8"));


        mapper = new ObjectMapper();

        /*
         * This allows the ObjectMapper to accept single values for a collection.
         * For example: "location" property in the returned JSON is a collection that
         * can accept multiple objects but, in deserialization process, this property just
         * have one object and causes an Exception.
         */
        mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        /*
         * If some JSON property is not present, avoid exceptions setting
         * FAIL_ON_UNKNOWN_PROPERTIES to false
         */
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        items = mapper.readTree(url).get("items").getElements();
    }

    @Override
    protected void doClose() throws Exception {

    }

    public void setQuery(String query) {
        this.query = query;
    }
}
