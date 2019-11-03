package lk.excellent.pharamacy_management.asset.prescriber.controller;/*
package com.excellenthealthSolution.pharmacy.asset.prescriber.controller;

import com.excellenthealthSolution.pharmacy.util.service.DateTimeAgeService;
import com.excellenthealthSolution.pharmacy.security.service.UserService;
import com.excellenthealthSolution.pharmacy.asset.prescriber.service.ConsultationService;
import com.excellenthealthSolution.pharmacy.asset.prescriber.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorService doctorService;
    private final ConsultationService consultationService;
    private final DateTimeAgeService dateTimeAgeService;
    private final UserService userService;

    @Autowired
    public DoctorController(DoctorService doctorService, ConsultationService consultationService, DateTimeAgeService dateTimeAgeService, UserService userService) {
        this.doctorService = doctorService;
        this.consultationService = consultationService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.userService = userService;
    }

    @RequestMapping
    public String doctorPage(Model model) {
        model.addAttribute("doctors", doctorService.findAll());
        return "doctor/doctor";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String doctorView(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("doctorDetail", doctorService.findById(id));
        model.addAttribute("consultations", consultationService.findAll());
        return "doctor/doctor-detail";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editDoctorFrom(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("doctor", doctorService.findById(id));
        model.addAttribute("addStatus", false);
        model.addAttribute("consultations", consultationService.findAll());
        model.addAttribute("title", Title.values());
        model.addAttribute("gender", Gender.values());
        return "doctor/addDoctor";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String doctorAddFrom(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("consultations", consultationService.findAll());
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("title", Title.values());
        model.addAttribute("gender", Gender.values());
        return "doctor/addDoctor";
    }

    // Above method support to send data to front end - All List, update, edit
    //Bellow method support to do back end function save, delete, update, search

    @RequestMapping(value = {"/add","/update"}, method = RequestMethod.POST)
    public String addDoctor(@Valid @ModelAttribute Doctor doctor, BindingResult result, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userService.findByUserIdByUserName(auth.getName());
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                System.out.println(error.getField() + ": " + error.getDefaultMessage());
            }
            model.addAttribute("addStatus", true);
            model.addAttribute("consultations", consultationService.findAll());
            model.addAttribute("doctor", doctor);
            model.addAttribute("title", Title.values());
            model.addAttribute("gender", Gender.values());
            return "doctor/addDoctor";
        }

        if (doctor.getId() != null){
            doctorService.persist(doctor);
            model.addAttribute("addStatus", false);
            return "redirect:/doctor";
        }

        doctor.setCreateAt(dateTimeAgeService.getCurrentDate());
        doctorService.persist(doctor);
        return "redirect:/doctor";
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String removeDoctor(@PathVariable Integer id) {
        doctorService.delete(id);
        return "redirect:/doctor";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model, Doctor doctor) {
        model.addAttribute("doctorDetail", doctorService.search(doctor));
        return "doctor/doctor-detail";
    }
}

*/