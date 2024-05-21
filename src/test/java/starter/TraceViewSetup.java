package starter;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TraceViewSetup {
    public static void main(String[] args) {
        Playwright playwright = null;
        Browser browser = null;
        BrowserContext context = null;
        boolean tracingStarted = false;

        try {
            playwright = Playwright.create();

            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

            context = browser.newContext();

            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true)
            );
            tracingStarted = true;

            Page page = context.newPage();
            page.setDefaultNavigationTimeout(80000); // Set navigation timeout to 80 seconds

            // Navigate to the website
            page.navigate("https://ecommerce-playground.lambdatest.io/");

            // Perform interactions on the page
            assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(" My account"))).isVisible();
            Locator myAccountButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(" My account"));
            myAccountButton.hover();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Login")).click();
            page.getByPlaceholder("E-Mail Address").click();
            page.getByPlaceholder("E-Mail Address").type("immaculate@gmail.com");
            page.getByPlaceholder("Password").click();
            page.getByPlaceholder("Password").fill("lambdatest");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();

            // Assert that the expected warning message is displayed
            assertThat(page.locator("#account-login")).containsText("Warning: Your account has exceeded allowed number of login attempts. Please try again in 1 hour.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (tracingStarted) {
                    context.tracing().stop(new Tracing.StopOptions()
                            .setPath(Paths.get("TestTraces.zip"))
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Close the context, browser, and playwright
                if (context != null) {
                    context.close();
                }
                if (browser != null) {
                    browser.close();
                }
                if (playwright != null) {
                    playwright.close();
                }
            }
        }
    }
}
