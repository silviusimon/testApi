package com.testapi.testapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RatesWrapper implements Serializable {

    public String success;
    public long timestamp;
    public String base;
    public Date date;
    public Map<String, Double> rates;

}
