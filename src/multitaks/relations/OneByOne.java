package multitaks.relations;

import multitaks.interfaces.ActionOneByOne;

/**
 *
 * @author dogi_
 */

public class OneByOne<T> implements ActionOneByOne<T>{
    
    T item=null;
    
    @Override
    public void set(T item){
        this.item=item;
    }
    
    @Override
    public T get(){
        return this.item;
    }
    
    @Override
    public void remove(){
        this.item=null;
    }
    
}
