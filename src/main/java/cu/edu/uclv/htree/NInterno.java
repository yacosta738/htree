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
public class NInterno <E extends Comparable<E>,V> extends Nodo<E,V> {
    
    private ArrayList<E> k;//H -> Indice a partir del cual se borra
    private ArrayList<Nodo<E,V>> b;
    private int cantL;
    private HashMap<ArrayList<E>,HTree<E,V>> l;

    public NInterno(ArrayList<E> k, ArrayList<Nodo<E, V>> b, int cantL, HashMap<ArrayList<E>,HTree<E,V>> l, NInterno<E, V> padre,int orden) {
        super(padre,orden);
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
        
        Set<ArrayList<E>> intervalo=l.keySet();
        for (Iterator<ArrayList<E>> it = intervalo.iterator(); it.hasNext();) {
            ArrayList<E> arrayList = it.next();
            if((min.compareTo(arrayList.get(0))>=0 && min.compareTo(arrayList.get(1))<=0) || (max.compareTo(arrayList.get(0))>=0 && max.compareTo(arrayList.get(1))<=0)){
                HTree subArb=l.get(arrayList);
                NInterno<E,V> n=subArb.getRaiz();
                while(n.getPadre()!=null && (!(min.compareTo(n.getK().get(0))>=0 )||!(max.compareTo(n.getK().get(n.getK().size()-1))<=0)))
                    n=n.getPadre();
                res.addAll(subArb.busqueda(n, min, max));
            }
            
        }
        
        return res;
    }
    
    public NHoja<E,V> busqAux(E llave){
        int i=0;
        while(i<k.size() && llave.compareTo(k.get(i))>0)
            i++;
        return b.get(i).busqAux(llave);
        
    }
    
    public void dividir(){ 
        int cant=k.size()/2;
        ArrayList<E> knuevo=(ArrayList<E>)k.subList(cant+1, k.size());
        E llave=k.get(cant);
        k.removeAll(knuevo);
        k.remove(llave);
        ArrayList<Nodo<E,V>> bnuevo=(ArrayList<Nodo<E,V>>)b.subList(cant+1, b.size());
        b.removeAll(bnuevo);
        NInterno<E,V> nuevoN= new NInterno<E, V>(knuevo, bnuevo, 0, null, padre,orden);
        
        ArrayList<E> listak;
        HashMap<ArrayList<E>,HTree<E,V>> lnuevo=new HashMap<ArrayList<E>,HTree<E,V>>();
        HashMap<ArrayList<E>,HTree<E,V>> lpadre;
        int pos=-1;
        
        if(padre==null){
            listak= new ArrayList<E>();
            listak.add(llave);
            
            lpadre=new HashMap<ArrayList<E>,HTree<E,V>>();
            
        }else{
            listak= padre.getK();
            int i=0;
            while(i<listak.size() && llave.compareTo(listak.get(i))<0)
                i++;
            if(i==listak.size() && llave.compareTo(listak.get(i-1))>0){
                listak.add(llave);
            }else{
                pos=i;
                listak.add(pos, llave);
                }    
            
            lpadre=padre.getL();
            
        }
        for (Map.Entry<ArrayList<E>, HTree<E, V>> entrySet : l.entrySet()) {
                ArrayList<E> key = entrySet.getKey();
                HTree<E, V> value = entrySet.getValue();
                if(key.get(0).compareTo(llave)>0){
                    lnuevo.put(key, value);
                    l.remove(key);
                }else if(key.get(1).compareTo(llave)>0){
                    lpadre.put(key, value);
                    l.remove(key);
                }
            }
            
        cantL=l.size();

        nuevoN.setL(lnuevo);
        nuevoN.setCantL(lnuevo.size());
            
        ArrayList<Nodo<E,V>> listab;
        if(padre==null){
            listab=new ArrayList<Nodo<E, V>>();
            listab.add(this);
            listab.add(nuevoN);
            padre= new NInterno<E, V>(listak, listab, lpadre.size(), lpadre,null,orden); 
        }else{
            listab=padre.getB();
            if(pos==-1)
                listab.add(nuevoN);
            else
                listab.add(pos+1, nuevoN);
            padre= new NInterno<E, V>(listak, listab, lpadre.size(), lpadre,padre.getPadre(),orden); 
        }
        
    }
    
    
    public HTree<E,V> buscarHtree(int id){
        HTree<E,V> h;
        if(cantL!=0){
            Object[] llaves=l.keySet().toArray();
            for (Object llave : llaves) {
                ArrayList<E> clave = (ArrayList<E>) llave;
                h=l.get(clave);
                if(h.getId()==id)
                    return h;
            }
        }
        
        Nodo<E,V> n;
        NInterno<E,V> ni;
        for (Nodo<E, V> b1 : b) {
            n = b1;
            if(n instanceof NInterno){
                ni=(NInterno<E, V>)n;
                h=ni.buscarHtree(id);
                if(h!=null)
                    return h;
            }
        }
        return null;
    }
    
