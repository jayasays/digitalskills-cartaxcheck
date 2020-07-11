package com.digitalskills.cartaxcheck.util;

import com.digitalskills.cartaxcheck.mapper.VehicleMapper;
import com.digitalskills.cartaxcheck.model.Vehicle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class FileHelper {

    private static final Logger LOGGER = Logger.getLogger(FileHelper.class.getName());

    private static final String CAR_INPUT_FILE_NAME = "resources/car_input.txt";
    private static final String CAR_OUTPUT_FILE_NAME = "resources/output/car_output.txt";
    private static final String INPUT_RESOURCES_DIR = "resources/input";
    private static final String OUTPUT_RESOURCES_DIR = "resources/output";
    private static final String VEHICLE_NUMBER_REGEX = "(?=.{1,7})(([a-zA-Z]){1,3}(\\d){1,3}(\\s?)([a-zA-Z]){1,3})";

    /**
     * Reads each line from car input resource file, returns all matched vehicle registration numbers
     *
     * @return
     */
    public static List<String> getVehicleRegistrationNumbers() throws Exception {
        List<String> registrationNumbers = new ArrayList<>();
        Pattern pattern = Pattern.compile(VEHICLE_NUMBER_REGEX);

        List<Path> files = Files.list(Paths.get(INPUT_RESOURCES_DIR))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".txt"))
                .collect(Collectors.toList());

        files.forEach(file -> {
            try (Stream<String> stream = Files.lines(file)) {
                List<String> regNumbers = stream.flatMap(line -> StreamSupport.stream(new MatchItr(pattern.matcher(line)), false))
                                                .collect(Collectors.toList());
                registrationNumbers.addAll(regNumbers);
            } catch (IOException ex) {
                ex.printStackTrace();
                LOGGER.log(Level.SEVERE, "Error occurred when loading and processing " + CAR_INPUT_FILE_NAME, ex);
            }

        });


        return registrationNumbers;
    }

    /**
     * Reads data from car_output.txt and creates a map of vehicle details with it's registration number as key.
     *
     * @return
     */
    public static Map<String, Vehicle> getExpectedVehicleData() {

        try (Stream<String> stream = Files.lines(Paths.get(CAR_OUTPUT_FILE_NAME))) {
            return stream.skip(1)
                    .map(VehicleMapper::mapToVehicle)
                    .collect(Collectors.toMap(Vehicle::getRegistration, Function.identity()));

        } catch (IOException ex) {
            ex.printStackTrace();
            LOGGER.log(Level.SEVERE, "Error occurred when loading and processing " + CAR_OUTPUT_FILE_NAME, ex);
        }

        return Collections.emptyMap();
    }

    final static class MatchItr extends Spliterators.AbstractSpliterator<String> {
        private final Matcher matcher;

        MatchItr(Matcher m) {
            super(m.regionEnd() - m.regionStart(), ORDERED | NONNULL);
            matcher = m;
        }

        public boolean tryAdvance(Consumer<? super String> action) {
            if (!matcher.find()) return false;
            action.accept(matcher.group());
            return true;
        }
    }

    public static void main(String[] args) throws Exception {

        Files.list(Paths.get("resources"))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".txt"))
                .forEach(System.out::println);
    }
}
