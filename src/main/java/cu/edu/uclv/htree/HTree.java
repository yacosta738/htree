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

    /**
     *
     * @param valueToDelete indexed value to delete
     * @param indexH  index to delete from.
     * @param oids list of objects to be deleted
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
    public void delete(V valueToDelete,E indexH,HashMap<E,ArrayList<V>> oids){

    }

}