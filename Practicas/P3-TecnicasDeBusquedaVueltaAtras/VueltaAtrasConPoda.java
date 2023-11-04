public class VueltaAtrasConPoda {

    private int nTareas;
    private int tiempoDisponible;
    private int[] as;
    private int[] bs;

    public VueltaAtrasConPoda(int nTareas, int tiempoDisponible, int[] as, int[] bs) {
        this.nTareas = nTareas;
        this.tiempoDisponible = tiempoDisponible;
        this.as = as;
        this.bs = bs;
    }

    public int resolver() {
        int[] asignacionActual = new int[nTareas];
        int mejorValor = 0;
        int[] mejorPlan = new int[nTareas];

        return vueltaAtrasRecursivo(0, asignacionActual, mejorValor, mejorPlan);
    }

    private int vueltaAtrasRecursivo(int minuto, int[] as, int[] bs, int[] asignacionActual, int mejorValor, int[] mejorPlan) {
        if (minuto >= nTareas) {
            int valorActual = calcularValor(asignacionActual, as, bs);
            if (valorActual > mejorValor) {
                mejorValor = valorActual;
                System.arraycopy(asignacionActual, 0, mejorPlan, 0, asignacionActual.length);
            }
            return mejorValor;
        }

        if (!podar(asignacionActual)) {
            return mejorValor;
        }

        for (int supercomputador = 0; supercomputador < 2; supercomputador++) {
            asignacionActual[minuto] = supercomputador;
            mejorValor = vueltaAtrasRecursivo(minuto + 1, asignacionActual, mejorValor, mejorPlan);
            asignacionActual[minuto] = -1;
        }
        return mejorValor;
    }

    private boolean podar(int[] asignacionActual) {
        // Comprobar que la duración de la asignación parcial no supera el tiempo disponible.

        int duracionTotal = 0;
        for (int tarea = 0; tarea < nTareas; tarea++) {
            int supercomputador = asignacionActual[tarea];
            if (supercomputador == 0) {
                duracionTotal += as[tarea];
            } else {
                duracionTotal += bs[tarea];
            }
        }

        if (duracionTotal > tiempoDisponible) {
            return false;
        }

        // Comprobar que cada tarea se asigna a un supercomputador.

        for (int tarea = 0; tarea < nTareas; tarea++) {
            if (asignacionActual[tarea] == -1) {
                return false;
            }
        }

        return true;
    }

    private int calcularValor(int[] asignacion, int[] as, int[] bs) {
        int valor = 0;
        int maquinaActual = 0; // 0 para A, 1 para B

        for (int i = 0; i < as.length; i++) {
            int pasosEnA = as[i];
            int pasosEnB = bs[i];

            if (maquinaActual != asignacion[i]) {
                valor++; // Agregamos el retraso de 1 minuto por cambio de máquina
                maquinaActual = asignacion[i];
            }

            valor += (maquinaActual == 0) ? pasosEnA : pasosEnB;
        }

        return valor;
    }
    public static void main(String[] args) {
        int nTareas = 10;
        int tiempoDisponible = 100;
        int[] as = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] bs = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        VueltaAtrasConPoda problema = new VueltaAtrasConPoda(nTareas, tiempoDisponible, as, bs);
        int valorOptimo = problema.resolver();

        System.out.println("Valor óptimo: " + valorOptimo);
    }
}