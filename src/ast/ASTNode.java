package ast;

import util.Helper;

public abstract class ASTNode {

    @Override
    public boolean equals(Object o) {
        return Helper.equal(this, o);
    }
}
