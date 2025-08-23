package test;

import com.dogiloki.minecraftserver.core.exceptions.InstanceSaveException;

/**
 *
 * @author _dogi
 */

public class Test{
    
    public static void main(String[] args){
        try{
            throw new InstanceSaveException();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
}
