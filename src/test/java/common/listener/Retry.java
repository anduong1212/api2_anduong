package common.listener;

import common.report.ExtentReportManager;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
    private        int count = 0;
    private static int maxTryTime = 2;
    @Override
    public boolean retry(ITestResult iTestResult){
        //If the test case is not passed for first time
        if (!iTestResult.isSuccess()){
            if (count < maxTryTime){
                count++;
                iTestResult.setStatus(ITestResult.FAILURE);
                //handle the failure if have
                ExtentReportManager.extentReportFailedOperation(iTestResult);
                return true; //tell testNG should re-run the test
            }
        } else {
            iTestResult.setStatus(ITestResult.SUCCESS); //If the test passed, TestNG marks it passed
        }
        return false;
    }
}
