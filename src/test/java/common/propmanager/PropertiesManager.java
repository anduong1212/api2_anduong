package common.propmanager;

import utilities.DateTimeUtils;
import utilities.StringUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingDeque;

public class PropertiesManager {
   private static Properties defaultProperties;
   private static String linkFile;
   private static FileInputStream defaultFileInputStream;
   private static FileOutputStream defaultFileOutputStream;
   private static String defaultPropertiesFile = "src/test/resources/token_storage.properties";

   public static Properties loadAllPropFiles(){
       LinkedBlockingDeque<String> allPropertiesFiles = new LinkedBlockingDeque<>();
        allPropertiesFiles.add(defaultPropertiesFile);

        try{
            defaultProperties = new Properties();

            for (String file : allPropertiesFiles){
                Properties tempProperties = new Properties();
                defaultFileInputStream = new FileInputStream(StringUtils.getCurrentDir() + file);
                tempProperties.load(defaultFileInputStream);
                defaultProperties.putAll(tempProperties);
            }
            return defaultProperties;
        } catch (IOException e){
            return new Properties();
        }
   }

   public static String getDefaultPropValue(String key){
       String keyValue = "";
       try{
           if(defaultFileInputStream == null){
               defaultProperties = new Properties();
               defaultFileInputStream = new FileInputStream(StringUtils.getCurrentDir() + defaultPropertiesFile);
               defaultProperties.load(defaultFileInputStream);
               defaultFileInputStream.close();
           }
           keyValue = defaultProperties.getProperty(key);
       } catch (IOException e){
           throw new RuntimeException("[DEFAULT PROPERTIES FILE]- Unable to get value from Properties file");
       }
       return keyValue;
   }

   public static void setDefaultPropValue(String key, String keyValue){
       try{
           if(defaultFileInputStream == null){
               defaultProperties = new Properties();
               defaultFileInputStream = new FileInputStream(StringUtils.getCurrentDir() + defaultPropertiesFile);
               defaultProperties.load(defaultFileInputStream);
               defaultFileInputStream.close();

               defaultFileOutputStream = new FileOutputStream(StringUtils.getCurrentDir() + defaultPropertiesFile);
           }

           defaultFileOutputStream = new FileOutputStream(StringUtils.getCurrentDir() + defaultPropertiesFile);

           defaultProperties.setProperty(key, keyValue);
           defaultProperties.store(defaultFileOutputStream, "Written at: " + DateTimeUtils.getCurrentDateTime());
           defaultFileOutputStream.close();
       } catch (IOException e){
           throw new RuntimeException("[SET DEFAULT PROPERTIES FILE] - Unable to set value to Properties file");
       }
   }
}
