
/**
 * Write a description of class Grafos here.
 * 
 * @author (�ngel Vel�zquez Iturbide, Departamento de Inform�tica y Estad�stica, Universidad Rey Juan Carlos)
 * Asignatura: Algoritmos Avanzados, Grado en Ingenier�a Inform�tica
 * @version (Curso indefinido, revisado 2019-20)
 */
public class CaminosMinimos
{
   public static int[] Dijkstra1 (int[][] grafo, int origen) {
      // calcula las longitudes de los caminos m�nimos
      // conjunto de candidatos, con todos los nodos salvo el origen
      // y distancias inicializadas como vienen dadas en el grafo
      boolean[] candidatos = new boolean[grafo.length];
      int[] distancias = new int[grafo.length];
      for (int i=0; i<grafo.length; i++) {
         candidatos[i] = true;
         distancias[i] = grafo[origen][i];
      }
      candidatos[origen] = false;
      distancias[origen] = 0;
      // iteraci�n para todos los candidatos
      for (int i=0; i<grafo.length-1; i++) {
         // se selecciona el candidato m�s cercano al origen
         // se busca un primer candidato...
         int j;
         for (j=0; j<candidatos.length && !candidatos[j]; j++) {}
         int menor = j;
         // ...y se siguen examinando los dem�s candidatos para determinar el m�s cercano al origen
         for (; j<candidatos.length; j++) {
            if (candidatos[j] && distancias[j]<distancias[menor])
               menor = j;
         }
         candidatos[menor] = false;
         // se actualizan las distancias m�nimas al resto de nodos
         // teniendo en cuenta los nuevos caminos que pasan por el candidato seleccionado
         for (j=0; j<candidatos.length; j++) {
            if (candidatos[j])
               distancias[j] = Math.min (distancias[j], distancias[menor]+grafo[menor][j]);
         }
      }
      return distancias;
   }
   
   public static int[] Dijkstra2 (int[][] grafo, int origen) {
      // calcula las longitudes de los caminos m�nimos y los propios caminos m�nimos
      // conjunto de candidatos, con todos los nodos salvo el origen
      // distancias inicializadas como vienen dadas en el grafo, y predecesores
      boolean[] candidatos = new boolean[grafo.length];
      int[] distancias = new int[grafo.length];
      int[] predecesores = new int[grafo.length];
      for (int i=0; i<grafo.length; i++) {
         candidatos[i] = true;
         distancias[i] = grafo[origen][i];
         predecesores[i] = grafo[origen][i]<1000?origen:-1; /*1000 tomado como valor de infinito*/
      }
      candidatos[origen] = false;
      distancias[origen] = 0;
      predecesores[origen] = origen;
      // iteraci�n para cada candidato
      for (int i=0; i<grafo.length-1; i++) {
         // se selecciona el candidato m�s cercano al origen
         // se busca un primer candidato...
         int j;
         for (j=0; (j<candidatos.length) && !(candidatos[j]); j++) {}
         int menor = j;
         // ...y se siguen examinando los dem�s candidatos para determinar el m�s cercano al origen
         for (; j<candidatos.length; j++) {
            if ((candidatos[j]) && (distancias[j]<distancias[menor]))
               menor = j;
         }
         candidatos[menor] = false;
         // se actualizan las distancias m�nimas al resto de nodos
         // teniendo en cuenta los nuevos caminos que pasan por el candidato seleccionado
         for (j=0; j<candidatos.length; j++) {
            if (candidatos[j])
               if (distancias[menor]+grafo[menor][j]<distancias[j]) {
                   distancias[j] = distancias[menor]+grafo[menor][j];
                   predecesores[j] = menor;
               }
         }
      }
      imprimirCaminos (predecesores, origen, distancias);
      return distancias;
   }
   
