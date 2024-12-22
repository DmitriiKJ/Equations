package equations;

import java.lang.reflect.Array;
import java.util.*;

public class Equations {
    // рівняння виду ax + by = c + k
    public static void solveEquationWithParameter(double a, double b, double c, double k){
        if(b == 0 && a == 0){
            if(c + k == 0){
                System.out.println("Значення x та y можуть бути будь-якими");
            }
            else{
                System.out.println("Некоректні дані");
            }
            return;
        }

        if(b == 0){
            System.out.println("x: " + ((c + k)/a) + ", y: будь-яке число");
        }

        if(a == 0){
            System.out.println("x: будь-яке число, y: " + ((c + k)/b));
        }

        System.out.println("Ось деякі пари значень для рівняння " + a + "x + " + b + "y = " + c + " + " + k);

        for (double x = 0; x < 10; x += 1){
            double y = (c + k - a * x) / b;
            System.out.println("(x: " + x + ", y: " + y + ")");
        }
    }

    // рівняння виду ax + b > c
    public static void solveInequality(double a, double b, double c){
        if(a == 0){
            if(b == c){
                System.out.println("x: будь-яке число");
            }
            else{
                System.out.println("Некоректні дані");
            }
            return;
        }

        System.out.println("рішення рівняння " + a + "x + " + b + " > " + c);
        System.out.println("x " + (a < 0 ? "< " : "> ") + ((c - b) / a));
    }

    // рівняння виду ax + by = c
    public static void solveTwoVariableEquation(double a, double b, double c){
        solveEquationWithParameter(a, b, c, 0);
    }

    // для системи рівнянь
    // a1x + b1y + c1z = d1
    // a2x + b2y + c2z = d2
    // a3x + b3y + c3z = d3
    public static void solveThreeVariableSystem(double a1, double b1, double c1, double a2, double b2, double c2, double a3, double b3, double c3, double d1, double d2, double d3){
        double determinant = determinantForSizeTree(a1, b1, c1, a2, b2, c2, a3, b3, c3);
        if(determinant != 0){
            kramarMethod(a1, b1, c1, a2, b2, c2, a3, b3, c3, d1, d2, d3);
        }
        else{
            compatibleIncompatible(a1, b1, c1, a2, b2, c2, a3, b3, c3, d1, d2, d3);
        }
    }

    // Метод Крамера для системи рівнянь
    private static void kramarMethod(double a1, double b1, double c1, double a2, double b2, double c2, double a3, double b3, double c3, double d1, double d2, double d3){
        double determinant = determinantForSizeTree(a1, b1, c1, a2, b2, c2, a3, b3, c3);
        double determinantX = determinantForSizeTree(d1, b1, c1, d2, b2, c2, d3, b3, c3);
        double determinantY = determinantForSizeTree(a1, d1, c1, a2, d2, c2, a3, d3, c3);
        double determinantZ = determinantForSizeTree(a1, b1, d1, a2, b2, d2, a3, b3, d3);

        System.out.println("X: " + determinantX/determinant + ", Y: " + determinantY/determinant + ", Z: " + determinantZ/determinant);
    }

