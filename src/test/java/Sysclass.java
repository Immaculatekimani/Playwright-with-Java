import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Sysclass {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("http://192.168.200.222:30023/Xe/");
            page.navigate("http://192.168.200.222:30023/Xe/#login");
            page.getByPlaceholder("Username").click();
            page.getByPlaceholder("Username").click();
            page.getByPlaceholder("Username").fill("admin");
            page.getByPlaceholder("Password").click();
            page.getByPlaceholder("Password").press("CapsLock");
            page.getByPlaceholder("Password").fill("A");
            page.getByPlaceholder("Password").press("CapsLock");
            page.getByPlaceholder("Password").fill("Admin@123");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
            page.navigate("http://192.168.200.222:30023/Xe/#");
            page.navigate("http://192.168.200.222:30023/Xe/#home");
            page.locator("#tool-1262-toolEl").click();
            assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(" Scheme Setup"))).isVisible();
        }
    }
}
