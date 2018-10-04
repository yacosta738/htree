/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.uclv.htree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
/**
 *
 * @author anakarla
 */
public class NHoja <E extends Comparable<E>,V> extends Nodo<E,V> {
    private HashMap<E,ArrayList<V>> valores;//oids Lista de objetos que seran borrados
    private NHoja<E,V> sgteHerm;

    public NHoja(HashMap<E, ArrayList<V>> valores, NHoja<E, V> sgteHerm, Nodo<E, V> padre) {
        super(padre);
        this.valores = valores;
        this.sgteHerm = sgteHerm;
    }

    public HashMap<E, ArrayList<V>> getValores() {
        return valores;
    }

    public void setValores(HashMap<E, ArrayList<V>> valores) {
        this.valores = valores;
    }

    public NHoja<E, V> getSgteHerm() {
        return sgteHerm;
    }

    public void setSgteHerm(NHoja<E, V> sgteHerm) {
        this.sgteHerm = sgteHerm;
    }
    
    public ArrayList<V> busqueda(E min, E max){
        ArrayList<V> res= new ArrayList<V>();
        Set<E> llaves=valores.keySet();
        boolean parar=false;
        for (Iterator<E> it = llaves.iterator(); it.hasNext() && !parar;) {
            E e = it.next();
            if(e.compareTo(min)>=0 && e.compareTo(max)<=0)
                res.addAll(valores.get(e));
            else
                parar=true;            
        }
        
        return res;
    }
    
    
}
