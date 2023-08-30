package utilities;

public class StringUtils {
    public static String getCurrentDir(){
        String path = System.getProperty("user.dir") + "/";
        return path;
    }
}
