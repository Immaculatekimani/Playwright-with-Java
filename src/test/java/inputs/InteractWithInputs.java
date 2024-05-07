package inputs;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class InteractWithInputs {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
        Page inputPage = browser.newPage();

        inputText(inputPage);
        fillVsType(inputPage);

        playwright.close();

    }
    public static void inputText(Page page){
        page.navigate("https://www.lambdatest.com/selenium-playground/simple-form-demo");
        page.locator("input#user-message").type("Hey Tester");
        page.locator("id=showInput").click();
        String message = page.locator("#message").textContent();
        System.out.println(message);
        assertThat( page.locator("#message")).hasText("Hey Tester");
    }

    public static void fillVsType(Page page){
        page.navigate("https://www.lambdatest.com/selenium-playground/generate-file-to-download-demo");
        page.locator("#textbox").fill("To find XPath in Chrome, you can use the following steps:\n" +
                "Open the URL or webpage in Chrome\n" +
                "Hover the mouse over the desired element\n" +
                "Right-click on the element\n" +
                "Select Inspect\n" +
                "In the elements panel, press ctrl+F to enabl");
    }
}
