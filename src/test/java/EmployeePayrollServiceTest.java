import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class EmployeePayrollServiceTest {

    //uc2
    @Test
    public void given3Employees_WhenWrittenToFile_ShouldMatchEmployeeEntries() {
        EmployeePayrollData[] arrayOfEmp = { new EmployeePayrollData(1, "Bill", 100000.0),
                new EmployeePayrollData(2, "Mark ", 200000.0), new EmployeePayrollData(3, "Charlie", 300000.0) };
        EmployeePayrollService employeePayrollService;
        employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmp));
        employeePayrollService.writeEmployeeData(EmployeePayrollService.IOService.FILE_IO);
        long entries = employeePayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO);
        employeePayrollService.printData(EmployeePayrollService.IOService.FILE_IO);
        List<EmployeePayrollData> employeeList = employeePayrollService.readEmployeepayrollData(EmployeePayrollService.IOService.FILE_IO);
        System.out.println(employeeList);
        Assertions.assertEquals(3, entries);
    }

    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService
                .readEmployeepayrollData(EmployeePayrollService.IOService.DB_IO);
        System.out.println(employeePayrollData);
        Assertions.assertEquals(3, employeePayrollData.size());
    }

    //uc3

    @Test
    public void givenNewSalaryForEmployeeWhenupdatedShouldSyncWith_DB() throws EmployeePayrollException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService ();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService
                .readEmployeepayrollData(EmployeePayrollService.IOService.DB_IO);
        employeePayrollService.updateEmployeeSalary("Terisa", 250000.0);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
        Assertions.assertFalse(result);
        System.out.println(employeePayrollData);
    }

    //uc4
    @Test
    public void givenNewSalaryForEmployeeWhenUpdatedShouldSyncWith_DB() throws EmployeePayrollException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService
                .readEmployeepayrollData(EmployeePayrollService.IOService.DB_IO);
        employeePayrollService.updateEmployeeSalary("Terisa", 350000.0);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
        Assertions.assertFalse(result);
        System.out.println(employeePayrollData);
    }
    //uc5
    @Test
    public void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeepayrollData(EmployeePayrollService.IOService.DB_IO);
        LocalDate startDate = LocalDate.of(2018, 01, 01);
        LocalDate endDate = LocalDate.now();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService
                .readPayrollDataForRange(EmployeePayrollService.IOService.DB_IO, startDate, endDate);
        Assertions.assertEquals(3, employeePayrollData.size());
    }

    //uc6
    @Test
    public void givenEmployeePayrollData_ShouldMatch_AverageSalary_GroupByGender() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        Map<String, Double> avgSalaryByGender = employeePayrollService.readPayrollDataForAvgSalary(EmployeePayrollService.IOService.DB_IO);
        Assertions.assertTrue(avgSalaryByGender.get("M").equals(1800000.0) && avgSalaryByGender.get("F").equals(350000.0));
    }

    @Test
    public void givenEmployeePayrollData_ShouldMatch_SumOfSalary_GroupByGender() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        Map<String, Double> sumOfSalaryByGender = employeePayrollService.readPayrollDataForSumSalary(EmployeePayrollService.IOService.DB_IO);
        Assertions.assertTrue(
                sumOfSalaryByGender.get("M").equals(5400000.0) && sumOfSalaryByGender.get("F").equals(350000.0));
    }

    @Test
    public void givenEmployeePayrollData_ShouldMatch_MaxSalary_GroupByGender() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        Map<String, Double> MaxSalaryByGender = employeePayrollService.readPayrollDataForMaxSalary(EmployeePayrollService.IOService.DB_IO);
        Assertions.assertTrue(
                MaxSalaryByGender.get("M").equals(5000000.0) && MaxSalaryByGender.get("F").equals(350000.0));
    }

    @Test
    public void givenEmployeePayrollData_ShouldMatch_MinSalary_GroupByGender() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        Map<String, Double> MinSalaryByGender = employeePayrollService.readPayrollDataForMinSalary(EmployeePayrollService.IOService.DB_IO);
        Assertions.assertTrue(
                MinSalaryByGender.get("M").equals(100000.0) && MinSalaryByGender.get("F").equals(350000.0));
    }

    @Test
    public void givenEmployeePayrollData_ShouldMatch_Emp_CountGroupByGender() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        Map<String, Double> employeeCountByGender = employeePayrollService
                .readPayrollDataFor_CountOfEmployee_ByGender(EmployeePayrollService.IOService.DB_IO);
        Assertions.assertTrue(
                employeeCountByGender.get("M").equals(3.0) && employeeCountByGender.get("F").equals(1.0));
    }

    //uc7
    @Test
    public void givenNewEmployee_WhenAddedShouldSyncWithDB() throws EmployeePayrollException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService
                .readEmployeepayrollData(EmployeePayrollService.IOService.DB_IO);
        employeePayrollService.addEmployeeToPayRoll("Ashwini",100000.00, LocalDate.now(),"F");
        System.out.println(employeePayrollData);
        Boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Ashwini");
        Assertions.assertTrue(result);
        System.out.println(employeePayrollData);
    }
    //uc9
    @Test
    public void givenNewSalaryForEmployeeWhenupdatedShouldSyncWith_NewNormalisedDB() throws EmployeePayrollException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService
                .readEmployeepayrollData(EmployeePayrollService.IOService.DB_IO);
        employeePayrollService.updateEmployeeSalary("Ashwini", 200000.0);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Ashwini");
        Assertions.assertTrue(result);
        System.out.println(employeePayrollData);
    }
    //uc10
    @Test
    public void givenNewSalaryForEmployeeWhenupdatedShouldSyncWith_RefactorNewNormalisedDB() throws EmployeePayrollException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService
                .readEmployeepayrollData(EmployeePayrollService.IOService.DB_IO);
        employeePayrollService.updateEmployeeSalary("Hima", 200000.0);
        System.out.println(employeePayrollData);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Hima");
        Assertions.assertTrue(result);
    }
    //UC11
    @Test
    public void givenNewEmployeesWhenAddedInNormalised_ShouldSyncWithDB() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeepayrollData(EmployeePayrollService.IOService.DB_IO);
        employeePayrollService.addEmployeeToPayRoll("Revanth", 5000000.00, LocalDate.now(), "M");
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Revanth");
        Assertions.assertTrue(result);
    }
    //UC12
    @Test
    public void givenEmployeePayrollData_ShouldReturn_ActiveEmployees() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeepayrollData(EmployeePayrollService.IOService.DB_IO);
        List<EmployeePayrollData> employeePayrollData = employeePayrollService
                .readPayrollDataForActiveEmployees(EmployeePayrollService.IOService.DB_IO);
        System.out.println(employeePayrollData);
        Assertions.assertEquals(5, employeePayrollData.size());
    }

}

