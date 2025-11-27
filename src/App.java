import Ui.*;
import database.ClientDAO;
import model.Client;

public class App {
    public static void main(String[] args) throws Exception {
        Client c = new Client("Lucy Kung'u", null, null, null, null, null);
        Dashboard.clientDashboard(c);
    }
}