package cu.edu.uclv.htree;

import java.util.ArrayList;
/**
 * @author yuniel acosta, anakarla sotolongo, elvis hernandez y danay serrano
 * Created in 18/9/2018 09:24
 * @project htree
 **/
public class HTree <E,V> {
    
    private NInterno<E,V> raiz;
    private int orden;

    public HTree(NInterno<E, V> raiz, int orden) {
        this.raiz = raiz;
        this.orden = orden;
    }

    public NInterno<E, V> getRaiz() {
        return raiz;
    }

    public void setRaiz(NInterno<E, V> raiz) {
        this.raiz = raiz;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
    
    public ArrayList<V> busqueda( Nodo<E, V> n, E min, E max){
        
     ArrayList<V> res=new ArrayList<V>();
    
     return res;
     
    } 
    
    public void insertar(E llave, V valor){
    
    }

}