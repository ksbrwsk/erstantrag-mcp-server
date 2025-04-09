package de.hdi.erstantrag.web;

import de.hdi.erstantrag.model.ErstantragForm;
import de.hdi.erstantrag.service.ErstantragService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ErstantragController {

    private final static String SUCCESS_MESSAGE = "successMessage";
    private final static String ERROR_MESSAGE = "errorMessage";

    private static final String PAGE_INDEX = "index";
    private static final String PAGE_SUCCESS = "success";

    private final ErstantragService erstantragService;

    public ErstantragController(ErstantragService erstantragService) {
        this.erstantragService = erstantragService;
    }

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("erstantragForm", new ErstantragForm());
        return PAGE_INDEX;
    }

    @PostMapping("/processErstantrag")
    public String processErstantrag(Model model,
                                    @Valid @ModelAttribute("erstantragForm") ErstantragForm erstantragForm,
                                    BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()) {
            this.addFieldErrors( bindingResult.getFieldErrors(), model);
            return PAGE_INDEX;
        } else {
            var dataCheck = this.erstantragService.handleErstantrag(erstantragForm);
            if(dataCheck.isDataValid()) {
                //model.addAttribute("erstantragForm", erstantragForm);
                model.addAttribute(SUCCESS_MESSAGE, "Vielen Dank für Ihren Antrag. Wir werden uns in Kürze bei Ihnen melden.");
                return PAGE_SUCCESS;
            } else {
                model.addAttribute(ERROR_MESSAGE, dataCheck.toString());
                return PAGE_INDEX;
            }
        }
    }

    private void addFieldErrors(List<FieldError> fieldErrors, Model model) {
        List<String> errors = fieldErrors.stream()
                .map(fieldError -> fieldError.getField().toUpperCase() + " - " + fieldError.getDefaultMessage())
                .toList();
        model.addAttribute(ERROR_MESSAGE, errors);
    }
}
