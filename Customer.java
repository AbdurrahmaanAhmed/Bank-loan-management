import java.util.ArrayList;

// Class Customer implementing CheckerPrinter interface
class Customer implements CheckerPrinter {
    // Member variables
    private String customerID; // Stores the ID of the customer
    private double annualIncome; // Stores the annual income of the customer
    private boolean eligibilityStatus; // Stores the eligibility status of the customer
    private ArrayList<Loan> creditRecords; // Stores the credit records (loans) associated with the customer

    // Constructor
    public Customer(String customerID, double annualIncome) {
        this.customerID = customerID; // Initialize customer ID
        this.annualIncome = annualIncome; // Initialize annual income
        this.eligibilityStatus = false; // Initialize eligibility status to false
        this.creditRecords = new ArrayList<>(); // Initialize the ArrayList to store credit records
    }

    // Getter method for creditRecords
    public ArrayList<Loan> getCreditRecords() {
        return creditRecords; // Return the ArrayList of credit records
    }

    // Getter method for customerID
    public String getCustomerId() {
        return customerID; // Return the customer ID
    }

    // Method to update the annual income of the customer
    public void updateIncome(double newIncome) {
        this.annualIncome = newIncome; // Update the annual income
        updateEligibilityStatus(); // Update the eligibility status based on the new income
    }

    // Method to add a credit record (loan) to the customer's credit records
    public boolean addCreditRecord(Loan loan) {
        return creditRecords.add(loan); // Add the loan to the credit records ArrayList
    }

    // Method to remove a credit record (loan) from the customer's credit records by record ID
    public void removeCreditRecord(String recordID) {
        creditRecords.removeIf(loan -> loan.getRecordID().equals(recordID)); // Remove the loan with the specified record ID
    }

    // Method to remove a loan from the customer's credit records by record ID
    public void removeLoan(String recordID) {
        creditRecords.removeIf(loan -> loan.recordID.equals(recordID)); // Remove the loan with the specified record ID
    }

    // Method to add a loan to the customer's credit records
    public boolean addLoan(Loan loan) {
        // You might want to check eligibility criteria here
        creditRecords.add(loan); // Add the loan to the credit records ArrayList
        return true; // Assuming the addition is always successful for this context
    }

    // Method to check if the customer is eligible for loans
    public boolean isEligible() {
        return eligibilityStatus; // Return the eligibility status
    }

    // Method to update the eligibility status based on the annual income and total loan amount
    private void updateEligibilityStatus() {
        // Calculate the total amount left to pay for all loans
        double totalAmountLeft = creditRecords.stream()
                .mapToDouble(Loan::getAmountLeft)
                .sum();
        // Update the eligibility status based on the income and total amount left
        eligibilityStatus = checkEligibility(annualIncome, totalAmountLeft);
    }

    // Method to print customer details
    @Override
    public void printCustomerDetails() {
        // Print credit records in a table
        System.out.println("Maximum number of Records: ");
        System.out.println("Registered records: ");
        System.out.println("================================");
        // Print eligibility status
        System.out.println("Eligible to arrange new loans - " + checkEligibility(gettotalAmountLeft(), getAnnualIncome()));;
        System.out.println("CustomerID: " + getCustomerId());
        // Print credit records in tabular format
        System.out.printf("%-12s %-15s %-12s %-15s %-18s%n",
                "Record ID", "Loan Type", "Interest Rate", "Amount Left", "Loan Term Left");
        // Print each credit record
        for (Loan loan : creditRecords) {
            System.out.printf("%-12s %-15s %-12.2f £%-15.2f %-18d%n",
                    loan.getRecordID(), loan.getLoanType(),
                    loan.getInterestRate(), loan.getAmountLeft(), loan.getLoanTermLeft());
        }
    }

    // Method to check eligibility based on income and total loan amount
    @Override
    public boolean checkEligibility(double income, double totalLoanAmount) {
        // Assuming the eligibility is based on whether total loan amount does not exceed a certain threshold of income
        return totalLoanAmount <= 4 * income;
    }

    // Getter method for annualIncome
    public double getAnnualIncome() {
        return this.annualIncome; // Return the annual income
    }

    // Getter method for total amount left to pay (assuming it's the same as annual income)
    public double gettotalAmountLeft() {
        return this.annualIncome; // Return the total amount left to pay
    }

    // Method to check if the customer has a loan with a specific record ID
    public boolean hasLoanWithRecordId(String recordId) {
        // Iterate through credit records to check for the specified record ID
        for (Loan loan : creditRecords) {
            if (loan.getRecordID().equals(recordId)) {
                return true; // Return true if the record ID is found
            }
        }
        return false; // Return false if the record ID is not found
    }
}
