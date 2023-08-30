package testbase;

import common.apibase.TokenManager;
import common.propmanager.PropertiesManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.util.Properties;

public class TestBase {

    @BeforeTest
    public void configure(){
        PropertiesManager.loadAllPropFiles();
    }

    @BeforeMethod
    public void setUp(){
        TokenManager.getAccessToken();
        TokenManager.getCloudID();
    }
}
