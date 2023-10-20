// Algoritmo 1 - Heurístico 1 (H1):
// Este algoritmo examinará los valores de as y bs de forma lineal y tomará decisiones basadas en si es mejor cambiar de máquina en cada minuto o no. 
//Comprobamos si es mejor cambiar a B en el próximo minuto

public class SimulacionSupercomputadores {
    
    public static int heuristico1(int[] as, int[] bs) {
        int n = as.length;
        int totalPasos = 0;
        int maquinaActual = 0; // 0 para A, 1 para B

        for (int i = 0; i < n; i++) {
            int pasosEnA = as[i];
            int pasosEnB = bs[i];

            if (maquinaActual == 0) {
                // Si estamos en A, comprobamos si es mejor cambiar a B en el próximo minuto
                if (i < n - 1 && pasosEnB > pasosEnA + bs[i + 1]) {
                    maquinaActual = 1; // Cambiamos a B
                }
            } else {
                // Si estamos en B, comprobamos si es mejor cambiar a A en el próximo minuto
                if (i < n - 1 && pasosEnA > pasosEnB + as[i + 1]) {
                    maquinaActual = 0; // Cambiamos a A
                }
            }

            totalPasos += (maquinaActual == 0) ? pasosEnA : pasosEnB;
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
                maquinaActual = (pasosEnB > pasosEnA) ? 1 : 0; //operación ternaria
            } else {
                maquinaActual = (pasosEnA > pasosEnB) ? 0 : 1;
            }
            totalPasos += (maquinaActual == 0) ? pasosEnA : pasosEnB;
        }

        return totalPasos;
    }

    public static void main(String[] args) {
        int[] as = {10, 2, 6, 9};
        int[] bs = {5, 4, 12, 15};

        int resultadoH1 = heuristico1(as, bs);
        int resultadoH2 = heuristico2(as, bs);

        System.out.println("Resultado del algoritmo heurístico 1: " + resultadoH1);
        System.out.println("Resultado del algoritmo heurístico 2: " + resultadoH2);
    }
}
