package de.hdi.erstantrag.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samskivert.mustache.Mustache;
import de.hdi.erstantrag.model.ErstantragForm;
import de.hdi.erstantrag.model.Partner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class ErstantragMailAcknowledgement {

    private static final Resource MAIL_TEMPLATE = new ClassPathResource("mail_template.mustache");
    private static final Resource SAP_MAIL_TEMPLATE = new ClassPathResource("sap_template.mustache");

    private final JavaMailSender javaMailSender;

    public ErstantragMailAcknowledgement(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendAckMail(ErstantragForm erstantragForm) throws Exception {
        String populatedTemplate = createMustacheContextAndPopulateTemplate(erstantragForm);
        var mimeMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("mailpit@localhost");
        mimeMessageHelper.setTo(erstantragForm.getEmail());
        mimeMessageHelper.setSubject("Ihr Antrag");
        mimeMessageHelper.setText(populatedTemplate, true);
        this.javaMailSender.send(mimeMessage);
    }

    public void sendSapMail(Partner partner) throws Exception {
        String populatedTemplate = createSapMustacheContextAndPopulateTemplate(partner);
        var mimeMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("mailpit@localhost");
        mimeMessageHelper.setTo(partner.email());
        mimeMessageHelper.setSubject("Erstantrag Altersrente an SAP übergeben");
        mimeMessageHelper.setText(populatedTemplate, true);
        this.javaMailSender.send(mimeMessage);
    }

    private String createMustacheContextAndPopulateTemplate(ErstantragForm erstantragForm) throws Exception {
        String contentsOfTestEmailMustacheFile = MAIL_TEMPLATE.getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> context = new HashMap<>();
        context.put("name", erstantragForm.getVorname() + " " + erstantragForm.getNachname());
        context.put("date", LocalDate.now().toString());
        context.put("antragnummer", erstantragForm.getAntragnummer());
        Mustache.Compiler compiler = Mustache.compiler();
        return compiler.compile(contentsOfTestEmailMustacheFile).execute(context);
    }

    private String createSapMustacheContextAndPopulateTemplate(Partner partner) throws Exception {
        String contentsOfTestEmailMustacheFile = SAP_MAIL_TEMPLATE.getContentAsString(StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(partner);
        Map<String, Object> context = new HashMap<>();
        context.put("data", json);
        Mustache.Compiler compiler = Mustache.compiler();
        return compiler.compile(contentsOfTestEmailMustacheFile).execute(context);
    }
}


