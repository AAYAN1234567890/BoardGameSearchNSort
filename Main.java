import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Board Game Collection Manager!\n");

        ArrayList<String> boardGames = new ArrayList<>();
        loadFromCSV(boardGames, "board_games.csv");

        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addGame(boardGames);
                    break;
                case 2:
                    printAlphabetically(boardGames);
                    break;
                case 3:
                    printByDifficulty(boardGames);
                    break;
                case 4:
                    printByGenre(boardGames);
                    break;
                case 5:
                    printByTime(boardGames);
                    break;
                case 6:
                    saveToCSV(boardGames, "board_games.csv");
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nWhat would you like to do:");
        System.out.println("1. Add a game");
        System.out.println("2. Print games alphabetically");
        System.out.println("3. Print games based on difficulty");
        System.out.println("4. Print games based on genre");
        System.out.println("5. Print games based on time");
        System.out.println("6. Save and exit");
        System.out.print("Enter your choice: ");
    }

    private static void addGame(ArrayList<String> collection) {
        System.out.print("Enter the title of the game to add: ");
        String title = scanner.nextLine();
        collection.add(title);
        System.out.println("Game added to the collection!");
    }

    private static void loadFromCSV(ArrayList<String> collection, String filename) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filename))) {
            // Skip the header line
            fileReader.readLine();
            
            String line;
            while ((line = fileReader.readLine()) != null) {
                collection.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading from CSV file: " + e.getMessage());
        }
    }
    

    private static void saveToCSV(ArrayList<String> collection, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (String game : collection) {
                writer.write(game + "\n");
            }
            System.out.println("Collection saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving to CSV file: " + e.getMessage());
        }
    }

    private static void printAlphabetically(ArrayList<String> collection) {
        // Dummy implementation, replace with actual sorting logic based on alphabetical order
        System.out.println("\nGames in alphabetical order: ");
        collection.sort(String::compareToIgnoreCase);
        collection.forEach(Main::printFormatted);
    }
    

    private static void printByDifficulty(ArrayList<String> collection) {
        // Dummy implementation, replace with actual sorting logic based on difficulty
        System.out.println("\nGames sorted by difficulty:");
        collection.forEach(Main::printFormatted);
    }

    private static void printByGenre(ArrayList<String> collection) {
        // Dummy implementation, replace with actual sorting logic based on genre
        System.out.println("\nGames sorted by genre:");
        collection.forEach(Main::printFormatted);
        }

    private static void printByTime(ArrayList<String> collection) {
        // Dummy implementation, replace with actual sorting logic based on time
        System.out.println("\nGames sorted by time:");
        collection.forEach(Main::printFormatted);
    }

    private static void printFormatted(String game) {
        String[] attributes = game.split(",");
        System.out.printf("%-50s | Rating: %.2f | Difficulty: %.2f | Players: %3d | Time: %3d | Year: %d | Genre: %s\n",
                attributes[0], Double.parseDouble(attributes[1]), Double.parseDouble(attributes[2]),
                Integer.parseInt(attributes[3]), Integer.parseInt(attributes[4]),
                Integer.parseInt(attributes[5]), attributes[6]);
    }
}
