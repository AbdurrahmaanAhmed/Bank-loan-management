// Import statements
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Class definition for XYZBank
public class XYZBank {
    // Map to store customers with their IDs
    private Map<String, Customer> customers = new HashMap<>();
    // Variables to track current number of records and maximum allowed records
    private int currentNumberOfRecords=0;
    private int maxRecordsAllowed;

    // Getter method for currentNumberOfRecords
    public int getCurrentNumberOfRecords() {
        return currentNumberOfRecords;
    }

    // Constructor for XYZBank class
    public XYZBank(int maxRecords) {
        this.maxRecordsAllowed = maxRecords;
    }

    // Getter method for maxRecordsAllowed
    public int getMaxRecordsAllowed() {
        return maxRecordsAllowed;
    }

    // Method to add a loan to a customer
    public boolean addLoanToCustomer(String customerID, Loan loan) {
        // Check if maximum records limit reached
        if (currentNumberOfRecords >= maxRecordsAllowed) {
            System.out.println("Cannot add more loans, maximum records limit reached.");
            return false;
        }
        // Get the customer object using customer ID
        Customer customer = customers.get(customerID);
        // Add the loan to customer's credit records
        customer.getCreditRecords().add(loan);
        // Attempt to add loan to customer
        if (customer.addLoan(loan)) { // Corrected to use addLoan method
            currentNumberOfRecords++; // Increment currentNumberOfRecords
            System.out.println("Loan successfully added.");
            printAllCustomersDetails(); // Print details after adding the loan
            return true;
        } else {
            System.out.println("Loan addition failed due to eligibility criteria.");
            return false;
        }
    }

    // Method to register a new customer
    public void registerNewCustomer(String customerID, double income) {
        String upperCaseCustomerID = customerID.toUpperCase();
        // Check if customer already exists
        if (customers.containsKey(upperCaseCustomerID)) {
            System.out.println("Customer with ID " + upperCaseCustomerID + " already exists.");
        } else {
            // Add new customer to the map
            customers.put(upperCaseCustomerID, new Customer(upperCaseCustomerID, income));
            System.out.println("Customer registered successfully.");
        }
    }

    // Method to print details of a specific customer
    public void printCustomerDetails(String customerID) {
        // Retrieve customer object using customerID
        Customer customer = customers.get(customerID.toUpperCase());
        if (customer != null) {
            // Print details of the customer
            customer.printCustomerDetails();
        } else {
            System.out.println("Customer not found.");
        }
    }

    // Method to print details of all customers
    public void printAllCustomersDetails() {
        System.out.println("Maximum number of Records: " + maxRecordsAllowed);
        System.out.println("Registered records: " + currentNumberOfRecords);
        System.out.println("================================");

        for (Customer customer : customers.values()) {
            System.out.println("CustomerID: " + customer.getCustomerId());
            // Check if customer has any loans before attempting to print them
            if (customer.getCreditRecords().isEmpty()) {
                System.out.println("No loans found for this customer.");
            } else {
                System.out.printf("%-10s %-15s %-10s %-10s %-8s%n",
                        "RecordID", "LoanType", "IntRate", "AmountLeft", "TimeLeft");

                for (Loan loan : customer.getCreditRecords()) {
                    System.out.printf("%-10s %-15s %-10.2f %-10.2f %-8d%n",
                            loan.getRecordID(), loan.getLoanType(),
                            loan.getInterestRate(), loan.getAmountLeft(), loan.getLoanTermLeft());
                }
            }
            System.out.println("--------------------------------");
        }
    }

    // Method to update customer's income
    public void updateCustomerIncome(String customerID, double newIncome) {
        // Retrieve customer object using customerID
        Customer customer = customers.get(customerID.toUpperCase());
        if (customer != null) {
            // Update customer's income
            customer.updateIncome(newIncome);
        } else {
            System.out.println("Customer not found.");
        }
    }

    // Method to remove a loan from a customer
    public void removeLoanFromCustomer(String customerID, String recordID) {
        // Retrieve customer object using customerID
        Customer customer = customers.get(customerID.toUpperCase());
        if (customer != null) {
            // Remove the loan with specified recordID
            customer.removeLoan(recordID);
            // Decrement currentNumberOfRecords
            currentNumberOfRecords--;
        } else {
            System.out.println("Customer not found.");
        }
    }

