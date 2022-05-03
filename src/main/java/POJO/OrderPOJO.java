package POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPOJO {
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    //    public static OrderPOJO getRandom(){
//        String firstName = RandomStringUtils.randomAlphanumeric(8);
//        String lastName = RandomStringUtils.randomAlphanumeric(8);
//        String address = RandomStringUtils.randomAlphanumeric(8);
//        int metroStation = RandomUtils.nextInt(0, 9);
//        String phone = RandomStringUtils.randomAlphanumeric(8);
//        int rentTime = RandomUtils.nextInt(0, 9);
//        String deliveryDate = RandomStringUtils.randomAlphanumeric(8);
//        String comment = RandomStringUtils.randomAlphanumeric(8);
//        return new OrderPOJO(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, null);
//    }
    public static OrderPOJO getDefault() {
        return new OrderPOJO("Кабанчик",
                "Кабанище",
                "Поле, 414 apt.",
                3,
                "+7 888 000 55 44",
                5,
                "2020-05-06",
                "Кабанчик вернись в Кабаноху",
                null);
    }
}
