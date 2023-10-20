
/**
 * Write a description of class SeleccionActividades here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SelecActividades
{
   public static int selecActividades1 (int[] c, int[] f) {
      // actividades sin ordenar;
      // se ordenan los índices en 'is' en orden creciente de fin
      int[] is = new int[f.length];
      is = ordenarCrecienteIndices (f);
      // el algoritmo voraz, usando el vector de índices 'is'
      boolean[] s = new boolean[f.length];
      for (int i=0; i<s.length; i++)
         s[i] = false;
      s[is[0]] = true;
      int num = 1;
      int i = 0;
      for (int j=1; j<f.length; j++) {
         if (c[is[j]]>=f[is[i]]) {
            s[is[j]] = true;
            num++;
            i = j;
         }
         else
            s[is[j]] = false;
      }
      return num;
   }

   private static int[] ordenarCrecienteIndices (int[] v1) {
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

   public static int selecActividades2 (int[] c, int[] f) {
      // actividades sin ordenar;
      // se ordenan los índices en 'is' en orden decreciente de comienzo
      int[] is = new int[c.length];
      is = ordenarDecrecienteIndices (c);
      // el algoritmo voraz, usando el vector de índices 'is'
      boolean[] s = new boolean[c.length];
      for (int i=0; i<s.length; i++)
         s[i] = false;
      s[is[0]] = true;
      int num = 1;
      int i = 0;
      for (int j=1; j<c.length; j++) {
         if (f[is[j]]<=c[is[i]]) {
            s[is[j]] = true;
            num++;
            i = j;
         }
         else
            s[is[j]] = false;
      }
      return num;
   }

   private static int[] ordenarDecrecienteIndices (int[] v1) {
   // se ordena por inserción directa en orden decreciente
      int[] v2 = new int[v1.length];
      v2[0] = 0;
      for (int i=1; i<v1.length; i++) {
         float aux = v1[i];
         int j;
         for (j=i-1; j>=0 && v1[v2[j]]<aux; j--)
            v2[j+1] = v2[j];
         v2[j+1] = i;
      }
      return v2;
   }
   
   public static int selecActividades3 (int[] c, int[] f) {
      // actividades sin ordenar;
      // se ordenan los índices en 'is' en orden creciente de duración
      int[] d = new int[f.length];
      for (int i=0; i<d.length; i++)
         d[i] = f[i]-c[i];
      int[] is = new int[d.length];
      is = ordenarCrecienteIndices (d);
      // se determinan los plazos mínimo y máximo de realización de las actividades
      int min = c[0];
      for (int i=1; i<c.length; i++)
         if (c[i]<min)
            min = c[i];
      int max = f[0];
      for (int i=1; i<f.length; i++)
         if (f[i]>max)
            max = f[i];
      // el algoritmo voraz, usando el vector de índices 'is'
      boolean[] s = new boolean[c.length];
      for (int i=0; i<s.length; i++)
         s[i] = false;
      s[is[0]] = true;
      int num = 1;
      int i = 0;
      boolean[] libre = new boolean[max-min];
      for (int k=min; k<max; k++)
         libre[k-min] = !(k>=c[is[0]] && k<f[is[0]]);
      for (int j=1; j<c.length; j++) {
         boolean solape = false;
         int k=0;
         for (k=c[is[j]]; k<f[is[j]]; k++)
            if (!libre[k-min])
               solape = true;
            else
               libre[k-min] = false;
         if (!solape) {
            s[is[j]] = true;
            num++;
            i = j;
         }
         else
            s[is[j]] = false;
      }
      return num;
   }
}
