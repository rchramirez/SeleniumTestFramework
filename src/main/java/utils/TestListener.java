package utils;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Attachment
    public byte[] saveFailureScreenShot(WebDriver driver) {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("start");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("success");
    }


    public void onTestFailure(ITestResult iTestResult) {
        ITestContext context = iTestResult.getTestContext();
        WebDriver driver = (WebDriver) context.getAttribute("driver");
        if (driver instanceof WebDriver) {
            saveFailureScreenShot(driver);
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("percentage");
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println("start");
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("finish");
    }


}
