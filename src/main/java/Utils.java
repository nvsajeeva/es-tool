import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by zcfrank1st on 3/26/15.
 */
public class Utils {

    public static Properties loadConfig() {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(new File(Utils.class.getClassLoader().getResource("config.properties").getFile())));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return p;
    }
}
