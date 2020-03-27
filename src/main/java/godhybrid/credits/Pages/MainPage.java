package godhybrid.credits.Pages;

import godhybrid.credits.Utilities.Initialization;

import org.junit.Assert;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MainPage extends BasePageObj
{
    @FindBy(xpath = "//div/div[@class='service__title-text']")
    List<WebElement> mainMenuItem;

    public void selectMainMenuItem(String menuItem)
    {
        Boolean found = false;
        for (WebElement element: mainMenuItem)
        {
            if(element.getText().equalsIgnoreCase(menuItem))
            {
                waitForVisibilityOfElement(element);
                found = true;
                Actions actions = new Actions(Initialization.getDriver());
                actions.moveToElement(element).click().build().perform();
            }
        }
        if(!found)
        {
            Assert.fail(String.format("Меню '%s' не обнаружено", menuItem));
        }
    }
}
