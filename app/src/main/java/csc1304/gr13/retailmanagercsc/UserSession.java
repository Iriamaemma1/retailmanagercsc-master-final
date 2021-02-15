package csc1304.gr13.retailmanagercsc;
/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */

public class UserSession {

    private static String employeeId =  null;

    public static String getEmployeeId() {
        return employeeId;
    }

    public static void setEmployeeId(String employeeId) {
        UserSession.employeeId = employeeId;
    }
}
