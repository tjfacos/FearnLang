package ast.statement;

import java.util.ArrayList;

import ast.Declaration;

public class CompoundStatement extends Statement {
    public ArrayList<Declaration> declarations;
    public ArrayList<Statement> statements;

    public CompoundStatement(ArrayList<Declaration> decls, ArrayList<Statement> stmts)
    {
        declarations = decls;
        statements = stmts;
    }

    @Override public String toString()
    {
        return "BLOCK " + this.ID + " {\n\t" + declarations.toString() + "\n\t" + statements.toString() + "\n}";
    }
}
