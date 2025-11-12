package modelo;

public class ApuestaRojo extends ApuestaBase {
    
    public ApuestaRojo(int monto) {
        super(monto, "ROJO");
    }

    @Override
    public boolean acierta(int numero, String color) {
        if (numero == 0) return false; 
        
        int[] numerosRojos = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
        
        for (int numRojo : numerosRojos) {
            if (numRojo == numero) {
                return true;
            }
        }
        return false;
    }
}