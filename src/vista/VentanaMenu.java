package vista;
import controlador.SessionController;
import controlador.RuletaController;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaMenu {
    private SessionController session;
    private RuletaController ruletaController;
    private JFrame frame;
    private JLabel lblSaldo;
    private IRepositorioResultados repositorioActual;

    public VentanaMenu(SessionController session) {
        this.session = session;
        
        this.repositorioActual = new RepositorioEnMemoria();
        this.ruletaController = new RuletaController(1000, repositorioActual);
        inicializarComponentes();
    }
    private void inicializarComponentes() {
        frame = new JFrame("Menú Principal - Ruleta V8");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 400);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        lblSaldo = new JLabel("Saldo: $" + ruletaController.getSaldo());
        lblSaldo.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(lblSaldo);
        JButton btnJugar = new JButton("Jugar Ruleta");
        JButton btnPerfil = new JButton("Perfil");
        JButton btnHistorial = new JButton("Historial Completo");
        JButton btnEstadisticas = new JButton("Estadísticas Avanzadas");
        JButton btnCambiarRepo = new JButton("Cambiar a Persistencia Archivo");
        JButton btnSalir = new JButton("Salir");
        Component[] buttons = {btnJugar, btnPerfil, btnHistorial, btnEstadisticas, btnCambiarRepo, btnSalir};
        for (Component btn : buttons) {
            ((JButton) btn).setAlignmentX(Component.CENTER_ALIGNMENT);
            frame.add(Box.createRigidArea(new Dimension(0, 10)));
            frame.add((JButton) btn);
        }
        btnJugar.addActionListener(e -> mostrarRuleta());
        btnPerfil.addActionListener(e -> mostrarPerfil());
        btnHistorial.addActionListener(e -> mostrarHistorialCompleto());
        btnEstadisticas.addActionListener(e -> mostrarEstadisticasAvanzadas());
        btnCambiarRepo.addActionListener(e -> cambiarRepositorio());
        btnSalir.addActionListener(e -> salir());
    }

    private void mostrarHistorialCompleto() {
        try {
            List<Resultado> historial = ruletaController.obtenerHistorialCompleto();        
            JDialog historialDialog = new JDialog(frame, "Historial Completo", true);
            historialDialog.setSize(700, 400);
            historialDialog.setLayout(new BorderLayout());

            String[] columnNames = {"Fecha", "Número", "Color", "Apuesta", "Monto", "Resultado"};
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            JTable tabla = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(tabla);

            
            for (Resultado resultado : historial) {
                String resultadoTexto = resultado.isGanador() ? "GANÓ" : "PERDIÓ";
                tableModel.addRow(new Object[]{
                    resultado.getFecha().toString(),
                    resultado.getNumero(),
                    resultado.getColor(),
                    resultado.getTipoApuesta(),
                    "$" + resultado.getMonto(),
                    resultadoTexto
                });
            }

            historialDialog.add(scrollPane, BorderLayout.CENTER);

            JPanel panelInferior = new JPanel();
            JButton btnCerrar = new JButton("Cerrar");
            btnCerrar.addActionListener(e -> historialDialog.dispose());
            panelInferior.add(btnCerrar);

            historialDialog.add(panelInferior, BorderLayout.SOUTH);
            historialDialog.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, 
                "Error al cargar el historial: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
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
                String tipo = comboTipo.getSelectedItem().toString();
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
                JOptionPane.showMessageDialog(ruletaDialog, ex.getMessage());
            }
        });

        ruletaDialog.setVisible(true);
    }

    private void mostrarEstadisticasAvanzadas() {
        Estadisticas stats = ruletaController.obtenerEstadisticas();
        
        String mensaje = String.format(
            "ESTADÍSTICAS AVANZADAS\n\n" +
            "Total de Jugadas: %d\n" +
            "Victorias: %d\n" +
            "Porcentaje de Victorias: %.1f%%\n" +
            "Racha Máxima de Victorias: %d\n" +
            "Tipo de Apuesta Más Frecuente: %s\n" +
            "Monto Total Apostado: $%d\n" +
            "Monto Total Ganado: $%d\n" +
            "Balance General: $%d\n\n" +
            "Repositorio: %s",
            stats.getTotalJugadas(),
            stats.getVictorias(),
            stats.getPorcentajeVictorias(),
            stats.getRachaMaxima(),
            stats.getTipoMasJugado(),
            stats.getMontoTotalApostado(),
            stats.getMontoTotalGanado(),
            stats.getBalance(),
            repositorioActual.getClass().getSimpleName()
        );

        JOptionPane.showMessageDialog(frame, mensaje, "Estadísticas Avanzadas", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cambiarRepositorio() {
        if (repositorioActual instanceof RepositorioEnMemoria) {
            repositorioActual = new RepositorioArchivo();
            ruletaController.cambiarRepositorio(repositorioActual);
            JOptionPane.showMessageDialog(frame, 
                "Cambiado a persistencia en archivo\nLos datos se guardarán entre sesiones",
                "Repositorio Cambiado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            repositorioActual = new RepositorioEnMemoria();
            ruletaController.cambiarRepositorio(repositorioActual);
            JOptionPane.showMessageDialog(frame,
                "Cambiado a memoria temporal\nLos datos se perderán al cerrar",
                "Repositorio Cambiado", JOptionPane.INFORMATION_MESSAGE);
        }
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
        lblSaldo.setText("Saldo: " + ruletaController.getSaldo());
    }

    public void mostrar() {
        frame.setVisible(true);
    }
}