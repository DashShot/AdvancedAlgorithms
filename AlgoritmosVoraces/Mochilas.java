
/**
 * Write a description of class Mochila here.
 * 
 * @author (Ángel Velázquez Iturbide, Departamento de Informática y Estadística, Universidad Rey Juan Carlos)
 * Asignatura: Algoritmos Avanzados, Grado en Ingeniería Informática
 * @version (Curso indefinido)
 */
public class Mochilas
{
   public static int objetosNumero (int[] ps, int c) {
   // maximizar número de objetos introducidos
   // algoritmo iterativo, ps ordenados en orden creciente
      int n = 0;
      int r = c;
      for (int i=0; i<ps.length; i++) {
         if (ps[i]<=r) {
            n++;
            r = r - ps[i];
         }
      }
      return n;
   }

   public static int objetosPeso (int[] ps, int c) {
   // maximizar peso de los objetos introducidos
   // algoritmo iterativo, ps ordenados en orden creciente
      int p = 0;
      int r = c;
      for (int i=0; i<ps.length; i++) {
         if (ps[i]<=r) {
            p = p + ps[i];
            r = r - ps[i];
         }
      }
      return p;
   }

   public static float mochila1 (int[] ps, int[] bs, int c) {
   // maximizar beneficio de los objetos introducidos
   // algoritmo recursivo, ps y bs ordenados en orden decreciente de b/p
      float[] xs = new float[ps.length];
      float b = mochila (0, ps, bs, c, (float)0.0, xs);
      imprimir (xs);
      return b;
   }
   private static float mochila (int i, int[] ps, int[] bs, float p, float b, float[] xs) {
      if (i==ps.length)
         return b;
      else if (p==(float)0.0)
         return b;
      else {
         xs[i] = (ps[i]<=p) ? ((float)1.0) : ((float)p/ps[i]);
         return mochila (i+1, ps, bs, p-ps[i]*xs[i], b+bs[i]*xs[i], xs);
      }
   }

   public static float mochila2 (int[] ps, int[] bs, int c) {
   // algoritmo iterativo, ps y bs ordenados en orden decreciente de b/p
      float[] xs = new float[ps.length];
      int p = c;
      float b = (float)0.0;
      for (int i=0; i<ps.length && p>0.0; i++) {
         xs[i] = (ps[i]<=p ? (float)1.0 : (float)p/ps[i]);
         p -= ps[i]*xs[i];
         b += bs[i]*xs[i];
      }
      imprimir (xs);
      return b;
   }

   public static float mochila3 (int[] ps, int[] bs, int c) {
   // algoritmo iterativo, vectores sin ordenar
      // se calculan tasas de b/p
      float[] ts = new float[ps.length];
      for (int i=0; i<ps.length; i++)
         ts[i] = (float)bs[i]/ps[i];
      // se ordenan los índices en 'is' según las tasas de b/p
      int[] is = new int[ps.length];
      is = ordenarIndices (ts);
      // el algoritmo voraz, usando el vector de índices 'is'
      float[] xs = new float[ps.length];
      int p = c;
      float b = (float)0.0;
      for (int i=0; i<ps.length && p>=0.0; i++) {
         xs[is[i]] = (ps[is[i]]<=p ? (float)1.0 : (float)p/ps[is[i]]);
         p -= xs[is[i]]*ps[is[i]];
         b += xs[is[i]]*bs[is[i]];
      }
      imprimir(xs);
      return b;
   }
   private static int[] ordenarIndices (float[] v1) {
   // se ordena por inserción directa según las tasas contenidas en 'v1'
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
   
private static void imprimir (float[] v) {
      for (int i=0; i<v.length; i++)
         System.out.print (v[i]+"  ");
      System.out.println ();
   }
}
