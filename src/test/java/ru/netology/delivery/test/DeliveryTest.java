package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $x("//*[@data-test-id='city']//input").setValue(validUser.getCity());
        $x("//*[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//*[@data-test-id='date']//input").setValue(firstMeetingDate);
        $x("//*[@data-test-id='name']//input").setValue(validUser.getName());
        $x("//*[@data-test-id='phone']//input").setValue(validUser.getPhone());
        $x("//*[@data-test-id='agreement']//span").click();
        $x("//span[@class='button__text']").click();
        $x("//*[@data-test-id='success-notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $x("//*[@class='notification__content']").shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(secondMeetingDate);
        $x("//span[@class='button__text']").click();
        $x("//*[@data-test-id='success-notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $x("//*[@class='notification__content']").shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }
}
