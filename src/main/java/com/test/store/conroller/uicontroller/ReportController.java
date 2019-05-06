package com.test.store.conroller.uicontroller;

import com.test.store.conroller.controllerstemplates.ReportResponseTemplate;
import com.test.store.utils.CurrencyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReportController {

    @Autowired
    private CurrencyUtils currencyUtils;

    @PostMapping("/reportFormat")
    @ResponseBody
    public String reportFormat(
            @RequestBody ReportResponseTemplate jbody
    ){
        StringBuilder sb = new StringBuilder();
        sb.append("The cost of a products which stored in ").append(jbody.getYear())
                .append(" is ").append(jbody.getCurrencyCalc().name())
                .append(currencyUtils.normalize(jbody.getAmountResult()));
        return sb.toString();
    }


}
