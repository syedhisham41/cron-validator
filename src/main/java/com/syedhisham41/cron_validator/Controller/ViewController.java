package com.syedhisham41.cron_validator.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.syedhisham41.cron_validator.Constants.CronType;
import com.syedhisham41.cron_validator.DTO.CronRequest;
import com.syedhisham41.cron_validator.Service.CronQuartzService;
import com.syedhisham41.cron_validator.Service.CronService;
import com.syedhisham41.cron_validator.Service.ViewService;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("cronRequest", new CronRequest());
        return "index";
    }

    @PostMapping("/process")
    public String validateForm(
            @ModelAttribute CronRequest cronRequest,
            @RequestParam String action,
            Model model) throws Exception {

        CronService cronService = cronRequest.getCronType().equals(CronType.QUARTZ)
                ? new CronQuartzService()
                : null;

        ViewService viewService = new ViewService(cronService);

        String result = "";
        boolean hasError = false;

        if (action.equals("validate")) {
            result = viewService.validateCronExpression(cronRequest);
            hasError = result.toLowerCase().contains("invalid") || result.toLowerCase().contains("error");

        } else if (action.equals("readable")) {
            try {
                result = viewService.processCronExpression(cronRequest);
            } catch (Exception e) {
                hasError = true;
                result = "Error: " + e.getMessage();
            }
        }

        model.addAttribute("cronRequest", cronRequest);
        model.addAttribute("result", result);
        model.addAttribute("hasError", hasError);

        return "index";
    }

}
