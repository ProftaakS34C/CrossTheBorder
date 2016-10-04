package crosstheborder.lib;

import crosstheborder.lib.interfaces.InputPropertiesGetter;

import java.io.*;
import java.util.Properties;

/**
 * Singleton that holds a properties object that can get all the server properties.
 *
 * @author Oscar de Leeuw
 */
public class ServerSettings implements InputPropertiesGetter {
    private static ServerSettings ourInstance = new ServerSettings();
    private final String propFileName = "serverconf.properties";
    private Properties properties;

    private ServerSettings() {
        this.properties = new Properties();
        File file = new File(propFileName);

        try (InputStream is = new FileInputStream(file)) {

            properties.load(is);

        } catch (FileNotFoundException ex) {
            try {
                file.createNewFile();
                ourInstance = new ServerSettings();
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Gets the instance of the singleton.
     *
     * @return The instance of the singleton.
     */
    public static ServerSettings getInstance() {
        return ourInstance;
    }

    /**
     * Gets the size of the input buffer.
     *
     * @return The size of the input buffer.
     */
    @Override
    public int getInputBufferSize() {
        try {
            return Integer.parseInt(properties.getProperty("InputBufferSize"));
        } catch (NumberFormatException ex) {
            ex.printStackTrace(System.err);
        }
        return 10;
    }
}
