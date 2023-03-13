package util.relations;

/**
 *
 * @author dogi_
 */

public class OneByOne<T>{
    
    T item=null;
    
    public void set(T item){
        this.item=item;
    }
    
    public T get(){
        return this.item;
    }
    
    public void remove(){
        this.item=null;
    }
    
}
