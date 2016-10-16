package crosstheborder.lib;

import crosstheborder.lib.interfaces.InputPropertiesGetter;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton that holds a properties object that can get all the server properties.
 *
 * @author Oscar de Leeuw
 */
public class ServerSettings implements InputPropertiesGetter {
    private static final Logger LOGGER = Logger.getLogger(ServerSettings.class.getName());
    private static final String propFileName = "serverconf.properties";
    private static ServerSettings ourInstance = new ServerSettings();
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
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
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
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        return 10;
    }

    /**
     * Gets the tick rate of the server.
     * <p>If the key is not found in the properties it returns the integer value 5.</p>
     *
     * @return An integer that represents the tick rate of the server.
     */
    public int getServerTickRate() {
        try {
            return Integer.parseInt(properties.getProperty("ServerTickRate"));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        return 5;
    }
}
