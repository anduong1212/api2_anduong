package testbase;

import common.apibase.TokenManager;
import common.propmanager.PropertiesManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.Properties;

public class TestBase {

    @BeforeTest
    @Parameters("Environment")
    public void configure(String environment){
        PropertiesManager.loadAllPropFiles();
//        PropertiesManager.setConfigPropValue("environment", environment);
    }

    @BeforeClass
    public void setUp(){
        TokenManager.getAccessToken();
        TokenManager.getCloudID();
    }
}
