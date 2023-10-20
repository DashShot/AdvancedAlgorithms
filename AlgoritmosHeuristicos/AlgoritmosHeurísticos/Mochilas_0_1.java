
/**
 * Write a description of class Mochilas_0_1 here.
 * 
 * @author (Ángel Velázquez Iturbide, Departamento de Informática y Estadística, Universidad Rey Juan Carlos)
 * Asignatura: Algoritmos Avanzados, Grado en Ingeniería Informática
 * @version (Curso 2014-15)
 */
public class Mochilas_0_1
{
   public static int mochila01_voraz (int[] ps, int[] bs, int c) {
   // greedy algorithm, vectores sin ordenar
      // se calculan tasas de b/p
      float[] ts = new float[ps.length];
      for (int i=0; i<ps.length; i++)
         ts[i] = (float)bs[i]/ps[i];
      // se ordenan los índices en 'is' según las tasas de b/p
      int[] is = new int[ps.length];
      is = ordenarIndices (ts);
      // el algoritmo voraz, usando el vector de índices 'is'
      int[] xs = new int[ps.length];
      int p = c;
      int b = 0;
      for (int i=0; i<ps.length; i++) {
         xs[is[i]] = (ps[is[i]]<=p ? 1:0);
         p -= xs[is[i]]*ps[is[i]];
         b += xs[is[i]]*bs[is[i]];
      }
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

   public static int mochila01_aprox (int[] ps, int[] bs, int c) {
   // algoritmo aproximado
      // se calcula el "mejor" objeto que quepa en la mochila
      int mayor = 0;
      for (int i=0; i<bs.length; i++)
         if (bs[i]>mayor && ps[i]<=c)
            mayor = bs[i];
      return Math.max(mayor,mochila01_voraz(ps,bs,c));
   }

   public static int mochila01_back (int[] ps, int[] bs, int c) {
      int[] solParcial = new int[ps.length];
      int[] solOptima = new int[ps.length];
      int bOpt = buscar01 (ps, bs, c, 0, c, solParcial, 0, solOptima, -1);
      return bOpt;
   }
   private static int buscar01 (int[] ps, int[] bs, int c,
                                int i, int p,
                                int[] solParc, int b,
                                int[] solOpt, int bOpt) {
      // versión optimizada, con el bucle desplegado y optimizado
      //decisión 0
      solParc[i] = 0;
      if (i==ps.length-1) {
         if (b>bOpt) {
            bOpt = b;
            for (int j=0; j<ps.length; j++)
               solOpt[j] = solParc[j];
         }
      }
      else
         bOpt = buscar01 (ps, bs, c, i+1, p, solParc, b, solOpt, bOpt);
      //decisión 1
      if (ps[i]<=p) {
         solParc[i] = 1;
         int np = p - ps[i];
         int nb = b + bs[i];
         if (i==ps.length-1) {
            if (nb>bOpt) {
               bOpt = nb;
               for (int j=0; j<ps.length; j++)
                  solOpt[j] = solParc[j];
            }
         }
         else
            bOpt = buscar01 (ps, bs, c, i+1, np, solParc, nb, solOpt, bOpt);
      }
      return bOpt;
   }

   public static int mochila01_PD (int[] ps, int[] bs, int c) {
   // versión tabulada con una matriz
      int[][] tabla = new int[ps.length+1][c+1];
      for (int p=0; p<=c; p++)
         tabla[ps.length][p] = 0;
      for (int i=ps.length-1; i>=0; i--) {
         for (int p=c; p>=ps[i]; p--)
            tabla[i][p] = Math.max(tabla[i+1][p], tabla[i+1][p-ps[i]]+bs[i]);
         for (int p=Math.min(c,ps[i]-1); p>=0; p--)
            tabla[i][p] = tabla[i+1][p];
      }
      return tabla[0][c];
   }

   public static int mochila01_aproxSerie (int[] ps, int[] bs, int c, int K) {
   // serie de funciones aproximación, para 0<=k<=n
      // se calculan tasas de b/p
      float[] ts = new float[ps.length];
      for (int i=0; i<ps.length; i++)
         ts[i] = (float)bs[i]/ps[i];
      // se ordenan los índices en 'is' según las tasas de b/p
      int[] is = new int[ps.length];
      is = ordenarIndices (ts);
      // el *** algoritmo voraz, usando el vector de índices 'is' ***
      int[] comb       = new int[ps.length];
      int[] subconj    = new int[ps.length];
      int[] subconjOpt = new int[ps.length];
      int[] solParcial = new int[ps.length];
      int p, b;
      int bOpt = -1;
      int[] solOptima  = new int[ps.length];
      for (int k=0; k<=K; k++) {
         comb = iniciar(k, ps.length);
         // se crea la solución asociada a comb
         p = c; b = 0;
         for (int l=0; l<ps.length; l++) {
            subconj[is[l]] = comb[l];
            if (comb[l]==1) {
               p -= ps[is[l]];
               b += bs[is[l]];
            }
         }   
         boolean haySig = true;
         while (haySig) {
            b = completar (ps, bs, c, is, p, subconj, b);
            if (b>bOpt) {
               bOpt = b;
               for (int l=0; l<ps.length; l++)
                  subconjOpt[l] = subconj[l];
            }
            imprimirSubconj (comb, subconj, b, is);
            haySig = siguiente(comb, k);
            if (haySig) {
               p = c; b = 0;
               for (int l=0; l<ps.length; l++) {
                  subconj[is[l]] = comb[l];
                  if (comb[l] == 1) {
                     p -= ps[is[l]];
                     b += bs[is[l]];
                  }
               } 
            }
         }
         imprimirEtapa (k, subconjOpt, bOpt);
      }
      return bOpt;
   }
   private static int[] iniciar (int k, int n) {
      int[] v = new int[n];
      for (int i=0; i<n; i++)
         v[i] = i<k?1:0;
      return v;
   }

   private static boolean siguiente (int[] comb, int K) {
      boolean haySig;
      int j;
      for (j=comb.length-1; comb[j]==1; j--) ;
      if (j==comb.length-1) { //acaba en 0
         for (j=comb.length-1; (j>=0) && (comb[j]==0); j--) ;
         if (j>=0) {
            comb[j]   = 0;
            comb[j+1] = 1;
            haySig = true;
         } else
            haySig = false;
      } else {
         int nUnos = comb.length-1-j;
         if (nUnos == K) //ya se ha generado la última combinación
            haySig = false;
         else {
            int i;
            for (i=j; comb[i]==0; i--) ;
            comb[i] = 0;
            for (int k=i+1; k<=i+1+nUnos; k++)
               comb[k] = 1;
            for (int k=i+1+nUnos+1; k<comb.length; k++)
               comb[k] = 0;
            haySig = true;
         }
      }//
      return haySig;
   }

   private static int completar (int[] ps, int[] bs, int c, int[] is,
                                 int p, int[] subConj, int b) {
      for (int i=0; i<ps.length; i++)
         if ((subConj[is[i]]!=1) && (ps[is[i]]<=p)) {
            subConj[is[i]] = 1;
            p -= ps[is[i]];
            b += bs[is[i]];
         }
      return b;
   }

   private static void imprimirEtapa (int k, int[] subconjOpt, int bOpt) {
      System.out.print ("k = " + k + ": mejor solución {" + subconjOpt[0]);
      for (int i=1; i<subconjOpt.length; i++)
         System.out.print ("," + subconjOpt[i]);
      System.out.println ("} con beneficio " + bOpt);
   }
   private static void imprimirSubconj (int[] comb, int[] subconj, int b, int[] is) {
      System.out.print ("Comb {" + comb[0]);
      for (int i=1; i<comb.length; i++)
         System.out.print ("," + comb[i]);
      System.out.print ("} y subconj {" + subconj[0]);
      for (int i=1; i<subconj.length; i++)
         System.out.print ("," + subconj[i]);
      System.out.println ("} con beneficio " + b);
   }

}
