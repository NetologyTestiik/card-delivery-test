package ru.netology.delivery;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {



    @BeforeEach
    void setup() {
        // Открываем приложение
        Configuration.browserSize = "1280x800";
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitFormSuccessfully() {
        // Генерируем тестовые данные
        String city = DataGenerator.generateCity();
        String date = DataGenerator.generateDate(4); // Дата через 4 дня
        String name = DataGenerator.generateName();
        String phone = DataGenerator.generatePhone();

        // Заполняем форму

        // 1. Город
        $("[data-test-id=city] input").setValue(city);

        // 2. Дата
        // Сначала очищаем поле даты (так как оно может иметь значение по умолчанию)
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);

        // 3. Имя
        $("[data-test-id=name] input").setValue(name);

        // 4. Телефон
        $("[data-test-id=phone] input").setValue(phone);

        // 5. Согласие на обработку данных
        $("[data-test-id=agreement]").click();

        // 6. Нажимаем кнопку "Забронировать"
        $(byText("Забронировать")).click();

        // Проверяем успешную отправку формы
        $("[data-test-id=notification]")
                .shouldHave(Condition.text("Встреча успешно забронирована"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldShowErrorWithInvalidCity() {
        // Заполняем форму с неверным городом
        $("[data-test-id=city] input").setValue("НекорректныйГород");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(DataGenerator.generateDate(4));
        $("[data-test-id=name] input").setValue(DataGenerator.generateName());
        $("[data-test-id=phone] input").setValue(DataGenerator.generatePhone());
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();

        // Проверяем сообщение об ошибке
        $("[data-test-id=city].input_invalid .input__sub")
                .shouldHave(Condition.text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldShowErrorWithPastDate() {
        // Заполняем форму с прошедшей датой
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());

        // Устанавливаем вчерашнюю дату
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        String pastDate = DataGenerator.generateDate(-1);
        $("[data-test-id=date] input").setValue(pastDate);

        $("[data-test-id=name] input").setValue(DataGenerator.generateName());
        $("[data-test-id=phone] input").setValue(DataGenerator.generatePhone());
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();

        // Проверяем сообщение об ошибке
        $("[data-test-id=date] .input_invalid .input__sub")
                .shouldHave(Condition.text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldShowErrorWithInvalidName() {
        // Заполняем форму с именем на английском
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(DataGenerator.generateDate(4));
        $("[data-test-id=name] input").setValue("John Doe"); // Английские буквы
        $("[data-test-id=phone] input").setValue(DataGenerator.generatePhone());
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();

        // Проверяем сообщение об ошибке
        $("[data-test-id=name].input_invalid .input__sub")
                .shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldShowErrorWithInvalidPhone() {
        // Заполняем форму с неверным телефоном
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(DataGenerator.generateDate(4));
        $("[data-test-id=name] input").setValue(DataGenerator.generateName());
        $("[data-test-id=phone] input").setValue("123"); // Неверный телефон
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();

        // Проверяем сообщение об ошибке
        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldShowErrorWithoutAgreement() {
        // Заполняем форму без согласия
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(DataGenerator.generateDate(4));
        $("[data-test-id=name] input").setValue(DataGenerator.generateName());
        $("[data-test-id=phone] input").setValue(DataGenerator.generatePhone());
        // Не ставим галочку согласия
        $(byText("Забронировать")).click();

        // Проверяем, что чекбокс подсвечен красным
        $("[data-test-id=agreement].input_invalid")
                .shouldBe(Condition.visible);
    }
}