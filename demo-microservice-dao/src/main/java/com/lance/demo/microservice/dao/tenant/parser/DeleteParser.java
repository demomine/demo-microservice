package com.lance.demo.microservice.dao.tenant.parser;

import com.lance.demo.microservice.dao.tenant.cache.ParsedSQL;

import java.util.regex.Pattern;

/**
 * DELETE 语句的解析
 */
public class DeleteParser extends BaseParser {

    public DeleteParser(String sql) {
        this.parsedSQL = new ParsedSQL<>();
        this.sql = sql;
        this.pattern = Pattern.compile("^delete from" + TABLE_NAME + " ", mask);
    }

    @Override
    public ParsedSQL<String> parse() {
        return parse(1, -1);
    }

}
