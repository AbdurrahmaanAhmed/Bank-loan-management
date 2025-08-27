import java.util.Scanner;
import java.util.regex.Pattern;

// Abstract class representing a loan
abstract class Loan {
    // Member variables
    protected String recordID; // Record ID of the loan
    protected String loanType; // Type of loan
    protected double interestRate; // Interest rate of the loan
    protected double amountLeft; // Amount left to pay for the loan
    protected int loanTermLeft; // Remaining loan term

    // Constructor
    public Loan(String loanType, double interestRate, double amountLeft, int loanTermLeft) {
        // Initialize loan properties
        this.loanType = loanType;
        this.interestRate = interestRate;
        this.amountLeft = amountLeft;
        this.loanTermLeft = loanTermLeft;
        // Call method to input record ID
        this.recordID = inputRecordID();
    }

    // Abstract method to print loan details
    public abstract void printDetails();

    // Getter methods
    public String getRecordID() { return recordID; }
    public String getLoanType() { return loanType; }
    public double getInterestRate() { return interestRate; }
    public int getLoanTermLeft() { return loanTermLeft; }
    public double getAmountLeft() { return amountLeft; }

    // Method to input record ID
    private String inputRecordID() {
        Scanner scanner = new Scanner(System.in); // Create a scanner object for input
        String input;
        do {
            System.out.print("Enter Record ID (6 digits only): "); // Prompt user for input
            input = scanner.nextLine(); // Read input from the user
        } while (!isValidRecordID(input)); // Loop until a valid record ID is provided
        return input; // Return the valid record ID
    }

    // Validation method for record ID
    private boolean isValidRecordID(String recordID) {
        return Pattern.matches("\\d{6}", recordID); // Check if the record ID matches the pattern (6 digits)
    }
}

// Subclass AutoLoan
class AutoLoan extends Loan {
    // Constructor
    public AutoLoan(String recordID, double interestRate, double amountLeft, int loanTermLeft) {
        // Call superclass constructor with loan type "Auto"
        super("Auto", interestRate, amountLeft, loanTermLeft);
    }
    
    // Method to print auto loan details
    @Override
    public void printDetails() {
        // Print auto loan details
        System.out.println("Auto Loan: RecordID=" + getRecordID() + ", InterestRate=" + getInterestRate() +
            ", AmountLeftToPay=" + getAmountLeft() + ", LoanTermLeft=" + getLoanTermLeft());
    }
}

// Subclass BuilderLoan
class BuilderLoan extends Loan {
    // Additional member variable for overpayment
    double overpayment;

    // Constructor
    public BuilderLoan(String recordID, double interestRate, double amountLeft, int loanTermLeft, double overpayment) {
        // Call superclass constructor with loan type "Builder"
        super("Builder", interestRate, amountLeft, loanTermLeft);
        // Initialize overpayment
        this.overpayment = overpayment;
    }

    // Method to print builder loan details
    @Override
    public void printDetails() {
        // Print builder loan details
        System.out.println("Builder Loan: RecordID=" + getRecordID() + ", InterestRate=" + getInterestRate() +
            ", AmountLeftToPay=" + getAmountLeft() + ", LoanTermLeft=" + getLoanTermLeft() +
            ", OverpaymentOption=" + overpayment);
    }
}

// Subclass MortgageLoan
class MortgageLoan extends Loan {
    // Additional member variable for overpayment
    double overpayment;

    // Constructor
    public MortgageLoan(String recordID, double interestRate, double amountLeft, int loanTermLeft, double overpayment) {
        // Call superclass constructor with loan type "Mortgage"
        super("Mortgage", interestRate, amountLeft, loanTermLeft);
        // Initialize overpayment
        this.overpayment = overpayment;
    }

    // Method to print mortgage loan details
    @Override
    public void printDetails() {
        // Print mortgage loan details
        System.out.println("Mortgage Loan: RecordID=" + getRecordID() + ", InterestRate=" + getInterestRate() +
            ", AmountLeftToPay=" + getAmountLeft() + ", LoanTermLeft=" + getLoanTermLeft() +
            ", OverpaymentOption=" + overpayment);
    }
}

// Subclass PersonalLoan
class PersonalLoan extends Loan {
    // Constructor
    public PersonalLoan(String recordID, double interestRate, double amountLeft, int loanTermLeft) {
        // Call superclass constructor with loan type "Personal"
        super("Personal", interestRate, amountLeft, loanTermLeft);
    }

    // Method to print personal loan details
    @Override
    public void printDetails() {
        // Print personal loan details
        System.out.println("Personal Loan: RecordID=" + getRecordID() + ", InterestRate=" + getInterestRate() +
            ", AmountLeftToPay=" + getAmountLeft() + ", LoanTermLeft=" + getLoanTermLeft());
    }
}

// Subclass OtherLoan
class OtherLoan extends Loan {
    // Constructor
    public OtherLoan(String recordID, double interestRate, double amountLeft, int loanTermLeft) {
        // Call superclass constructor with loan type "Other"
        super("Other", interestRate, amountLeft, loanTermLeft);
    }

    // Method to print other loan details
    @Override
    public void printDetails() {
        // Print other loan details
        System.out.println("Other Loan: RecordID=" + getRecordID() + ", InterestRate=" + getInterestRate() +
            ", AmountLeftToPay=" + getAmountLeft() + ", LoanTermLeft=" + getLoanTermLeft());
    }
}
