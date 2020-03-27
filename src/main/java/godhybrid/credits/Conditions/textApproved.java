package godhybrid.credits.Conditions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class textApproved implements ExpectedCondition<Boolean> {
    String expectedText;
    WebElement element;

    public textApproved(String expectedText, WebElement element)
    {
        this.expectedText = expectedText;
        this.element = element;
    }

    public Boolean apply(WebDriver driver)
    {
        Boolean isTextFieldCorrect = element.getAttribute("value").replaceAll("[^\\d.]", "").equals(expectedText);
        if(!isTextFieldCorrect)
        {
            element.clear();
            element.sendKeys(expectedText);
        }
        return isTextFieldCorrect;
    }
}
