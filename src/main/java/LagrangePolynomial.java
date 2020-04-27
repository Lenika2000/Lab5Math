public class LagrangePolynomial {
    private double[] X;
    private double[] Y;
    private double x;

    public LagrangePolynomial(double[] X, double[] Y, double x) {
        this.X = X;
        this.Y = Y;
        this.x = x;
    }

    public double getAnswer() {
        double y = 0;
        for (int i = 0; i < X.length; i++) {
            double l = 1;
            for (int j = 0; j < X.length; j++) {
                if (i != j) {
                    l *= (x - X[j]) / (X[i] - X[j]);
                }
            }
            y += l * Y[i];
        }
        System.out.printf("Многочлен Лагранжа.Приближенное значение функции y=f(x) при x=%.4f для заданной таблицы %f\n", x, y);
        return y;
    }
}
