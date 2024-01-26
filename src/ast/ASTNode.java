package ast;

import static org.assertj.core.api.Assertions.*;

public abstract class ASTNode {

    @Override
    public boolean equals(Object o) {
        
        try {
            assertThat(this).usingRecursiveComparison().isEqualTo(o);
        } catch (AssertionError e) {
            return false;
        }

        return true;

    }

    public abstract String toString();
}
