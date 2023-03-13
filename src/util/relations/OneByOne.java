package util.relations;

import interfaces.ActionRelations;

/**
 *
 * @author dogi_
 */

public class OneByOne<T> implements ActionRelations<T>{
    
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
