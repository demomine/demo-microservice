package com.lance.demo.microservice.dao.tenant.parser;


import com.lance.demo.microservice.dao.tenant.cache.ParsedSQL;

import java.util.regex.Pattern;

/**
 * UPDATE 语句的解析
 */
public class UpdateParser extends BaseParser {

    public UpdateParser(String sql) {
        this.parsedSQL = new ParsedSQL<>();
        this.sql = sql;
        this.pattern = Pattern.compile("^update" + TABLE_NAME + " ", mask);
    }

    @Override
    public ParsedSQL<String> parse() {
        return parse(1, -1);
    }

}
