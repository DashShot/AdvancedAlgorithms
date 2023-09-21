
/**
 * Write a description of class SecTareasConPlazos here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SecTareasConPlazos
{
    public static int SecTareasConPlazos1 (int[] bs, int[] pls) {
      // tareas ordenadas según beneficios decrecientes
      int[] tareas = new int[bs.length];
      tareas[0] = 0;
      int j = 0;
      int b = bs[0];
      for (int i=1; i<bs.length; i++) {
         int k = j;
         while (k>=0 && pls[tareas[k]]>pls[i] && pls[tareas[k]]>k)
            k--;
         if (k<0 || (pls[tareas[k]]<=pls[i] && pls[i]>k)) {
            for (int l=j; l>=k+1; l--)
               tareas[l+1] = tareas[l];
            tareas[k+1] = i;
            b += bs[i];
            j++;
         }
      }
      imprimir (tareas, j);
      return (b);
    }
    
    public static int SecTareasConPlazos2 (int[] bs, int[] pls) {
      // tareas sin ordenar
      // se ordenan los índices según beneficios decrecientes
      int[] is = new int[bs.length];
      is = ordenarIndices(bs);
      // el algoritmo voraz, usando el vector de índices 'is'
      int[] tareas = new int[bs.length];
      tareas[0] = is[0];
      int j = 0;
      int b = bs[is[0]];
      for (int i=1; i<bs.length; i++) {
         int k = j;
         while (k>=0 && pls[tareas[k]]>pls[is[i]] && pls[tareas[k]]>k)
            k--;
         if (k<0 || (pls[tareas[k]]<=pls[is[i]] && pls[is[i]]>k)) {
            for (int l=j; l>=k+1; l--)
               tareas[l+1] = tareas[l];
            tareas[k+1] = is[i];
            b += bs[is[i]];
            j++;
        }
      }
      imprimir (tareas, j);
      return (b);
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
   
   private static void imprimir (int[] v, int n) {
      for (int i=0; i<=n; i++)
         System.out.print ((v[i]<10?("  "+v[i]):(v[i]<100?" "+v[i]:v[i]))+" ");
      System.out.println();
   }

}
