import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserActions {

    public List<Car> findAllCars() {
        List<Car> arrayCars = new ArrayList<>();
        try (Connection connection = new DBConnection().dbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from cars")) {

            ResultSet resultSet = preparedStatement.executeQuery();
            Car car;

            while(resultSet.next()) {
                car = new Car();
                car.setBrand(resultSet.getString("brand"));
                car.setModel(resultSet.getString("model"));
                car.setPrice(resultSet.getInt("price"));
                car.setYear(resultSet.getInt("year"));
                car.setStatus(resultSet.getString("status"));
                arrayCars.add(car);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return arrayCars;
    }

    public void createNewCars(List<Car> list) {
        if (list.size() == 0) {
            System.out.println("New cars is not found.");
        } else {
            try (Connection connection = new DBConnection().dbConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "insert into cars (brand, model, price, year, status) " +
                                 "values (?, ?, ?, ?, ?)")) {

                for (Car j: list) {
                    preparedStatement.setString(1, j.getBrand());
                    preparedStatement.setString(2, j.getModel());
                    preparedStatement.setInt(3, j.getPrice());
                    preparedStatement.setInt(4, j.getYear());
                    preparedStatement.setString(5, j.getStatus());
                    preparedStatement.addBatch();
                }

                preparedStatement.executeBatch();

                System.out.println("Was add " + list.size() + " cars.");

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    public List<Car> carsMatches(List<Car> carsListFromParsing, List<Car> listFoundCars) {
        List<Car> listSortedCars = new ArrayList<>();
        for (Car j : carsListFromParsing) {
            Car carForCheck = new Car();
            carForCheck.setBrand(j.getBrand());
            carForCheck.setModel(j.getModel());
            carForCheck.setPrice(j.getPrice());
            carForCheck.setYear(j.getYear());
            carForCheck.setStatus(j.getStatus());
            if (!listFoundCars.contains(carForCheck)) {
                listSortedCars.add(carForCheck);
            }
        }
        return listSortedCars;
    }

}
