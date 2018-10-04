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
public class NInterno <E extends Comparable<E>,V> extends Nodo<E,V> {
    
    private ArrayList<E> k;//H -> Indice a partir del cual se borra
    private ArrayList<Nodo<E,V>> b;
    private int cantL;
    private HashMap<ArrayList<E>,HTree<E,V>> l;

    public NInterno(ArrayList<E> k, ArrayList<Nodo<E, V>> b, int cantL, HashMap<ArrayList<E>,HTree<E,V>> l, Nodo<E, V> padre) {
        super(padre);
        this.k = k;
        this.b = b;
        this.cantL = cantL;
        this.l = l;
    }

    

    public ArrayList<E> getK() {
        return k;
    }

    public void setK(ArrayList<E> k) {
        this.k = k;
    }

    public ArrayList<Nodo<E,V>> getB() {
        return b;
    }

    public void setB(ArrayList<Nodo<E,V>> b) {
        this.b = b;
    }

    public int getCantL() {
        return cantL;
    }

    public void setCantL(int cantL) {
        this.cantL = cantL;
    }

    public HashMap<ArrayList<E>,HTree<E,V>> getL() {
        return l;
    }

    public void setL(HashMap<ArrayList<E>,HTree<E,V>> l) {
        this.l = l;
    }
    
    public ArrayList<V> busqueda(E min, E max){
        ArrayList<V> res= new ArrayList<V>();
        int i=0;
        while(i<k.size() && min.compareTo(k.get(i))>0)
            i++;
        res.addAll(b.get(i).busqueda(min, max));
        if(i<k.size()){
            i++;
            while(i<k.size() && max.compareTo(k.get(i))>0){
                res.addAll(b.get(i).busqueda(min, max));
                i++;
            }
            res.addAll(b.get(i).busqueda(min, max));
        }
        
        boolean parar=false;
        Set<ArrayList<E>> intervalo=l.keySet();
        for (Iterator<ArrayList<E>> it = intervalo.iterator(); it.hasNext() && !parar;) {
            ArrayList<E> arrayList = it.next();
            if((min.compareTo(arrayList.get(0))>=0 && min.compareTo(arrayList.get(1))<=0) || (max.compareTo(arrayList.get(0))>=0 && max.compareTo(arrayList.get(1))<=0)){
                HTree subArb=l.get(arrayList);
                res.addAll(subArb.busqueda(subArb.getRaiz(), min, max));
            }else
                parar=true;
            
        }
        
        return res;
    }
    
}

