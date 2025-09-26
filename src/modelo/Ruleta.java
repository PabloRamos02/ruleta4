package modelo;

public class Ruleta {
    private int saldo;

    public Ruleta(int saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
        this.saldo = saldoInicial;
    }

    public Ruleta() {
        this(0);
    }

    public int getSaldo() { return saldo; }

    public void depositar(int monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }
        this.saldo += monto;
    }

    public boolean apostar(int monto, TipoApuesta tipo) {
        if (monto <= 0 || monto > saldo) {
            return false;
        }

        int numero = generarNumero();
        boolean gana = evaluarResultado(numero, tipo);

        if (gana) {
            saldo += monto;
        } else {
            saldo -= monto;
        }

        return gana;
    }

    private int generarNumero() {
        return (int) (Math.random() * 37);
    }

    private boolean evaluarResultado(int numero, TipoApuesta tipo) {
        if (numero == 0) return false;

        return switch (tipo) {
            case ROJO -> esRojo(numero);
            case NEGRO -> !esRojo(numero);
            case PAR -> numero % 2 == 0;
            case IMPAR -> numero % 2 != 0;
        };
    }

    private boolean esRojo(int numero) {
        int[] rojos = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
        for (int rojo : rojos) {
            if (rojo == numero) return true;
        }
        return false;
    }
}