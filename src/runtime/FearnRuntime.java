import java.util.Arrays;

public class FearnRuntime {
    
    public static Boolean equals(Object op1, Object op2)
    {
        if (op1 == null && op2 == null)
        {
            return true;
        }

        if (op1.getClass().isArray())
        {
            Object[] arr1 = (Object[])op1;
            Object[] arr2 = (Object[])op2;

            Boolean areEqual = arr1.length == arr2.length;

            for (int i = 0; i < arr1.length; i++)
            {
                areEqual = areEqual && equals(arr1[i], arr2[i]);
            }

            return areEqual;
            
        }

        return op1.equals(op2);
    }
    
    // Maths functions
    public static Integer exp(int op1, int op2)
    {
        Double d = Math.pow((double)op1, (double)op2);
        return d.intValue();
    }

    public static Double exp(double op1, double op2)
    {
        Double d = Math.pow((double)op1, (double)op2);
        return d;
    }

    // Boolean functions
    public static Boolean less(int op1, int op2)
    {
        return op1 < op2;
    }

    public static Boolean less(double op1, double op2)
    {
        return op1 < op2;
    }


    public static Boolean less_eq(int op1, int op2)
    {
        return op1 <= op2;
    }

    public static Boolean less_eq(double op1, double op2)
    {
        return op1 <= op2;
    }
    public static Boolean greater(int op1, int op2)
    {
        return op1 > op2;
    }

    public static Boolean greater(double op1, double op2)
    {
        return op1 > op2;
    }
    public static Boolean greater_eq(int op1, int op2)
    {
        return op1 >= op2;
    }

    public static Boolean greater_eq(double op1, double op2)
    {
        return op1 >= op2;
    }
    
    

    public static Boolean not(Boolean op)
    {
        return !op;
    }
    
    public static Boolean and(Boolean a, Boolean b)
    {
        return a && b;
    }

    public static Boolean or(Boolean a, Boolean b)
    {
        return a || b;
    }


    
    public static String concat(String a, String b)
    {
        return a + b;
    }

    public static Boolean Obj2B(Object o)
    {
        return !(o.equals(0) || o.equals(0.0));
    }

    public static String Obj2Str(Object o)
    {
        if (o.getClass().isArray())
        {
            return Arrays.deepToString((Object[])o);
        } else {
            return o.toString();
        }
    }



    // Sequence 
    public static Integer length(Object o)
    {
        if (o instanceof String)
        {
            return String.valueOf(o).length();
        }

        return ((Object[])o).length;
    }

    
    
    public static String slice(String seq, Integer start, Integer end)
    {
        return seq.substring(start, end);
    }

    public static <T> T[] slice(T[] seq, Integer start, Integer end)
    {
        return Arrays.copyOfRange(seq, start, end);
    }



}
