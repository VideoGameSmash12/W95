package me.videogamesm12.w95.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInformation
{
    public String description();

    public String usage();
}
