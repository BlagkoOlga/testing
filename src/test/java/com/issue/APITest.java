package com.issue;

import com.jayway.awaitility.Duration;
import com.jayway.awaitility.core.ConditionFactory;
import com.jayway.jsonpath.JsonPath;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.issue.IssueConstants.PORT;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static com.jayway.awaitility.Awaitility.await;

public class APITest extends BaseTest {

    private static String url = "http://localhost:" + PORT + "/";
    private static final Logger logger = Logger.getLogger(APITest.class);

    @Test
    public void testAddServiceToClient() throws IOException {

        logger.info("Step 2 - create client with balance");
        setDataBaseValue();

        logger.info("Step 3 - Get user services");
        List<Map<String, Object>> clientServices = getUserServices();

        logger.info("Step 4 - Get all services");
        List<Map<String, Object>> servicesName = getAllServices();

        logger.info("Step 5 - found service isn't connected to client and save id and cost");
        List<Map<String, Object>> missedServices = getMissedServices(clientServices, servicesName);
        List<Integer> servicesId = getAddedServiceId(missedServices);
        List<Double> servicesCost = getAddedServiceCost(missedServices);

        logger.info("Step 6 - connected service to client");

        addServiceToClient(servicesId);

        logger.info("Step 7 - wait for services connected");
        WAIT.until(() -> getNewServices());

        logger.info("Step 8 - get balance from DB");
        double balancesValueNew = getNewBalancesFromDB();

        logger.info("Step 9 - check balances");
        Assert.assertEquals(balancesValueNew, balancesValue - servicesCost.get(0));


    }

    private double getNewBalancesFromDB() {
        ResultSet result = executeCommand("select * from CLIENTS left outer join BALANCES on BALANCES.CLIENTS_CLIENT_ID = CLIENTS.CLIENT_ID where CLIENTS.CLIENT_ID=" + clientId + ";");

        double balancesValueNew = 0;
        try {
            balancesValueNew = result.getDouble("balance");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balancesValueNew;
    }

    private ValidatableResponse getNewServices() {
        return given().body("{\"client_id\":" + clientId + "}")
                .when()
                .contentType(ContentType.JSON)
                .post(url + "/client/services")
                .then().assertThat().body("count", equalTo("2"));
    }

    private Response addServiceToClient(List<Integer> servicesId) {
        return given()
                .body("{\"client_id\":" + clientId + ", \"service_id\": " + servicesId.get(0) + "}")
                .when()
                .contentType(ContentType.JSON)
                .expect().statusCode(202)
                .post(url + "/client/add_service");
    }

    private List<Integer> getAddedServiceId(List<Map<String, Object>> servicesName) {
        List<Integer> servicesId = new LinkedList<>();
        for (Map<String, Object> ser : servicesName) {
            servicesId.add(Integer.valueOf(ser.get("id").toString()));
        }
        return servicesId;
    }

    private List<Double> getAddedServiceCost(List<Map<String, Object>> servicesName) {
        List<Double> servicesCost = new LinkedList<>();

        for (Map<String, Object> ser : servicesName) {
            servicesCost.add(Double.valueOf(ser.get("cost").toString()));
        }
        return servicesCost;
    }

    private List<Map<String, Object>> getMissedServices(List<Map<String, Object>> clientServices, List<Map<String, Object>> servicesName) {
        if (clientServices.size() == 0) {
            return servicesName;
        } else {
            for (Map<String, Object> str : clientServices) {
                if (clientServices.contains(str)) {
                    servicesName.remove(str);
                }
            }
        }
        return servicesName;
    }

    private List<Map<String, Object>> getAllServices() {
        Response response1 = given()
                .when()
                .contentType(ContentType.JSON)
                .expect().statusCode(200)
                .get(url + "/services");
        return JsonPath.read(response1.body().asString(), "$.items[*]");
    }

    private List<Map<String, Object>> getUserServices() {
        Response response = given()
                .body("{\"client_id\":" + clientId + "}")
                .when()
                .contentType(ContentType.JSON)
                .expect().statusCode(200)
                .post(url + "/client/services");

        return JsonPath.read(response.body().asString(), "$.items[*]");
    }

    private void setDataBaseValue() {
        try {

            ResultSet rs = executeCommand("select * from BALANCES where BALANCES.BALANCE>0;");
            if (rs.next()) {
                executeUpdateCommand("insert into CLIENTS  values (5,\"Lala\");");
                executeUpdateCommand("insert into BALANCES  values (5,5.0);");
            }
            ResultSet result = executeCommand("select * from CLIENTS left outer join BALANCES on BALANCES.CLIENTS_CLIENT_ID = CLIENTS.CLIENT_ID where BALANCES.BALANCE>5.0");
            if (result.next()) {
                balancesValue = result.getDouble("balance");
                clientId = result.getInt("client_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final ConditionFactory WAIT = await()
            .atMost(new Duration(60, TimeUnit.SECONDS))
            .pollInterval(Duration.ONE_SECOND)
            .pollDelay(Duration.ONE_SECOND);


}
