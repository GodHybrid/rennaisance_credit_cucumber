package godhybrid.credits.Steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;

import godhybrid.credits.Utilities.Initialization;

public class BaseSteps
{
    @Before
    public static void preparation()
    {
        Initialization.initializeDriver();
    }

    @After
    public static void quitAfter()
    {
        Initialization.getDriver().close();
    }
}
