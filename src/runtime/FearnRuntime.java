public class FearnRuntime {
    
    public static Boolean equals(Object op1, Object op2)
    {
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
    
    public static void print(Object a)
    {
        System.out.println(a);
    }




}
