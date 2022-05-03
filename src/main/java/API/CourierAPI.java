package API;

import POJO.CourierCredentials;
import POJO.CourierPOJO;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class CourierAPI extends MainAPI {
    @Step("Послать POST запрос на ручку /courier")
    public Response sendPostRequestCreateCourier(CourierPOJO courierPOJO) {
        return reqSpec.body(courierPOJO)
                .when()
                .post("/courier");
    }

    @Step("Получить статус об успешном создании курьера - 201")
    public boolean courierCreatedStatus201(Response response) {
        return response.then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");
    }

    @Step("Получить ошибку о том, что такой логин уже существует на сервере - 409")
    public String courierConflict409(Response response) {
        return response.then()
                .assertThat()
                .statusCode(409)
                .extract()
                .path("message");
    }

    @Step("Получить ошибку о том, что курьер не был создан - 400")
    public String courierNotCreatedStatus400(Response response) {
        return response.then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
    }

    @Step("Авторизация")
    public int courierLogin(CourierCredentials credentials) {
        return reqSpec.body(credentials)
                .when()
                .post("/courier/login")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }

    @Step("Получить ошибку о том, что не хватает данных для авторизации - 400")
    public String courierLoginNotEnoughDataStatus400(CourierCredentials credentials) {
        return reqSpec.body(credentials)
                .when()
                .post("/courier/login")
                .then().log().all()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
    }

    @Step("Получить ошибку о том, что такого курьера не существует - 404")
    public String courierLoginCourierDoesNotExistStatus400(CourierCredentials credentials) {
        return reqSpec.body(credentials)
                .when()
                .post("/courier/login")
                .then().log().all()
                .assertThat()
                .statusCode(404)
                .extract()
                .path("message");
    }

    @Step("Удаление курьера")
    public void deleteCourier(int courierId) {
        reqSpec.pathParam("courierId", courierId)
                .when()
                .delete("/courier/{courierId}")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }


}
