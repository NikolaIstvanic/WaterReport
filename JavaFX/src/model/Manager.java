package model;

/**
 * Class to represent a Manager.
 *
 * @author Nikola Istvanic
 */
public class Manager extends User {
    public Manager() {}

    public Manager(String username, String password, String ID, String position) {
        super(username, password, ID, position);
    }
}