    // Main method
    public static void main(String[] args) {
        // Scanner object for user input
        Scanner scanner = new Scanner(System.in);
        int maxRecords = 0; // Initialize to a default value
        while (true) {
            // Prompt user to enter maximum number of loan records
            System.out.print("Enter maximum number of loan records: ");
            String maxRecordsInput = scanner.nextLine();
            if (maxRecordsInput.matches("\\d+")) {
                // Input is a valid number, convert to integer
                maxRecords = Integer.parseInt(maxRecordsInput);
                break; // Exit loop if input is valid
            } else {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        // Create XYZBank object with maximum records limit
        XYZBank bank = new XYZBank(maxRecords);
        boolean exit = false;
        while (!exit) {
            // Display menu options
            System.out.println("\nXYZ Bank System");
            System.out.println("1) Register New Customer");
            System.out.println("2) Add Loan to Customer");
            System.out.println("3) Print Customer Details");
            System.out.println("4) Print All Customers' Details");
            System.out.println("5) Update Customer Income");
            System.out.println("6) Remove Loan from Customer");
            System.out.println("7) Exit");
            System.out.print("Please select an option from the above choices: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine()); // Attempt to parse user input as an integer
            } catch (NumberFormatException e) { // Catch NumberFormatException if input cannot be parsed
                System.out.println("Invalid input. Please enter a number between 1 and 7."); // Inform user about invalid input
                continue; // Continue to the next iteration of the loop
            }
            
            switch (choice) { // Switch statement based on user choice
        case 1: // Case for registering a new customer
                String customerId; // Variable to store customer ID
                boolean tryAgain; // Flag for reattempting input
                do {
                    System.out.print("Enter Customer ID: "); // Prompt user to enter customer ID
                    customerId = scanner.next().toUpperCase(); // Read customer ID from input and convert to uppercase
                    if (!isValidCustomerId(customerId)) { // Check if the entered customer ID is valid
                        System.out.println("Invalid Customer ID format. Customer ID should be 3 letters followed by 3 numbers."); // Inform user about invalid format
                        tryAgain = true; // Set flag to true to reattempt input
                    } else {
                        tryAgain = false; // Set flag to false to exit the loop
                    }
                } while (tryAgain); // Continue loop until valid customer ID is entered
            
                scanner.nextLine(); // Consume the newline character from the input buffer
            
                System.out.print("Enter Annual Income: "); // Prompt user to enter annual income
                double income = 0; // Variable to store annual income, initialized to a default value
                while (true) { // Loop until valid input is received
                    String incomeInput = scanner.nextLine(); // Read user input for annual income
                    if (incomeInput.matches("\\d+(\\.\\d+)?")) { // Check if input is a valid number
                        income = Double.parseDouble(incomeInput); // Parse input as double
                        break; // Exit loop if input is valid
                    } else {
                        System.out.println("Invalid input. Please enter a number."); // Inform user about invalid input
                        System.out.print("Enter Annual Income: "); // Prompt again for input
                    }
                }
            
                // Add logic to register new customer with provided ID and income
                bank.registerNewCustomer(customerId, income); // Call method to register new customer
                break; // Exit switch statement
            
                case 2:
                String custIdForLoan = "";
                boolean validCustomerIdFormat = false;
                while (!validCustomerIdFormat) {
                    System.out.print("Enter Customer ID for Loan: ");
                    custIdForLoan = scanner.nextLine().toUpperCase(); // Convert to uppercase
                    if (!isValidCustomerId(custIdForLoan)) {
                        System.out.println("Invalid Customer ID format. Customer ID should be 3 letters followed by 3 numbers.");
                    } else {
                        validCustomerIdFormat = true; // Exit loop if ID is valid
                        System.out.println("Customer ID is of the correct format: " + custIdForLoan);
                    }
                }
                // Accepting 6-digit record ID
                String recordId;
                do {
                    System.out.print("Enter Record ID (6 digits only): ");
                    recordId = scanner.nextLine();
                    if (!recordId.matches("\\d{6}")) {
                        System.out.println("Invalid Record ID. Please enter a 6-digit numeric value.");
                    }
                } while (!recordId.matches("\\d{6}"));
            
                // Check for duplicate records
                boolean duplicate = false;
                for (Customer customer : bank.customers.values()) {
                    if (customer.hasLoanWithRecordId(recordId)) {
                        duplicate = true;
                        break;
                    }
                }
                // If duplicate record found, prompt user and continue to next iteration
                if (duplicate) {
                    System.out.println("Record with the same Record ID already exists for another customer.");
                    break;
                }
            
                String loanType ="";
                boolean validLoanType = false;
                while (!validLoanType) {
                    System.out.print("Enter Loan Type (Auto, Builder, etc.): ");
                    loanType = scanner.nextLine();
                    // Assuming you have a predefined list of valid loan types
                    if (loanType.equalsIgnoreCase("Auto") || loanType.equalsIgnoreCase("Builder") ||
                        loanType.equalsIgnoreCase("Personal") || loanType.equalsIgnoreCase("Mortgage") ||
                        loanType.equalsIgnoreCase("Other")) {
                        validLoanType = true;
                    } else {
                        System.out.println("Invalid loan type. Please enter Auto, Builder, Personal, Mortgage, or Other.");
                    }
                }
            
                double interestRate;
                while (true) {
                    System.out.print("Enter Interest Rate: ");
                    try {
                        interestRate = Double.parseDouble(scanner.nextLine());
                        if (interestRate > 0) {
                            break;
                        } else {
                            System.out.println("Interest rate must be positive. Please enter a valid rate.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a numeric value.");
                    }
                }
            
                double amountLeft;
                while (true) {
                    System.out.print("Enter Amount Left To Pay (in thousands): ");
                    try {
                        amountLeft = Double.parseDouble(scanner.nextLine());
                        if (amountLeft > 0) {
                            break;
                        } else {
                            System.out.println("Amount left must be positive. Please enter a valid amount.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a numeric value.");
                    }
                }
            
                int loanTermLeft;
                while (true) {
                    System.out.print("Enter Loan Term Left (in years): ");
                    try {
                        loanTermLeft = Integer.parseInt(scanner.nextLine());
                        if (loanTermLeft > 0) {
                            break;
                        } else {
                            System.out.println("Loan term must be positive. Please enter a valid term.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a numeric value.");
                    }
                }
            
                double overpayment;
                while (true) {
                    System.out.print("What is your overpayment: ");
                    try {
                        overpayment = Double.parseDouble(scanner.nextLine());
                        if (overpayment >= 0) { // Assuming overpayment can be 0
                            break;
                        } else {
                            System.out.println("Overpayment cannot be negative. Please enter a valid amount.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a numeric value.");
                    }
                }
                if ("Auto".equalsIgnoreCase(loanType)){ // Check if the loan type is "Auto" (case-insensitive)
                    // If the loan type is "Auto", create a new AutoLoan object and add it to the customer's loans
                    bank.addLoanToCustomer(custIdForLoan, new AutoLoan(recordId, interestRate, amountLeft, loanTermLeft));
                } else if ("Builder".equalsIgnoreCase(loanType)){ // Check if the loan type is "Builder" (case-insensitive)
                    // If the loan type is "Builder", create a new BuilderLoan object and add it to the customer's loans
                    bank.addLoanToCustomer(custIdForLoan, new BuilderLoan(recordId, interestRate, amountLeft, loanTermLeft, overpayment));
                } else if ("Personal".equalsIgnoreCase(loanType)){ // Check if the loan type is "Personal" (case-insensitive)
                    // If the loan type is "Personal", create a new PersonalLoan object and add it to the customer's loans
                    bank.addLoanToCustomer(custIdForLoan, new PersonalLoan(recordId, interestRate, amountLeft, loanTermLeft));
                } else if ("Mortgage".equalsIgnoreCase(loanType)){ // Check if the loan type is "Mortgage" (case-insensitive)
                    // If the loan type is "Mortgage", create a new MortgageLoan object and add it to the customer's loans
                    bank.addLoanToCustomer(custIdForLoan, new MortgageLoan(recordId, interestRate, amountLeft, loanTermLeft, overpayment));
                } else if ("Other".equalsIgnoreCase(loanType)){ // Check if the loan type is "Other" (case-insensitive)
                    // If the loan type is "Other", create a new OtherLoan object and add it to the customer's loans
                    bank.addLoanToCustomer(custIdForLoan, new OtherLoan(recordId, interestRate, amountLeft, loanTermLeft));
                } else {
                    // If the loan type is not recognized, inform the user that it's unsupported
                    System.out.println("Unsupported loan type. Please enter a valid loan type.");
                }
                break; // Exit the switch statement after processing the loan type
            case 3: // Handling the case to print customer details
                String customerIdToPrint; // Initialize variable to store customer ID
                boolean validCustomerId; // Initialize variable to track if customer ID format is valid
                do {
                    System.out.print("Enter Customer ID to Print Details: "); // Prompt user to enter customer ID
                    customerIdToPrint = scanner.nextLine().toUpperCase(); // Read input and convert to uppercase
                    validCustomerId = customerIdToPrint.matches("[A-Z]{3}\\d{3}"); // Check if the entered customer ID matches the required format
                    if (!validCustomerId) {
                        System.out.println("Invalid Customer ID format. Customer ID should be 3 letters followed by 3 numbers."); // Inform user about invalid format
                    }
                } while (!validCustomerId); // Continue loop until valid customer ID format is provided
                bank.printCustomerDetails(customerIdToPrint); // Print details of the customer with the provided ID
                break; // Exit the switch statement after printing customer details
            case 4: // Handling the case to print details of all customers
                bank.printAllCustomersDetails(); // Invoke the method to print details of all customers
                break; // Exit the switch statement after printing all customer details
            
            case 5: // Handling the case to update customer income
                String customerIdToUpdate; // Initialize variable to store customer ID to be updated
                do {
                    System.out.print("Enter Customer ID to Update Income: "); // Prompt user to enter customer ID
                    customerIdToUpdate = scanner.nextLine().toUpperCase(); // Read input and convert to uppercase
                    validCustomerId = customerIdToUpdate.matches("[A-Z]{3}\\d{3}"); // Check if the entered customer ID matches the required format
                    if (!validCustomerId) {
                        System.out.println("Invalid Customer ID format. Customer ID should be 3 letters followed by 3 numbers."); // Inform user about invalid format
                    }
                } while (!validCustomerId); // Continue loop until valid customer ID format is provided
                
                double newIncome = 0; // Initialize variable to store new income value
                while (true) {
                    System.out.print("Enter New Income: "); // Prompt user to enter new income value
                    String incomeInput = scanner.nextLine(); // Read user input
                    if (incomeInput.matches("\\d+(\\.\\d+)?")) {
                        // Input is a valid number
                        newIncome = Double.parseDouble(incomeInput); // Convert input to double
                        break; // Exit loop if input is valid
                    } else {
                        System.out.println("Invalid input. Please enter a number."); // Inform user about invalid input
                    }
                }
                bank.updateCustomerIncome(customerIdToUpdate, newIncome); // Update customer income with the provided ID and new income value
                break; // Exit the switch statement after updating customer income
                case 6: // Case statement to handle removing a loan from a customer
                String customerIdToRemoveLoan; // Initialize variable to store the customer ID from which the loan will be removed
                
                do {
                    System.out.print("Enter Customer ID to Remove Loan From: "); // Prompt user to enter the customer ID
                    customerIdToRemoveLoan = scanner.nextLine().toUpperCase(); // Read input and convert to uppercase
                    validCustomerId = customerIdToRemoveLoan.matches("[A-Z]{3}\\d{3}"); // Check if the entered customer ID matches the required format
                    if (!validCustomerId) {
                        System.out.println("Invalid Customer ID format. Customer ID should be 3 letters followed by 3 numbers."); // Inform the user about the invalid format
                    } else if (!bank.customers.containsKey(customerIdToRemoveLoan)) {
                        System.out.println("Customer with ID " + customerIdToRemoveLoan + " does not exist."); // Inform the user if the customer does not exist
                        validCustomerId = false;
                    }
                } while (!validCustomerId); // Continue loop until a valid customer ID is provided
                
                String loanRecordId; // Initialize variable to store the loan record ID to be removed
                boolean validLoanRecordId;
                do {
                    System.out.print("Enter Record ID of Loan to Remove: "); // Prompt user to enter the loan record ID
                    loanRecordId = scanner.nextLine(); // Read user input
                    validLoanRecordId = loanRecordId.matches("\\d{6}"); // Check if the entered loan record ID matches the required format
                    if (!validLoanRecordId) {
                        System.out.println("Invalid Record ID format. Please enter a 6-digit numeric value."); // Inform the user about the invalid format
                    } else {
                        boolean loanExists = false;
                        for (Customer customer : bank.customers.values()) {
                            if (customer.hasLoanWithRecordId(loanRecordId)) {
                                loanExists = true;
                                break;
                            }
                        }
                        if (!loanExists) {
                            System.out.println("Loan with Record ID " + loanRecordId + " does not exist."); // Inform the user if the loan does not exist
                            validLoanRecordId = false;
                        }
                    }
                } while (!validLoanRecordId); // Continue loop until a valid loan record ID is provided
                
                bank.removeLoanFromCustomer(customerIdToRemoveLoan, loanRecordId); // Remove the loan from the customer
                break; // Exit the switch statement after removing the loan
            
            case 7: // Case statement to handle exiting the program
                System.out.println("Exiting..."); // Inform the user about the program exiting
                scanner.close(); // Close the scanner to release system resources
                return; // Return from the main method, effectively exiting the program
            
            default: // Default case for handling invalid choices
                System.err.println("Invalid choice. Please enter a number between 1 and 7."); // Inform the user about the invalid choice
        }
    }
}

    // Validation method for record ID
    private static boolean isValidRecordID(String recordID) {
        return recordID.matches("\\d+");
    }
    
    // Validation method for customer ID
    private static boolean isValidCustomerId(String customerId) {
        return customerId.matches("[A-Z]{3}\\d{3}");
    }

    





}
