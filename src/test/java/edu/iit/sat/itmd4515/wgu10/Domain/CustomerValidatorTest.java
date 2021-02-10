package edu.iit.sat.itmd4515.wgu10.Domain;

import edu.iit.sat.itmd4515.wgu10.Domain.Customer;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wenganGu
 */
public class CustomerValidatorTest {
    //1.beforeAll
    //2.beforeEach
    //3.test1
    //4.afterEach
    //5.beforeEach
    //6.test2
    //7.afterEach
    //8.afterAll
    private static Validator validator;
    @BeforeAll
    public static void beforeAll() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

    }

    @AfterAll
    public static void afterAll() {

    }

    @BeforeEach
    public void beforeEach() {

    }

    @AfterEach
    public void afterEach() {

    }

    @Test
    public void customerIsCompleteValidEmail_expectFailure() {
        Customer c = new Customer(1, "Customer", "One", "customercustomer.net");
        Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(c);
        assertTrue(constraintViolations.size() == 1);
        assertEquals(1, constraintViolations.size());
        assertFalse(constraintViolations.isEmpty());
        for (ConstraintViolation<Customer> problem : constraintViolations) {
            System.out.println(problem.toString());
        }
    }

    @Test
    public void customerIsCompleteValid_expectPass(){
        Customer c = new Customer(1, "Customer", "One", "customer@customer.net");
        Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(c);
        assertTrue(constraintViolations.size() == 0);
        assertEquals(0,constraintViolations.size( ));
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void customerIsCompleteValid(){
        Customer c = new Customer(1, "Customer", "One", "customer@customer.net");
        Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(c);
        assertTrue(constraintViolations.size() == 0);
        assertEquals(0,constraintViolations.size( ));
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void test2(){

    }
}