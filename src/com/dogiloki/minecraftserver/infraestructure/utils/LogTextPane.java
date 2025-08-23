package com.dogiloki.minecraftserver.infraestructure.utils;

import com.dogiloki.multitaks.Function;
import com.dogiloki.multitaks.logger.LogEntry;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 *
 * @author _dogi
 * @param <T>
 */

public class LogTextPane<T extends LogEntry> extends JTextPane{
    
    private StyleContext context;
    private StyledDocument doc;
    private Style style;
    private Integer line=null;
    private Color color=null;
    
    public LogTextPane(){
        super();
        super.setEditable(false);
        this.context=new StyleContext();
        this.doc=this.getStyledDocument();
        this.style=this.context.addStyle("colored",null);
        this.setEditable(false);
        this.setBackground(new Color(245,245,245));
    }
    
    public LogTextPane line(Integer line){
        this.line=line;
        return this;
    }
    
    public LogTextPane color(Color color){
        this.color=color;
        return this;
    }
    
    public boolean insert(LogEntry entry){
        return this._insert(entry);
    }
    
    public boolean insert(LogEntry entry, Color color){
        this.color(color);
        return this._insert(entry);
    }
    
    public boolean insertLine(Integer line, LogEntry entry){
        this.line(line);
        return this._insert(entry);
    }
    
    public boolean insertLine(Integer line, LogEntry entry, Color color){
        this.line(line);
        this.color(color);
        return this._insert(entry);
    }
    
    public String time(){
        return Function.getDateTime();
    }
    
    private boolean _insert(LogEntry entry){
        StyleConstants.setForeground(this.style,Function.set(this.color,entry.type().getColor()));
        try{
            // Insertar al final si no se especifica línea
            int pos=Function.set(this.line,this.doc.getLength());
            this.doc.insertString(pos,entry.toString()+"\n",this.style);
            this.setCaretPosition(this.doc.getLength());
            this.line(null);
            this.color(null);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    
}
