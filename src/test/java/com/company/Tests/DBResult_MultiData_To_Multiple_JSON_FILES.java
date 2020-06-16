package com.company.Tests;

import com.company.Utils.DBUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBResult_MultiData_To_Multiple_JSON_FILES {

    @Test
    public void example() throws SQLException, IOException {
        DBUtil.createConnectionToHrDB();
        // Give me first 5 employee in employees table
        String query = "select * from employees limit 5";
        // Store result in List Of maps
        List<Map<String, Object>> resultMap = DBUtil.executeQueryAndGetResultMap(query);
        //Creating a list of CustomerDetails class before looping
        List<CustomerDetails_Pojo> customersList = new ArrayList<>();
        for (int i = 0; i < resultMap.size(); i++) {
            //Create object of Pojo class inside loop in order to create a new object for each looping.
            CustomerDetails_Pojo customerDetails = new CustomerDetails_Pojo();
            // Getting result from db
            Object firstName = resultMap.get(i).get("first_name");
            Object lastName = resultMap.get(i).get("last_name");
            Object employeeID = resultMap.get(i).get("employee_id");
            // Setting value of Pojo class variables using setter methods.
            customerDetails.setFirst_name(firstName.toString());
            customerDetails.setLast_name(lastName.toString());
            customerDetails.setEmployee_id(Integer.valueOf(employeeID.toString()));
            // adding all created objects to the list.
            customersList.add(customerDetails);
        }
        // Create multiple Json file
        for (int i = 0; i < customersList.size(); i++) {
            File filePath = new File("MultiData" + i + " .json");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(filePath, customersList.get(i));
        }

        DBUtil.destroyConnection();
    }


}
