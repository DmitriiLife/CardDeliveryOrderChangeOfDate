package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.utils.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ChangeOfDate {
    private RegistrationInfo registrationInfo;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        registrationInfo = DataGenerator.RegistrationCard.generateInfo("ru");
    }

    @Test
    void generateTestDataUsing() {
        $("[data-test-id='city'] input").setValue(registrationInfo.getCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        String date = DataGenerator.getDate(3);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id=name] input").setValue(registrationInfo.getName());
        $("[data-test-id=phone] input").setValue(registrationInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(appear, Duration.ofSeconds(4))
                .shouldBe(exactText("Встреча успешно запланирована на " + date))
                .click();
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        String reDate = DataGenerator.getDate(9);
        $("[data-test-id='date'] input").setValue(reDate);
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='replan-notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(4))
                .shouldBe(exactText("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .click();
        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(appear, Duration.ofSeconds(4))
                .shouldBe(exactText("Встреча успешно запланирована на " + reDate));

    }
}
