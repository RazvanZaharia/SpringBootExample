package hello;

import hello.model.User;
import hello.model.userDTOs.NewUserDTO;
import hello.model.userDTOs.UpdateUserDTO;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;


public class UserUT {
    private static final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void checkExamMapping() {
        NewUserDTO creation = new NewUserDTO();
        creation.setFirstName("FirstName");
        creation.setLastName("LastName");
        creation.setUserName("first_last");
        creation.setPassword("first123");

        User exam = modelMapper.map(creation, User.class);
        assertEquals(creation.getFirstName(), exam.getFirstName());
        assertEquals(creation.getLastName(), exam.getLastName());
        assertEquals(creation.getUserName(), exam.getUserName());
        assertEquals(creation.getPassword(), exam.getPassword());

        UpdateUserDTO update = new UpdateUserDTO();
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
