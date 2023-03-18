package multitaks.interfaces;

/**
 *
 * @author dogi_
 */

public interface ActionOneByOne<T>{
    
    public void set(T item);
    public T get();
    public void remove();
    
}
