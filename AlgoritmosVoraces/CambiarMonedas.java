
/**
 * Write a description of class CambiarMonedas here.
 * 
 * @author (�ngel Vel�zquez Iturbide, Departamento de Inform�tica y Estad�stica, Universidad Rey Juan Carlos)
 * Asignatura: Algoritmos Avanzados, Grado en Ingenier�a Inform�tica
 * @version (Curso indefinido)
 */
public class CambiarMonedas {
   static int[] unidades = {200, 100, 50, 20, 10, 5, 2, 1};

   public static int cambiarMonedas1 (int total) {
      // versi�n recursiva
      return cambiarMonedas (0, total, 0);
   }
   private static int cambiarMonedas (int i, int resto, int monedas) {
      if ((i==unidades.length) || (resto==0))
         return monedas;
      else {
         monedas += resto/unidades[i];
         resto = resto%unidades[i];
         return cambiarMonedas (i+1, resto, monedas);
      }
   }

   public static int cambiarMonedas2 (int total) {
      // versi�n iterativa
      int monedas = 0;
      int resto = total;
      for (int i=0; i<unidades.length && resto!=0; i++) {
         monedas += resto/unidades[i];
         resto = resto%unidades[i];
      }
      return (monedas);
   }

   public static int cambiarMonedas3 (int total) {
      // versi�n iterativa, que calcula tambi�n el cambio
      int[] cambio = new int[unidades.length];
      int monedas = 0;
      int resto = total;
      for (int i=0; i<unidades.length && resto!=0; i++) {
         cambio[i] = resto/unidades[i];
         monedas += cambio[i];
         resto = resto%unidades[i];
      }
      imprimir (unidades);
      imprimir (cambio);
      return (monedas);
   }

  private static void imprimir (int[] v) {
      for (int i=0; i<v.length; i++)
         System.out.print ((v[i]<10?("  "+v[i]):(v[i]<100?" "+v[i]:v[i]))+" ");
      System.out.println();
   }
}
