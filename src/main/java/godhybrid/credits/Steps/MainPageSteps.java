package godhybrid.credits.Steps;

import godhybrid.credits.Pages.MainPage;

import io.qameta.allure.Step;

public class MainPageSteps extends BaseSteps
{
    @Step("В меню выбрано '{menuItem}'")
    public MainPageSteps stepSelectMenu(String menuItem)
    {
        new MainPage().selectMainMenuItem(menuItem);
        return this;
    }
}
