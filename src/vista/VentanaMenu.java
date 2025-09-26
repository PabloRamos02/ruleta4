package vista;

import controlador.SessionController;
import controlador.RuletaController;
import modelo.TipoApuesta;
import javax.swing.*;

public class VentanaMenu {
    private SessionController session;
    private RuletaController ruletaController;
    private JFrame frame;
    private JLabel lblSaldo;

    public VentanaMenu(SessionController session) {
        this.session = session;
        this.ruletaController = new RuletaController(1000);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        frame = new JFrame("Menú Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        lblSaldo = new JLabel("Saldo: $" + ruletaController.getSaldo());
        frame.add(lblSaldo);

        JButton btnJugar = new JButton("Jugar Ruleta");
        JButton btnPerfil = new JButton("Perfil");
        JButton btnSalir = new JButton("Salir");

        frame.add(btnJugar);
        frame.add(btnPerfil);
        frame.add(btnSalir);

        btnJugar.addActionListener(e -> mostrarRuleta());
        btnPerfil.addActionListener(e -> mostrarPerfil());
        btnSalir.addActionListener(e -> salir());
    }

    private void mostrarRuleta() {
        JDialog ruletaDialog = new JDialog(frame, "Ruleta", true);
        ruletaDialog.setSize(300, 200);
        ruletaDialog.setLayout(new BoxLayout(ruletaDialog.getContentPane(), BoxLayout.Y_AXIS));

        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"ROJO", "NEGRO", "PAR", "IMPAR"});
        JTextField txtMonto = new JTextField(10);
        JButton btnApostar = new JButton("Apostar");

        ruletaDialog.add(new JLabel("Tipo de apuesta:"));
        ruletaDialog.add(comboTipo);
        ruletaDialog.add(new JLabel("Monto:"));
        ruletaDialog.add(txtMonto);
        ruletaDialog.add(btnApostar);

        btnApostar.addActionListener(e -> {
            try {
                TipoApuesta tipo = TipoApuesta.valueOf(comboTipo.getSelectedItem().toString());
                int monto = Integer.parseInt(txtMonto.getText());

                if (ruletaController.apostar(monto, tipo)) {
                    JOptionPane.showMessageDialog(ruletaDialog, "¡Ganaste!");
                } else {
                    JOptionPane.showMessageDialog(ruletaDialog, "Perdiste");
                }
                refrescarSaldo();
                ruletaDialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ruletaDialog, "Monto inválido");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(ruletaDialog, "Tipo de apuesta inválido");
            }
        });

        ruletaDialog.setVisible(true);
    }

    private void mostrarPerfil() {
        new VentanaPerfil(session, ruletaController).mostrar();
        refrescarSaldo();
    }

    private void salir() {
        session.cerrarSesion();
        System.exit(0);
    }

    private void refrescarSaldo() {
        lblSaldo.setText("Saldo: $" + ruletaController.getSaldo());
    }

    public void mostrar() {
        frame.setVisible(true);
    }
}