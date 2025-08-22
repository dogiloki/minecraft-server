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
    private Integer default_line=0;
    private Color default_color=null;
    
    public LogTextPane(){
        super();
        super.setEditable(false);
        this.context=new StyleContext();
        this.doc=this.getStyledDocument();
        this.style=this.context.addStyle("colored",null);
        this.setEditable(false);
        this.setBackground(new Color(245,245,245));
    }
    
    public LogTextPane defaultLine(Integer line){
        this.default_line=line;
        return this;
    }
    
    public LogTextPane defaultColor(Color color){
        this.default_color=color;
        return this;
    }
    
    public LogTextPane line(Integer line){
        this.line=line;
        return this;
    }
    
    public Integer getLine(){
        return Function.set(this.line,this.default_line);
    }
    
    public boolean insert(LogEntry entry){
        return this._insert(entry);
    }
    
    public boolean insert(LogEntry entry, Color color){
        this.defaultColor(color);
        return this._insert(entry);
    }
    
    public boolean insertLine(Integer line, LogEntry entry){
        this.line(line);
        return this._insert(entry);
    }
    
    public boolean insertLine(Integer line, LogEntry entry, Color color){
        this.line(line);
        this.defaultColor(color);
        return this._insert(entry);
    }
    
    public String time(){
        return Function.getDateTime();
    }
    
    private boolean _insert(LogEntry entry){
        StyleConstants.setForeground(this.style,Function.set(this.default_color,entry.type().getColor()));
        try{
            this.doc.insertString(this.getLine(),entry.toString()+"\n",this.style);
            this.line(null);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    
}
