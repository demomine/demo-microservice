package com.lance.demo.microservice.dao.tenant.parser;

import com.lance.demo.microservice.dao.tenant.cache.ParsedParam;
import com.lance.demo.microservice.dao.tenant.cache.ParsedSQL;
import com.lance.demo.microservice.dao.tenant.cache.TableCache;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * INSERT 语句的解析
 */
public class InsertParser extends BaseParser {

    private Pattern values;

    public InsertParser(String sql) {
        this.parsedSQL = new ParsedSQL<>();
        this.sql = sql;
        this.pattern = Pattern.compile("^insert into" + TABLE_NAME + " ", mask);
        this.values = Pattern.compile("[ ]?\\) value[s ]?\\([ ]?", mask);
    }

    public ParsedSQL<String> parse() {
        return parse(1, -1);
    }

    @Override
    public void parseColumn(Matcher matcher, int nameIndex, int aliasIndex) {

        String name = matcher.group(nameIndex);
        TableCache cache = TableCache.get(name);
        String column = cache.getColumn();

        Matcher valueMatcher = values.matcher(sql);
        if (valueMatcher.find()) {
            parsedSQL.addParam(new ParsedParam<>(column, tenant, String.class, -1, false));
        }
    }

}
