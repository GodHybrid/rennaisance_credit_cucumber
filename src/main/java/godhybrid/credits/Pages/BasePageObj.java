package godhybrid.credits.Pages;

import godhybrid.credits.Conditions.textApproved;
import godhybrid.credits.Utilities.Initialization;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePageObj
{
    public static WebDriver driver;
    Actions bobDoSomething;
    public static WebDriverWait waitForLoad;

    BasePageObj()
    {
        driver = Initialization.getDriver();
        waitForLoad = new WebDriverWait(driver, 5);
        bobDoSomething = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    public void fillField(WebElement field, String value)
    {
        waitVisibilityOfElement(field);
        waitClickableOfElement(field);
        field.click();
        field.clear();
        field.sendKeys(value);
        waitForLoad.until(new textApproved(value,field));
    }

    public WebElement waitForLoadElement(WebElement element)
    {
        return waitForLoad.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForTextApproved(WebElement element, String text)
    {
        while(!waitForLoad.until(new textApproved(text, element))) waitForTextApproved(element, text);
        return element;
    }

    public static WebElement waitVisibilityOfElement(WebElement element)
    {
        return waitForLoad.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitClickableOfElement(WebElement element)
    {
        return waitForLoad.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static boolean waitTextToBePresentInElement(WebElement element, String text)
    {
       try
       {
           waitForLoad.until(ExpectedConditions.textToBePresentInElement(element,text));
           return true;
       }
       catch (Exception e)
       {
           return false;
       }
    }
}
