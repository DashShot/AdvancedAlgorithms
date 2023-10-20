
/**
 * Write a description of class LlenadoCajas here.
 * 
 * @author (Ángel Velázquez Iturbide, Departamento de Informática y Estadística, Universidad Rey Juan Carlos)
 * Asignatura: Algoritmos Avanzados, Grado en Ingeniería Informática
 * @version (Cursos 2014-15. 2019-20)
 */
public class LlenadoCajas
{

   public static int masObjetosAprox1 (int[] ps, int n, int c) {
      int[] is = new int[ps.length];
      is = ordenarIndicesCrec (ps);
      int numObj = 0;
      int numCajas = 1;
      int p = c;
      for (int i=0; i<ps.length; i++)
         if (ps[is[i]]<=p) {
            numObj++;
            p = p-ps[is[i]];
         }
         else if (numCajas<n) {
            numObj++;
            numCajas++;
            p = c-ps[is[i]];
         }
      return numObj;
   }
   
   public static int masObjetosBack (int[] ps, int n, int c) {
      int[] cajas = new int[n];
      for (int i=0; i<n; i++)
         cajas[i] = c;
      int[] cajasOpt = new int[n];
      int num = buscarObjetos (ps, n, c, 0, 0, 0, cajas, -1, cajasOpt);
      return num;       
   }

   private static int buscarObjetos (int[] ps, int n, int c,
                                     int i, int nCajas,
                                     int nObj, int[] cajas,
                                     int nOpt, int[] cajasOpt) {
      // versión basada en el esquema de la técnica de vuelta atrás para solución óptima
      // opciones de meter el objeto en alguna caja
      int limite = (nCajas<n)?nCajas+1:n;
      for (int j=0; j<limite; j++)
         if (ps[i]<=cajas[j]) {
            nObj++;
            cajas[j] -= ps[i];
            if ((nCajas<n) && (j==nCajas))
               nCajas++;
            if (i==ps.length-1) {
               if (nObj>nOpt) {
                  nOpt = nObj;
                  for (int k=0; k<n; k++)
                     cajasOpt[k] = cajas[k];
               }
            }
            else 
               nOpt = buscarObjetos (ps, n, c, i+1, nCajas, nObj, cajas, nOpt, cajasOpt);
            nObj--;
            cajas[j] += ps[i];
            if ((nCajas<=n) && (j==nCajas))
               nCajas--;
         }
      // opción de no meter el objeto en ninguna caja
      if (i==ps.length-1) {
         if (nObj>nOpt) {
            nOpt = nObj;
            for (int k=0; k<n; k++)
               cajasOpt[k] = cajas[k];
         }
      }
      else 
         nOpt = buscarObjetos (ps, n, c, i+1, nCajas, nObj, cajas, nOpt, cajasOpt);
      return nOpt;
   }

   public static int masObjetosAprox1Detallado (int[] ps, int n, int c) {
      int[] is = new int[ps.length];
      is = ordenarIndicesCrec (ps);
      int numObj = 0;
      int numCajas = 1;
      System.out.print ("Caja 0, objetos de peso:");
      int p = c;
      for (int i=0; i<ps.length; i++)
         if (ps[is[i]]<=p) {
            numObj++;
            System.out.print (ps[is[i]] + "  ");
            p = p-ps[is[i]];
         }
         else if (numCajas<n) {
            numObj++;
            numCajas++;
            System.out.println (); System.out.print ("Caja " + numCajas + ", objetos de peso:");
            System.out.print (ps[is[i]] + " ");
            p = c-ps[is[i]];
         }
      System.out.println (); System.out.println ();
      return numObj;
   }
   
   public static int masObjetosBackDetallado (int[] ps, int n, int c) {
      int[] cajas = new int[n];
      for (int i=0; i<n; i++)
         cajas[i] = c;
      int[] cajasOpt = new int[n];
      int[][] desglose    = new int[n][ps.length+1];
      int[][] desgloseOpt = new int[n][ps.length+1];
      int num = buscarObjetosD (ps, n, c, 0, 0, 0, cajas, desglose, -1, cajasOpt, desgloseOpt);
      imprimirSolMax (desgloseOpt, num);
      return num;       
   }

