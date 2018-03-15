package hello;

import hello.model.Customer;
import hello.model.customerDTOs.NewCustomerDTO;
import hello.model.customerDTOs.UpdateCustomerDTO;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;


public class CustomerUT {
    private static final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void checkExamMapping() {
        NewCustomerDTO creation = new NewCustomerDTO();
        creation.setFirstName("FirstName");
        creation.setLastName("LastName");
        creation.setUserName("first_last");
        creation.setPassword("first123");

        Customer exam = modelMapper.map(creation, Customer.class);
        assertEquals(creation.getFirstName(), exam.getFirstName());
        assertEquals(creation.getLastName(), exam.getLastName());
        assertEquals(creation.getUserName(), exam.getUserName());
        assertEquals(creation.getPassword(), exam.getPassword());

        UpdateCustomerDTO update = new UpdateCustomerDTO();
        update.setFirstName("FirstNameUpdate");
        update.setLastName("LastNameUpdate");
        update.setUserName("first_last_update");

        modelMapper.map(update, exam);
        assertEquals(update.getFirstName(), exam.getFirstName());
        assertEquals(update.getLastName(), exam.getLastName());
        assertEquals(creation.getPassword(), exam.getPassword());
        assertEquals(update.getUserName(), exam.getUserName());
    }
}
