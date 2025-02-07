package io.github.tjfacos.fearnlang.semantics.table;

/* 
 * Row.java
 * 
 * Abstract super class of all rows that can exist with a symbol table
 * Includes:
    -> identifier - the symbol's name in the program
    -> descriptor - the symbol's type/method descriptor
    -> owner      - the program class the symbol belongs to
*/

public abstract class Row {
    
    public String identifier;
    public String descriptor;

    public String owner = null;

    public Row(String id)
    {
        identifier = id;
    }
}
