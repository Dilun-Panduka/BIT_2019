package lk.excellent.pharamacy_management.asset.prescriber.controller;


import lk.excellent.pharamacy_management.asset.prescriber.entity.Consultations;
import lk.excellent.pharamacy_management.asset.prescriber.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/consultation")
public class ConsultationController {
    private final ConsultationService consultationService;

    @Autowired
    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @RequestMapping
    public String consultationPage(Model model) {
        model.addAttribute("consultations", consultationService.findAll());
        return "consultations/consultations";
    }

/*    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String consultationView(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("consultationDetail", consultationService.findById(id));
        return "consultations/consultations-detail";
    }*/

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editConsultationFrom(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("consultation", consultationService.findById(id));
        model.addAttribute("addStatus", false);
        return "consultations/addConsultation";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String consultationAddFrom(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("consultation", new Consultations());
        return "consultations/addConsultation";
    }

    // Above method support to send data to front end - All List, update, edit
    //Bellow method support to do back end function save, delete, update, search

    @RequestMapping(value = {"/add","/update"}, method = RequestMethod.POST)
    public String addConsultation(@Valid @ModelAttribute Consultations consultations, BindingResult result, Model model) {
        System.out.println(consultations);
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                System.out.println(error.getField() + ": " + error.getDefaultMessage());
            }
            model.addAttribute("addStatus", false);
            model.addAttribute("consultation", consultations);
            return "/consultations/addConsultation";
        }
        consultationService.persist(consultations);
        return "redirect:/consultation";
    }


    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String removeConsultation(@PathVariable Integer id) {
        consultationService.delete(id);
        return "redirect:/consultation";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model, Consultations consultations) {
        model.addAttribute("consultationDetail", consultationService.search(consultations));
        return "consultations/consultations-detail";
    }

}
