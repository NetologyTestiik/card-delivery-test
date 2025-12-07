package ru.netology.delivery;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {

    }


    private static final String[] CITIES = {
            "Москва", "Санкт-Петербург", "Казань", "Новосибирск", "Екатеринбург",
            "Нижний Новгород", "Челябинск", "Самара", "Омск", "Ростов-на-Дону",
            "Уфа", "Красноярск", "Воронеж", "Пермь", "Волгоград",
            "Краснодар", "Саратов", "Тюмень", "Тольятти", "Ижевск",
            "Барнаул", "Ульяновск", "Иркутск", "Хабаровск", "Ярославль",
            "Владивосток", "Махачкала", "Томск", "Оренбург", "Кемерово"
    };


    private static final String[] FIRST_NAMES = {
            "Александр", "Алексей", "Андрей", "Антон", "Артем",
            "Борис", "Вадим", "Валентин", "Валерий", "Василий",
            "Виктор", "Виталий", "Владимир", "Владислав", "Геннадий",
            "Георгий", "Григорий", "Даниил", "Денис", "Дмитрий",
            "Евгений", "Егор", "Иван", "Игорь", "Илья",
            "Кирилл", "Константин", "Лев", "Леонид", "Максим"
    };

    private static final String[] LAST_NAMES = {
            "Иванов", "Петров", "Сидоров", "Смирнов", "Кузнецов",
            "Попов", "Васильев", "Павлов", "Семенов", "Голубев",
            "Виноградов", "Богданов", "Воробьев", "Федоров", "Михайлов",
            "Беляев", "Тарасов", "Белов", "Комаров", "Орлов",
            "Киселев", "Макаров", "Андреев", "Ковалев", "Ильин",
            "Гусев", "Титов", "Кузьмин", "Кудрявцев", "Баранов"
    };

    private static final Random random = new Random();


    public static String generateCity() {
        return CITIES[random.nextInt(CITIES.length)];
    }


    public static String generateDate(int daysToAdd) {
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(daysToAdd);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return futureDate.format(formatter);
    }


    public static String generateName() {
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        return lastName + " " + firstName;
    }


    public static String generatePhone() {
        // Формат: +7 900 000-00-00
        StringBuilder phone = new StringBuilder("+79");
        for (int i = 0; i < 9; i++) {
            phone.append(random.nextInt(10));
        }
        return phone.toString();
    }


    public static String generateInvalidPhone() {
        String[] invalidPhones = {
                "123",
                "8900123456",       // 10 цифр вместо 11
                "890012345678",     // 12 цифр вместо 11
                "abc",
                "+7900abc1234",
                "+7 900 123 45 67", // с пробелами
                "8-900-123-45-67"   // с дефисами
        };
        return invalidPhones[random.nextInt(invalidPhones.length)];
    }


    public static String generateInvalidName() {
        String[] invalidNames = {
                "John Doe",
                "Иван123",          // с цифрами
                "Иван!Петров",      // со специальными символами
                "Иван Петров-",     // дефис в конце
                "-Иван Петров",     // дефис в начале
                "Иван  Петров",     // двойной пробел
                "  Иван Петров  "   // пробелы по краям
        };
        return invalidNames[random.nextInt(invalidNames.length)];
    }


    public static String generatePastDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate pastDate = currentDate.minusDays(random.nextInt(10) + 1); // от 1 до 10 дней назад
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return pastDate.format(formatter);
    }


    public static String generateDateLessThan3Days() {
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(random.nextInt(3)); // 0, 1 или 2 дня
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return futureDate.format(formatter);
    }
}