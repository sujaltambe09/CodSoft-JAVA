import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class CurrencyConverter {

    private static final String API_URL = "https://v6.exchangerate-api.com/v6/YOUR_API_KEY/latest/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Currency Selection
        System.out.print("Enter the base currency (e.g., USD): ");
        String baseCurrency = scanner.nextLine().toUpperCase();

        System.out.print("Enter the target currency (e.g., EUR): ");
        String targetCurrency = scanner.nextLine().toUpperCase();

        // Step 2: Amount Input
        System.out.print("Enter the amount you want to convert: ");
        double amount = scanner.nextDouble();

        // Step 3: Fetch Exchange Rate
        double exchangeRate = getExchangeRate(baseCurrency, targetCurrency);

        if (exchangeRate != -1) {
            // Step 4: Currency Conversion
            double convertedAmount = amount * exchangeRate;

            // Step 5: Display Result
            System.out.printf("%.2f %s is equal to %.2f %s\n", amount, baseCurrency, convertedAmount, targetCurrency);
        } else {
            System.out.println("Could not fetch the exchange rate. Please try again.");
        }

        scanner.close();
    }

    /**
     * @param baseCurrency
     * @param targetCurrency
     * @return
     */
    private static double getExchangeRate(String baseCurrency, String targetCurrency) {
        try {
            // Create the API URL
            String urlString = API_URL + baseCurrency;
            URL url = new URL(urlString);

            // Make the request
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Close connections
            in.close();
            connection.disconnect();

            // Parse JSON response
            JSONObject jsonResponse = new JSONObject(content.toString());
            JSONObject conversionRates = jsonResponse.getJSONObject("conversion_rates");

            // Get the target currency rate
            return conversionRates.getDouble(targetCurrency);

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}

