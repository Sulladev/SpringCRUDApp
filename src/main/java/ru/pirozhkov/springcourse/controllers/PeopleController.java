package ru.pirozhkov.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pirozhkov.springcourse.dao.PersonDAO;
import ru.pirozhkov.springcourse.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    // создаёт людей из DAO и отдаёт на отображение в представление
    @GetMapping ()  // адрес метода /people
    public String index (Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";  //thymeleaf шаблон
    }

    //получает одного человека по id из DAO и отдаёт на отображение в представление
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }


    // возвращает html форму для создания человека
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    // берёт данные из POST - запроса и добавляет нового человека в DB
    @PostMapping()
    public String create(@ModelAttribute("person") Person person) {
        personDAO.save(person);
        return "redirect:/people";
    }


    @GetMapping ("/{id}/edit")
    public String edit (@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    // принимает PATCH запрос на /people/id
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("person") Person person) {    // MA принимает Person  из формы
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
