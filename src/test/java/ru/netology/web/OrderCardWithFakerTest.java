package ru.netology.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Keys;
import ru.netology.util.ScreenShooterReportPortalExtension;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.DataGenerator.*;

@ExtendWith({ScreenShooterReportPortalExtension.class})
public class OrderCardWithFakerTest {

    private String city = getCity();
    private String date = getDate(3);
    private String dateOver = getDate(10);
    private String dateExpired = getDate(0);
    private String datePast = getDate(-2);
    private String name = getName();
    private String phone = getPhone();


    @Test
    void shouldRegisterNewDate() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] [type='text']").setValue(city);
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[data-test-id='name'] [type='text']").setValue(name);
        $("[data-test-id='phone'] [type='tel']").setValue(phone);
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(".notification__title").waitUntil(visible, 12000);

        open("http://localhost:9999/");
        $("[data-test-id='city'] [type='text']").setValue(city);
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(dateOver);
        $("[data-test-id='name'] [type='text']").setValue(name);
        $("[data-test-id='phone'] [type='tel']").setValue(phone);
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='replan-notification'] button").click();
        String getText = $("[data-test-id='success-notification'] div.notification__title").waitUntil(visible,500).getText();
        assertEquals("Успешнbо!", getText);
    }

    @Test
    void shouldRegisterExpiredDate() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] [type='text']").setValue(city);
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[data-test-id='name'] [type='text']").setValue(name);
        $("[data-test-id='phone'] [type='tel']").setValue(phone);
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(".notification__title").waitUntil(visible, 12000);

        open("http://localhost:9999/");
        $("[data-test-id='city'] [type='text']").setValue(city);
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(dateExpired);
        $("[data-test-id='name'] [type='text']").setValue(name);
        $("[data-test-id='phone'] [type='tel']").setValue(phone);
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        String getText = $("[data-test-id=date] .input__sub").getText();
        assertEquals("Заказ на выбранную дату невозможен", getText);

    }

    @Test
    void shouldRegisterPastDate() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] [type='text']").setValue(city);
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[data-test-id='name'] [type='text']").setValue(name);
        $("[data-test-id='phone'] [type='tel']").setValue(phone);
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(".notification__title").waitUntil(visible, 12000);

        open("http://localhost:9999/");
        $("[data-test-id='city'] [type='text']").setValue(city);
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(dateExpired);
        $("[data-test-id='name'] [type='text']").setValue(name);
        $("[data-test-id='phone'] [type='tel']").setValue(phone);
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        String getText = $("[data-test-id=date] .input__sub").getText();
        assertEquals("Заказ на выбранную дату невозможен", getText);

    }
}