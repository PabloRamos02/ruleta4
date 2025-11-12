package controlador;

import modelo.Resultado;
import modelo.Usuario;
import java.util.List;
import java.util.ArrayList;

public class ResultadoController {
    private SessionController session;
    private List<Resultado> resultadosGlobales; 

    public ResultadoController(SessionController session) {
        this.session = session;
        this.resultadosGlobales = new ArrayList<>();
    }

    public List<Resultado> obtenerHistorialUsuario() {
        if (session.hayUsuario()) {
            Usuario usuario = session.getUsuarioActual();
            List<Resultado> historialUsuario = new ArrayList<>();
            
            for (Resultado resultado : resultadosGlobales) {
                if (resultado.getUsuarioId() == usuario.getId()) {
                    historialUsuario.add(resultado);
                }
            }
            return historialUsuario;
        }
        return new ArrayList<>();
    }

    public void agregarResultado(Resultado resultado) { 
        if (session.hayUsuario() && resultado != null) {
            Usuario usuario = session.getUsuarioActual();
            resultado.setUsuarioId(usuario.getId());
            resultadosGlobales.add(resultado);
        }
    }

    public int obtenerTotalJugadas() {
        return obtenerHistorialUsuario().size();
    }

    public int obtenerVictorias() {
        return (int) obtenerHistorialUsuario().stream()
                .filter(Resultado::isGanador)
                .count();
    }

    public double obtenerPorcentajeVictorias() {
        int total = obtenerTotalJugadas();
        if (total == 0) return 0;
        return (obtenerVictorias() * 100.0) / total;
    }
}