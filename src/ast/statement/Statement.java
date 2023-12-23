package ast.statement;

import java.util.UUID;

import ast.ASTNode;

public abstract class Statement extends ASTNode {

    String ID = UUID.randomUUID().toString();
    @Override public abstract String toString();

}
