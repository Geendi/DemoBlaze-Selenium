package com.demoblaze.drivers;

import com.demoblaze.utils.data.PropertyReader;
import com.demoblaze.utils.logs.LogsManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.regex.Pattern;

public class RemoteFactory implements DriverFactory {
    private static final HttpClient HTTP = HttpClient.newHttpClient();
    private static final Pattern READY = Pattern.compile("\"ready\"\s*:\s*true");
    private static volatile boolean gridReady;

    @Override
    public WebDriver createDriver() {
        URL url = gridUrl();
        waitForGrid(url);
        WebDriver driver = new RemoteWebDriver(url, options());
        driver.manage().window().setSize(new Dimension(
                Integer.parseInt(DriverConfig.WINDOW_WIDTH), Integer.parseInt(DriverConfig.WINDOW_HEIGHT)));
        return driver;
    }

    private static synchronized void waitForGrid(URL url) {
        if (gridReady)
            return;
        int seconds = Integer.parseInt(PropertyReader.getProperty("gridReadySeconds"));
        try {
            new FluentWait<>(url)
                    .withTimeout(Duration.ofSeconds(seconds))
                    .pollingEvery(Duration.ofSeconds(1))
                    .until(RemoteFactory::isReady);
        } catch (TimeoutException e) {
            throw new IllegalStateException("Selenium Grid at " + url + " is not ready after " + seconds
                    + "s. Is it running? Try: docker compose up -d", e);
        }
        gridReady = true;
    }

    private static boolean isReady(URL url) {
        try {
            HttpRequest request = HttpRequest.newBuilder(url.toURI().resolve("/status"))
                    .timeout(Duration.ofSeconds(2))
                    .GET()
                    .build();
            HttpResponse<String> response = HTTP.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200 && READY.matcher(response.body()).find();
        } catch (IOException | URISyntaxException e) {
            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    private MutableCapabilities options() {
        MutableCapabilities options = switch (Browser.fromConfigName()) {
            case CHROME -> ChromeFactory.options();
            case EDGE -> EdgeFactory.options();
            case FIREFOX -> FirefoxFactory.options();
        };
        options.setCapability("se:name", LogsManager.currentTest());
        return options;
    }

    private URL gridUrl() {
        String url = PropertyReader.getProperty("remoteUrl");
        if (url == null || url.isBlank())
            throw new IllegalStateException("remoteUrl is not set in webApp.properties");
        try {
            return URI.create(url.trim()).toURL();
        } catch (MalformedURLException | IllegalArgumentException e) {
            throw new IllegalStateException("remoteUrl is not a valid URL: " + url, e);
        }
    }

}