    private static void compatibleIncompatible(double a1, double b1, double c1, double a2, double b2, double c2, double a3, double b3, double c3, double d1, double d2, double d3) {
        // знаходження рангу матриці
        final int rows = 3, cols = 4;
        double[][] matr = {{a1, b1, c1, d1}, {a2, b2, c2, d2}, {a3, b3, c3, d3}};
        for (int i = 0; i < rows; i++) {
            // Знаходимо ведучий елемент
            int lead = i;
            while (lead < cols && matr[i][lead] == 0) {
                lead++;
            }

            if (lead == cols) {
                continue; // Усі елементи в цьому рядку дорівнюють 0
            }

            double leadValue = matr[i][lead];
            for (int j = lead; j < cols; j++) {
                matr[i][j] /= leadValue;
            }

            for (int k = i + 1; k < rows; k++) {
                double factor = matr[k][lead];
                for (int j = lead; j < cols; j++) {
                    matr[k][j] -= factor * matr[i][j];
                }
            }
        }

//        for (int i = 0; i < rows; i++){
//            for (int j = 0; j < cols; j++){
//                System.out.printf(Double.toString(matr[i][j]) + " ");
//            }
//        }

        // ранг звичайної матриці та розширеної (тобто з коефіціентами що йдуть після =)
        int rank = 0, rankExpanded = 0;
        for (int i = 0; i < rows; i++) {
            boolean forRank = false;
            boolean forRankExpanded = false;

            for (int j = 0; j < cols; j++) {
                if (matr[i][j] != 0) {
                    if (j < cols - 1) {
                        forRank = true;
                        forRankExpanded = true;
                    } else { // у випадку якщо лише у матриці розширення є ненульовий елемент
                        forRankExpanded = true;
                    }
                    break;
                }
            }

            if (forRank) rank++;
            if (forRankExpanded) rankExpanded++;
        }

        if (rank != rankExpanded) {
            System.out.println("Система рівнянь не має розв'язків");
            return;
        }

        // Знайдемо базисний мінор (мінор визначник якого не дорівнює 0)
        int bas1 = -1, bas2 = -1; // 0 - x, 1 - y, 2 - z
        for (int i = 0; i < rows - 2; i++) {
            for (int j = 0; j < cols - 2; j++) {
                if(determinantForSizeTwo(matr[i][j], matr[i][j + 1], matr[i + 1][j], matr[i + 1][j + 1]) != 0){
                    bas1 = i;
                    bas2 = i + 1;
                    break;
                }
            }
            if(bas1 != -1 && bas2 != -1){
                break;
            }
        }

        // Маючи ступінчасту матрицю та базисний мінор можемо розв'язати рівняння

        String y = String.format("(%.2f - %.2fc) / %.2f", matr[1][3], matr[1][2], matr[1][1]);
        String x = String.format("(%.2f - %.2fc - %.2f * %s) / %.2f", matr[0][3], matr[0][2], matr[0][1], y, matr[0][0]);

        System.out.printf("Загальний розв'язок системи: (%s; %s; c), де c - константа (або z)\n", x, y);



    }

    // визначник для матриці типу
    // a1 b1
    // a2 b2
    public static double determinantForSizeTwo(double a1, double b1, double a2, double b2){
        return (a1 * b2) - (b1 * a2);
    }

    // визначник для матриці типу
    // a1 b1 c1
    // a2 b2 c2
    // a3 b3 c3
    public static double determinantForSizeTree(double a1, double b1, double c1, double a2, double b2, double c2, double a3, double b3, double c3){
        return (a1 * b2 * c3) + (a2 * b3 * c1) + (b1 * c2 * a3) - (c1 * b2 * a3) - (c2 * b3 * a1) - (b1 * a2 * c3);
    }

    // рівняння типу |ax + b| = c
    public static void solveAbsoluteValueEquation(double a, double b, double c){
        if(c < 0){
            System.out.println("Некоректні дані");
            return;
        }

        if(a == 0){
            if(b == 0){
                System.out.println("x: будь-яке число");
            }
            else{
                System.out.println("Некоректні дані");
            }
            return;
        }

        if(c == 0){
            System.out.println("x: " + (-b / a));
        }
        else{
            // Якщо маємо |f(x)| = c, то f(x) = c && f(x) = -c
            System.out.println("x1: " + ((c - b) / a) + ", x2: " + ((-c - b) / a));
        }
    }

    // Записувати через пробіл
    public static double calculateRPN(String expression){
        Stack<String> fields = new Stack<String>();
        ArrayList<String> arr = new ArrayList<String>();

        arr.addAll(Arrays.stream(expression.split(" ")).toList());

        Collections.reverse(arr);

        for (String str : arr){
            fields.push(str);
        }

        try {
            while (fields.size() != 1) {
                double first = Double.valueOf(fields.pop());
                double second = Double.valueOf(fields.pop());
                action(first, second, fields);
            }
        }
        catch (Exception e){
            System.out.println("Невірно заданий вираз");
            return 0;
        }

        return Double.valueOf(fields.pop());
    }

