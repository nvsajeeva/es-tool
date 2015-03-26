import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.SearchHit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zcfrank1st on 3/26/15.
 */
public class ESClient {
    private Client client = null;
    private ESClient(String name, String host, int port) {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", name).build();
        client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(host, port));
    }

    static class Builder{
        private Client esClient = null;
        private String host = "";
        private int port = 0;
        private String name = "";

        public Builder(){
        }

        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public Builder setClusterName(String name) {
            this.name = name;
            return this;
        }

        public ESClient build() {
            if (host.equals("") || name.equals("")) {
                throw new RuntimeException("no cluster config to build");
            } else {
                if ( 0 == port ) {
                    return new ESClient(name, host, 9300);
                } else {
                    return new ESClient(name, host, port);
                }
            }
        }
    }

    public List<Map<String, Object>> obtain(String index, int count) {
        SearchResponse searchResponse = client.prepareSearch(index).setSize(count).execute().actionGet();
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        for (SearchHit searchHit: searchResponse.getHits().getHits()) {
            resultList.add(searchHit.getSource());
        }
        client.close();
        return resultList;
    }

    public void insert(String index, List<Map<String, Object>> results) {
        for (Map<String, Object> map : results) {
            client.prepareIndex(index, "doc").setSource(map).execute().actionGet();
        }
        client.close();
    }
}