   public static int[] Dijkstra3 (int[][] grafo, int origen) {
      // versi�n basada en el uso de mont�culos
      // calcula las longitudes de los caminos m�nimos y los propios caminos m�nimos
      // conjunto de candidatos, con todos los nodos salvo el origen
      // distancias inicializadas como vienen dadas en el grafo, y predecesores
      boolean[] candidatos = new boolean[grafo.length];
      int[] distancias = new int[grafo.length];
      int[] predecesores = new int[grafo.length];
      for (int i=0; i<grafo.length; i++) {
         candidatos[i] = true;
         distancias[i] = grafo[origen][i];
         predecesores[i] = grafo[origen][i]<1000?origen:-1; /*1000 tomado como valor de infinito*/
      }
      candidatos[origen] = false;
      distancias[origen] = 0;
      predecesores[origen] = origen;
      // crea un mont�culo con todos los nodos salvo el nodo origen
      MonticuloNodos mont = new MonticuloNodos(distancias);
      mont.borrar();
      // iteraci�n para cada candidato
      for (int i=0; i<grafo.length-1; i++) {
         // se selecciona el candidato m�s cercano al origen
         int menor = mont.menorNodo();
         mont.borrar();
         candidatos[menor] = false;
         // se actualizan las distancias m�nimas al resto de nodos
         // teniendo en cuenta los nuevos caminos que pasan por el candidato seleccionado
         for (int j=0; j<candidatos.length; j++) {
            if (candidatos[j])
               if (distancias[menor]+grafo[menor][j]<distancias[j]) {
                   distancias[j] = distancias[menor]+grafo[menor][j];
                   predecesores[j] = menor;
                   mont.cambiar(j,distancias[j]);
               }
         }
      }
      imprimirCaminos (predecesores, origen, distancias);
      return distancias;
   }
   
   public static int[] Dijkstra4 (int[] us, int[] vs, int[] costes, int n, int origen) {
      // versi�n basada en el uso de mont�culos, que usa una representaci�n del grafo como listas de adyacencias
      // calcula las longitudes de los caminos m�nimos y los propios caminos m�nimos
      // conjunto de candidatos, con todos los nodos salvo el origen
      // distancias inicializadas como vienen dadas en el grafo, y predecesores
      boolean[] candidatos = new boolean[n];
      int[] distancias = new int[n];
      int[] predecesores = new int[n];
      for (int i=0; i<n; i++) {
         candidatos[i] = true;
         distancias[i] = 1000; /*1000 tomado como valor de infinito*/
         predecesores[i] = -1;
      }
      candidatos[origen] = false;
      distancias[origen] = 0;
      predecesores[origen] = origen;
      for (int i=0; i<us.length; i++)
         if (us[i]==origen) {
            distancias[vs[i]] = costes[i];
            predecesores[vs[i]] = origen;
         }
      // crea un mont�culo con todos los nodos salvo el nodo origen
      MonticuloNodos mont = new MonticuloNodos(distancias);
      mont.borrar();
      // iteraci�n para cada candidato
      for (int i=0; i<n-1; i++) {
         // se selecciona el candidato m�s cercano al origen
         int menor = mont.menorNodo();
         mont.borrar();
         candidatos[menor] = false;
         // se actualizan las distancias m�nimas al resto de nodos
         // teniendo en cuenta los nuevos caminos que pasan por el candidato seleccionado
         for (int j=0; j<us.length; j++) {
            if ((us[j]==menor) && (candidatos[vs[j]]))
               if (distancias[menor]+costes[j]<distancias[vs[j]]) {
                   distancias[vs[j]] = distancias[menor]+costes[j];
                   predecesores[vs[j]] = menor;
                   mont.cambiar(vs[j],distancias[vs[j]]);
               }
         }
      }
      imprimirCaminos (predecesores, origen, distancias);
      return distancias;
   }
   
   private static void imprimirCaminos (int[] preds, int origen, int[] distancias) {
      for (int i=0; i<preds.length; i++) {
         System.out.print ("Nodo "+i+" : camino = ");
         if (distancias[i]==1000) /*1000 tomado como valor de infinito*/
            System.out.print ("-");
         else
            imprimirCamino(preds,origen,i);
         System.out.println ();
      }
   }
   
   private static void imprimirCamino (int[] preds, int origen, int j) {
      if (j!=origen)
         imprimirCamino (preds,origen,preds[j]);
      System.out.print (j+" ");
   }

}