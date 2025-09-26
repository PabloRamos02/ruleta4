package modelo;

public class Usuario {
    private static int contador = 0;
    private int id;
    private String username;
    private String password;
    private String nombre;

    // Constructor con parámetros
    public Usuario(String username, String password, String nombre) {
        this.id = ++contador;
        this.username = username;
        this.password = password;
        this.nombre = nombre;
    }

    public Usuario() {
        this("invitado", "", "Invitado");
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getNombre() { return nombre; }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre.trim();
    }

    public boolean validarCredenciales(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}