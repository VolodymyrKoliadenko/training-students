package org.koliadenko.KeyValue.web;

import org.koliadenko.KeyValue.DAO.PropertyRepository;
import org.koliadenko.KeyValue.domain.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {

    static String first = "FIRST";
    static String second = "SECOND";

    @Autowired
    PropertyRepository repository;

    @PostConstruct
    public void init() {
        Property p1 = new Property(first, "INT", "100");
        Property p2 = new Property(second, "INT", "60");

        repository.saveAll(Arrays.asList(p1, p2));

        repository.save(new Property(first, "Agility","null"));
        repository.save(new Property(first, "SPEED","300 000 km/sec"));
        repository.save(new Property(first, "Tahion","Disabled"));
    }

    @RequestMapping("/")
    public String main(Map<String, Object> model) {

        List<Property> firstList = repository.findByOwner(first);
        model.put("l1", firstList);
        List<Property> secondList = repository.findByOwner(second);
        model.put("l2", secondList);

        Property left1 = new Property();
        left1.setOwner(first);
        model.put("object1", left1);

        Property right2 = new Property();
        right2.setOwner(second);
        model.put("object2", right2);

        return "index";
    }
    @RequestMapping("/click")
    public String click(Property newProperty) { //Map<String, Object> model

        //System.out.println("_____ clicked============");

        repository.save(newProperty);
        //System.out.println(newProperty);
        return "redirect:/";
    }
}
