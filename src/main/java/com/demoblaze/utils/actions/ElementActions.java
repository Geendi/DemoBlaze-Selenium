package com.demoblaze.utils.actions;

import com.demoblaze.utils.logs.LogsManager;
import com.demoblaze.utils.waits.WaitManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.nio.file.Path;
import java.util.List;

public class ElementActions {
    private final WebDriver driver;
    private final WaitManager waitManager;

    public ElementActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    public ElementActions click(By locator) {
        waitManager.fluentWait().until(d -> {
            WebElement element = ExpectedConditions.elementToBeClickable(locator).apply(d);
            if (element == null) return false;
            scrollToElement(element);
            element.click();
            return true;
        });
        LogsManager.debug("Clicked on element:", locator.toString());
        return this;
    }

    public ElementActions clearAndType(By locator, String text) {
        waitManager.fluentWait().until(d -> {
            WebElement element = ExpectedConditions.visibilityOfElementLocated(locator).apply(d);
            if (element == null) return false;
            scrollToElement(element);
            element.clear();
            element.sendKeys(text);
            return text.equals(element.getDomProperty("value"));
        });
        LogsManager.debug("Typed text:", text, "into element:", locator.toString());
        return this;
    }

    public String getText(By locator) {
        WebElement element = waitManager.fluentWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
        scrollToElement(element);
        String text = element.getText();
        LogsManager.debug("Retrieved text from element:", locator.toString(), "Text:", text);
        return text;
    }

    public List<String> getTexts(By locator) {
        try {
            List<WebElement> elements = waitManager.fluentWait()
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
            List<String> texts = elements.stream().map(WebElement::getText).toList();
            LogsManager.debug("Retrieved", String.valueOf(texts.size()), "texts from elements:", locator.toString());
            return texts;
        } catch (TimeoutException e) {
            LogsManager.debug("No visible elements found:", locator.toString());
            return List.of();
        }
    }

    public boolean isDisplayed(By locator) {
        try {
            waitManager.fluentWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public ElementActions waitForVisibility(By locator) {
        waitManager.fluentWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
        LogsManager.debug("Element is visible:", locator.toString());
        return this;
    }

    public ElementActions waitForInvisibility(By locator) {
        waitManager.fluentWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
        LogsManager.debug("Element is no longer visible:", locator.toString());
        return this;
    }

    public ElementActions clickAndWaitForRefresh(By locator, By refreshedLocator) {
        WebElement oldElement = waitManager.fluentWait()
                .until(ExpectedConditions.presenceOfElementLocated(refreshedLocator));
        click(locator);
        waitManager.fluentWait().until(ExpectedConditions.stalenessOf(oldElement));
        LogsManager.debug("Content refreshed:", refreshedLocator.toString());
        return this;
    }

    public ElementActions clickUntilInvisible(By locator, By disappearingLocator) {
        waitManager.fluentWait().until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                if (!d.findElement(disappearingLocator).isDisplayed()) return true;
                d.findElement(locator).click();
                return false;
            }

            @Override
            public String toString() {
                return "clicking " + locator + " until " + disappearingLocator + " is invisible";
            }
        });
        LogsManager.debug("Clicked", locator.toString(), "until", disappearingLocator.toString(), "disappeared");
        return this;
    }

    public ElementActions uploadFile(By locator, String filePath) {
        String fileAbsolutePath = Path.of(filePath).toAbsolutePath().toString();
        WebElement element = waitManager.fluentWait().until(ExpectedConditions.presenceOfElementLocated(locator));
        element.sendKeys(fileAbsolutePath);
        LogsManager.debug("Uploaded file:", fileAbsolutePath, "to element:", locator.toString());
        return this;
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'auto',block:'center',inline:'center'});", element);
    }
}
