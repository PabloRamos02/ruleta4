package controlador;

import java.util.List;

import modelo.*;

public class RuletaController {
    private Ruleta ruleta;
    private IRepositorioResultados repositorio;

    public RuletaController(int saldoInicial, IRepositorioResultados repositorio) {
        this.repositorio = repositorio;
        this.ruleta = new Ruleta(saldoInicial, repositorio);
    }
    public RuletaController(int saldoInicial) {
        this(saldoInicial, new RepositorioEnMemoria());
    }

    public RuletaController() {
        this(0, new RepositorioEnMemoria());
    }

    
    public int getSaldo() {
        return ruleta.getSaldo();
    }

    public void depositar(int monto) {
        ruleta.depositar(monto);
    }

    public boolean apostar(int monto, String tipoApuesta) {
        ApuestaBase apuesta = crearApuesta(monto, tipoApuesta);
        return ruleta.apostar(apuesta);
    }

    
    public Estadisticas obtenerEstadisticas() {
        return ruleta.obtenerEstadisticas();
    }

    public List<Resultado> obtenerHistorialCompleto() {
    return ruleta.obtenerHistorialCompleto();
}

    public void cambiarRepositorio(IRepositorioResultados nuevoRepositorio) {
        this.repositorio = nuevoRepositorio;
        int saldoActual = ruleta.getSaldo();
        this.ruleta = new Ruleta(saldoActual, nuevoRepositorio);
    }

    private ApuestaBase crearApuesta(int monto, String tipoApuesta) {
        switch (tipoApuesta.toUpperCase()) {
            case "ROJO":
                return new ApuestaRojo(monto);
            case "NEGRO":
                return new ApuestaNegro(monto);
            case "PAR":
                return new ApuestaPar(monto);
            case "IMPAR":
                return new ApuestaImpar(monto);
            default:
                throw new IllegalArgumentException("Tipo de apuesta no válido: " + tipoApuesta);
        }
    }
}