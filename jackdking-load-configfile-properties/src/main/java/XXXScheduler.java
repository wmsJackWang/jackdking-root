import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class XXXScheduler {


    private static String VERSION_MAJOR = "UNKNOWN";
    private static String VERSION_MINOR = "UNKNOWN";
    private static String VERSION_ITERATION = "UNKNOWN";

    static {
        Properties properties = new Properties();
        InputStream is = null;

        try {
            is = XXXScheduler.class.getResourceAsStream("XXXX-build.properties");
            if (Objects.nonNull(is)) {
                    properties.load(is);
                    String version = properties.getProperty("version");
                    if (Objects.nonNull(version)) {
                        String[] versionComponents = version.split("\\.");
                        VERSION_MAJOR = versionComponents[0];
                        VERSION_MINOR = versionComponents[1];
                        if(versionComponents.length > 2)
                            VERSION_ITERATION = versionComponents[2];
                        else
                            VERSION_ITERATION = "0";
                    } else {

                        //print error message
                    }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
