package com.mehehe;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;
import javax.validation.Valid;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import org.knowm.dropwizard.sundial.SundialConfiguration;

public class DropwizardBikeConfiguration extends Configuration {
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @Valid
    @NotNull
    public SundialConfiguration sundialConfiguration = new SundialConfiguration();


    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }
    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }


    @JsonProperty("sundial")
    public SundialConfiguration getSundialConfiguration() {

        return sundialConfiguration;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory factory) {
        this.database = factory;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }
}
