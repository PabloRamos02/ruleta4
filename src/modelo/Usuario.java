package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Usuario {
    private static int contador = 0;
    private int id;
    private String username;
    private String password;
    private String nombre;
    private List<Resultado> historial; 

    public Usuario(String username, String password, String nombre) {
        this.id = ++contador;
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.historial = new ArrayList<>();
    }

    public Usuario() {
        this("invitado", "", "Invitado");
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getNombre() { return nombre; }
    public List<Resultado> getHistorial() {
        return Collections.unmodifiableList(historial);
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre.trim();
    }

    public boolean validarCredenciales(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void agregarResultado(Resultado resultado) {
        if (resultado != null) {
            historial.add(resultado);
        }
    }

    public int getTotalJugadas() {
        return historial.size();
    }

    public int getVictorias() {
        return (int) historial.stream()
                .filter(Resultado::isGanador)
                .count();
    }

    public double getPorcentajeVictorias() {
        if (historial.isEmpty()) return 0;
        return (getVictorias() * 100.0) / getTotalJugadas();
    }
}