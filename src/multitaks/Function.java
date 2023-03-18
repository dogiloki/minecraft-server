package multitaks;

import java.awt.Dimension;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.text.DecimalFormat;

/**
 *
 * @author dogi_
 */

public class Function<T>{
    
    //  Convertir bytes en KB, MB, GB y TB
    public static String convertSize(long bytes){
        double kilobytes=bytes/Math.pow(1024,1);
        double megas=bytes/Math.pow(1024,2);
        double gigas=bytes/Math.pow(1024,3);
        double teras=bytes/Math.pow(1024,4);
        if(kilobytes<1024){
            return new DecimalFormat("#").format(kilobytes)+" KB";
        }else
        if(megas<1024){
            return new DecimalFormat("#.##").format(megas)+" MB";
        }else
        if(gigas<1024){
            return new DecimalFormat("#.##").format(gigas)+" GB";
        }else
        if(teras<1024){
            return new DecimalFormat("#.##").format(teras)+" TB";
        }else
        return "";
    }
    
    // Asignar el primer valor diferente a nulo, dentro de un array
    public T set(T... values){
        for(T value:values){
            if(value!=null){
                return value;
            }
        }
        return null;
    }
    
    public static <T> T assign(T... values){
        for(T value:values){
            if(value!=null){
                if(!value.equals("")){
                    return value;
                }
            }
        }
        return null;
    }
    
    public static <T> T assignNotNull(T... values){
        for(T value:values){
            if(value!=null){
                return value;
            }
        }
        return null;
    }
    
    // Crea un array Method
    public static Method[] createdArray(Method... args){
        return args;
    }
    // Crea un array de tipo texto o caracter, según la cantidad de parametros
    public static String[] createdArray(String... args){
        return args;
    }
    // Crea un array de tipo entero, según la cantidad de parametros
    public static int[] createdArray(int... args){
        return args;
    }
    // Crea un array de tipo decimal, según la cantidad de parametros
    public static float[] createdArray(float... args){
        return args;
    }
    // Crea un array de tipo decimal grande, según la cantidad de parametros
    public static double[] createdArray(double... args){
        return args;
    }
    
    // Convertir dos parámetro numéricos en dimenciones
    public static Dimension createDimencion(float ancho, float alto){
        return new Dimension((int)ancho,(int)alto);
    }
    
    // Obtener ip privada
    public static String getIp(){
        try{
            InetAddress address=InetAddress.getLocalHost();
            return address.getHostAddress();
        }catch(Exception ex){
            System.out.println(ex);
            return "";
        }
    }
    public static String getIp(String ip){
        try{
            InetAddress address=InetAddress.getByName(ip);
            return address.getHostAddress();
        }catch(Exception ex){
            System.out.println(ex);
            return "";
        }
    }
    public static String getHost(){
        try{
            InetAddress address=InetAddress.getLocalHost();
            return address.getHostName();
        }catch(Exception ex){
            System.out.println(ex);
            return "";
        }
    }
    public static String getHost(String host){
        try{
            InetAddress address=InetAddress.getByName(host);
            return address.getHostName();
        }catch(Exception ex){
            System.out.println(ex);
            return "";
        }
    }
    
}
