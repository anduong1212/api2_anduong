package common.listener;

import com.aventstack.extentreports.Status;
import common.logger.Log;
import common.report.ExtentReportManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    public String getTestName(ITestResult result){
        return result.getTestName() != null ? result.getTestName() : result.getMethod().getConstructorOrMethod().getName();
    }

    public String getTestDescription(ITestResult result){
        return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
    }

    @Override
    //all test is completed
    public void onFinish(ITestContext testContext){
        Log.info("Completed test case [RESULT]: " + testContext.getName());

        //Finalizing the reporter, write it to the report
        ExtentReportManager.getExtentReport().flush();
    }

    @Override
    public void onTestStart(ITestResult testResult){
        Log.info("[RUNNING THE TEST: " + testResult.getName() + "]");

        //Starting writing a test status to report
        ExtentReportManager.saveToReport(getTestName(testResult), getTestDescription(testResult));
    }

    @Override
    public void onTestSuccess(ITestResult testResult){
        Log.info("[" + testResult.getTestName() + "]" + " is PASSED !");

        //Save the pass status to report
        ExtentReportManager.logSuccess("<b>[" + testResult.getName() + "]</b>" + " is PASSED");
    }

    @Override
    public void onTestFailure(ITestResult testResult){
        Log.info("[" + testResult.getName() + "]" + " is FAILED");
        Log.info(testResult.getThrowable().getMessage());

        //Save the fail status to report
        ExtentReportManager.logFailure("<b>[" + testResult.getName() + "]</b>" + " is FAILED");
        ExtentReportManager.logThrowable(testResult.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult testResult){
        Log.info("[" + testResult.getName() + "]" + " is SKIPPED");
        Log.info(testResult.getThrowable().getMessage());

        //Save the skip status to report
        ExtentReportManager.logSkipped("<b>[" + testResult.getName() + "]</b>" + " is SKIPPED");
        ExtentReportManager.logThrowable(testResult.getThrowable());
    }
}
