package testng.tests;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Example1 {
    private Playwright playwright;
    private Browser browser;
    private Page page;
    private BrowserContext context;

    @BeforeMethod
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
        context = browser.newContext();
        page = context.newPage();
    }

    @Test
    public void testExample() {
        // Start tracing
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true)
        );

        page.navigate("https://ecommerce-playground.lambdatest.io/");
        Locator myAccount = page.locator("//a[contains(.,'My account')][@role='button']");
        myAccount.hover();
        page.click("//a[contains(.,'Login')]");
        assertThat(page).hasTitle("Account Login");
        page.getByPlaceholder("E-Mail Address").type("immaculate@gmail.com");
        page.getByPlaceholder("Password").type("lambdatest");
        page.locator("//input[@value='Login']").click();
        assertThat(page).hasTitle("My Account");

        // Stop tracing and save trace
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("traceExample.zip"))
        );
    }

    @Test
    public void testAttach() {
        // Start tracing
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true)
        );

        page.navigate("https://ecommerce-playground.lambdatest.io/");

        // Define the path for the screenshot
        Path screenshotPath = Paths.get("./snaps/scr.png");
        Path pagescreenshotPath = Paths.get("./snaps/page.png");

        // Ensure the directory exists
        try {
            Files.createDirectories(screenshotPath.getParent());
            // Take a page screenshot
            Page.ScreenshotOptions screenshotOptions = new Page.ScreenshotOptions();
            screenshotOptions.setPath(screenshotPath);
            page.screenshot(screenshotOptions);

            // Take full page screenshot
            Page.ScreenshotOptions fullscreenshotOptions = new Page.ScreenshotOptions();
            fullscreenshotOptions.setFullPage(true).setPath(pagescreenshotPath);
            page.screenshot(fullscreenshotOptions);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Stop tracing and save trace
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("traceAttach.zip"))
        );
    }

    @AfterMethod
    public void testTeardown() {
        page.close();
        context.close();
        browser.close();
        playwright.close();
    }
}
