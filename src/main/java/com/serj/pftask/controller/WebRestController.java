package com.serj.pftask.controller;


import com.serj.pftask.model.*;
import org.joda.time.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
public class WebRestController {
    @Autowired
    private HolidayRepository holidayRepository;

    Iterable<Holiday> holidayList;


    @PostMapping(value = {"/calculateDate"})
    public ResponseEntity<?> calculateDays(@Valid @RequestBody AjaxRequest ajaxRequest, Errors errors) {
        holidayList = holidayRepository.findAll();
        AjaxResponseBody result = new AjaxResponseBody();
        String msg;
        LocalDate firstDate, secondDate;
        long count = 0;
        if (errors.hasErrors()) {
            result.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(result);
        }
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
        msg = "holidays count is " + count;
        result.setMsg(msg);
        return ResponseEntity.ok(result);
    }
}
