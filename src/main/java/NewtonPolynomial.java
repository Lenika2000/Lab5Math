public class NewtonPolynomial {
    private double[] X;
    private double[] Y;
    private double x;
    private boolean isEquallySpacedNodes = true; //являются ли узлы равноотсттоящими
    double h;//расстояние между равноотстоящими узлами
    double[][] deltaY;

    public NewtonPolynomial(double[] X, double[] Y, double x) {
        this.X = X;
        this.Y = Y;
        this.x = x;
        int i = 2;
        h = X[1] - X[0];
        while (isEquallySpacedNodes && i < X.length) {
            isEquallySpacedNodes = Math.abs(X[i] - X[i - 1] - h) < 1.0e-10;
            i++;
        }
    }

    public double getAnswer() throws MethodException {
        if (isEquallySpacedNodes) {
            System.out.println("Интерполяционная формула Ньютона для равноотстоящих узлов.");
            return equallySpacedNodesMethod();
        } else
            return unequalNodesMethod();
    }

    public double unequalNodesMethod() throws MethodException {
        int x0_index = getX0_indexUnequalNodesMethod(); //индекс x0 узла
        double y1 = findSolutionUnequalNodesMethod(x0_index - 1);
        double y2 = findSolutionUnequalNodesMethod(x0_index);
        System.out.printf("\nИнтерполяционная формула Ньютона для неравноотстоящих узлов.\nПриближенное значение функции y=f(x) при x=%.4f для заданной таблицы %f", x, (y1 + y2) / 2);
        return (y1 + y2) / 2;
    }

    public int getX0_indexUnequalNodesMethod() throws MethodException {
        int x0_index = 0; //индекс x0 узла
        if (X.length < 3)
            throw new MethodException("В таблице менее трех узлов");
        if (x < X[1]) {
            return 1;
        }
        if (x > X[X.length - 1]) {
            return X.length - 3; //становимся на 3 элемент с конца
        }
        for (int i = 0; i < X.length; i++) {
            if (x < X[i]) {
                x0_index = i - 1;
                break;
            }
        }
        int ost = X.length - x0_index - 1;//сколько элементов в массиве осталось после x0_index
        if (ost == 0) {
            x0_index -= 2;
        } else if (ost == 1) {
            x0_index--;
        }
        return x0_index;
    }

    public double findSolutionUnequalNodesMethod(int x0_index) {
        double y;
        double f_x0x1_ = (Y[x0_index + 1] - Y[x0_index]) / (X[x0_index + 1] - X[x0_index]);
        double f_x1x2_ = (Y[x0_index + 2] - Y[x0_index + 1]) / (X[x0_index + 2] - X[x0_index + 1]);
        double f_x0x1x2_ = (f_x1x2_ - f_x0x1_) / (X[x0_index + 2] - X[x0_index]);
        y = Y[x0_index] + f_x0x1_ * (x - X[x0_index]) + f_x0x1x2_ * (x - X[x0_index]) * (x - X[x0_index + 1]);
        return y;
    }

    public double equallySpacedNodesMethod() throws MethodException {
        int x0_index = getX0_indexForEquallySpacedNodesMethod(); //индекс x0 узла
        double y;
        double t;
        if (x0_index > X.length / 2) {
            System.out.println("Интерполирование назад");
            //TODO интерполирование назад
            int xn_index = (x0_index == X.length - 1) ? X.length - 1 : x0_index + 1;
            getTable(0);
            t = (x - X[xn_index]) / h;
            y = Y[xn_index];
            double k = 0;
            double T = 1;
            for (int j = 0; j < xn_index; j++) {
                k = t - j;
                T = T * k / (j + 1);
                y += T * deltaY[xn_index - 1 - j][j];
            }
        } else {
            //интерполирование вперед
            System.out.println("Интерполирование вперед");
            getTable(x0_index);
            t = (x - X[x0_index]) / h;
            y = Y[x0_index];
            double k = 0;
            double T = 1;
            for (int j = 0; j < X.length - x0_index; j++) {
                k = t - j;
                T = T * k / (j + 1);
                y += T * deltaY[0][j];
            }
        }
        System.out.printf("Приближенное значение функции y=f(x) при x=%.4f для заданной таблицы %f", x, y);
        return y;
    }


    public int getX0_indexForEquallySpacedNodesMethod() throws MethodException {
        int x0_index = 0; //индекс x0 узла
        if (X.length < 2)
            throw new MethodException("В таблице менее двух узлов");
        if (x < X[1]) {
            return 0;
        }
        if (x > X[X.length - 1]) {
            return X.length - 1; //становимся на последний элемент
        }
        for (int i = 0; i < X.length; i++) {
            if (x < X[i]) {
                x0_index = i - 1;
                break;
            }
        }
        return x0_index;
    }


    //все хорошо
    public void getTable(int x0_index) {
        deltaY = new double[X.length - x0_index + 1][X.length - x0_index + 1];
        int n = X.length - 1; //максимальный индекс строк и столбцов
        //заполняем первую дельту y
        for (int j = x0_index + 1; j < X.length; j++) {
            deltaY[j - x0_index - 1][0] = Y[j] - Y[j - 1];
        }
        //заполнение таблицы конечных разностей функций
        for (int i = x0_index + 1; i < X.length - 1; i++) { //изменяется столбец
            for (int j = x0_index; j < n - 1; j++) { //изменяется строка
                deltaY[j - x0_index][i - x0_index] = deltaY[j - x0_index + 1][i - x0_index - 1] - deltaY[j - x0_index][i - x0_index - 1];
            }
            n--;//с продвижением вправо количество строк в столце уменьшается
        }
        //печать таблицы конечных разностей функций
        System.out.println("                   Таблица конечных разностей функции");
        System.out.println("---------------------------------------------------------");
        n = X.length - x0_index - 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) { //изменяется строка
                System.out.printf("%8.4f|", deltaY[i][j]);
            }
            System.out.println("\n---------------------------------------------------------");
        }
    }


}
