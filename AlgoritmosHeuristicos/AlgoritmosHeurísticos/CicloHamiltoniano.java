
/**
 * Write a description of class CicloHamiltoniano here.
 * 
 * @author (Ángel Velázquez Iturbide, Departamento de Informática y Estadística, Universidad Rey Juan Carlos)
 * Asignatura: Algoritmos Avanzados, Grados en Ingeniería Informática e Ingeniería de Computadores
 * @version (Curso 2023-24)
 */
public class CicloHamiltoniano
{
    public static int cicloHamiltonianoHeur1 (int[][]grafo) {
    // heurística del vecino más cercano
       int longitud = 0;
       int[] ciclo = new int[grafo.length+1];
       ciclo[0] = 0;
       boolean[] visitado = new boolean[grafo.length];
       visitado[0] = true;
       for (int i=1; i<grafo.length; i++)
          visitado[i] = false;
       int ultimo = 0; 
       for (int i=1; i<grafo.length; i++) {
          int menor = Integer.MAX_VALUE;
          int sig = 0;
          for (int j=1; j<grafo.length; j++)
             if (!visitado[j] && (grafo[ultimo][j]<menor)) {
                menor = grafo[ultimo][j];
                sig = j;
             }
          longitud += menor;
          ciclo[i] = sig;
          visitado[sig] = true;
          ultimo = sig;
       }
       longitud += grafo[ultimo][0];
       ciclo[grafo.length] = 0;
       imprimirCiclo (grafo, ciclo); 
       return longitud;
    }

    private static void imprimirCiclo (int[][] grafo, int[] ciclo) {
       System.out.print ("Ciclo hamiltoniano, con nodos (distancia): " + ciclo[0]);
       for (int i=1; i<ciclo.length-1; i++) {
          System.out.print (" (" + grafo[ciclo[i-1]][ciclo[i]] + ") " + ciclo[i]);
       }
       System.out.println (" (" + grafo[ciclo[ciclo.length-2]][ciclo[ciclo.length-1]] + ") " + ciclo[ciclo.length-1]);
    }

    public static int cicloHamiltonianoHeur2 (int n, int[] us, int[] vs, int[] costes) {
    // heurística del arco más corto
       int[] orig = new int[n];
       int[] dest = new int[n];
       int a = 0;
       int longitud = 0;
       // representación implícita de los subgrafos que formarán el ciclo hamiltoniano
       int[] conjs = new int[n];
       for (int i=0; i<n; i++) 
          conjs[i] = i;
       //contadores de arcos elegidos para cada nodo
       int[] contador = new int[n];
       // se ordenan los arcos en orden creciente de coste
       int[] is = new int[costes.length];
       is = ordenarIndices (costes);
       // bucle voraz
       for (int i=0; a<n-1; i++) {
          // se selecciona el arco más corto
          int u = us[is[i]];
          int v = vs[is[i]];
          // se halla el conjunto disjunto de sus nodos
          int conju = conjs[u];
          int conjv = conjs[v];
          // se comprueba si pertenecen a conjuntos disjuntos 
          if ((conju != conjv) && (contador[u]<2) && (contador[v]<2)){
             orig[a] = u;
             dest[a] = v;
             a++;
             longitud += costes[is[i]];
             contador[u]++;
             contador[v]++;
             // se fusionan
             int min = Math.min (conju, conjv);
             int max = Math.max (conju, conjv);
             for (int k=0; k<n; k++)
                if (conjs[k]==max)
                   conjs[k] = min;
          }
       }
       //se busca el arco que cierra el ciclo
       boolean primero = true;
       for (int i=0; i<n; i++)
          if ((contador[i]<2) && primero) {
             orig[n-1] = i;
             primero = false;
          } else if (contador[i]<2)
             dest[n-1] = i;
       //se busca la longitud de este arco
       boolean encontrado = false;
       for (int i=0; (i<n) && !encontrado; i++)
          if ((us[i]==orig[n-1]) && (vs[i]==dest[n-1]))
             longitud += costes[i];
       imprimirArcos (orig, dest);
       return longitud;
    }
    
   private static int[] ordenarIndices (int[] v1) {
   // se ordena por inserción directa en orden creciente
      int[] v2 = new int[v1.length];
      v2[0] = 0;
      for (int i=1; i<v1.length; i++) {
         float aux = v1[i];
         int j;
         for (j=i-1; j>=0 && v1[v2[j]]>aux; j--)
            v2[j+1] = v2[j];
         v2[j+1] = i;
      }
      return v2;
   }
   
   private static void imprimirArcos (int[] orig, int[] dest) {
      for (int i=0; i<orig.length; i++)
         System.out.println ("Arco "+i+": ("+orig[i]+","+dest[i]+")");
   }
      
   public static int cicloHamiltonianoLocal (int[][]grafo) {
   // el ciclo inicial se basa en la suposición de que el grafo es completo
   // en general, puede crearse con un algoritmo de búsqueda en profundidad
   //    o con algunos de los algoritmos heurísticos
       int[] ciclo    = crearCicloHamiltonianoSimple (grafo);
       int[] posicion = calcularPosiciones (ciclo, grafo);
       int longitud   = calcularRecorrido (ciclo, grafo);
       imprimirCiclo (grafo, ciclo); 
       System.out.println (longitud);
       boolean mejora;
       do {
          mejora = false;
          for (int i=0; i<grafo.length && !mejora; i++) {
             // se toma un arco del ciclo actual
             int origen  = ciclo[i];
             int destino = ciclo[i+1];
             for (int j=1; j<grafo.length && !mejora; j++) {
                //se toma otro arco del ciclo
                if (j!=origen && j!=destino) {
                   int destino2 = ciclo[posicion[j]+1];
                   // se comprueba si se reduce longitud intercambiando los dos arcos por otros dos
                   if ((grafo[origen][j]+grafo[destino][destino2]<grafo[origen][destino]+grafo[j][destino2])) {
                      ciclo[i+1] = j;
                      ciclo[posicion[j]] = destino;
                      posicion[destino] = posicion[j];
                      posicion[j] = i+1;
                      longitud = longitud 
                                - (grafo[origen][destino]+grafo[j][destino2])
                                + (grafo[origen][j]+grafo[destino][destino2]);
                      mejora = true;
                      imprimirCiclo (grafo, ciclo); 
                      System.out.println (longitud);
                   }
                }
             }
          }
       } while (mejora);
       return longitud;
   }
   
   private static int[] crearCicloHamiltonianoSimple (int[][] grafo) {
      int[] ciclo = new int[grafo.length+1];
      ciclo[0] = 0;
      for (int i=1; i<grafo.length; i++)
         ciclo[i] = i;
      ciclo[grafo.length] = 0;
      return ciclo;
   }
   
   private static int[] calcularPosiciones (int[] ciclo, int[][] grafo) {
      int[] posiciones = new int[grafo.length];
      for (int i=0; i<grafo.length; i++) {
         boolean encontrado = false;
         for (int j=0; j<grafo.length && !encontrado; j++)
            if (i==ciclo[j]) {
               posiciones[i] = j;
               encontrado = true;
         }
      }
      return posiciones;
   }
   
   private static int calcularRecorrido (int[] ciclo, int[][] grafo) {
      int longitud = 0;
      for (int i=0; i<grafo.length; i++) {
         longitud += grafo[ciclo[i]][ciclo[i+1]];
      }
      return longitud;
   }

}
