
/**
 * Write a description of class EquilibradoCarga here.
 * 
 * @author (Ángel Velázquez Iturbide, Departamento de Informática y Estadística, Universidad Rey Juan Carlos)
 * Asignatura: Algoritmos Avanzados, Grado en Ingeniería Informática
 * @version (Curso 2014-15)
 */
public class EquilibradoCarga
{

   public static int equilibrarCargasAprox1 (int m, int[] ts) {
      // máquina asignada a cada tarea i
      int[] as = new int[ts.length];
      for (int i=0; i<as.length; i++)
         as[i] = -1;
      // tiempo acumulado por cada máquina
      int[] tms = new int[m];
      for (int i=0; i<m; i++)
         tms[i] = 0;
      // algoritmo aproximado
      for (int i=0; i<ts.length; i++) {
         int min = 0;
         for (int j=1; j<m; j++)
            if (tms[j]<tms[min])
               min = j;
         as[i] = min;
         tms[min] += ts[i];
      }
      int max = 0;
      for (int i=0; i<m; i++)
         if (tms[i]>tms[max])
            max = i;
      return tms[max];
   }

   public static int equilibrarCargasAprox2 (int m, int[] ts) {
      // máquina asignada a cada tarea i
      int[] as = new int[ts.length];
      for (int i=0; i<as.length; i++)
         as[i] = -1;
      // tiempo acumulado por cada máquina
      int[] tms = new int[m];
      for (int i=0; i<m; i++)
         tms[i] = 0;
      // algoritmo aproximado
      // se ordenan los índices en 'is' según beneficios decrecientes
      int[] is = ordenarIndices(ts);
      for (int i=0; i<ts.length; i++) {
         int min = 0;
         for (int j=1; j<m; j++)
            if (tms[j]<tms[min])
               min = j;
         as[is[i]] = min;
         tms[min] += ts[is[i]];
      }
      int max = 0;
      for (int i=0; i<m; i++)
         if (tms[i]>tms[max])
            max = i;
      return tms[max];
   }

    private static int[] ordenarIndices (int[] v1) {
      int[] v2 = new int[v1.length];
      v2[0] = 0;
      for (int i=1; i<v1.length; i++) {
         int aux = v1[i];
         int j;
         for (j=i-1; j>=0 && v1[v2[j]]<aux; j--)
            v2[j+1] = v2[j];
         v2[j+1] = i;
      }
      return v2;
   }
   
   public static int equilibrarCargasBack (int m, int[] ts) {
      int[] cargasParcial = new int[m];
      int[] cargasOptima  = new int[m];
      for (int i=0; i<m; i++)
         cargasOptima[i] = Integer.MAX_VALUE;
      ecBack (0, m, ts, cargasParcial, cargasOptima);
      return (maximo(cargasOptima));
   }

   private static void ecBack (int i,
                               int m,
                               int[] ts,
                               int[] csParcial,
                               int[] csOptima) {
      for (int k=0; k<m; k++) {
         csParcial[k] += ts[i];
         if (i==ts.length-1) {
            int cargaParcial = maximo(csParcial);
            int cargaOptima  = maximo(csOptima);
            if (cargaParcial<cargaOptima)
               for (int j=0; j<m; j++)
                  csOptima[j] = csParcial[j];
        } else
            ecBack (i+1, m, ts, csParcial, csOptima);
         csParcial[k] -= ts[i];
      }
   }
   private static int maximo (int[] v) {
      int max = Integer.MIN_VALUE;
      for (int i=0; i<v.length; i++)
         if (v[i]>max)
            max = v[i];
      return max;
   }

   public static int equilibrarCargasAprox1Prob (int m, int[] ts) {
      // máquina asignada a cada tarea i
      int[] as = new int[ts.length];
      for (int i=0; i<as.length; i++)
         as[i] = -1;
      // tiempo acumulado por cada máquina
      int[] tms = new int[m];
      for (int i=0; i<m; i++)
         tms[i] = 0;
      // algoritmo aproximado
      for (int i=0; i<ts.length; i++) {
         int proc = (int) Math.random()*ts.length;
         as[i] = proc;
         tms[proc] += ts[i];
      }
      int max = 0;
      for (int i=0; i<m; i++)
         if (tms[i]>tms[max])
            max = i;
      return tms[max];
   }

}
