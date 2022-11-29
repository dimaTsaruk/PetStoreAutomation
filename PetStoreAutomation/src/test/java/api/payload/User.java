package api.payload;

import lombok.Data;

@Data
public class User {
    int id;
    String username;
    String firstName;
    String lastName;
    String email;
    String password;
    String phone;
    int userStatus = 0;
}
