import equations.Equations;

public class Main {
    public static void main(String[] args) {
        // Перевірка методу для знаходження визначника (при ручному розрахунку вийшло -11)
        System.out.println(Equations.determinantForSizeTree(4, 2, -1, 1, 2, 1, 0, 1, -1));
        System.out.println();

        Equations.solveEquationWithParameter(3, 1, 2, 0);
        System.out.println();

        Equations.solveInequality(-4, 2, 10);
        System.out.println();

        Equations.solveAbsoluteValueEquation(5, -10, 11);
        System.out.println();

        System.out.println(Equations.calculateRPN("6 5 + 12 56 2 + * 5 4 + ^ *"));
        System.out.println();

        // Неправильний вираз
        System.out.println(Equations.calculateRPN("5 *"));
        System.out.println();

        // При ручному розрахунку вийшло 8.75
        System.out.println(Equations.evaluateExpressionWithList("10 / ( 3 + 5 ) * 7"));
        System.out.println();

        // При ручному розрахунку вийшло 0.18
        System.out.println(Equations.evaluateExpressionWithList("10 / ( ( 3 + 5 ) * 7 )"));
        System.out.println();

        // При ручному розрахунку вийшло 23831
        System.out.println(Equations.evaluateExpressionWithList("3 * ( 2 + 5 * 3 ^ 2 ) * ( 8 + ( 2 + 3 ) ) ^ 2 + 8 % 3"));
        System.out.println();

        // При ручному розрахунку вийшло (1, -1, 2)
        Equations.solveThreeVariableSystem(4, 2, -1, 1, 2, 1, 0, 1, -1, 0, 1, -3);
        System.out.println();

        Equations.solveThreeVariableSystem(3, 1, 4, 2, 2, 3, 1, -1, 1, 1, 9, -8);
        System.out.println();

    }
}