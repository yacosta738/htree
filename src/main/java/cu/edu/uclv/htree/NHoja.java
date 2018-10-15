/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.uclv.htree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
/**
 *
 * @author anakarla
 */
public class NHoja <E extends Comparable<E>,V> extends Nodo<E,V> {
    private HashMap<E,ArrayList<V>> valores;//oids Lista de objetos que seran borrados
    private NHoja<E,V> sgteHerm;
    

    public NHoja(HashMap<E, ArrayList<V>> valores, NHoja<E, V> sgteHerm, NInterno<E, V> padre, int orden) {
        super(padre,orden);
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
        for (Iterator<E> it = llaves.iterator(); it.hasNext();) {
            E e = it.next();
            if(e.compareTo(min)>=0 && e.compareTo(max)<=0)
                res.addAll(valores.get(e));            
        }
        
        return res;
    }
    
    public NHoja<E,V> busqAux(E llave){
        return this;
    }
   
    public void dividir(){
        int cant=valores.size()/2;
        Object[] llaves=valores.keySet().toArray();
        HashMap<E,ArrayList<V>> valorD=new HashMap<E,ArrayList<V>>();
        for (int i = cant; i < llaves.length; i++) {
            E clave=(E)llaves[i];
            valorD.put(clave,valores.get(clave));
            valores.remove(clave);
        }
        int cantH=0;
        NHoja<E,V> h=sgteHerm;
        while(h!=null){
         cant++;
         h=h.getSgteHerm();
        }
        NHoja<E,V> nuevo= new NHoja<E, V>(valorD, sgteHerm, padre,orden);
        sgteHerm=nuevo;
        
        ArrayList<Nodo<E,V>>lb=padre.getB();
        ArrayList<E> lc=padre.getK();
        int pos=lb.size()-cantH;
        lb.add(pos, nuevo);
        lc.add(pos-1,(E)llaves[cant-1]);
        padre.setB(lb);
        padre.setK(lc);
        if(lc.size()>orden)
            padre.dividir();
    }
    
    public boolean isOverflow() {
        return valores.size() > orden;
    }
    public boolean isUnderflow() {
        return valores.size() < orden/2;
    }

    @Override
    public void merge() {
        Object[] toArray = valores.keySet().toArray();
        E key = (E) toArray[toArray.length - 1];//la llave mayor del nodo hoja
        if (sgteHerm != null) {
            HashMap<E, ArrayList<V>> vSgteH = sgteHerm.getValores();
            valores.putAll(vSgteH);
            ArrayList<Nodo<E, V>> b = padre.getB();
            ArrayList<E> k = padre.getK();

            if (sgteHerm.getSgteHerm() != null) {
                //actualizo el siguiente hermano con el hermano de su hermano si este existe
                sgteHerm = sgteHerm.getSgteHerm();
            } else {
                sgteHerm = null;
            }

            int index = 0;//index 
            while (index <= k.size() && key.compareTo(k.get(index)) > 0) {
                index++;
            }
            
            k.remove(index);//elimino la llave
            b.remove(index + 1);//elimino la referencia al hijo que mescle

            //if overflow occur in leaf node, split the node
            if (isOverflow()) {//overflow
                dividir();
            }
            //if overflow occur in parent node, merge the node
            if (padre.isUnderflow()) {
                padre.merge();
            }

        }//ver si no tiene hermano como unirlos

    }
   
    
}
