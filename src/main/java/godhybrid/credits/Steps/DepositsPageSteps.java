package godhybrid.credits.Steps;

import godhybrid.credits.Pages.DepositsPage;
import static godhybrid.credits.Pages.BasePageObj.waitForTextTrue;

import io.qameta.allure.Step;

import static org.junit.Assert.assertTrue;

public class DepositsPageSteps extends BaseSteps
{
    public DepositsPage depositsPage = new DepositsPage();

    @Step("Выбрана валюта: {0}")
    public DepositsPageSteps chooseCurrency(String s)
    {
        depositsPage.prepare().chooseCurrency(s);
        return this;
    }

    @Step("Выбран срок вклада: {0}")
    public DepositsPageSteps selectTerm(String term)
    {
        depositsPage.selectTerm(term);
        return this;
    }

    @Step("В поле {0} введено '{1}'")
    public DepositsPageSteps fillField(String field, String value)
    {
        //depositsPage.scrollToElement(DepositsPage.form,65).fillField(field, value);
        depositsPage.fillField(field, value);
        return this;
    }

    @Step("Нажата кнопка {0}")
    public DepositsPageSteps clickElement(String name)
    {
        depositsPage.clickOnButton(name);
        return this;
    }

    @Step("В поле {0} записано значение '{1}'")
    public DepositsPageSteps checkCalcValue(String field, String value)
    {
        if (waitForTextTrue(DepositsPage.withdraw, value) == false)
        {
            String actual = DepositsPage.withdraw.getText();
            assertTrue(String.format("Значение поля [%s] неверно! Текущее: [%s]. Должно быть: [%s].", field, actual, value),
                    actual.equals(value));
        }
        return this;
    }
}
