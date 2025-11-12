package modelo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Estadisticas {
    private List<Resultado> historial;

    public Estadisticas(List<Resultado> historial) {
        this.historial = historial;
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

    public int getRachaMaxima() {
        int rachaMaxima = 0;
        int rachaActual = 0;

        for (Resultado resultado : historial) {
            if (resultado.isGanador()) {
                rachaActual++;
                rachaMaxima = Math.max(rachaMaxima, rachaActual);
            } else {
                rachaActual = 0;
            }
        }
        return rachaMaxima;
    }

    public String getTipoMasJugado() {
        if (historial.isEmpty()) return "vacio";

        Map<String, Long> conteo = historial.stream()
                .collect(Collectors.groupingBy(
                    Resultado::getTipoApuesta,
                    Collectors.counting()
                ));

        return conteo.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }

    public int getMontoTotalApostado() {
        return historial.stream()
                .mapToInt(Resultado::getMonto)
                .sum();
    }

    public int getMontoTotalGanado() {
        return historial.stream()
                .filter(Resultado::isGanador)
                .mapToInt(r -> r.getMonto() * 2) 
                .sum();
    }

    public int getBalance() {
        return getMontoTotalGanado() - getMontoTotalApostado();
    }
}