public class VueltaAtrasConPoda {
    public static int ramificacionYPoda(int[] as, int[] bs) {
        int n = as.length;
        int[] asignacionActual = new int[n];
        int mejorValor = 0;
        int[] mejorPlan = new int[n];
        int cotaInicial = calcularCotaInicial(as, bs);
    
        return ramificacionYPodaRecursivo(0, as, bs, asignacionActual, mejorValor, mejorPlan, cotaInicial);
    }
    
    
    public static int ramificacionYPodaRecursivo(int minuto, int[] as, int[] bs, int[] asignacionActual, int mejorValor, int[] mejorPlan, int cota) {
        if (minuto >= as.length) {
            int valorActual = calcularValor(asignacionActual, as, bs);
            if (valorActual > mejorValor) {
                mejorValor = valorActual;
                System.arraycopy(asignacionActual, 0, mejorPlan, 0, asignacionActual.length);
            }
            return mejorValor;
        }
    
        if (cota > mejorValor) { // Comprueba la cota antes de continuar
            for (int supercomputador = 0; supercomputador < 2; supercomputador++) {
                asignacionActual[minuto] = supercomputador;
                int nuevaCota = calcularNuevaCota(cota, minuto, supercomputador, as, bs);
                mejorValor = ramificacionYPodaRecursivo(minuto +1 , as, bs, asignacionActual, mejorValor, mejorPlan, nuevaCota);
                asignacionActual[minuto] = -1;
            }
        }
    
        return mejorValor;
    }
    
    private static int calcularCotaInicial(int[] as, int[] bs) {
        int cota = 0;
        for (int i = 0; i < as.length; i++) {
            cota += as[i] + bs[i];
        }
        return cota;
    }
    
    private static int calcularNuevaCota(int cotaAnterior, int minuto, int supercomputador, int[] as, int[] bs) {
        int cota = cotaAnterior;
        if (supercomputador == 0) {
            cota -= as[minuto];
        } else {
            cota -= bs[minuto];
        }
        return cota;
    }
    private static int calcularValor(int[] asignacion, int[] as, int[] bs) {
        int valor = 0;
        int maquinaActual = 0; // 0 para A, 1 para B
        int retraso = 0; // Inicialmente no hay retraso
    
        for (int i = 0; i < as.length; i++) {
            int pasosEnA = as[i];
            int pasosEnB = bs[i];
    
            if (maquinaActual != asignacion[i]) {
                // Cambio de máquina, agregamos un retraso de 1 minuto
                valor += retraso;
                retraso = 1; // Se reinicia el retraso
                maquinaActual = asignacion[i];
            } else {
                retraso = 0; // No hay cambio de máquina, no hay retraso
            }
    
            valor += (maquinaActual == 0) ? pasosEnA : pasosEnB;
        }
    
        return valor;
    }
    /* 
    public int resolver() {
        int[] asignacionActual = new int[nTareas];
        int mejorValor = 0;
        int[] mejorPlan = new int[nTareas];

        return vueltaAtrasRecursivoPoda(0, asignacionActual, mejorValor, mejorPlan);
    }

    private int vueltaAtrasRecursivoPoda(int minuto, int[] as, int[] bs, int[] asignacionActual, int mejorValor, int[] mejorPlan) {
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
            mejorValor = vueltaAtrasRecursivoPoda(minuto + 1, asignacionActual, mejorValor, mejorPlan);
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
    */
    
    public static void main(String[] args) {
        int[] as = {10, 2, 6, 9};
        int[] bs = {5, 4, 12, 15};

        int mejorValor = ramificacionYPoda(as, bs);

        System.out.println("Mejor valor encontrado: " + mejorValor);

    }
}