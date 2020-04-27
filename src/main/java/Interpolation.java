import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Interpolation {
    private double[] X;
    private double[] Y;
    private double[][] data;
    private double x;
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));


    public Interpolation() {
        getData();
        getX();
        X = new double[data[0].length];
        Y = new double[data[0].length];
        for (int i = 0; i < data[0].length; i++) {
            X[i] = data[0][i];
            Y[i] = data[1][i];
        }
    }

    public void getData() {
        DataReceiver dataReceiver = new DataReceiver();
        data = dataReceiver.receiveData();
    }

    public void getX() {
        while (true) {
            System.out.println("Введите значение аргумента, для которого вычисляется приближенное значение функции");
            try {
                x = Double.parseDouble(in.readLine().replace(",", "."));
                return;
            } catch (IOException e) {
                System.out.println("При вводе х прозошла ошибка!");
            } catch (NumberFormatException e) {
                System.out.println("Введенное выражение не является числом!");
            }
        }
    }

    public void interpolation() {
        if (x < X[0] || x > X[X.length - 1]) {
            System.out.println("Внимание! Введенная точка выходит за пределы исследуемого интервала, указанного в таблице.");
        }
        LagrangePolynomial lagrangePolynomial = new LagrangePolynomial(X, Y, x);
        lagrangePolynomial.getAnswer();
        NewtonPolynomial newtonPolynomial = new NewtonPolynomial(X, Y, x);
        try {
            newtonPolynomial.getAnswer();
        } catch (MethodException e) {
            System.out.println(e.getMessage());
        }
    }

}
