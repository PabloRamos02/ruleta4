
package vista;

import controlador.SessionController;
import controlador.ResultadoController;
import modelo.Resultado;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class VentanaHistorial {
    private SessionController session;
    private ResultadoController resultadoController;
    private JDialog dialog;

    public VentanaHistorial(SessionController session) {
        this.session = session;
        this.resultadoController = new ResultadoController(session);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        dialog = new JDialog();
        dialog.setTitle("Historial de Jugadas");
        dialog.setSize(600, 400);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
        dialog.setModal(true);

        
        String[] columnNames = {"Fecha", "Número", "Color", "Apuesta", "Monto", "Resultado"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable tablaHistorial = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablaHistorial);

        
        JButton btnRefrescar = new JButton("Refrescar");
        JButton btnCerrar = new JButton("Cerrar");
        JButton btnEstadisticas = new JButton("Ver Estadísticas");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnRefrescar);
        panelBotones.add(btnEstadisticas);
        panelBotones.add(btnCerrar);

        dialog.add(scrollPane);
        dialog.add(panelBotones);

        
        cargarHistorial(tableModel);

        
        btnRefrescar.addActionListener(e -> cargarHistorial(tableModel));
        
        btnEstadisticas.addActionListener(e -> {
            mostrarEstadisticas();
        });

        btnCerrar.addActionListener(e -> dialog.dispose());
    }

    private void cargarHistorial(DefaultTableModel tableModel) {
        
        tableModel.setRowCount(0);

        
        List<Resultado> historial = resultadoController.obtenerHistorialUsuario();

        if (historial.isEmpty()) {
            tableModel.addRow(new Object[]{"No hay jugadas registradas", "", "", "", "", ""});
        } else {
            for (Resultado resultado : historial) {
                String resultadoTexto = resultado.isGanador() ? "GANÓ" : "PERDIÓ";
                String colorTexto = resultado.getColor();
                
                tableModel.addRow(new Object[]{
                    resultado.getFecha().toString(),
                    resultado.getNumero(),
                    colorTexto,
                    resultado.getTipoApuesta(),
                    "$" + resultado.getMonto(),
                    resultadoTexto
                });
            }
        }
    }

    private void mostrarEstadisticas() {
        List<Resultado> historial = resultadoController.obtenerHistorialUsuario();
        
        if (historial.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "No hay jugadas para calcular estadísticas");
            return;
        }

        
        int totalJugadas = historial.size();
        int victorias = 0;
        int montoTotalApostado = 0;
        int montoTotalGanado = 0;

        for (Resultado resultado : historial) {
            montoTotalApostado += resultado.getMonto();
            if (resultado.isGanador()) {
                victorias++;
                montoTotalGanado += resultado.getMonto() * 2; 
            }
        }

        double porcentajeVictorias = totalJugadas > 0 ? (victorias * 100.0) / totalJugadas : 0;
        int balance = montoTotalGanado - montoTotalApostado;

        String mensaje = String.format(
            "Estadísticas del Jugador:\n\n" +
            "Total de Jugadas: %d\n" +
            "Victorias: %d\n" +
            "Porcentaje de Victorias: %.1f%%\n" +
            "Monto Total Apostado: $%d\n" +
            "Monto Total Ganado: $%d\n" +
            "Balance: $%d",
            totalJugadas, victorias, porcentajeVictorias, 
            montoTotalApostado, montoTotalGanado, balance
        );

        JOptionPane.showMessageDialog(dialog, mensaje, "Estadísticas", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrar() {
        dialog.setVisible(true);
    }
}