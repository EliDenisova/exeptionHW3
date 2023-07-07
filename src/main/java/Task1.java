import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Task1 {

    static class UserInputException extends Exception {
        public UserInputException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        try {
            String input = getUserInput();
            String[] inputData = input.split(" ");
            validateInputData(inputData);

            String lastName = inputData[0];
            String firstName = inputData[1];
            String middleName = inputData[2];
            LocalDate dateOfBirth = parseDateOfBirth(inputData[3]);
            String phoneNumber = inputData[4];
            char gender = parseGender(inputData[5]);

            User user = new User(lastName, firstName, middleName, dateOfBirth, phoneNumber, gender);
            writeUserDataToFile(user);
            System.out.println("Данные успешно записаны в файл.");
        } catch (UserInputException e) {
            System.out.println("Ошибка ввода данных: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка при записи данных в файл: " + e.getMessage());
            e.printStackTrace();
        }
    }

    static class User {
        private String lastName;
        private String firstName;
        private String middleName;
        private LocalDate dateOfBirth;
        private String phoneNumber;
        private char gender;

        public User(String lastName, String firstName, String middleName, LocalDate dateOfBirth, String phoneNumber, char gender) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.middleName = middleName;
            this.dateOfBirth = dateOfBirth;
            this.phoneNumber = phoneNumber;
            this.gender = gender;
        }

        public String getLastName() {
            return lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public LocalDate getDateOfBirth() {
            return dateOfBirth;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public char getGender() {
            return gender;
        }

        public String toString() {
            return lastName + " " + firstName + " " + middleName + " " + dateOfBirth + " " + phoneNumber + " " + gender;
        }
    }


    private static final int REQUIRED_FIELDS = 6;

    private static String getUserInput() throws UserInputException {
        System.out.println("Введите данные в формате: Фамилия Имя Отчество Дата_рождения Номер_телефона Пол");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String input = scanner.nextLine();
        if (input.trim().isEmpty()) {
            throw new UserInputException("Пустой ввод данных.");
        }
        return input;
    }

    private static void validateInputData(String[] inputData) throws UserInputException {
        if (inputData.length < REQUIRED_FIELDS) {
            throw new UserInputException("Введено меньше данных, чем требуется.");
        } else if (inputData.length > REQUIRED_FIELDS) {
            throw new UserInputException("Введено больше данных, чем требуется.");
        }
    }

    private static LocalDate parseDateOfBirth(String dateOfBirth) throws UserInputException {
        try {
            return LocalDate.parse(dateOfBirth, java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException e) {
            throw new UserInputException("Неверный формат даты рождения. Ожидается формат: dd.mm.yyyy");
        }
    }

    private static char parseGender(String gender) throws UserInputException {
        if (gender.length() != 1) {
            throw new UserInputException("Неверный формат пола. Ожидается символ латиницей f или m.");
        }
        char genderChar = gender.charAt(0);
        if (genderChar != 'f' && genderChar != 'm') {
            throw new UserInputException("Неверный формат пола. Ожидается символ латиницей f или m.");
        }
        return genderChar;
    }

    private static void writeUserDataToFile(User user) throws IOException {
        String fileName = user.getLastName() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(user.toString());
            writer.newLine();
        }
    }
}
