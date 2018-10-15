/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.uclv.htree;

import java.util.ArrayList;

/**
 *
 * @author anakarla
 */
public abstract class Nodo<E extends Comparable<E>,V> {
    protected NInterno<E,V> padre;
    protected int orden;

    public Nodo(NInterno<E, V> padre, int orden) {
        this.padre = padre;
        this.orden=orden;
    }
    
    public NInterno<E,V> getPadre() {
        return padre;
    }

    public void setPadre(NInterno<E,V> padre) {
        this.padre = padre;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
    
    
    abstract public ArrayList<V> busqueda(E min, E max);
    abstract public NHoja<E,V> busqAux(E llave);
    abstract public void dividir();
    abstract public void merge();
    
}
