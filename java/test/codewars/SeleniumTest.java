
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static org.junit.Assert.*;

public class SeleniumTest {

    private WebDriver ffDriver;

    @Before
    public void intialize(){
        var options = new FirefoxOptions();
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\jbadillo\\Documents\\workspace\\lib\\geckodriver.exe");
        options.setBinary("C:\\Users\\jbadillo\\AppData\\Local\\Mozilla Firefox\\firefox.exe");
        ffDriver = new FirefoxDriver(options);
    }

    @Test
    public void openPageTest(){
        ffDriver.navigate().to("http://www.fnal.gov/");
        var home = ffDriver.findElement(By.linkText("Home"));
        home.click();
    }

    @After
    public void finish(){
        ffDriver.close();
        //ffDriver.quit();
    }
}
