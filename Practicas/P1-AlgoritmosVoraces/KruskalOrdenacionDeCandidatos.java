
public class KruskalOrdenacionDeCandidatos {

    public static int Kruskal1(int[] us, int[] vs, int[] costes, int n) {
        // calcula el coste m�nimo y los arcos correspondientes

        int[] costAux = ordenarIndices(costes); //Contiene los indices de costes Ordenados
        
        // vectores que representan los arcos del MST (or�genes y destinos)
        int[] orig = new int[n - 1];
        int[] dest = new int[n - 1];
        int a = 0;
        int coste = 0;

        // representaci�n impl�cita del bosque de nodos que forman el MST
        int[] conjs = new int[n];
        for (int i = 0; i < n; i++)
            conjs[i] = i;

        // bucle voraz
        for (int i = 0; i < us.length && a < n -1; i++) {
            //Se seleccion el arco del primer indice de menor coste
            int u = us[costAux[i]];
            int v = vs[costAux[i]];
            // se halla el conjunto disjunto de sus nodos
            int conju = conjs[u];
            int conjv = conjs[v];
            // se comprueba si pertenecen a conjuntos disjuntos
            if (conju != conjv) {
                orig[a] = u;
                dest[a] = v;
                a++;
                coste += costes[costAux[i]];
                
                // se fusionan
                int min = Math.min(conju, conjv);
                int max = Math.max(conju, conjv);
                for (int k = 0; k < n; k++)
                    if (conjs[k] == max)
                        conjs[k] = min;
            }
        }
        imprimirArcos(orig, dest);
        return coste;
    }

    private static int[] ordenarIndices(int[] v1) {
        int[] v2 = new int[v1.length];
        for (int i = 0; i < v1.length; i++) {
            v2[i] = i;
        }
        for (int i = 1; i < v1.length; i++) {
            int aux = v1[i];
            int auxIndex = v2[i];
            int j = i - 1;
            while (j >= 0 && v1[j] > aux) {
                v1[j + 1] = v1[j];
                v2[j + 1] = v2[j];
                j--;
            }
            v1[j + 1] = aux;
            v2[j + 1] = auxIndex;
        }
        return v2;
    }

    private static void imprimirArcos(int[] orig, int[] dest) {
        for (int i = 0; i < orig.length; i++)
            System.out.println("Arco " + i + ": (" + orig[i] + "," + dest[i] + ")");
        System.out.println();
    }

    public static void main(String[] args) {

    int[] us = {0, 0, 0, 1, 1, 2, 2, 3};
    int[] vs = { 1, 2, 4, 3, 4, 3, 4, 4};
    int[] cs = {16,12,21, 6,11,18,33,14};
    int n = 5;

    int coste = Kruskal1(us, vs, cs, n);
    System.out.println("Longitud del árbol recubridor de coste mínimo: " + coste);

    }

}

