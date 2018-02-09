package com.lance.demo.microservice.dao.tenant.cache;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class ParsedSQL<T> {
    @Getter@Setter
    private String sql;
    @Getter
    private List<ParsedParam<T>> params;

    public void addParam(ParsedParam<T> param) {
        if (params == null) {
            params = new ArrayList<>();
        }
        this.params.add(param);
    }
}
