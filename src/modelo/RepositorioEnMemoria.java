package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class RepositorioEnMemoria implements IRepositorioResultados {
    private List<Resultado> resultados;
    
    public RepositorioEnMemoria() {
        this.resultados = new ArrayList<>();
    }
    
    @Override
    public void guardarResultado(Resultado resultado) {
        if (resultado != null) {
            resultados.add(resultado);
        }
    }
    
    @Override
    public List<Resultado> obtenerHistorial() {
        return Collections.unmodifiableList(resultados);
    }
    
    @Override
    public List<Resultado> obtenerHistorialPorUsuario(int usuarioId) {
        List<Resultado> historialUsuario = new ArrayList<>();
        for (Resultado resultado : resultados) {
            if (resultado.getUsuarioId() == usuarioId) {
                historialUsuario.add(resultado);
            }
        }
        return historialUsuario;
    }
}