import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class AppMain {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        Handler handler = new Handler();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        saxParser.parse(new File("src/xml.xml"), handler);

        UserActions userActions = new UserActions();

        // получение коллекции машин в результате парсинга
        List<Car> arrayList = handler.getArrayList();

        // получение коллекции машин из базы
        List<Car> listFoundCars = userActions.findAllCars();

        // проверка на повторы
        List<Car> finalCarsList = userActions.carsMatches(arrayList, listFoundCars);

        // запись/внесение уникальных машин в базу
        userActions.createNewCars(finalCarsList);

    }

}
