package modelo;

public class ApuestaNegro extends ApuestaBase {
    
    public ApuestaNegro(int monto) {
        super(monto, "NEGRO");
    }

    @Override
    public boolean acierta(int numero, String color) {
        if (numero == 0) return false;
        
        ApuestaRojo apuestaRojo = new ApuestaRojo(0);
        return !apuestaRojo.acierta(numero, color);
    }
}