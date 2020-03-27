package godhybrid.credits.Pages;

import godhybrid.credits.Utilities.Initialization;

import org.junit.Assert;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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
    WebElement amountField;

    @FindBy(name = "replenish")
    WebElement replenishField;

    @FindBy(name = "deposit_b_n")
    WebElement bankDepBtn;

    @FindBy(name = "deposit_b_y")
    WebElement intBankDepBtn;

    @FindBy(name = "capitalization")
    WebElement capitalizationBtn;

    @FindBy(name = "partial_out")
    WebElement partialOut;

    @FindBy(xpath = "//select[@class=\"calculator__slide-input js-slide-value\"]")
    WebElement selectTerm;

    @FindBy(xpath = "//form/div[@class='calculator__content']")
    public static WebElement form;

    @FindBy(xpath = "//span[@class='js-calc-result']")
    public static WebElement withdraw;

    public DepositsPage()
    {
        PageFactory.initElements(Initialization.getDriver(),this);
    }


    public DepositsPage fillField(String fieldName, String value)
    {

        switch (fieldName)
        {
            case  "Сумма вклада":
                fillField(amountField, value);
                break;
            case  "Ежемесячное пополнение":
                fillField(replenishField, value);
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
            case "В отделении банка":
                actions.moveToElement(bankDepBtn).click().build().perform();
                break;
            case "В интернет-банке":
                actions.moveToElement(intBankDepBtn).click().build().perform();
                break;
            case "Ежемесячная капитализация":
                actions.moveToElement(capitalizationBtn).click().build().perform();
                break;
            case "Частичное снятие":
                actions.moveToElement(partialOut).click().build().perform();
                break;
            default:
                throw new AssertionError("Нет такой кнопки" + title);
        }
        return this;
    }

    public DepositsPage scrollToElement(WebElement element, int offset)
    {
        waitVisibilityOfElement(element);
        int y = element.getLocation().getY() + offset;
        element.click();
        JavascriptExecutor js = (JavascriptExecutor) Initialization.getDriver();
        js.executeScript("window.scrollTo(0,"+y+")");
        return this;
    }

    public DepositsPage prepare()
    {
        je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].scrollIntoView();", calculator);
        waitForLoadElement(calculator);
        return new DepositsPage();
    }

    public void chooseCurrency(String s)
    {
        Boolean found = false;
        for (WebElement element: currencies)
        {
            if(element.getText().equalsIgnoreCase(s))
            {
                BasePageObj.waitVisibilityOfElement(element).click();
                found = true;
            }
        }
        if(!found)
        {
            Assert.fail(String.format("Валюта '%s' не обнаружена", s));
        }
    }
}
