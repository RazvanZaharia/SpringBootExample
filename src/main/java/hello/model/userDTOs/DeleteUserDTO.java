package hello.model.userDTOs;

import java.io.Serializable;

public class DeleteUserDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
