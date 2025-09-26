package launcher;

import controlador.SessionController;
import vista.VentanaLogin;

public class Main {
    public static void main(String[] args) {
        SessionController session = new SessionController();
        new VentanaLogin(session).mostrarVentana();
    }
}