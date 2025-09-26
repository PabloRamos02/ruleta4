package vista;

import controlador.SessionController;
import javax.swing.*;

public class VentanaLogin {
    private SessionController session;
    private JFrame frame;

    public VentanaLogin(SessionController session) {
        this.session = session;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JTextField txtUser = new JTextField(15);
        JPasswordField txtPass = new JPasswordField(15);
        JButton btnLogin = new JButton("Ingresar");

        frame.add(new JLabel("Usuario:"));
        frame.add(txtUser);
        frame.add(new JLabel("Contraseña:"));
        frame.add(txtPass);
        frame.add(btnLogin);

        btnLogin.addActionListener(e -> intentarLogin(
                txtUser.getText(),
                new String(txtPass.getPassword())
        ));
    }

    private void intentarLogin(String user, String pass) {
        if (session.iniciarSesion(user, pass)) {
            new VentanaMenu(session).mostrar();
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(frame, "Credenciales incorrectas");
        }
    }

    public void mostrarVentana() {
        frame.setVisible(true);
    }
}