package controlador;

import modelo.Usuario;
import modelo.BaseDatos;

public class SessionController {
    private Usuario usuarioActual;
    private BaseDatos baseDatos;

    public SessionController() {
        this.baseDatos = new BaseDatos();
    }

    public void registrarUsuario(String username, String password, String nombre) {
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Todos los datos son requeridos");
        }

        if (baseDatos.buscarUsuario(username) != null) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        Usuario nuevoUsuario = new Usuario(username, password, nombre);
        baseDatos.agregarUsuario(nuevoUsuario);
        this.usuarioActual = nuevoUsuario;
    }

    public boolean iniciarSesion(String username, String password) {
        Usuario usuario = baseDatos.buscarUsuario(username);
        if (usuario != null && usuario.validarCredenciales(username, password)) {
            this.usuarioActual = usuario;
            return true;
        }
        return false;
    }

    public boolean hayUsuario() {
        return usuarioActual != null;
    }

    public String getNombreUsuario() {
        return hayUsuario() ? usuarioActual.getNombre() : "";
    }

    public String getUsername() {
        return hayUsuario() ? usuarioActual.getUsername() : "";
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void actualizarNombreUsuario(String nuevoNombre) {
        if (hayUsuario()) {
            usuarioActual.setNombre(nuevoNombre);
        }
    }

    public void cerrarSesion() {
        usuarioActual = null;
    }
}