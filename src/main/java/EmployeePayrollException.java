/**
 * create a class name as EmployeePayrollException
 */
public class EmployeePayrollException extends Exception {

    /**
     * variable
     */
    private static final long serialVersionUID = 1L;

    /**
     * create enum class
     * this data is constant
     */
    public enum Exception {
        DATA_NULL
    }

    public Exception type;

    /**
     * create a parameterized constructor name as EmployeePayrollException
     * @param message
     */
    public EmployeePayrollException( Exception type,String message) {
        super(message);
        this.type = type;
    }
}
