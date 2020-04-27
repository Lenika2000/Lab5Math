import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class NewtonPolynomialTest {

    @Test
    public void unequalNodesMethod() {
        double [] X = {0.15,0.2,0.33,0.47,0.62};
        double [] Y={1.25,2.38,3.79,5.44,7.14};
        double x = 0.7;
        NewtonPolynomial newtonPolynomial = new NewtonPolynomial(X,Y,x);
        double expected = 0;
        try {
            expected = newtonPolynomial.getAnswer();
        } catch (MethodException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(expected,2.66492, 0.0001);
    }

    @Test
    public void equallySpacedNodesMethodTheory() {
        //почему в таблице конечных разностей функции лишняя строчка???
        double[] X = {0.1, 0.2, 0.3, 0.4, 0.5};
        double[] Y = {1.25, 2.38, 3.79, 5.44, 7.14};
        double x = 0.22;
        NewtonPolynomial newtonPolynomial = new NewtonPolynomial(X, Y, x);
        double expected = 0;
        try {
            expected = newtonPolynomial.getAnswer();
        } catch (MethodException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(expected, 2.63368, 0.0001);
    }



    @Test
    public void equallySpacedNodesMethod() {
        //равностоящие узлы из практики лабы
        double [] X1 = {0.5,0.55,0.6,0.65,0.7,0.75,0.8};
        double [] Y1={1.532,2.5356,3.5406,4.5462,5.5504,6.5559,7.5594};
        double x1 = 0.523;
        NewtonPolynomial newtonPolynomial1 = new NewtonPolynomial(X1,Y1,x1);
        double expected1 = 0;
        try {
            expected1 = newtonPolynomial1.getAnswer();
        } catch (MethodException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(expected1,1.995279, 0.01);
    }


    @Test
    public void equallySpacedNodesMethodBackInterpolation() {
        //равностоящие узлы из практики лабы
        double [] X1 = {0.5,0.55,0.6,0.65,0.7,0.75,0.8};
        double [] Y1={1.532,2.5356,3.5406,4.5462,5.5504,6.5559,7.5594};
        double x1 = 0.759;
        NewtonPolynomial newtonPolynomial1 = new NewtonPolynomial(X1,Y1,x1);
        double expected1 = 0;
        try {
            expected1 = newtonPolynomial1.getAnswer();
        } catch (MethodException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(expected1,6.737092, 0.01);
    }



}