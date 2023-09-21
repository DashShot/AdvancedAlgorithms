
/**
 * Write a description of class MonticuloNodos here.
 * 
 * @author (Ángel Velázquez Iturbide, Departamento de Informática y Estadística, Universidad Rey Juan Carlos)
 * Asignatura: Algoritmos Avanzados, Grado en Ingeniería Informática
 * @version (Curso indefinido, revisado 2021-22)
 */
public class MonticuloNodos
{
   static int n;
   static int[] mont;
   static int[] map1; //posición dentro del montículo que ocupa el nodo 'i'
   static int[] map2; //nodo que está en la posición 'i' del montículo
   
   public MonticuloNodos (int[] distancias) {
      mont = new int[distancias.length];
      map1 = new int[distancias.length];
      map2 = new int[distancias.length];
      mont[0] = distancias[0];
      map1[0] = 0;
      map2[0] = 0;
      n = 1;
      for (int i=1; i<distancias.length; i++) {
         mont[i] = distancias[i];
         map1[i] = i;
         map2[i] = i;
         n++;
         flotar(i);
      }
   }
    
   public int menorValor () {
      return mont[0];
   }
   
   public int menorNodo () {
      return map2[0];
   }
   
   public void borrar () {
      mont[0] = mont[n-1];
      map2[0] = map2[n-1];
      map1[map2[n-1]] = 0;
      n--;
      hundir(0);
   }
   
/*   public void anyadir (int nodo, int dist) {
      n++;
      mont[n-1] = dist;
      map [n-1] = nodo;
      flotar(n);
   }
*/   
   public void cambiar (int nodo, int d) {
      int i = map1[nodo];
      int x = mont[i];
      mont[i] = d;
      if (d>x)
         hundir(i);
      else
         flotar(i);
   }
    
   private void hundir (int i) {
      int k = i+1;
      int j;
      do {
         j = k;
         if ((2*j<=n) && (mont[2*j-1]<mont[k-1]))
            k = 2*j;
         if ((2*j<n) && (mont[2*j]<mont[k-1]))
            k = 2*j+1;
         int temp = mont[j-1];
         mont[j-1] = mont[k-1];
         mont[k-1] = temp;
         map1[map2[j-1]] = k-1;
         map1[map2[k-1]] = j-1;
         temp = map2[j-1];
         map2[j-1] = map2[k-1];
         map2[k-1] = temp;
      } while (j!=k);
   }
   
   private void flotar (int i) {
      int k = i+1;
      int j;
      do {
         j = k;
         if ((j>1) && (mont[j/2-1]>mont[k-1]))
            k = j/2;
         int temp = mont[j-1];
         mont[j-1] = mont[k-1];
         mont[k-1] = temp;
         map1[map2[j-1]] = k-1;
         map1[map2[k-1]] = j-1;
         temp = map2[j-1];
         map2[j-1] = map2[k-1];
         map2[k-1] = temp;
      } while (j!=k);
   }

}
