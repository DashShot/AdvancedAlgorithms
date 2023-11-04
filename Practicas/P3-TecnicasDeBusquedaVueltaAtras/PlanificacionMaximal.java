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
    public static int heuristico1(int[] as, int[] bs) {
        int n = as.length;
        int totalPasos = 0;
        int maquinaActual = 0; // 0 para A, 1 para B

        for (int i = 0; i < n; i++) {
            int pasosEnA = as[i];
            int pasosEnB = bs[i];
    
            if (maquinaActual == 0) {
                if (i < n - 1 && pasosEnB + 1 < pasosEnA + bs[i + 1]) {
                    maquinaActual = 1;
                    totalPasos++;
                }
            } else {
                if (i < n - 1 && pasosEnA + 1 < pasosEnB + as[i + 1]) {
                    maquinaActual = 0;
                    totalPasos++;
                }
            }
    
            if (i < n - 1) {
                totalPasos += (maquinaActual == 0) ? pasosEnA : pasosEnB;
            } else {
                // Último minuto: sin retraso
                totalPasos += (maquinaActual == 0) ? pasosEnA : pasosEnB;
            }
        }

        return totalPasos;
    }

    // Algoritmo 2 - Heurístico 2 (H2):
    // Este algoritmo podría utilizar una estrategia diferente para tomar decisiones sobre en qué máquina asignar el trabajo en cada minuto
    // La estrategia se basa en comparar los pasos disponibles en las máquinas A y B en cada minuto y asignar el trabajo a la máquina que tiene más pasos disponibles en ese minuto.

    public static int heuristico2(int[] as, int[] bs) {
        int n = as.length;
        int totalPasos = 0;
        int maquinaActual = 0; // 0 para A, 1 para B
    
        for (int i = 0; i < n; i++) {
            int pasosEnA = as[i];
            int pasosEnB = bs[i];
    
            if (maquinaActual == 0) {
                if (i < n - 1 && pasosEnB + 1 < pasosEnA + bs[i + 1]) {
                    maquinaActual = 1;
                    totalPasos++;
                }
            } else {
                if (i < n - 1 && pasosEnA + 1 < pasosEnB + as[i + 1]) {
                    maquinaActual = 0;
                    totalPasos++;
                }
            }
    
            if (i < n - 1) {
                totalPasos += (maquinaActual == 0) ? pasosEnA : pasosEnB;
            } else {
                // Último minuto: sin retraso
                totalPasos += (maquinaActual == 0) ? pasosEnA : pasosEnB;
            }
        }
    
        return totalPasos;
    }



    //////ALGORITMO VueltaAtrásCon Poda//////////////////
    

    public static void main(String [] args){

        
        int[] as = {10, 2, 6, 9};
        int[] bs = {5, 4, 12, 15};

        int resultadoH1 = heuristico1(as, bs);
        int resultadoH2 = heuristico2(as, bs);
        int resultadoVA = vueltaAtras(as, bs);

        System.out.println("Resultado del algoritmo heurístico 1: " + resultadoH1);
        System.out.println("Resultado del algoritmo heurístico 2: " + resultadoH2);
        System.out.println("Resultado del algoritmo vuelta atras 2: " + resultadoVA);

        //Imprimimos los resultados
        //System.out.println("Mejor plan encontrado: " + Arrays.toString(planificador.mejorPlan));
        //System.out.println("Valor del mejor plan: " + planificador.mejorValor);
    }
}