package cu.edu.uclv.htree;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author yuniel acosta, anakarla sotolongo, elvis hernandez y danay serrano
 * Created in 18/9/2018 09:24
 * @project htree
 **/
public class HTree <E extends Comparable<E>,V> {
    
    private NInterno<E,V> raiz;
    private int orden;
    private int id;

    public HTree(NInterno<E, V> raiz, int orden, int id) {
        this.raiz = raiz;
        this.orden = orden;
        this.id=id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    public ArrayList<V> busqueda( Nodo<E, V> n, E min, E max){
     
     ArrayList<V> res= new ArrayList<V>();   
     if(n==null)
        return res;
     res.addAll(n.busqueda(min,max));
     return res;
     
    } 
    
    public NHoja<E,V> busqAux(NInterno<E,V> n, E llave){
        return n.busqAux(llave);
    }
    
    public void insertar(E llave, V valor){
        if(raiz==null){
            ArrayList<E> lc=new ArrayList<E>();
            lc.add(llave);
            raiz=new NInterno<E, V>(lc, null, 0, null, null,orden);
            
            ArrayList<V> lv=new ArrayList<V>();
            lv.add(valor);
            HashMap<E, ArrayList<V>> hm=new HashMap<E, ArrayList<V>>();
            hm.put(llave, lv);
            NHoja<E,V> hijo= new NHoja<E, V>(hm, null,raiz,orden);
            
            ArrayList<Nodo<E, V>>lh=new ArrayList<Nodo<E, V>>();
            lh.add(hijo);
            raiz.setB(lh);
            
            
        }else{
            NInterno<E,V> n=raiz;
            while(n.getPadre()!=null)
                n=n.getPadre();
            NHoja<E,V> nh=busqAux(n, llave);
            HashMap<E,ArrayList<V>> th=nh.getValores();   
            ArrayList<V> l;
            if(th.containsKey(llave))
                l=th.get(llave);
            else
               l=new ArrayList<V>();
            l.add(valor);
            th.put(llave, l);
            nh.setValores(th);
            if(th.size()>orden)
                nh.dividir();
        }
    }

    /**
     *
     * @param valueToDelete indexed value to delete
     * @param key  index to delete from.
     *             Algorithm Delete
     *
     *      (1)    Traverse index H to the leaf node that contains the value valueToDelete.
     *             Let the leaf node be cnode. Delete the oids and remove the indexed entry
     *             with K = valueToDelete if its P is empty.
     *      (2)    If the deletion is to remove all the indexed value valueToDelete in H
     *             and its nested indexes, search through the nested entries as in the
     *             algorithm SEARCH to delete all the indexed values wit K = valueToDelete.
     *      (3)    If cnode underflows after removing the entry: Merge it with Its sibling node
     *             NODEsibling. Let the resultant node be cnode. Redistribute the entries among
     *             cnode and NODEsibling if overflow occurs.
     *      (4)  (i) If resplit occurs,
     *                  if there are L(NODEsibling) and L(cnode) in its superclass’s H-tree,
     *                  a simple readjustment is enough; check if they are required to be moved
     *                  up or down.
     *                If there exists only one link, say L(cnode), in N of Hsuperclass, then
     *                use Nest to re-nest cnode in N.
     *                Check also if the L links in both nodes need to be readjusted
     *            (ii) Otherwise, if there are L(NODEsibling) and L(cnode) in its
     *                 superclass’s H-tree, among these two nodes, put L(cnode) in the node whose
     *                 range provides better coverage of the range of cnode. Delete old L(NODEsibling)
     *                 and L(cnode). Check if cnode is covered properly; if not, move the L(cnode) up.
     *              If there is only one link, say L(cnode), use the Nest algorithm to re-nest
     *              all child nodes of cnode to the node that contains L(cnode).
     *              Check the parent node if there are any links to a subclass that could be
     *              pushed down to cnode.
     *        (5)   If a node is deleted, the corresponding entry in the parent node must be
     *              deleted. If the parent node is the root node and has one entry after the
     *              deletion, make its child the new root. If it is not the root and the node
     *              under flows, repeat step 3 with parent node as cnode.
     *
     */
    public void delete(E key, V valueToDelete) {
        NHoja<E, V> cnode = busqAux(raiz, key);
        HashMap<E, ArrayList<V>> valores = cnode.getValores();
        if (valores != null) {
            ArrayList<V> oids = valores.get(key);
            if (oids != null) {
                oids.remove(valueToDelete);
                if (oids.isEmpty()) {
                    valores.remove(key);
                }
            } else {
                valores.remove(key);
            }
            if (valores.isEmpty()) {//si no hay mas valores eliminar el nodo
                NInterno<E, V> padre = cnode.getPadre();
                padre.getB().remove(cnode);
                //en padre eliminar k
                if (padre.isUnderflow()) {//underflows
                    padre.merge();
                }
            } else if (cnode.isUnderflow()) { //underflows
                cnode.merge();
            }
        }
    }
    
    public void insertar(E llave, V valor, HTree<E,V> ht){
        int ident=ht.getId();
        if(id==ident)
            insertar(llave, valor);
        else{
            NInterno<E,V> n=raiz;
            HTree<E,V> h= n.buscarHtree(ident);
            if(h!=null)
                h.insertar(llave, valor);
            else
                insertar(llave, valor);
        }
    
    }
    
    public void delete(E key, V valueToDelete, HTree<E,V> ht){
        int ident=ht.getId();
        //eliminar en todos los htrees anidados
        if(id==ident)
            delete(key, valueToDelete);
        else{
            NInterno<E,V> node=raiz;
            HTree<E,V> htree= node.buscarHtree(ident);
            if(htree!=null)
                htree.delete(key, valueToDelete);
            else
               delete(key, valueToDelete);
        }
    }

}