package com.digitalskills.cartaxcheck.mapper;

import com.digitalskills.cartaxcheck.model.Vehicle;
import com.digitalskills.cartaxcheck.pageobjects.CarTaxDetailsPage;

import java.util.Arrays;
import java.util.List;

public class VehicleMapper {

    private static final String SPACE_PATTERN = "\\s*,\\s*";

    public static Vehicle mapToVehicle(final String data) {
        List<String> vehicleData = Arrays.asList(data.split(SPACE_PATTERN));
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistration(vehicleData.get(0));
        vehicle.setMake(vehicleData.get(1));
        vehicle.setModel(vehicleData.get(2));
        vehicle.setColor(vehicleData.get(3));
        vehicle.setYear(vehicleData.get(4));

        return vehicle;
    }

    public static Vehicle mapToActualVehicle(final CarTaxDetailsPage detailsPage) {
        Vehicle actualVehicle = new Vehicle();
        actualVehicle.setRegistration(detailsPage.getRegistration());
        actualVehicle.setMake(detailsPage.getMake());
        actualVehicle.setModel(detailsPage.getModel());
        actualVehicle.setColor(detailsPage.getColor());
        actualVehicle.setYear(detailsPage.getYear());

        return actualVehicle;
    }
}
