public interface CheckerPrinter {
    boolean checkEligibility(double income, double amountRequested);
    void printCustomerDetails();
}