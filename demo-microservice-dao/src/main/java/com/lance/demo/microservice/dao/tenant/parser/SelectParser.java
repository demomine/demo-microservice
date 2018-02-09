package com.lance.demo.microservice.dao.tenant.parser;


import com.lance.demo.microservice.dao.tenant.cache.ParsedSQL;

import java.util.regex.Pattern;

/**
 * SELECT 语句的解析
 */
public class SelectParser extends BaseParser {

    public static final Pattern select = Pattern.compile(" from" + TABLE_NAME +"( as)?"+ TABLE_NAME + "?[, ]?", mask);

    public SelectParser(String sql) {
        this.sql = sql;
        this.parsedSQL = new ParsedSQL<>();
        this.pattern = select;
    }

    @Override
    public ParsedSQL<String> parse() {
        return parse(1, 3);
    }

}
