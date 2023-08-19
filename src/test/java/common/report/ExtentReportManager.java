package common.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import common.logger.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExtentReportManager {

    private static final ExtentReports extentReport = new ExtentReports();
    private static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    private static ExtentReports extendTest = getExtentReport();

    //this method using to generate the report instances
    public synchronized static ExtentReports getExtentReport(){

        final File CONF = new File("src/test/resources/spark-config.json");

        //ExtentSparkReporter is a rich-HTML reporter available from the standard ExtentReports library.
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("src/test/output/api_2_report.html");

        try {
            sparkReporter.loadJSONConfig(CONF);
        } catch (IOException e){
            Log.error(e.toString());
        }

        //configuration extend report
        extentReport.attachReporter(sparkReporter);
        extentReport.setSystemInfo("Framework Name", "API Level 2");
        extentReport.setSystemInfo("Author", "An Duong");

        return extentReport;
    }

    //Return an instances of ExtentTest, which gonna save to the report for giving the test information
    public static ExtentTest getTest(){
        //get the current test base on the thread id
        return extentTestMap.get((int) Thread.currentThread().getId());
    }

    public static synchronized ExtentTest saveToReport(String testName, String descrption){
        //set the configuration test to specific test with current thread id
        ExtentTest extentTestInstance = extendTest.createTest(testName, descrption);
        extentTestMap.put((int) Thread.currentThread().getId(), extentTestInstance);

        return extentTestInstance;
    }

    public static void logMessage(String message){
        getTest().log(Status.INFO, message);
    }


}
