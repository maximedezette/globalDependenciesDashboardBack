package com.globaldashboard.domain;

import java.net.MalformedURLException;
import java.net.URL;

public record PomUrl(String url)
{
    public PomUrl {
        if(!url.endsWith("pom.xml")) {
            throw new IllegalArgumentException("The given URL is not valid %s".formatted(url));
        }
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
