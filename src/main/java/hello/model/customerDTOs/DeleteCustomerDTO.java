package hello.model.customerDTOs;

import java.io.Serializable;

public class DeleteCustomerDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
