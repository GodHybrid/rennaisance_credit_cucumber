package godhybrid.credits.Glue;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;

import godhybrid.credits.Steps.BaseSteps;
import godhybrid.credits.Steps.DepositsPageSteps;
import godhybrid.credits.Steps.MainPageSteps;

import io.cucumber.datatable.DataTable;

import java.util.Map;

public class MyStepdefs {

        public MainPageSteps mainPageSteps;
        public DepositsPageSteps depositsPageSteps;

        @Before
        public void before()
        {
                BaseSteps.preparation();
                mainPageSteps = new MainPageSteps();
                depositsPageSteps  = new DepositsPageSteps();
        }

        @Когда("^выбрано меню \"(.+)\"")
        public void выбрано_меню(String menuItem)
        {
            mainPageSteps.stepSelectMenu(menuItem);
        }

        @Когда("^заполняются поля:$")
        public void заполняются_поля(DataTable fields)
        {
            Map<String,String> dataMap = fields.asMap(String.class, String.class);
            dataMap.forEach((field, value) -> { depositsPageSteps.fillField(field, value); });
        }

        @Когда("^выбран срок вклада \"(.+)\"$")
        public void выбран_срок_вклада(String term)
        {
                depositsPageSteps.selectTerm(term);
        }

        @Когда("^выполнено нажатие на кнопку \"(.+)\"$")
        public void нажатие_на_кнопку(String name)
        {
                depositsPageSteps.clickElement(name);
        }

        @Тогда("^значение поля \"(.+)\" равно \"(.+)\"$")
        public void значения_поля(String field, String value)
        {
                depositsPageSteps.checkCalcValue(field, value);
        }

        @И("выбрана валюта {string}")
        public void выбранаВалюта(String s)
        {
                depositsPageSteps.chooseCurrency(s);
        }

        @After
        public void after()
        {
                BaseSteps.quitAfter();
        }
}

