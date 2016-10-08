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
                if (!file.createNewFile()) {
                    throw new IOException();
                }
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
     * {@inheritDoc}
     * <p>If the key is not found in the properties it returns the integer value 10.</p>
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

    /**
     * Gets the tick rate of the server.
     * <p>If the key is not found in the properties it returns the integer value 0.2f.</p>
     *
     * @return An integer that represents the tick rate of the server.
     */
    public int getServerTickRate() {
        try {
            return Integer.parseInt(properties.getProperty("ServerTickRate"));
        } catch (NumberFormatException ex) {
            ex.printStackTrace(System.err);
        }
        return 200;
    }
}
