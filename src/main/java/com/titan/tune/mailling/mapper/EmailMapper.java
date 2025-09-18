package com.titan.tune.mailling.mapper;


import com.titan.tune.mailling.dto.request.EmailRequest;
import com.titan.tune.mailling.dto.response.EmailResponse;
import com.titan.tune.mailling.entity.Email;
import lombok.AllArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailMapper {


    public EmailResponse toDto(Email email) {
        EmailResponse response = new EmailResponse();
        BeanUtils.copyProperties(email, response);
        return response;
    }

    public Email toEntity(EmailRequest request) {
        Email email = new Email();
        BeanUtils.copyProperties(request, email);
        return email;
    }

    public Email toEntity(EmailRequest request, Email email) {
        BeanUtils.copyProperties(request, email);
        return email;
    }

}