   private static int buscarObjetosD (int[] ps, int n, int c,
                                      int i, int nCajas,
                                      int nObj, int[] cajas, int[][] desglose,
                                      int nOpt, int[] cajasOpt, int[][] desgloseOpt) {
      // versión basada en el esquema de la técnica de vuelta atrás para solución óptima
      // opciones de meter el objeto en alguna caja
      int limite = (nCajas<n)?nCajas+1:n;
      for (int j=0; j<limite; j++)
         if (ps[i]<=cajas[j]) {
            nObj++;
            cajas[j] -= ps[i];
            desglose[j][0]++;
            desglose[j][desglose[j][0]] = ps[i];
            if ((nCajas<n) && (j==nCajas))
               nCajas++;
            if (i==ps.length-1) {
               if (nObj>nOpt) {
                  nOpt = nObj;
                  for (int k=0; k<n; k++)
                     cajasOpt[k] = cajas[k];
                  for (int k=0; k<n; k++) {
                     desgloseOpt[k][0] = desglose[k][0];
                     for (int l=1; l<=desglose[k][0]; l++)
                        desgloseOpt[k][l] = desglose[k][l];
                  }
               }
            }
            else 
               nOpt = buscarObjetosD (ps, n, c, i+1, nCajas, nObj, cajas, desglose, nOpt, cajasOpt, desgloseOpt);
            nObj--;
            cajas[j] += ps[i];
            desglose[j][0]--;
            if ((nCajas<=n) && (j==nCajas))
               nCajas--;
         }
      // opción de no meter el objeto en ninguna caja
      if (i==ps.length-1) {
         if (nObj>nOpt) {
            nOpt = nObj;
            for (int k=0; k<n; k++)
               cajasOpt[k] = cajas[k];
            for (int k=0; k<n; k++) {
               desgloseOpt[k][0] = desglose[k][0];
               for (int l=1; l<=desglose[k][0]; l++)
                  desgloseOpt[k][l] = desglose[k][l];
            }
         }
      }
      else 
         nOpt = buscarObjetosD (ps, n, c, i+1, nCajas, nObj, cajas, desglose, nOpt, cajasOpt, desgloseOpt);
      return nOpt;
   }

   public static void imprimirSolMax (int[][] desglose, int num) {
      for (int i=0; i<desglose.length; i++) {
         System.out.print ("Caja " + i + ", objetos de peso:");
         for (int j=1; j<=desglose[i][0]; j++)
            System.out.print (desglose[i][j] + "  "); 
         System.out.println ();
      }
      System.out.println ("Total de objetos metidos: " + num);
      System.out.println ();
   }
   
   
   public static int menosCajasAprox1 (int[] ps, int c) {
      // heurística 'next fit'
      int[] is = new int[ps.length];
      is = ordenarIndicesCrec (ps);
      // cajas[i] contiene el índice del último objeto introducido en la caja i
      int[] cajas = new int[ps.length];
      // empezamos con 1 caja, pero lo inicializamos en uno menos para indexar arrays
      int num = 0;
      int p = c;
      for (int i=0; i<ps.length; i++) {
         if (ps[is[i]]<=p)
            p -= ps[is[i]];
         else {
            num++;
            p = c-ps[is[i]];
         }
         cajas[num] = i;
      }
      imprimirSolMin1 (cajas, num, ps, is);
      return num+1;
   }

   private static int[] ordenarIndicesCrec (int[] v1) {
   // se ordena por inserción directa
      int[] v2 = new int[v1.length];
      v2[0] = 0;
      for (int i=1; i<v1.length; i++) {
         int aux = v1[i];
         int j;
         for (j=i-1; j>=0 && v1[v2[j]]>aux; j--)
            v2[j+1] = v2[j];
         v2[j+1] = i;
      }
      return v2;
   }
   
