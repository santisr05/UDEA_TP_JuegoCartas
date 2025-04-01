import java.util.Random;

import javax.swing.JPanel;

public class Jugador {

    private int TOTAL_CARTAS = 10;
    private int MARGEN = 10;
    private int DISTANCIA = 40;

    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Random r = new Random(); // Para generar números aleatorios,la suerte del jugador

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        
        int posicion =  MARGEN + (TOTAL_CARTAS - 1) * DISTANCIA;
        for (Carta carta : cartas) {
            carta.mostrar(pnl, posicion, MARGEN);
            posicion -= DISTANCIA;
        }

        pnl.repaint();
    }

    public String getGrupos() {
        String mensaje = "No se encontraron grupos";

        int[] contadores = new int[NombreCarta.values().length];
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }

        
        boolean hayGrupo = false;
        for (int contador : contadores) {
            if (contador >= 2) {
                hayGrupo = true;
                break;
            }
        }

        if (hayGrupo) {
            mensaje = "Se encontraron los siguientes grupos: \n";
            int fila=0;
            for (int contador : contadores) {
                if (contador >= 2) {
                mensaje += Grupo.values()[contador]+ " de " + NombreCarta.values()[fila] + "\n";
                }
                fila ++;
            }
        } return mensaje;
    }
}
