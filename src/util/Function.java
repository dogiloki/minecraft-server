package util;

import java.awt.Dimension;
import java.text.DecimalFormat;

public class Function{
    
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
    
    // Búscar el primer caracter en texto
    public static int searchCaracter(String texto, String search){
        for(int a=0; a<texto.length(); a++){
            if(texto.substring(a,a+1).equals(search)){
                return a;
            }
        }
        return -1;
    }
    
    // Convertir dos parámetro numéricos en dimenciones
    public static Dimension createDimencion(float ancho, float alto){
        return new Dimension((int)ancho,(int)alto);
    }
    
}
