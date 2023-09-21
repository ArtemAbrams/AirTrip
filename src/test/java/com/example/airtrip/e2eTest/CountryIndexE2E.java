package com.example.airtrip.e2eTest;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

public class CountryIndexE2E {
    private WebDriver driver;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.edge.driver", "D:\\My learning of Java\\AirTrip\\msedgedriver.exe");
        driver = new EdgeDriver();
    }

    @Test
    public void testListOfConflictCountriesPage() {
        driver.get("https://localhost:8080/conflict/country/getAll");
        var a = driver.getCurrentUrl();
        Assertions.assertEquals("List of Conflict Countries", driver.getTitle());

        WebElement homeLink = driver.findElement(By.linkText("Home"));
        WebElement productLink = driver.findElement(By.linkText("Product"));
        WebElement orderLink = driver.findElement(By.linkText("Order"));
        WebElement countryLink = driver.findElement(By.linkText("Country"));

        WebElement header = driver.findElement(By.tagName("h1"));
        Assertions.assertEquals("List of Conflict Countries", header.getText());

        WebElement createButton = driver.findElement(By.linkText("Create"));
        Assertions.assertTrue(createButton.isDisplayed());


    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
