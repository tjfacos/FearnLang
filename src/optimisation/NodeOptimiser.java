package optimisation;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.tree.*;

public class NodeOptimiser {

    public void Optimise(MethodNode node)
    {
        for (int i = 0; i < node.instructions.size(); i++)
        {
            AbstractInsnNode current_insn = node.instructions.get(i);
            
            // Check for redundant int casting
            if (current_insn.getOpcode() == INVOKESTATIC && ((MethodInsnNode)current_insn).desc.equals("(I)Ljava/lang/Integer;")) {
                    AbstractInsnNode next_insn = node.instructions.get(i + 1);
                    if (next_insn.getOpcode() == INVOKEVIRTUAL && ((MethodInsnNode)next_insn).desc.equals("()I"))
                    {
                        // Remove both instructions
                        node.instructions.remove(current_insn);
                        node.instructions.remove(next_insn);

                        // Decrement index to stay at same location
                        i--;
                    }
            }

            // Check for redundant bool casting
            if (current_insn.getOpcode() == INVOKESTATIC && ((MethodInsnNode)current_insn).desc.equals("(Z)Ljava/lang/Boolean;")) {
                    AbstractInsnNode next_insn = node.instructions.get(i + 1);
                    if (next_insn.getOpcode() == INVOKEVIRTUAL && ((MethodInsnNode)next_insn).desc.equals("()Z"))
                    {
                        // Remove both instructions
                        node.instructions.remove(current_insn);
                        node.instructions.remove(next_insn);

                        // Decrement index to stay at same location
                        i--;
                    }
            }

            // Check for redundant double casting
            if (current_insn.getOpcode() == INVOKESTATIC && ((MethodInsnNode)current_insn).desc.equals("(D)Ljava/lang/Double;")) {
                    AbstractInsnNode next_insn = node.instructions.get(i + 1);
                    if (next_insn.getOpcode() == INVOKEVIRTUAL && ((MethodInsnNode)next_insn).desc.equals("()D"))
                    {
                        // Remove both instructions
                        node.instructions.remove(current_insn);
                        node.instructions.remove(next_insn);

                        // Decrement index to stay at same location
                        i--;
                    }
            }

        }

    }
}
