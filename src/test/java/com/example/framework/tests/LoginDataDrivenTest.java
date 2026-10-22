package com.example.framework.tests;

import com.example.framework.data.CsvData;
import com.example.framework.driver.DriverFactory;
import com.example.framework.pages.InventoryPage;
import com.example.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginDataDrivenTest extends BaseTest {
    @DataProvider(name = "validLogins")
    public Object[][] validLogins() { return CsvData.read("testdata/login.csv"); }

    @Test(dataProvider = "validLogins", groups = {"smoke", "regression"})
    public void validUserCanLogin(String username, String password) {
        InventoryPage inventory = new LoginPage(DriverFactory.getDriver())
                .open().enterCredentials(username, password).submitValidLogin();
        Assert.assertEquals(inventory.title(), "Products");
    }
}
