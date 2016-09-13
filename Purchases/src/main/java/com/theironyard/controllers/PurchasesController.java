package com.theironyard.controllers;

import com.theironyard.entities.Customer;
import com.theironyard.entities.Purchase;
import com.theironyard.repositories.CustomerRepository;
import com.theironyard.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.Integer.valueOf;

@Controller
public class PurchasesController {

    @Autowired
    CustomerRepository customers;
    @Autowired
    PurchaseRepository purchases;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, String category, Integer page) {
        page = (page == null) ? 0 : page;

        PageRequest pr = new PageRequest(page, 10);
        Page<Purchase> p;

        if (category != null) {
            p = purchases.findByCategory(pr, category);
        }
        else {
            p = purchases.findAll(pr);
        }

        model.addAttribute("category", category);
        model.addAttribute("purchases", p);
        model.addAttribute("nextPage", page + 1);
        model.addAttribute("showNext", p.hasNext());
        return "home";
    }

    @PostConstruct
    public void init () throws FileNotFoundException{
        // use scanner and while has next split on commas new object on next line then add to repo

        if (customers.count() == 0) {
            File f = new File("customers.csv");
            Scanner scanner = new Scanner(f);
            scanner.nextLine();
            while (scanner.hasNext()) {
                String fileContents = scanner.nextLine();
                String[] columns = fileContents.split(",");
                Customer customer = new Customer(columns[0], columns[1]);
                customers.save(customer);
            }

        }
        if (purchases.count() == 0) {
            File g = new File("purchases.csv");
            Scanner scanner1 = new Scanner(g);
            scanner1.nextLine();

            while (scanner1.hasNext()) {
                String fileContents = scanner1.nextLine();
                String[] columns2 = fileContents.split(",");
                Purchase purchase = new Purchase(customers.findOne(valueOf(columns2[0])), (columns2[1]),
                        (columns2[2]), valueOf(columns2[3]), columns2[4]);
                purchases.save(purchase);
            }
        }
    }
}