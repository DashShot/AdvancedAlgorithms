
/**
 * Write a description of class ColoreadoGrafo here.
 * 
 * @author (Ángel Velázquez Iturbide, Departamento de Informática y Estadística, Universidad Rey Juan Carlos)
 * Asignatura: Algoritmos Avanzados, Grados en Ingeniería Informática e Ingeniería de Computadores
 * @version (Curso 2023-24)
 */
public class ColoreadoGrafo
{

   public static int coloreadoGrafoH1 (int[][] grafo) {
      int num = 0;
      int[] colores = new int[grafo.length];
      colores[0] = 0;
      for (int i=1; i<grafo.length; i++) {
         boolean usado = true;
         int c;
         for (c=0; (c<=num) && usado; c++) {
            usado = false;
            for (int j=0; (j<i) && !usado; j++)
               if (grafo[i][j]==1)
                  usado = (colores[j]==c);
         }
         if (usado) {
            num++;
            colores[i] = num;
         } else
            colores[i] = c-1;
      }
      imprimirGrafoColoreado (num+1, colores);
      return num+1;
   }
   
   private static void imprimirGrafoColoreado (int n, int[] color) {
      System.out.println ("El grafo se ha coloreado con " + n + " colores de la siguiente forma:");
      for (int i=0; i<color.length; i++)
         System.out.println ("El nodo " + i + " tiene el color " + color[i]);
   }
}
