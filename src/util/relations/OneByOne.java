package util.relations;

import interfaces.ActionRelation;

/**
 *
 * @author dogi_
 */

public class OneByOne<T> implements ActionRelation<T>{
    
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
