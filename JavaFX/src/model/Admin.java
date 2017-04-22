package model;

/**
 * Class to represent an Admin.
 *
 * @author Nikola Istvanic
 */
public class Admin extends User {
    public Admin() {}

    public Admin(String username, String password, String ID, String position) {
        super(username, password, ID, position);
    }
}
