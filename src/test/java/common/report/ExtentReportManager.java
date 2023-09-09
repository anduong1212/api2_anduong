package common.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import common.logger.Log;
import org.apache.http.Header;
import org.testng.ITestResult;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtentReportManager {

    private static final ExtentReports extentReport = new ExtentReports();
    private static final Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    private static final ExtentReports extendTest = getExtentReport();

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

    public static void logMessage(Status status, String message){
        getTest().log(status, message);
    }

    public static void logSuccess(String message){
        getTest().pass(createMarkup(message, ExtentColor.GREEN));
    }

    public static void logFailure(String message){
        getTest().fail(createMarkup(message, ExtentColor.RED));
    }

    public static void logSkipped(String message){
        getTest().skip(createMarkup(message, ExtentColor.YELLOW));
    }

    public static void logThrowable(Throwable throwable){
        getTest().warning(throwable);
    }

    public static void logVerifyStep(String message){
        getTest().info(createMarkup(message, ExtentColor.LIME));
    }

    public static void logHeaders(List<Header> headerList){
       String[][] headerArray = headerList.stream().map(header -> new String[] {header.getName(), header.getValue()})
                .toArray(String[][]::new);
        getTest().info(MarkupHelper.createTable(headerArray));
    }

    //Still not able to change its status when reporting a failed step -> need improve
    public static void stepNode(String nodeName, String nodeDesc){
        logMessage(Status.INFO, nodeName);
        getTest().createNode("<b style =\"color:Orange\">STEP </b>" + nodeName).info(createMarkup(nodeDesc, ExtentColor.PINK));
    }

    public static void stepNodeVerify(String nodeName, String nodeDesc){
        logVerifyStep(nodeName);
        getTest().createNode("<b style =\"color:Orange\">VERIFY </b>" + nodeName).info(createMarkup(nodeDesc, ExtentColor.ORANGE));
    }

    public static void stepJsonNode(String nodeName, String description){
        logMessage(Status.INFO, nodeName);
        getTest().createNode("<b style =\"color:Blue\">JSON STEP </b>" + nodeName).info(MarkupHelper.createCodeBlock(description,CodeLanguage.JSON));
    }

    public static Markup createMarkup(String message, ExtentColor color){
        Markup markup = MarkupHelper.createLabel(message,color);
        return markup;
    }

    public static void setDevice(String device){
        extentTestMap.get((int) Thread.currentThread().getId()).assignDevice(device);
    }

    public static void extentReportFailedOperation(ITestResult iTestResult){
        logFailure("FAILED - handle as adding a attachment would be added later");
    }

}
