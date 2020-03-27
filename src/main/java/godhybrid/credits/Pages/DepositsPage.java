package godhybrid.credits.Pages;

import godhybrid.credits.Utilities.Initialization;

import org.junit.Assert;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class DepositsPage extends BasePageObj
{
    JavascriptExecutor je;

    @FindBy(xpath="//div[contains(@class, 'calculator-block')]")
    WebElement calculator;

    @FindBy(xpath="//div[@class='calculator__currency-content']/label")
    List<WebElement> currencies;

    @FindBy(name = "amount")
    WebElement deposit;

    @FindBy(name = "replenish")
    WebElement monthlyAdditions;

    @FindBy(name = "capitalization")
    WebElement capitalizationSwitch;

    @FindBy(xpath = "//select[@class=\"calculator__slide-input js-slide-value\"]")
    WebElement selectTerm;

    @FindBy(xpath = "//span[@class='js-calc-result']")
    public static WebElement withdraw;

    public DepositsPage()
    {
        super();
    }

    public DepositsPage prepare()
    {
        je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].scrollIntoView();", calculator);
        waitForLoadElement(calculator);
        return new DepositsPage();
    }

    public DepositsPage fillField(String fieldName, String value)
    {
        switch (fieldName)
        {
            case  "Сумма вклада":
                fillField(deposit, value);
                break;
            case  "Ежемесячное пополнение":
                fillField(monthlyAdditions, value);
                break;
            default:  throw new AssertionError("Поле '"+ fieldName+ "' не объявлено на странице");
        }
        return this;
    }

    public DepositsPage selectTerm(String term)
    {
        new Select(selectTerm).selectByVisibleText(term);
        return this;
    }

    public DepositsPage clickOnButton(String title)
    {

        Actions actions = new Actions(Initialization.getDriver());

        switch (title)
        {
            case "Ежемесячная капитализация":
                actions.moveToElement(capitalizationSwitch).click().build().perform();
                break;
            default:
                Assert.fail("Кнопка " + title + " не обработана.");
        }
        return this;
    }

    public void chooseCurrency(String s)
    {
        Boolean found = false;
        for (WebElement element: currencies)
        {
            if(element.getText().equalsIgnoreCase(s))
            {
                waitForVisibilityOfElement(element).click();
                found = true;
            }
        }
        if(!found)
        {
            Assert.fail(String.format("Валюта '%s' не обнаружена", s));
        }
    }
}
