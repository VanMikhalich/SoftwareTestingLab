package org.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DesmosTest {
    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.edge.driver", "D:\\учеба\\Тестирование ПО\\лабы\\lab2\\selenium\\msedgedriver.exe");
        driver = new EdgeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.desmos.com/?lang=ru");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Проверка заголовка страницы")
    public void testPageTitle() {
        driver.get("https://www.desmos.com?lang=ru");
        String expectedTitle = "Desmos | Прелестная бесплатная математика.";
        String actualTitle = driver.getTitle();
        assertEquals(expectedTitle, actualTitle, "Заголовок страницы не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Проверка видимости логотипа Desmos")
    public void testLogoVisibility() {
        driver.get("https://www.desmos.com?lang=ru");
        WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[local-name()='svg' and @class='dcg-desmos-svg-logo']")));
        assertTrue(logo.isDisplayed(), "Логотип Desmos не отображается на странице");
    }

    @Test
    @DisplayName("Проверка видимости кнопки 'Графический калькулятор'")
    public void testGraphingCalculatorButtonVisibility() {
        driver.get("https://www.desmos.com?lang=ru");
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'Графический калькулятор')]")));
        assertTrue(button.isDisplayed(), "Кнопка 'Графический калькулятор' не отображается");
    }

    @Test
    @DisplayName("Переход по ссылке 'Графический калькулятор'")
    public void testNavigateToGraphingCalculator() {
        driver.get("https://www.desmos.com?lang=ru");
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Графический калькулятор')]")));
        link.click();
        wait.until(ExpectedConditions.urlContains("calculator"));
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("calculator"), "Не получилось перейти на страницу");

    }

    @Test
    @DisplayName("Проверка видимости поля ввода выражений в калькуляторе")
    public void testCalculatorInputFieldVisibility() {
        driver.get("https://www.desmos.com/calculator?lang=ru");
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='dcg-main']")));
        assertTrue(inputField.isDisplayed(), "Поле ввода выражений не отображается в калькуляторе");
    }

    @Test
    @DisplayName("Ввод выражения 'test'")
    public void testEnterTextInCalculator() {
        driver.get("https://www.desmos.com/calculator?lang=ru");
        String text = "test";
        WebElement mathFieldContainer = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("div.dcg-math-field.dcg-mq-editable-field")));
        mathFieldContainer.click();
        Actions actions = new Actions(driver);
        actions.sendKeys(text).perform();
        WebElement spanWithText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.dcg-math-field.dcg-mq-editable-field > span.dcg-mq-root-block")));
        String displayedText = spanWithText.getText();
        assertEquals(text, displayedText); // вместо assertTrue сразу сравниваем значения
    }

    @Test
    @DisplayName("Проверка видимости кнопки 'Научный калькулятор'")
    public void testScientificCalculatorButtonVisibility() {
        driver.get("https://www.desmos.com?lang=ru");
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'Научный калькулятор')]")));
        assertTrue(button.isDisplayed(), "Кнопка 'Научный калькулятор' не отображается");
    }

    @Test
    @DisplayName("Эмуляция нажатия на кнопку 'Научный калькулятор'")
    public void testClickScientificCalculatorButton() {
        driver.get("https://www.desmos.com?lang=ru");
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Научный калькулятор')]")));
        button.click();
        wait.until(ExpectedConditions.urlContains("scientific"));
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("scientific"), "Не получилось перейти на страницу");
    }

    @Test
    @DisplayName("Проверка видимости кнопки переключения языка")
    public void testLanguageSwitchButtonVisibility() {
        driver.get("https://www.desmos.com?lang=ru");
        WebElement languageButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='dcg-language-picker__anchor-container']")));
        assertTrue(languageButton.isDisplayed(), "Кнопка переключения языка не отображается");
    }

    @Test
    @DisplayName("Проверка отображения футера страницы")
    public void testFooterVisibility() {
        driver.get("https://www.desmos.com/?lang=ru");
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//footer")));
        WebElement footer = driver.findElement(By.tagName("footer"));
        Assertions.assertTrue(footer.isDisplayed(), "Футер страницы успешно отображается");
    }
}