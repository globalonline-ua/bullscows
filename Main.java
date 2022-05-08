package bullscows;


import java.util.Random;
import java.util.Scanner;

public class Main {

    public static StringBuilder userTurn(String userInput, String[] secretArr) {
        String[] userArr = userInput.split("");

        int userBulls = 0;
        int userCows = 0;
        for (int i = 0; i < secretArr.length; i++) {
            if (secretArr[i].compareTo(userArr[i]) == 0) {
                userBulls++;
            } else {
                for (String s : userArr) {
                    if (secretArr[i].compareTo(s) == 0) {
                        userCows++;
                    }
                }
            }
        }
        StringBuilder userGrade = new StringBuilder("");

        if (userBulls == secretArr.length) {
            userGrade.append(userBulls).append(" bull(s)");
            return new StringBuilder(String.format("Grade: %s. Congratulations! You guessed the secret code.", userGrade));
        }

        if (userBulls == 1) {
            userGrade.append(userBulls).append(" bull");
        }
        if (userBulls > 1) {
            userGrade.append(userBulls).append(" bulls");
        }
        if (userBulls > 0 && userCows > 0) {
            userCows -= userBulls;
            userGrade.append(" and ");
        }
        if (userCows == 1) {
            userGrade.append(userCows).append(" cow");
        }
        if (userCows > 1) {
            userGrade.append(userCows).append(" cows");
        }

        if (userGrade.toString().compareTo("") == 0) {
            userGrade.append("None");
        }

        return new StringBuilder(String.format("Grade: %s", userGrade));
    }

    public static String rndNumber (int digits, int symbols) {
        StringBuilder secretCode = new StringBuilder();
        char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        char[] code = new char[symbols];
        System.arraycopy(digit, 0, code, 0,10);
        if (symbols > 10) {
            System.arraycopy(alphabet, 0, code, 10, symbols - 10);
        }

        char secCode = code[new Random().nextInt(code.length)];

        for (int i = 1; i <= digits; i++) {
            while (secretCode.toString().contains(String.valueOf(secCode))) {
                secCode = code[new Random().nextInt(code.length)];
            }
            secretCode.append(secCode);
        }

        return secretCode.toString();
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        String userInput = scanner.nextLine();
        try {
            Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            System.out.printf("Error: %s isn't a valid number.", userInput);
            return;
        }
        if (Integer.parseInt(userInput) < 1 || Integer.parseInt(userInput) > 36) {
            System.out.println("Error: input > 0 && <= 36!");
            return;
        }
        System.out.println("Input the number of possible symbols in the code:");
        String userInput2 = scanner.nextLine();
        try {
            Integer.parseInt(userInput2);
        } catch (NumberFormatException e) {
            System.out.printf("Error: %s isn't a valid number.", userInput2);
            return;
        }
        if (Integer.parseInt(userInput2) < 0 || Integer.parseInt(userInput2) > 36) {
            System.out.println("Error: input > 0 && <= 36!");
            return;
        }
        if (Integer.parseInt(userInput2) < Integer.parseInt(userInput)) {
            System.out.printf("Error: it's not possible to generate a code with a length of %s with %s unique symbols.", userInput, userInput2);
            return;
        }

        String secretCode = rndNumber(Integer.parseInt(userInput), Integer.parseInt(userInput2));
        String[] secretArr = secretCode.toString().split("");

        char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        String abStr = (Integer.parseInt(userInput2) > 10 ? ", a-" + alphabet[Integer.parseInt(userInput2) - 11] : "");
        System.out.println("The secret is prepared: " + "*".repeat(secretArr.length) + " (0-9" + abStr + ").");
        System.out.println("Okay, let's start a game!");

        Scanner scanner1 = new Scanner(System.in);
        String userInput3;
        StringBuilder usrTurn;
        int turn = 0;
        do {
            System.out.println("Turn " + ++turn + ":");
            userInput3 = scanner1.nextLine();

            if (userInput3.contains("\\s+") || userInput3.length() < 1 || userInput3.length() > Integer.parseInt(userInput2)) {
                System.out.println("Error: wrong input!");
                return;
            }

            usrTurn = userTurn(userInput3, secretArr);
            System.out.println(usrTurn);
        } while (!usrTurn.toString().contains("guessed"));


    }
}
