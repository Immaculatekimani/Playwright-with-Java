package starter;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CodeGen {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://ecommerce-playground.lambdatest.io/");
            assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(" My account"))).isVisible();
            Locator myAccountButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(" My account"));
            myAccountButton.hover();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Login")).click();
            page.getByPlaceholder("E-Mail Address").click();
            page.getByPlaceholder("E-Mail Address").type("immaculate@gmail.com");
            page.getByPlaceholder("Password").click();
            page.getByPlaceholder("Password").fill("lambdatest");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
            assertThat(page.locator("#account-login")).containsText("Warning: Your account has exceeded allowed number of login attempts. Please try again in 1 hour.");
        }
    }
}

