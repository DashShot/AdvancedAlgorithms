
/**
 * Write a description of class SeleccionActividades here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SelecActividades
{
   public static int selecActividades1 (int[] c, int[] f) {
      // actividades ordenadas en orden creciente de fin
      int num = 1;
      int i = 0;
      for (int j=1; j<c.length; j++) {
         if (c[j]>=f[i]) {
            num++;
            i = j;
         }
      }
      return num;
   }

   public static int selecActividades2 (int[] c, int[] f) {
      // actividades ordenadas en orden creciente de fin
      // determina también las actividades seleccionadas
      boolean[] s = new boolean[c.length];
      s[0] = true;
      int num = 1;
      int i = 0;
      for (int j=1; j<c.length; j++) {
         if (c[j]>=f[i]) {
            s[j] = true;
            num++;
            i = j;
         }
         else
            s[j] = false;
      }
      imprimir (s);
      return num;
   }
   private static void imprimir (boolean[] s) {
      for (int i=0; i<s.length; i++)
         System.out.print (s[i]+"  ");
      System.out.println ();
   }
      
}
