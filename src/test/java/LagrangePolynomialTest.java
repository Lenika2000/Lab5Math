import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class LagrangePolynomialTest {

    @Test
    public void getAnswer() {
        double [] X = {0.1,0.2,0.3,0.4,0.5};
        double [] Y={1.25,2.38,3.79,5.44,7.14};
        double x = 0.35;
        LagrangePolynomial lagrangePolynomial = new LagrangePolynomial(X,Y,x);
        double expected = lagrangePolynomial.getAnswer();
        Assert.assertEquals(expected,4.59336, 0.0001);
    }
}