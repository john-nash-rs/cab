package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import rider.models.Rider;

import rider.services.IRiderService;
import storage.IStorageService;
import storage.StorageFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@MultipartConfig
public class RiderServelet extends HttpServlet {
    //    private static StorageFactory storageFactory;
    private IStorageService storageService;
    private IRiderService riderService;

    ObjectMapper objectMapper = new ObjectMapper();

    public RiderServelet(IStorageService storageService, IRiderService riderService) {
        this.storageService = storageService;
        this.riderService = riderService;
    }

    //Url To get the Rider Detail : http://localhost:8090/rider?rideUserId=+91910
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String riderUserId = request.getParameter("rideUserId");
        System.out.println("Rider User Id" + riderUserId);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        Rider rider = storageService.getRiderWithRiderUserID(riderUserId);
        objectMapper.writeValue(response.getWriter(), rider);
    }

    /* Url to add rider : http://localhost:8090/rider
        x-www-form-urlencoded
        Body :
        name:HarshRider
        country_code:+99
        phone_number:8863456790
     */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        Rider rider = new Rider();
        System.out.println("Request: " + req.getParameterMap());
        rider.setName(req.getParameter("name"));
        rider.setCountryCode(req.getParameter("country_code"));
        rider.setPhoneNumber(req.getParameter("phone_number"));
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        boolean result = riderService.register(rider);
        if (result) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("\"Rider\": \"\"" + rider);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Rider already exist in the system");
        }
    }
}
