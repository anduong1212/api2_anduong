import common.report.ExtentReportManager;
import org.testng.annotations.Test;

public class Test1 {
    @Test(priority = 1)
    public void loginTest(){
        ExtentReportManager.logMessage("ahihi");

    }
}
