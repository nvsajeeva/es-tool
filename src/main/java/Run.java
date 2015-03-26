import java.util.Properties;

/**
 * Created by zcfrank1st on 3/25/15.
 */
public class Run {
    public static void main(String[] args) {
        Properties p = Utils.loadConfig();

        ESClient source = new ESClient
                .Builder()
                .setHost(p.getProperty("source_host"))
                .setPort(Integer.parseInt(p.getProperty("source_port")))
                .setClusterName(p.getProperty("source_cluster_name"))
                .build();

        ESClient target = new ESClient
                .Builder()
                .setHost(p.getProperty("target_host"))
                .setPort(Integer.parseInt(p.getProperty("target_port")))
                .setClusterName(p.getProperty("target_cluster_name"))
                .build();

        target.insert(p.getProperty("index"),source.obtain(p.getProperty("index"), Integer.parseInt(p.getProperty("count"))));

    }
}
