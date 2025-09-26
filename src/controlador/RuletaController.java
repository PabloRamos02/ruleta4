package controlador;

import modelo.Ruleta;
import modelo.TipoApuesta;

public class RuletaController {
    private Ruleta ruleta;

    public RuletaController(int saldoInicial) {
        this.ruleta = new Ruleta(saldoInicial);
    }

    public RuletaController() {
        this.ruleta = new Ruleta();
    }

    public int getSaldo() {
        return ruleta.getSaldo();
    }

    public void depositar(int monto) {
        ruleta.depositar(monto);
    }

    public boolean apostar(int monto, TipoApuesta tipo) {
        return ruleta.apostar(monto, tipo);
    }
}