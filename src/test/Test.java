package test;

import com.dogiloki.minecraftserver.core.exceptions.InstanceSaveException;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author _dogi
 */

public class Test{
    
    public static void main(String[] args){
        try{
            URLClassLoader loader=new URLClassLoader(
                    new URL[]{
                        new File("E:\\Github\\Netbeans\\minecraft-server\\libraries\\forge\\1.21.8-58.0.6\\libraries\\net\\minecraftforge\\forge\\1.21.8-58.0.6\\forge-1.21.8-58.0.6-server.jar").toURI().toURL()
                    },
                    null
            );
            Class<?> clazz=Class.forName("net.minecraft.nbt.CompoundTag",true,loader);
            Object tag = clazz.getDeclaredConstructor().newInstance();
            System.out.println(tag);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
}
