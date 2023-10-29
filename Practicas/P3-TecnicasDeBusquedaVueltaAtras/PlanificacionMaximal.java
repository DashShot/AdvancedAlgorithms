import java.util.Arrays;

public class PlanificacionMaximal{
    //Declaración de variables
    private int[] minutosA;
    private int[] minutosB;
    private int n;
    private int [] asignacionActual;
    private int mejorValor;
    private int [] mejorPlan;
    
    public PlanificacionMaximal(int[] minutosA,int[] minutosB){
        this.minutosA = minutosA;
        this.minutosB = minutosB;
        this.n = minutosA.length;
        this.asignacionActual = new int[n];
        this.mejorValor = 0;
        this.mejorPlan = new int[n];
    }
    public void vueltaAtras(int minuto) {
        if (minuto >= n) {
            int valorActual = calcularValor(asignacionActual);
            if (valorActual > mejorValor) {
                mejorValor = valorActual;
                System.arraycopy(asignacionActual, 0, mejorPlan, 0, n);
            }
            return;
        }

        for (int supercomputador = 0; supercomputador < 2; supercomputador++) {
            asignacionActual[minuto] = supercomputador;
            vueltaAtras(minuto + 1);
            asignacionActual[minuto] = -1;
        }
    }

    private int calcularValor(int[] asignacion) {
        int valor = 0;
        for(int i = 0;i<n;i++){
            if(asignacion[i] == 0){
                continue;
            }else{
                valor += (asignacion[i] == 0 ? minutosA[i]:minutosB[i]);
            }
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

    public static void main(String [] args){
        //Declaramos las variables
        int[] minutosA = {10, 2, 6, 9};
        int[] minutosB = {5, 4, 12, 15};
        
        //Implementamos la función de vuelta atras
        PlanificacionMaximal planificador = new PlanificacionMaximal(minutosA, minutosB);
        planificador.vueltaAtras(0);
        
        //Imprimimos los resultados
        System.out.println("Mejor plan encontrado: " + Arrays.toString(planificador.mejorPlan));
        System.out.println("Valor del mejor plan: " + planificador.mejorValor);
    }
}