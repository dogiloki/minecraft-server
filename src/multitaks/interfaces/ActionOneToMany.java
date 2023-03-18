package multitaks.interfaces;

import java.util.List;

/**
 *
 * @author dogi_
 */

public interface ActionOneToMany<T>{
    
    public void add(T item);
    public void set(List<T> items);
    public List<T> get();
    public void remove();
    public boolean exists(T item);
    
}