    public boolean isOverflow() {
        return k.size() > orden;
    }

    public boolean isUnderflow() {
        return k.size() < orden / 2;
    }

    @Override
    public void merge() {
        if (padre == null) {//es el nodo raiz

        } else {
            int indexB = padre.getB().indexOf(this);
            NInterno<E, V> left_aspirant = (indexB - 1 >= 0) ? (NInterno<E, V>) padre.getB().get(indexB - 1) : null;
            NInterno<E, V> right_aspirant = (padre.getB().size() > indexB + 1) ? (NInterno<E, V>) padre.getB().get(indexB + 1) : null;

            int leftvalue = (left_aspirant != null) ? left_aspirant.getK().size() : Integer.MAX_VALUE;
            int rightvalue = (right_aspirant != null) ? right_aspirant.getK().size() : Integer.MAX_VALUE;

            ArrayList<E> aspirant_K;
            ArrayList<Nodo<E, V>> aspirant_B;
            HashMap<ArrayList<E>, HTree<E, V>> aspirant_L;
            E key;

            if (k.size() + leftvalue < k.size() + rightvalue) {
                // left_aspirant

                aspirant_K = left_aspirant.getK();
                Object[] toArray = aspirant_K.toArray();
                key = (E) toArray[toArray.length - 1];//la llave menor del nodo izquierdo que voy a unir

                aspirant_B = left_aspirant.getB();
                aspirant_L = left_aspirant.getL();
                cantL += left_aspirant.getCantL();
                
                if (aspirant_B != null) {
                    b.addAll(0,aspirant_B);
                }
                if (aspirant_L != null) {
                    l.putAll(aspirant_L);
                }

                int index = 0;//index 
                while (index <= padre.getK().size() && key.compareTo(padre.getK().get(index)) > 0) {
                    index++;
                }
                
                if (aspirant_K != null) {
                    k.add(0,padre.getK().get(index));
                    k.addAll(0,aspirant_K);
                    
                }
                padre.k.remove(index);//elimino la llave
                padre.b.remove(index);//elimino la referencia al hijo que mescle

                //if overflow occur in leaf node, split the node
                if (isOverflow()) {//overflow
                    dividir();
                }
                //if overflow occur in parent node, split the node
                if (padre.isUnderflow()) {
                    padre.merge();
                }
            } else if (k.size() + leftvalue > k.size() + rightvalue) {
                // right_aspirant
                Object[] toArray = k.toArray();
                key = (E) toArray[toArray.length - 1];//la llave menor del nodo

                aspirant_K = right_aspirant.getK();
                aspirant_B = right_aspirant.getB();
                aspirant_L = right_aspirant.getL();
                cantL += right_aspirant.getCantL();
                
                if (aspirant_B != null) {
                    b.addAll(aspirant_B);
                }
                if (aspirant_L != null) {
                    l.putAll(aspirant_L);
                }

                int index = 0;//index 
                while (index <= padre.getK().size() && key.compareTo(padre.getK().get(index)) > 0) {
                    index++;
                }

                if (aspirant_K != null) {
                    k.add(padre.getK().get(index));
                    k.addAll(aspirant_K);
                }
                padre.k.remove(index);//elimino la llave
                padre.b.remove(index + 1);//elimino la referencia al hijo que mescle

                //if overflow occur in leaf node, split the node
                if (isOverflow()) {//overflow
                    dividir();
                }
                //if overflow occur in parent node, split the node
                if (padre.isUnderflow()) {
                    padre.merge();
                }
            }else{
              //no tiene hermanos
            }
        }
    }
}

