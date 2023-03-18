package multitaks.relations;

import java.util.List;
import multitaks.interfaces.ActionOneToMany;
import java.util.ArrayList;

/**
 *
 * @author dogi_
 */

public class OneToMany<T> implements ActionOneToMany<T>{
 
    List<T> items=null;
    
    @Override
    public void add(T item){
        if(this.items==null){
            this.items=new ArrayList<>();
        }
        this.items.add(item);
    }
    
    @Override
    public void set(List<T> items){
        this.items=items;
    }
    
    @Override
    public List<T> get(){
        return this.items;
    }
    
    @Override
    public void remove(){
        this.items=null;
    }
    
    @Override
    public boolean exists(T item){
        return this.items.contains(item);
    }

}
