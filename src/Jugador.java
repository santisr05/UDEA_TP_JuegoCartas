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

        int posicion = MARGEN + (TOTAL_CARTAS - 1) * DISTANCIA;
        for (Carta carta : cartas) {
            carta.mostrar(pnl, posicion, MARGEN);
            posicion -= DISTANCIA;
        }

        pnl.repaint();
    }

    public String getGrupos() {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append(verificarGrupos());
        mensaje.append("\n\n");
        mensaje.append(verificarEscaleras());
        mensaje.append("\n\n");
        mensaje.append(sumarCartasSinGrupo());
        return mensaje.toString();
    }

    private String verificarGrupos() {
        int[] contadores = new int[NombreCarta.values().length];

        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }

        StringBuilder grupos = new StringBuilder();
        boolean hayGrupo = false;

        for (int i = 0; i < contadores.length; i++) {
            int cantidad = contadores[i];
            if (cantidad >= 2) {
                hayGrupo = true;
                String tipo = switch (cantidad) {
                    case 2 -> "Par";
                    case 3 -> "Terna";
                    case 4 -> "Cuarta";
                    default -> cantidad + " iguales";
                };
                grupos.append(tipo).append(" de ").append(NombreCarta.values()[i]).append("\n");
            }
        }

        if (!hayGrupo) return "No se encontraron pares, ternas ni cuartas.";
        return "Se encontraron los siguientes grupos:\n" + grupos;
    }

    private String verificarEscaleras() {
        StringBuilder mensaje = new StringBuilder();
        boolean hayEscalera = false;
        Pinta[] pintas = Pinta.values();

        for (Pinta pinta : pintas) {
            int[] valores = new int[15];

            for (Carta carta : cartas) {
                if (carta.getPinta() == pinta) {
                    int valor = carta.getNombre().ordinal() + 1;
                    valores[valor] = 1;
                    if (valor == 1) valores[14] = 1;
                }
            }

            int inicio = -1;
            for (int i = 1; i <= 14; i++) {
                if (valores[i] == 1) {
                    if (inicio == -1) inicio = i;
                } else {
                    if (inicio != -1 && i - inicio >= 2) {
                        hayEscalera = true;
                        mensaje.append("Escalera en ").append(pinta).append(": ");
                        for (int j = inicio; j < i; j++) {
                            mensaje.append(convertirNombre(j)).append(", ");
                        }
                        mensaje.setLength(mensaje.length() - 2);
                        mensaje.append("\n");
                    }
                    inicio = -1;
                }
            }

            if (inicio != -1 && 15 - inicio >= 2) {
                hayEscalera = true;
                mensaje.append("Escalera en ").append(pinta).append(": ");
                for (int j = inicio; j <= 14; j++) {
                    mensaje.append(convertirNombre(j)).append(", ");
                }
                mensaje.setLength(mensaje.length() - 2);
                mensaje.append("\n");
            }
        }

        if (!hayEscalera) return "No se encontraron escaleras.";
        return "Se encontraron las siguientes escaleras:\n" + mensaje;
    }

    private String convertirNombre(int valor) {
        return switch (valor) {
            case 1 -> "A";
            case 11 -> "J";
            case 12 -> "Q";
            case 13 -> "K";
            case 14 -> "A";
            default -> String.valueOf(valor);
        };
    }
    private String sumarCartasSinGrupo() {
        int[] contadores = new int[NombreCarta.values().length];
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }

        boolean[] usada = new boolean[cartas.length];
        for (int i = 0; i < cartas.length; i++) {
            int valor = cartas[i].getNombre().ordinal();
            if (contadores[valor] >= 2) {
                usada[i] = true;
            }
        }

        int puntos = 0;
        StringBuilder detalle = new StringBuilder();
        detalle.append("Cartas sin Grupo:\n");

        for (int i = 0; i < cartas.length; i++) {
            if (!usada[i]) {
                int valor = cartas[i].getNombre().ordinal() + 1;
                if (valor == 1) valor = 1;
                else if (valor == 11) valor = 11;
                else if (valor == 12) valor = 12;
                else if (valor == 13) valor = 13;
                puntos += valor;
                detalle.append(convertirNombre(valor)).append(" ").append(cartas[i].getPinta()).append("\n");
            }
        }

        detalle.append("-> El jugador tiene ").append(puntos).append(" puntos");
        return detalle.toString();
    }
}

