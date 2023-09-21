
/**
 * Write a description of class OrdenarIndices here.
 * 
 * @author (�ngel Vel�zquez Iturbide, Departamento de Inform�tica y Estad�stica, Universidad Rey Juan Carlos)
 * Asignatura: Algoritmos Avanzados, Grado en Ingenier�a Inform�tica
 * @version (Curso indefinido)
 */
public class OrdenarIndices
{
   // coincide con el problema de almacenamento �ptimo en cintas
   // o del tiempo de espera en el sistema;
   // calcula el orden �ptimo pero no el valor �ptimo

   // ordenando en orden creciente por inserci�n
   public static void ordenarEnVectorI (int[] v) {
      for (int i=1; i<v.length; i++) {
         int aux = v[i];
         int j;
         for (j=i-1; j>=0 && v[j]>aux; j--)
            v[j+1] = v[j];
         v[j+1] = aux;
      }
   }

   public static int[] ordenarEnIndicesI (int[] v) {
      int[] v2 = new int[v.length];
      v2[0] = 0;
      for (int i=1; i<v.length; i++) {
         int aux = v[i];
         int j;
         for (j=i-1; j>=0 && v[v2[j]]>aux; j--)
            v2[j+1] = v2[j];
         v2[j+1] = i;
      }
      return v2;
   }

   // ordenando en orden creciente por burbuja
   public static void ordenarEnVectorB (int[] v) {
      for (int i=1; i<v.length; i++)
         for (int j=0; j<v.length-i; j++)
            if (v[j]>v[j+1]) {
               int aux = v[j];
               v[j] = v[j+1];
               v[j+1] = aux;   
            }
   }
   public static int[] ordenarEnIndicesB (int[] v) {
      int[] v2 = new int[v.length];
      for (int i=0; i<v.length; i++)
         v2[i] = i;
      for (int i=1; i<v.length; i++)
         for (int j=0; j<v.length-i; j++)
            if (v[v2[j]]>v[v2[j+1]]) {
               int aux = v2[j];
               v2[j] = v2[j+1];
               v2[j+1] = aux;   
            }
      return v2;
   }

}
