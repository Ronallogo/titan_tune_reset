package com.titan.tune.mailling.api;

import com.titan.tune.mailling.dto.request.EmailRequest;
import com.titan.tune.mailling.dto.response.EmailResponse;
import com.titan.tune.mailling.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/mailing/")
public class SendMailAPi {

    private final EmailService emailService;

    public SendMailAPi(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("send")
    @Operation(
            summary = "Send an email using the specified template",
            description = "This endpoint sends an email using a specified template. A valid EmailRequest object must be provided."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "500", description = "Error while sending the email")
    })
    public ResponseEntity<EmailResponse> sendEmail(@RequestBody EmailRequest request,
                                                   @RequestParam String template) {
        try {
            EmailResponse response = emailService.send(request, template);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {

            return ResponseEntity.status(500).body( new EmailResponse()
            );
        }
    }

}
