package com.serj.pftask.controller;

import com.serj.pftask.model.*;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.MonthDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class WebController {
    @Autowired
    private HolidayRepository holidayRepository;

    Iterable<Holiday> holidayList;

    @RequestMapping(value = {"/", "home"})
    public String home() {
        return "home";
    }

    @GetMapping(value = {"welcome"})
    public String welcome(AjaxRequest ajaxRequest) {
        holidayList = holidayRepository.findAll();
        return "welcome";
    }
    @RequestMapping(value = {"admin"})
    public String admin() {
        return "admin";
    }

    @RequestMapping(value = {"login"})
    public String login() {
        return "login";
    }

    @RequestMapping(value = {"/403"})
    public String Error403() {
        return "403";
    }
    @PostMapping(value = {"welcome"})
    public String calculate(@Valid AjaxRequest ajaxRequest, BindingResult bindingResult, ModelMap map) {

        int count = 0;
        if (bindingResult.hasErrors()) return "welcome";
        LocalDate firstDate, secondDate;
        firstDate = new LocalDate(ajaxRequest.getFirstDate());
        secondDate = new LocalDate(ajaxRequest.getSecondDate());
        if (firstDate.compareTo(secondDate) != -1) {
            LocalDate tmp = firstDate;
            firstDate = secondDate;
            secondDate = tmp;
        }
        do {
            String currentDate = new MonthDay(firstDate).toString("MM-dd");
            if(firstDate.getDayOfWeek() == DateTimeConstants.SATURDAY) count++;
            if(firstDate.getDayOfWeek() == DateTimeConstants.SUNDAY) count++;
            for(Holiday holiday : holidayList) {
                if (holiday.getDate().equals(currentDate)) {
                    count++;
                    break;
                }
            }
            firstDate = firstDate.plusDays(1);
        } while(firstDate.isBefore(secondDate));

        map.addAttribute("count", count);
        return "welcome";
    }
}
