package modelo;

import java.util.ArrayList;
import java.util.List;

public class BaseDatos {
    private List<Usuario> usuarios;

    public BaseDatos() {
        this.usuarios = new ArrayList<>();
        usuarios.add(new Usuario("admin", "123", "Administrador"));
    }

    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public Usuario buscarUsuario(String username) {
        return usuarios.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}