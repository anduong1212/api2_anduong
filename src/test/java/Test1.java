import com.aventstack.extentreports.Status;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import common.logger.Log;
import common.report.ExtentReportManager;
import constant.Constants;
import controller.jiraapi.Issue;
import controller.jiraapi.dataobjects.NewIssue;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testbase.TestBase;
import utilities.ResponseUtils;

import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Test1 extends TestBase {
    Issue issueController = new Issue();
    SoftAssert softAssert = new SoftAssert();
    @Test(testName = "C0001", priority = 1, description = "description for test 1")
    public void loginTest(){
        ExtentReportManager.stepNode("GET ALL ISSUE", "ADAPI");
        Response getAllIssues = issueController.getAllIssuesFromProject();
        ExtentReportManager.stepJsonNode("Step 2: Verify response", getAllIssues.getBody().asString());

    }

}
