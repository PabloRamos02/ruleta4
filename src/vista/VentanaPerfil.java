package vista;

import controlador.SessionController;
import controlador.RuletaController;
import javax.swing.*;

public class VentanaPerfil {
    private SessionController session;
    private RuletaController ruletaController;
    private JDialog dialog;

    public VentanaPerfil(SessionController session, RuletaController ruletaController) {
        this.session = session;
        this.ruletaController = ruletaController;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        dialog = new JDialog();
        dialog.setTitle("Perfil");
        dialog.setSize(300, 250);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
        dialog.setModal(true);

        JTextField txtNombre = new JTextField(session.getNombreUsuario(), 15);
        JTextField txtUsername = new JTextField(session.getUsername(), 15);
        JLabel lblSaldo = new JLabel("Saldo: $" + ruletaController.getSaldo());
        JButton btnActualizar = new JButton("Actualizar Nombre");
        JButton btnRecargar = new JButton("Recargar Saldo");
        JButton btnCerrar = new JButton("Cerrar");

        txtUsername.setEditable(false); 

        dialog.add(new JLabel("Nombre:"));
        dialog.add(txtNombre);
        dialog.add(new JLabel("Usuario:"));
        dialog.add(txtUsername);
        dialog.add(lblSaldo);
        dialog.add(btnActualizar);
        dialog.add(btnRecargar);
        dialog.add(btnCerrar);

        btnActualizar.addActionListener(e -> {
            try {
                session.actualizarNombreUsuario(txtNombre.getText());
                JOptionPane.showMessageDialog(dialog, "Nombre actualizado correctamente");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage());
            }
        });

        btnRecargar.addActionListener(e -> {
            String montoStr = JOptionPane.showInputDialog(dialog, "Monto a recargar:");
            try {
                int monto = Integer.parseInt(montoStr);
                ruletaController.depositar(monto);
                lblSaldo.setText("Saldo: $" + ruletaController.getSaldo());
                JOptionPane.showMessageDialog(dialog, "Saldo recargado correctamente");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Monto inválido");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage());
            }
        });

        btnCerrar.addActionListener(e -> dialog.dispose());
    }

    public void mostrar() {
        dialog.setVisible(true);
    }
}