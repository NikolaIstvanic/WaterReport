package model;

/**
 * Class to represent a Worker.
 *
 * @author Nikola Istvanic
 */
public class Worker extends User {
    public Worker() {}

    public Worker(String username, String password, String ID, String position) {
        super(username, password, ID, position);
    }
}
