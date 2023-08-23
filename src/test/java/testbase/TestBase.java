package testbase;

import common.apibase.TokenManager;
import org.testng.annotations.BeforeMethod;

public class TestBase {

    @BeforeMethod
    public void setUp(){
        TokenManager.getAccessTokenResponse();
    }
}
