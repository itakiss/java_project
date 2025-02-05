package hr.java.project.glavna.menu;

public class UserSessionController {

    private static String userId;
    private static String userFullName;
    private static String userName;
    private static String userEmail;
    private static String userStatus;
    private static String userAdmin;

    /**
     * Create an object of UserSessionController
     */
    private static final UserSessionController instance = new UserSessionController();

    /**
     * Make the constructor private so that this class cannot be instantiated
     */
    private UserSessionController() { }

    /**
     * Get the only object available
     * @return      UserSessionController instance.
     */


    public static void setUserFullName(String userFullName) {
        UserSessionController.userFullName = userFullName;
    }

    public static void setUserName(String userName) {
        UserSessionController.userName = userName;
    }

    public static void setUserAdmin(String userAdmin) {
        UserSessionController.userAdmin = userAdmin;
    }

    public static void setUserId(String userId) {
        UserSessionController.userId = userId;
    }


}

