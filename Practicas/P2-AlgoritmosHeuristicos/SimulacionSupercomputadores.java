// Algoritmo 1 - Heurístico 1 (H1):
// Este algoritmo examinará los valores de as y bs de forma lineal y tomará decisiones basadas en si es mejor cambiar de máquina en cada minuto o no. 
//Comprobamos si es mejor cambiar a B en el próximo minuto

public class SimulacionSupercomputadores {
    
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

    public static void main(String[] args) {
        int[] as = {10, 2, 6, 9};
        int[] bs = {5, 4, 12, 15};

        int resultadoH1 = heuristico1(as, bs);
        int resultadoH2 = heuristico2(as, bs);

        System.out.println("Resultado del algoritmo heurístico 1: " + resultadoH1);
        System.out.println("Resultado del algoritmo heurístico 2: " + resultadoH2);
    }
}
