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
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the title of the game to add: ");
        String title = scanner.nextLine();
        System.out.print("Enter the rating of the game: ");
        double rating = scanner.nextDouble();
        System.out.print("Enter the difficulty of the game: ");
        double difficulty = scanner.nextDouble();
        System.out.print("Enter the number of players: ");
        int players = scanner.nextInt();
        System.out.print("Enter the time to play (in minutes): ");
        int time = scanner.nextInt();
        System.out.print("Enter the year of release: ");
        int year = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        System.out.print("Enter the genre of the game: ");
        String genre = scanner.nextLine();
    
        String newGame = String.format("%s,%.5f,%.5f,%d,%d,%d,%s",
                title, rating, difficulty, players, time, year, genre);
        collection.add(newGame);
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
        // Implement alphabetical sorting
        for (int i = 1; i < collection.size(); i++) {
            String key = collection.get(i);
            int j = i - 1;
            while (j >= 0 && collection.get(j).compareToIgnoreCase(key) > 0) {
                collection.set(j + 1, collection.get(j));
                j--;
            }
            collection.set(j + 1, key);
        }
    
        // Display the sorted games
        System.out.println("\nGames in alphabetical order: ");
        wait(2);
        collection.forEach(Main::printFormatted);
    }
    
    

    private static void printByDifficulty(ArrayList<String> collection) {
        // Implement sorting by difficulty using bubble sort
        for (int i = 0; i < collection.size() - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < collection.size() - i - 1; j++) {
                if (getAttribute(collection.get(j), 2) > getAttribute(collection.get(j + 1), 2)) {
                    // Swap the games
                    String temp = collection.get(j);
                    collection.set(j, collection.get(j + 1));
                    collection.set(j + 1, temp);
                    swapped = true;
                }
            }
            // If no two elements were swapped in the inner loop, then the array is already sorted
            if (!swapped) {
                break;
            }
        }
    
        // Display the sorted games
        System.out.println("\nGames sorted by difficulty:");
        wait(2);
        collection.forEach(Main::printFormatted);
    }

    private static void printByGenre(ArrayList<String> collection) {
        // Bubble sort implementation for sorting games by genre
        int n = collection.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                String[] game1 = collection.get(j).split(",");
                String[] game2 = collection.get(j + 1).split(",");
                if (game1.length > 6 && game2.length > 6) {
                    if (game1[6].compareToIgnoreCase(game2[6]) > 0) {
                        // Swap elements
                        String temp = collection.get(j);
                        collection.set(j, collection.get(j + 1));
                        collection.set(j + 1, temp);
                    }
                }
            }
        }
    
        // Display the sorted games by genre
        System.out.println("\nGames sorted by genre:");
        wait(2);
        collection.forEach(Main::printFormatted);
    }
    
    

        private static void printByTime(ArrayList<String> collection) {
            // Implement bubble sort based on time attribute
            for (int i = 0; i < collection.size() - 1; i++) {
                for (int j = 0; j < collection.size() - i - 1; j++) {
                    double time1 = getAttribute(collection.get(j), 4);
                    double time2 = getAttribute(collection.get(j + 1), 4);
                    if (time1 > time2) {
                        // Swap elements
                        String temp = collection.get(j);
                        collection.set(j, collection.get(j + 1));
                        collection.set(j + 1, temp);
                    }
                }
            }
        
            // Display the sorted games
            System.out.println("\nGames sorted by time:");
            wait(2);
            collection.forEach(Main::printFormatted);
        }
        

    private static void printFormatted(String game) {
        String[] attributes = game.split(",");
        if (attributes.length >= 7) {
            System.out.printf("%-50s | Rating: %.2f | Difficulty: %.2f | Players: %3d | Time: %4d | Year: %d | Genre: %s\n",
                    attributes[0], Double.parseDouble(attributes[1]), Double.parseDouble(attributes[2]),
                    Integer.parseInt(attributes[3]), Integer.parseInt(attributes[4]),
                    Integer.parseInt(attributes[5]), attributes[6]);
        } else {
            //System.out.println("Invalid data format for game(s): " + game);
        }
    }

    private static double getAttribute(String game, int index) {
        String[] attributes = game.split(",");
        if (attributes.length <= index) {
            return Double.MAX_VALUE; // Return a large value for out of bounds index
        }
        return Double.parseDouble(attributes[index]);
    }

    private static void wait(int sec) { //brought this over from our old CS Project (Text-Based RPG)
        try {
        Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
        e.printStackTrace();
        }
        }
    
}