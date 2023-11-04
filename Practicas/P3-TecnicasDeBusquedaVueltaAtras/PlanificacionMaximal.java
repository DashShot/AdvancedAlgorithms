import java.util.Arrays;

public class PlanificacionMaximal{
    public static int vueltaAtras(int[] as, int[] bs) {
        int n = as.length;
        int [] asignacionActual = new int[n];
        int mejorValor = 0;
        int [] mejorPlan = new int[n];

        return vueltaAtrasRecursivo(0, as, bs, asignacionActual, mejorValor, mejorPlan);
    }
    
    public static int vueltaAtrasRecursivo(int minuto, int[] as, int[] bs, int[] asignacionActual, int mejorValor, int[] mejorPlan) {
        if (minuto >= as.length) {
            int valorActual = calcularValor(asignacionActual, as, bs);
            if (valorActual > mejorValor) {
                mejorValor = valorActual;
                System.arraycopy(asignacionActual, 0, mejorPlan, 0, asignacionActual.length);
            }
            return mejorValor;
        }

        for (int supercomputador = 0; supercomputador < 2; supercomputador++) {
            asignacionActual[minuto] = supercomputador;
            mejorValor = vueltaAtrasRecursivo(minuto + 1, as, bs, asignacionActual, mejorValor, mejorPlan);
            asignacionActual[minuto] = -1;
        }
        return mejorValor;
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
    
    public static int heuristico1(int[] as, int[] bs) {
        int comp = 0;

        int[][] sc = { as, bs };
        int out = 0;
        if (sc[comp][0] < sc[((comp + 1) % 2)][0])
            comp = ((comp++) % 2);

        for (int i = 0; i < as.length; i++) {
            if (i == as.length - 1) {
                out += sc[comp][i];
            } else {

                if (sc[comp][i] + sc[comp][i + 1] < sc[((comp + 1) % 2)][i + 1]) {
                    comp = ((comp++) % 2);
                } else {
                    out += sc[comp][i];
                }
            }
        }

        System.out.println("Simular heuristico termina");
        return out;
    }

    // Algoritmo 2 - Heurístico 2 (H2):
    // Este algoritmo podría utilizar una estrategia diferente para tomar decisiones sobre en qué máquina asignar el trabajo en cada minuto
    // La estrategia se basa en comparar los pasos disponibles en las máquinas A y B en cada minuto y asignar el trabajo a la máquina que tiene más pasos disponibles en ese minuto.

    public static int heuristico2(int[] as, int[] bs) {
        int[][] sc = { as, bs };
        int comp = 0;
        int out = 0;

        for (int i = 0; i < as.length; i++) {
            out += sc[comp][i];

            if (i < as.length - 2 && (sc[((comp + 1) % 2)][i + 2] > sc[comp][i + 1] + sc[comp][i + 2])) {
                comp = ((comp + 1) % 2);
                i++;
            }
        }

        System.out.println("Simular voraz termina");

        return out;
    }

    public static void main(String [] args){

        
        int[] as = {10, 2, 6, 9};
        int[] bs = {5, 4, 12, 15};

        int resultadoH1 = heuristico1(as, bs);
        int resultadoH2 = heuristico2(as, bs);
        int resultadoVA = vueltaAtras(as, bs);
        int resultadoPRVA = ramificacionYPoda(as, bs);

        System.out.println("Resultado del algoritmo heurístico 1: " + resultadoH1);
        System.out.println("Resultado del algoritmo heurístico 2: " + resultadoH2);
        System.out.println("Resultado del algoritmo vuelta atras : " + resultadoVA);
        System.out.println("Resultado del algoritmo poda y ramificacion: " + resultadoPRVA);


    }
}