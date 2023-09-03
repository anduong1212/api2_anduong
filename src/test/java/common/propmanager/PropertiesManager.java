package common.propmanager;

import common.logger.Log;
import utilities.DateTimeUtils;
import utilities.StringUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;

public class PropertiesManager {
    private static Properties properties;
    private static String linkFile;
    private static FileInputStream fileIn;
    private static FileOutputStream fileOut;
    private static String defaultProperFilePath = "src/test/resources/token_storage.properties";
    private static String configProperFilePath = "src/test/resources/config.properties";

    public static Properties loadAllPropFiles(){
        LinkedList<String> filesName = new LinkedList<>();

        filesName.add("src/test/resources/token_storage.properties");
        filesName.add("src/test/resources/config.properties");
        try{
            properties = new Properties();
            for (String f : filesName){
                Properties tempProp = new Properties();
                linkFile = StringUtils.getCurrentDir() + f;
                fileIn = new FileInputStream(linkFile);
                tempProp.load(fileIn);
                properties.putAll(tempProp);
            }
            return properties;
        } catch (IOException e){
            Log.error(e.getMessage());
            return new Properties();
        }
    }

    //Using for specific env
    //Need implement for read and write specific Properties file;
    public static void setCustomFile(String propertiesFilePath){
        properties = new Properties();
        try{
            linkFile = propertiesFilePath;
            fileIn = new FileInputStream(linkFile);
            properties.load(fileIn);
            fileIn.close();
        } catch (IOException exception){
            throw new RuntimeException("[PropertiesManager] - unable to read given properties file");
        }
    }

    public static void setDefaultFile(){
        properties = new Properties();
        try{
            linkFile = StringUtils.getCurrentDir() + defaultProperFilePath;
            fileIn = new FileInputStream(linkFile);
            properties.load(fileIn);
            fileIn.close();
        } catch (IOException exception){
            throw new RuntimeException("[PropertiesManager] - unable to read DEFAULT properties file");
        }
    }

    public static String getDefaultPropValue(String key){
        String keyValue;
        try {
//            if (fileIn == null){
                setDefaultFile();
//            }
            keyValue = properties.getProperty(key);
        } catch (Exception exception){
            throw new RuntimeException("[Properties] Unable to get Properties value");
        }

        return keyValue;
    }

    public static String getCustomPropValue(String customKey){
        String keyValue;
        try{
            setCustomFile(StringUtils.getCurrentDir() + configProperFilePath);
            keyValue = properties.getProperty(customKey);
        } catch (Exception exception){
            throw new RuntimeException("[Config Properties] Unable to get Properties value");
        }
        return keyValue;
    }

    public static void setConfigPropValue(String key, String value){
        try{
            properties = new Properties();
            setCustomFile(StringUtils.getCurrentDir() + configProperFilePath);
            fileOut = new FileOutputStream(StringUtils.getCurrentDir() + configProperFilePath);

            properties.setProperty(key, value);
            properties.store(fileOut, "Written at: " + DateTimeUtils.getCurrentDateTime());
            fileOut.close();
        } catch (Exception exception){
            throw new RuntimeException("[Properties] Unable to set the value to " + linkFile + " file");
        }
    }


    public static void setDefaultPropValue(String key, String value){
        try {
//            if (fileIn == null){
                properties = new Properties();
                setDefaultFile();
                fileOut = new FileOutputStream(StringUtils.getCurrentDir() + defaultProperFilePath);
//            }
            fileOut = new FileOutputStream(linkFile);
            properties.setProperty(key, value);
            properties.store(fileOut, "Written at: ...");
            fileOut.close();
        } catch (Exception exception){
            throw new RuntimeException("[Properties] Unable to set the value to " + linkFile + " file");
        }
    }

}
