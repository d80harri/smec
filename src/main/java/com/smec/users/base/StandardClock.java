package com.smec.users.base;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class StandardClock implements IClock{

    @Override
    public Date now() {
        return new Date();
    }
}
