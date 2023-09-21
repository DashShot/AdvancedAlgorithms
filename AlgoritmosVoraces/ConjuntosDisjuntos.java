
/**
 * Write a description of class ConjuntosDisjuntos here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ConjuntosDisjuntos
{
    static int num;
    static int[] conjs;
    
    public static void ConjsDisjuntos (int n) {
       num = n;
       conjs = new int[n];
       for (int i=0; i<num; i++) {
          conjs[i] = i;
       }
    }
       
    public static int SuConj (int nodo) {
       return conjs[nodo];
    }
       
    public static void FusionarConjs (int c1, int c2) {
        int min = Math.min (c1, c2);
        int max = Math.max (c1, c2);
        for (int k=0; k<num; k++) {
           if (conjs[k]==max)
              conjs[k] = min;          
        }
    }
}
