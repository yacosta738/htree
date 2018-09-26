/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.uclv.htree;

import java.util.ArrayList;
import java.util.HashMap;
/**
 *
 * @author anakarla
 */
public class NHoja <E,V> extends Nodo<E,V> {
    private HashMap<E,ArrayList<V>> valores;
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
    
    
    
    
}
