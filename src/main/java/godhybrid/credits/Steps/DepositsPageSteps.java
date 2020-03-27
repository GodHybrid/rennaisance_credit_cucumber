package godhybrid.credits.Steps;

import godhybrid.credits.Pages.DepositsPage;
import static godhybrid.credits.Pages.BasePageObj.waitTextToBePresentInElement;

import io.qameta.allure.Step;

import static org.junit.Assert.assertTrue;

public class DepositsPageSteps extends BaseSteps
{
    public DepositsPage depositsPage = new DepositsPage();

    @Step("Выбран срок вклада {0}")
    public DepositsPageSteps selectTerm(String term)
    {
        depositsPage.selectTerm(term);
        return this;
    }

    @Step("Поле {0} заполняется значением {1}")
    public DepositsPageSteps fillField(String field, String value)
    {
        depositsPage
                .scrollToElement(DepositsPage.form,65)
                .fillField(field, value);
        return this;
    }

    @Step("Выполнено нажатие на кнопку {0}")
    public DepositsPageSteps clickElement(String name)
    {
        depositsPage.clickOnButton(name);
        return this;
    }

    @Step("Поле {0} заполнено значением {1}")
    public DepositsPageSteps checkCalcValue(String field, String value)
    {
        if (waitTextToBePresentInElement(DepositsPage.withdraw, value) == false)
        {
            String actual = DepositsPage.withdraw.getText();
            assertTrue(String.format("Значение поля [%s] равно [%s]. Ожидалось - [%s]", field, actual, value),
                    actual.equals(value));
        }
        return this;
    }

    public DepositsPageSteps chooseCurrency(String s)
    {
        depositsPage.prepare();
        depositsPage.chooseCurrency(s);
        return this;
    }
}
