package modelo;

import java.time.LocalDateTime;

public class Resultado {
    private int id;
    private int usuarioId;
    private int numero;
    private String color;
    private boolean ganador;
    private int monto;
    private String tipoApuesta;
    private LocalDateTime fecha;
    
    public Resultado(int numero, String color, boolean ganador, int monto, String tipoApuesta) {
        this.numero = numero;
        this.color = color;
        this.ganador = ganador;
        this.monto = monto;
        this.tipoApuesta = tipoApuesta;
        this.fecha = LocalDateTime.now();
    }
    
    
    public Resultado(int id, int usuarioId, int numero, String color, boolean ganador, 
                    int monto, String tipoApuesta, LocalDateTime fecha) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.numero = numero;
        this.color = color;
        this.ganador = ganador;
        this.monto = monto;
        this.tipoApuesta = tipoApuesta;
        this.fecha = fecha;
    }
    
    
    public int getId() { return id; }
    public int getUsuarioId() { return usuarioId; }
    public int getNumero() { return numero; }
    public String getColor() { return color; }
    public boolean isGanador() { return ganador; }
    public int getMonto() { return monto; }
    public String getTipoApuesta() { return tipoApuesta; }
    public LocalDateTime getFecha() { return fecha; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public void setId(int id) { this.id = id; }
}