    private static void action(double first, double second, Stack<String> stack){
        String str = stack.pop();
        if(isAction(str)){
            double res;
            switch (str){
                case "-":
                    res = first - second;
                    break;
                case "*":
                    res = first * second;
                    break;
                case "/":
                    res = first / second;
                    break;
                case "%":
                    res = first % second;
                    break;
                case "^":
                    res = Math.pow(first, second);
                    break;
                default:
                    res = first + second;
                    break;
            }
            stack.push(Double.toString(res));
        }
        else
        {
            if(stack.size() == 0){
                throw new IllegalArgumentException("Невірно заданий вираз");
            }
            action(second, Double.valueOf(str), stack);
            stack.push(Double.toString(first));
        }

    }

    private static boolean isAction(String str){
        return (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/") || str.equals("%") || str.equals("^"));
    }

    // записувати через пробіл
    public static double evaluateExpressionWithList(String expression){
        try {
            return additionalForExpression(expression);
        }
        catch (Exception e) {
            System.out.println("Невірно заданий вираз");
            return 0;
        }
    }

    private static int findSecondBracket(String expression, int firstBracketIndex){
        int state = 0;
        String[] strs = expression.split(" ");
        for (int i = firstBracketIndex + 1; i < expression.length(); i++){
            if(strs[i].equals("(")) state++;
            else if (strs[i].equals(")")) state--;
            if(state == - 1){
                return i;
            }
        }
        return -1;
    }

    private static double additionalForExpression(String expression){
        ArrayList<String> list = new ArrayList<String>();

        list.addAll(Arrays.stream(expression.split(" ")).toList());

        if (list.contains("(")) {
            if (list.contains(")") && list.indexOf("(") < list.indexOf(")")) {
                String exp = "";
                int firstBr = list.indexOf("(");
                int secondBr = findSecondBracket(expression, firstBr);
                if (secondBr == -1){
                    throw new IllegalArgumentException("Невірно заданий вираз");
                }
                for (int i = firstBr + 1; i < secondBr; i++) {
                    exp += list.get(i) + " ";
                }

                double change = additionalForExpression(exp);
                int idx = list.indexOf("(");
                int idxl = findSecondBracket(expression, idx);
                list.set(idx, Double.toString(change));
                for (int i = idx + 1; i <= idxl; i++) {
                    list.remove(idx + 1);
                }
            } else {
                throw new IllegalArgumentException("Невірно заданий вираз");
            }
        } else if (list.contains(")")) {
            throw new IllegalArgumentException("Невірно заданий вираз");
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals("^")) {
                    doAction('^', i, list);
                    i--;
                }
            }
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals("*")) {
                    doAction('*', i, list);
                    i--;
                } else if (list.get(i).equals("/")) {
                    doAction('/', i, list);
                    i--;
                } else if (list.get(i).equals("%")) {
                    doAction('%', i, list);
                    i--;
                }
            }
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals("+")) {
                    doAction('+', i, list);
                    i--;
                } else if (list.get(i).equals("-")) {
                    doAction('-', i, list);
                    i--;
                }
            }
        }

        if (list.size() != 1) {
            String exp = "";
            for (int i = 0; i < list.size(); i++) {
                exp += list.get(i) + " ";
            }
            return additionalForExpression(exp);
        }
        return Double.valueOf(list.get(0));
    }

    private static void doAction(char action, int i, ArrayList<String> list){
        switch (action){
            case '^':
                list.set(i-1, Double.toString(Math.pow(Double.valueOf(list.get(i-1)), Double.valueOf(list.get(i+1)))));
                break;
            case '*':
                list.set(i - 1, Double.toString(Double.valueOf(list.get(i - 1)) * Double.valueOf(list.get(i + 1))));
                break;
            case '/':
                list.set(i - 1, Double.toString(Double.valueOf(list.get(i - 1)) / Double.valueOf(list.get(i + 1))));
                break;
            case '%':
                list.set(i - 1, Double.toString(Double.valueOf(list.get(i - 1)) % Double.valueOf(list.get(i + 1))));
                break;
            case '+':
                list.set(i - 1, Double.toString(Double.valueOf(list.get(i - 1)) + Double.valueOf(list.get(i + 1))));
                break;
            case '-':
                list.set(i - 1, Double.toString(Double.valueOf(list.get(i - 1)) - Double.valueOf(list.get(i + 1))));
                break;
        }

        list.remove(i + 1);
        list.remove(i);
    }
}
