package POJO;

import lombok.Data;

@Data
public class CourierCredentials {
    private String login;
    private String password;

    public CourierCredentials(CourierPOJO courierPOJO) {
        this.login = courierPOJO.getLogin();
        this.password = courierPOJO.getPassword();
    }

    public static CourierCredentials from(CourierPOJO courierPOJO) {
        return new CourierCredentials(courierPOJO);
    }

}
