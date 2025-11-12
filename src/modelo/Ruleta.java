package modelo;

import java.util.List;

public class Ruleta {
    private int saldo;
    private IRepositorioResultados repositorio;

    public Ruleta(int saldoInicial, IRepositorioResultados repositorio) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
        this.saldo = saldoInicial;
        this.repositorio = repositorio;
    }

    public Ruleta(IRepositorioResultados repositorio) {
        this(0, repositorio);
    }

    public int getSaldo() { return saldo; }

    public void depositar(int monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }
        this.saldo += monto;
    }

    public boolean apostar(ApuestaBase apuesta) {
        if (apuesta.getMonto() <= 0 || apuesta.getMonto() > saldo) {
            return false;
        }

        int numero = generarNumero();
        String color = determinarColor(numero);
        boolean gana = apuesta.acierta(numero, color);


        Resultado resultado = new Resultado(numero, color, gana, apuesta.getMonto(), apuesta.getEtiqueta());
        repositorio.guardarResultado(resultado);

        
        if (gana) {
            saldo += apuesta.getMonto(); 
        } else {
            saldo -= apuesta.getMonto();
        }

        return gana;
    }

    private int generarNumero() {
        return (int) (Math.random() * 37);
    }

    private String determinarColor(int numero) {
        if (numero == 0) return "VERDE";
        ApuestaRojo apuestaRojo = new ApuestaRojo(0);
        return apuestaRojo.acierta(numero, "") ? "ROJO" : "NEGRO";
    }

    public Estadisticas obtenerEstadisticas() {
        return new Estadisticas(repositorio.obtenerHistorial());
    }
    public List<Resultado> obtenerHistorialCompleto() {
    return repositorio.obtenerHistorial();
    }

}