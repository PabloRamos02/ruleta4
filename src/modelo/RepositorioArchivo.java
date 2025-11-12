package modelo;

import java.util.*;
import java.io.*;


public class RepositorioArchivo implements IRepositorioResultados {
    private static final String ARCHIVO_HISTORIAL = "historial_ruleta.csv";
    private static final String SEPARADOR = ",";

    @Override
    public void guardarResultado(Resultado resultado) {
        try (FileWriter fw = new FileWriter(ARCHIVO_HISTORIAL, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            
            String linea = String.join(SEPARADOR,
                String.valueOf(resultado.getNumero()),
                resultado.getColor(),
                String.valueOf(resultado.isGanador()),
                String.valueOf(resultado.getMonto()),
                resultado.getTipoApuesta().toString(),
                new Date().toString()
            );
            
            out.println(linea);
            
        } catch (IOException e) {
            System.err.println("Error al guardar resultado: " + e.getMessage());
        }
    }

    @Override
    public List<Resultado> obtenerHistorial() {
        List<Resultado> historial = new ArrayList<>();
        File archivo = new File(ARCHIVO_HISTORIAL);
        
        if (!archivo.exists()) {
            return historial;
        }

        try (Scanner scanner = new Scanner(archivo)) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] partes = linea.split(SEPARADOR);
                
                if (partes.length >= 5) {
                    int numero = Integer.parseInt(partes[0]);
                    String color = partes[1];
                    boolean ganador = Boolean.parseBoolean(partes[2]);
                    int monto = Integer.parseInt(partes[3]);
                    String tipoApuestaStr = partes[4];
                    
                    Resultado resultado = new Resultado(numero, color, ganador, monto, tipoApuestaStr);
                    historial.add(resultado);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo de historial no encontrado: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al leer historial: " + e.getMessage());
        }
        
        return historial;
    }

    public void limpiarHistorial() {
        File archivo = new File(ARCHIVO_HISTORIAL);
        if (archivo.exists()) {
            if (!archivo.delete()) {
                System.err.println("Error al eliminar archivo de historial");
            }
        }
    }

    @Override
    public List<Resultado> obtenerHistorialPorUsuario(int usuarioId) {
        // Placeholder: filter results by user if user tracking is implemented
        return obtenerHistorial();
    }
}