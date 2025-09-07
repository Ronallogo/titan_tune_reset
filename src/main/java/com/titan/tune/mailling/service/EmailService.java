package com.titan.tune.mailling.service;


import com.titan.tune.mailling.dto.request.EmailRequest;
import com.titan.tune.mailling.dto.response.EmailResponse;

public interface EmailService {

    EmailResponse send(EmailRequest request, String template);


}
