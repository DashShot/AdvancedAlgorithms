
/**
 * Write a description of class ArbolesRecubrimientoCosteMinimo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ArbolesRecubrimientoCosteMinimo
{

   public static int Prim (int[][] grafo) {
      // calcula el coste mínimo y los arcos correspondientes
      // grafo como matriz de adyacencia
      // vectores que representan los arcos del MST (orígenes y destinos)
      int[] orig = new int[grafo.length-1];
      int[] dest = new int[grafo.length-1];
      int coste = 0;
      // representación implícita del subconjunto S de nodos candidatos
      // masProx[i] contiene el nodo de S más cercano al nodo i
      // distMin[i] = -1 si el nodo i pertenece a S
      //            = si no, distancia entre i y masProx[i]
      int[] masProx = new int[grafo.length];
      int[] distMin = new int[grafo.length];
      // inicialización de vectores auxiliares, suponiendo que el MST contiene al nodo cero
      for (int i=1; i<grafo.length; i++) {
         masProx[i] = 0;
         distMin[i] = grafo[i][0];
      }
      // bucle voraz
      for (int i=0; i<grafo.length-1; i++) {
         // se selecciona nodo más cercano al MST ya formado
         int min = Integer.MAX_VALUE;
         int k = 0;
         for (int j=1; j<grafo.length; j++)
            if ((0<=distMin[j]) && (distMin[j]<min)) {
               min = distMin[j];
               k = j;
            }
         // se añade el arco al nodo k al MST
         distMin[k] = -1;
         orig[i] = masProx[k];
         dest[i] = k;
         coste += grafo[orig[i]][dest[i]];
         // se actualizan las distancias mínimas al resto de nodos
         for (int j=1; j<grafo.length; j++)
            if (grafo[k][j]<distMin[j]) {
               distMin[j] = grafo[k][j];
               masProx[j] = k;
            }
      }
      imprimirArcos (orig, dest);
      return coste;
   }
   
   public static int Kruskal1 (int[] us, int[] vs, int[] costes, int n) {
      // calcula el coste mínimo y los arcos correspondientes
      // grafo como lista de adyacencia
      // presupone que los arcos vienen ordenados en orden creciente de coste
      // vectores que representan los arcos del MST (orígenes y destinos)
      int[] orig = new int[n-1];
      int[] dest = new int[n-1];
      int a = 0;
      int coste = 0;
      // representación implícita del bosque de nodos que forman el MST
      int[] conjs = new int[n];
      for (int i=0; i<n; i++)
         conjs[i] = i;
      // bucle voraz
      for (int i=0; i<us.length && a<n; i++) {
         // se selecciona el arco más corto
         int u = us[i];
         int v = vs[i];
         // se halla el conjunto disjunto de sus nodos
         int conju = conjs[u];
         int conjv = conjs[v];
         // se comprueba si pertenecen a conjuntos disjuntos
         if (conju != conjv) {
            orig[a] = u;
            dest[a] = v;
            a++;
            coste += costes[i];
            // se fusionan
            int min = Math.min (conju, conjv);
            int max = Math.max (conju, conjv);
            for (int k=0; k<n; k++)
               if (conjs[k]==max)
                  conjs[k] = min;
         }
      }
      imprimirArcos (orig, dest);
      return coste;
   }

   public static int Kruskal2 (int[] us, int[] vs, int[] costes, int n) {
      // utiliza un TAD para conjuntos disjuntos
      // calcula el coste mínimo y los arcos correspondientes
      // grafo como lista de adyacencia
      // presupone que los arcos vienen ordenados en orden creciente de coste
      // vectores que representan los arcos del MST (orígenes y destinos)
      int[] orig = new int[n-1];
      int[] dest = new int[n-1];
      int a = 0;
      int coste = 0;
      // representación implícita del bosque de nodos que forman el MST
      ConjuntosDisjuntos.ConjsDisjuntos (n);
      // bucle voraz
      for (int i=0; i<us.length && a<n; i++) {
         // se selecciona el arco más corto
         int u = us[i];
         int v = vs[i];
         // se halla el conjunto disjunto de sus nodos
         int conju = ConjuntosDisjuntos.SuConj(u);
         int conjv = ConjuntosDisjuntos.SuConj(v);
         // se comprueba si pertenecen a conjuntos disjuntos
         if (conju != conjv) {
            orig[a] = u;
            dest[a] = v;
            a++;
            coste += costes[i];
            // se fusionan
            ConjuntosDisjuntos.FusionarConjs (conju, conjv);
         }
      }
      imprimirArcos (orig, dest);
      return coste;
   }

   private static void imprimirArcos (int[] orig, int[] dest) {
      for (int i=0; i<orig.length; i++)
         System.out.println ("Arco "+i+": ("+orig[i]+","+dest[i]+")");
      System.out.println ();
   }
      

}
