package service;

import model.Car;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class Handler extends DefaultHandler {

    private List<Car> arrayList = new ArrayList<>();
    private String unitSearch;
    private String brand;
    private Car car;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        unitSearch = qName;

        if (unitSearch.equals("brand")) {
            if (attributes.getValue(0).equals("1")) {
                brand = "Ford";
            }
            if (attributes.getValue(0).equals("2")) {
                brand = "Mercedes-Benz";
            }
        }

        if (unitSearch.equals("unit")) {
            car = new Car();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        unitSearch = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String str = new String (ch, start, length);

        if (unitSearch.equals("title")) {
            car.setModel(str);
        }
        if (unitSearch.equals("price")) {
            car.setPrice(Integer.parseInt(str));
        }
        if (unitSearch.equals("year")) {
            car.setYear(Integer.parseInt(str));
        }
        if (unitSearch.equals("status")) {
            car.setStatus(str);
            car.setBrand(brand);
            arrayList.add(car);
        }

    }

    public List<Car> getArrayList() {
        return arrayList;
    }

}
