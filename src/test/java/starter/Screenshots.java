package starter;

import com.microsoft.playwright.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Screenshots {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false));
        BrowserContext context = browser.newContext();
        Page page = context.newPage();
        page.navigate("https://ecommerce-playground.lambdatest.io/");

        // Define the path for the screenshot
        Path screenshotPath = Paths.get("./snaps/scr.png");
        Path pagescreenshotPath = Paths.get("./snaps/page.png");
        Path locatorscreenshotPath = Paths.get("./snaps/locator.png");
        Path regionscreenshotPath = Paths.get("./snaps/header.png");

        try {
            // Ensure the directory exists
            Files.createDirectories(screenshotPath.getParent());

            // Take a page screenshot
            Page.ScreenshotOptions screenshotOptions = new Page.ScreenshotOptions();
            screenshotOptions.setPath(screenshotPath);
            page.screenshot(screenshotOptions);

//            take full page
            Page.ScreenshotOptions fullscreenshotOptions = new Page.ScreenshotOptions();
            fullscreenshotOptions.setFullPage(true).setPath(pagescreenshotPath);
            page.screenshot(fullscreenshotOptions);

            // locator screenshot
            Locator myAccount = page.locator("Shop by Category Home Special");
            myAccount.screenshot(new Locator.ScreenshotOptions().setPath(locatorscreenshotPath));

//            region screenshot
            Locator header = page.locator("#header");
            header.screenshot(new Locator.ScreenshotOptions().setPath(regionscreenshotPath));



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the page and playwright
            page.close();
            playwright.close();
        }
    }
}