   private static void imprimirSolMin1 (int[] v, int x, int[] ps, int[] is) {
      for (int i=0; i<=x; i++) {
         System.out.print ("Caja núm. "+i+" contiene los objetos de pesos (índices): ");
         for (int j=(i==0)?0:v[i-1]+1; j<=v[i]; j++)
            System.out.print (ps[is[j]] + "(" + is[j] + ") ");
         System.out.println ();
      }
      System.out.println ();
   }

   public static int menosCajasAprox2 (int[] ps, int c) {
      // heurística 'first fit decreasing'
      int[] is = new int[ps.length];
      is = ordenarIndicesDecrec (ps);
      // libre[i] contiene el peso que hay libre en la caja i
      int[] libre = new int[ps.length];
      libre[0] = c-ps[is[0]];
      //cajas[i] contiene el número de caja donde se mete el objeto i
      int[] cajas = new int[ps.length];
      cajas[0] = 0;
      int num = 0;
      for (int i=1; i<ps.length; i++) {
         boolean metido = false;
         for (int j=0; (j<=num) && !metido; j++) {
            if (ps[is[i]]<=libre[j]) {
               cajas[is[i]] = j;
               metido = true;
               libre[j] -= ps[is[i]];
            }
         }
         if (!metido) {
            num++;
            cajas[is[i]] = num;
            libre[num] = c-ps[is[i]];
         }
      }
      imprimirSolMin2 (cajas, num, ps, is);
      return num+1;
   }

   private static void imprimirSolMin2 (int[] v, int x, int[] ps, int[] is) {
      for (int i=0; i<=x; i++) {
         System.out.print ("Caja núm. "+i+" contiene los objetos de pesos (índices): ");
         for (int j=0; j<ps.length; j++)
            if (v[j]==i)
               System.out.print (ps[is[j]] + "(" + is[j] + ") ");
         System.out.println ();
      }
      System.out.println ();
   }

   private static int[] ordenarIndicesDecrec (int[] v1) {
   // se ordena por inserción directa
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

   public static int menosCajasBack (int[] ps, int c) {
      int[] libre = new int[ps.length];
      libre[0] = c-ps[0];
      for (int i=1; i<ps.length; i++)
         libre[i] = c;
      int[] cajas = new int[ps.length];
      cajas[0] = 0;
      int[] libreOpt = new int[ps.length];
      int[] cajasOpt = new int[ps.length];
      int num = buscarCajas (ps, c, 1, 0, libre, cajas, ps.length, libreOpt, cajasOpt);
      imprimirSolMin3 (cajasOpt, num, ps);
      return num+1;
   }
   
   private static int buscarCajas (int[] ps, int c,
                                   int i,
                                   int n, int[] libre, int[] cajas,
                                   int nOpt, int[] libreOpt, int[] cajasOpt) {
      // versión basada en el esquema de la técnica de vuelta atrás para solución óptima
      for (int j=0; j<=n+1; j++)
         if (ps[i]<=libre[j]) {
            cajas[i] = j; 
            libre[j] -= ps[i];
            int nn = Math.max(n,j);
            if (i==ps.length-1) {
               if (n<nOpt) {
                  nOpt = nn;
                  for (int k=0; k<nn; k++)
                     libreOpt[k] = libre[k];
                  for (int k=0; k<ps.length; k++)
                     cajasOpt[k] = cajas[k];
               }
            }
            else 
               nOpt = buscarCajas (ps, c, i+1, nn, libre, cajas, nOpt, libreOpt, cajasOpt);
            libre[j] += ps[i];
         }
      return nOpt;
   }

   private static void imprimirSolMin3 (int[] v, int x, int[] ps) {
      for (int i=0; i<=x; i++) {
         System.out.print ("Caja núm. "+i+" contiene los objetos de pesos (índices): ");
         for (int j=0; j<ps.length; j++)
            if (v[j]==i)
               System.out.print (ps[j] + "(" + j + ") ");
         System.out.println ();
      }
      System.out.println ();
   }

}
