import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class weatherapp {
    private static final String API_BASE_URL = "https://api.weatherapi.com/v1/history.json";
    private static final String API_KEY = "YOUR_WEATHER_API_KEY"; // Replace this with your actual API key

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Get weather");
            System.out.println("2. Get Wind Speed");
            System.out.println("3. Get Pressure");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the new line character after reading the integer input

            switch (choice) {
                case 1:
                    System.out.print("Enter the date (YYYY-MM-DD): ");
                    String dateForWeather = scanner.nextLine();
                    printTemperature(dateForWeather);
                    break;
                case 2:
                    System.out.print("Enter the date (YYYY-MM-DD): ");
                    String dateForWindSpeed = scanner.nextLine();
                    printWindSpeed(dateForWindSpeed);
                    break;
                case 3:
                    System.out.print("Enter the date (YYYY-MM-DD): ");
                    String dateForPressure = scanner.nextLine();
                    printPressure(dateForPressure);
                    break;
                case 0:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void printTemperature(String date) {
        String apiUrl = API_BASE_URL + "?key=" + API_KEY + "&q=New York&dt=" + date;
        String response = sendGetRequest(apiUrl);
        if (response != null) {
            String temperature = response.split("\"temp_c\":")[1].split(",")[0];
            System.out.println("Temperature on " + date + ": " + temperature + "°C");
        }
    }

    private static void printWindSpeed(String date) {
        String apiUrl = API_BASE_URL + "?key=" + API_KEY + "&q=New York&dt=" + date;
        String response = sendGetRequest(apiUrl);
        if (response != null) {
            String windSpeed = response.split("\"wind_kph\":")[1].split(",")[0];
            System.out.println("Wind Speed on " + date + ": " + windSpeed + " km/h");
        }
    }

    private static void printPressure(String date) {
        String apiUrl = API_BASE_URL + "?key=" + API_KEY + "&q=New York&dt=" + date;
        String response = sendGetRequest(apiUrl);
        if (response != null) {
            String pressure = response.split("\"pressure_mb\":")[1].split(",")[0];
            System.out.println("Pressure on " + date + ": " + pressure + " mb");
        }
    }

    private static String sendGetRequest(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(connection.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNextLine()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();
                return response.toString();
            } else {
                System.out.println("Failed to fetch weather data.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
