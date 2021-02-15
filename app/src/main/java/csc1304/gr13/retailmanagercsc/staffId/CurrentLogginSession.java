package csc1304.gr13.retailmanagercsc.staffId;


/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */

public class CurrentLogginSession {

    private static String staffId = null;

    private static String staffName = null;

    private static String staffPrimaryId = null;

    private static String staffEmail = null;

    private static String staffContact = null;

    private static String institutionId = null;

    private static String institutionName = null;


    public static String getStaffId() {
        return staffId;
    }

    public static void setStaffId(String staffId) {
        CurrentLogginSession.staffId = staffId;
    }



    public static String getStaffName() {
        return staffName;
    }

    public static void setStaffName(String staffName) {
        CurrentLogginSession.staffName = staffName;
    }

    public static String getStaffPrimaryId() {
        return staffPrimaryId;
    }

    public static void setStaffPrimaryId(String staffPrimaryId) {
        CurrentLogginSession.staffPrimaryId = staffPrimaryId;
    }


    public static String getStaffEmail() {
        return staffEmail;
    }

    public static void setStaffEmail(String staffEmail) {
        CurrentLogginSession.staffEmail = staffEmail;
    }

    public static String getStaffContact() {
        return staffContact;
    }

    public static void setStaffContact(String staffContact) {
        CurrentLogginSession.staffContact = staffContact;
    }

    public static String getInstitutionId() {
        return institutionId;
    }

    public static void setInstitutionId(String institutionId) {
        CurrentLogginSession.institutionId = institutionId;
    }

    public static String getInstitutionName() {
        return institutionName;
    }

    public static void setInstitutionName(String institutionName) {
        CurrentLogginSession.institutionName = institutionName;
    }
}
