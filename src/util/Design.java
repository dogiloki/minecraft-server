package util;

import java.awt.Component;
import java.awt.Rectangle;

public class Design{
    
    public static void margen(Component content, float size){
        Rectangle bounds=content.getBounds();
        int x=bounds.x+(int)(size/2);
        int y=bounds.y+(int)(size/2);
        int ancho=bounds.width-(int)size;
        int alto=bounds.height-(int)size;
        content.setBounds(x,y,ancho,alto);
    }
    
}
