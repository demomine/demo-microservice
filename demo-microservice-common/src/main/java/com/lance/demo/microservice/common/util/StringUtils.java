package com.lance.demo.microservice.common.util;

import com.google.common.base.Preconditions;
import com.lance.demo.microservice.common.model.Inflector;
import com.netflix.astyanax.util.TimeUUIDUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static final String IgnoreChars = "*#";
    private static final Integer MACHINE_MIN_LEN = 3;
    private static final Integer MACHINE_MAX_LEN = 6;
    private static final Date START_DATE = DateUtils.parseToDate("2010-01-01",DateUtils.DATE_FORMAT);
    private static final AtomicLong SMALL_RANDOM = new AtomicLong(0L);
    private static List<String> SINGULARS = Arrays.asList("goods", "Goods", "penny", "Penny", "sms", "Sms", "data", "Data");
    private static List<String> PLURALS = Arrays.asList("goods", "Goods", "pence", "Pence", "smses", "Smses", "data", "Data");


    public static boolean hasText(String text) {
        return org.apache.commons.lang3.StringUtils.isNoneBlank(text);
    }
    public static int compare(String s1, String s2, String ignoredChars, int matchCount) {
        if (ignoredChars == null) {
            return s1.equals(s2) ? 1 : -1;
        } else if (length(s1, ignoredChars) != 0 && length(s2, ignoredChars) != 0) {
            if (s1.length() != s2.length()) {
                return -1;
            } else {
                int count = 0;

                for(int i = 0; i < s1.length(); ++i) {
                    if (!org.apache.commons.lang3.StringUtils.contains(ignoredChars, s1.charAt(i)) && !org.apache.commons.lang3.StringUtils.contains(ignoredChars, s2.charAt(i))) {
                        if (s1.charAt(i) != s2.charAt(i)) {
                            return -1;
                        }

                        ++count;
                    }
                }
                if (matchCount == -1) {
                    matchCount = s1.length();
                }

                if (count >= matchCount) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } else {
            return 0;
        }
    }

    public static int compare(String s1, String s2) {
        return compare(s1, s2, -1);
    }

    public static int compare(String s1, String s2, int matchCount) {
        return compare(s1, s2, "*#", matchCount);
    }

    public static String merge(String s1, String s2, String ignoredChars) {
        Preconditions.checkArgument(org.apache.commons.lang3.StringUtils.isNotBlank(ignoredChars), "ignoredChars不能为空");
        if (length(s1, ignoredChars) == 0) {
            return s2;
        } else if (length(s2, ignoredChars) == 0) {
            return s1;
        } else if (s1.length() != s2.length()) {
            return null;
        } else {
            StringBuilder buf = new StringBuilder();

            for(int i = 0; i < s1.length(); ++i) {
                if (!org.apache.commons.lang3.StringUtils.contains(ignoredChars, s1.charAt(i)) && !org.apache.commons.lang3.StringUtils.contains(ignoredChars, s2.charAt(i))) {
                    if (s1.charAt(i) != s2.charAt(i)) {
                        return null;
                    }

                    buf.append(s1.charAt(i));
                } else {
                    buf.append(org.apache.commons.lang3.StringUtils.contains(ignoredChars, s1.charAt(i)) ? s2.charAt(i) : s1.charAt(i));
                }
            }

            return buf.toString();
        }
    }

    public static String merge(String s1, String s2) {
        return merge(s1, s2, "*#");
    }

    public static String mergeSave(String s1, String s2) {
        return mergeSave(s1, s2, "*#");
    }

    public static String mergeSave(String s1, String s2, String ignoredChars) {
        String result = s2;
        if (length(s1, ignoredChars) != 0) {
            if (length(s2, ignoredChars) != 0) {
                String merged = merge(s1, s2, "*#");
                if (org.apache.commons.lang3.StringUtils.isNotBlank(merged)) {
                    result = merged;
                } else if (length(s1, ignoredChars) > length(s2, ignoredChars)) {
                    result = s1;
                }
            } else {
                result = s1;
            }
        } else if (s1 != null && s2 == null) {
            result = s1;
        }

        return result;
    }

    public static int length(String src, String ignoreChars) {
        return org.apache.commons.lang3.StringUtils.isBlank(src) ? 0 : src.length() - ignoreChars.chars().map((c) -> {
            return org.apache.commons.lang3.StringUtils.countMatches(src, (char)c);
        }).sum();
    }

    public static String uuid() {
        return TimeUUIDUtils.getUniqueTimeUUIDinMillis().toString();
    }

    public static String digitalUUID() {
        return digitalUUID(20, (Long)null);
    }

    public static String digitalUUID(Integer len) {
        return digitalUUID(len, (Long)null);
    }

    public static String digitalUUID(Integer len, Long suffix) {
        Preconditions.checkArgument(len > 16, "len必须在17位或以上");
        StringBuilder buf = new StringBuilder(System.currentTimeMillis() + "");
        int machineLen = MACHINE_MIN_LEN;
        int randomLen = len - 13 - machineLen;
        if (randomLen > 4) {
            int leftCount = randomLen - 4;
            machineLen = 4 + leftCount / 2;
            if (machineLen > MACHINE_MAX_LEN) {
                machineLen = MACHINE_MAX_LEN;
            }

            randomLen = len - 13 - machineLen;
        }

        String hostName = NetUtils.getHostName();
        if (org.apache.commons.lang3.StringUtils.isBlank(hostName)) {
            buf.append(org.apache.commons.lang3.StringUtils.repeat("0", machineLen));
        } else {
            buf.append(SecurityUtils.md5(true, new Object[]{hostName}).substring(0, machineLen));
        }

        if (randomLen >= 16) {
            for(int i = 0; i < randomLen / 16; ++i) {
                buf.append(RandomUtils.nextLong(1000000000000000L, 10000000000000000L));
            }

            randomLen %= 16;
        }

        if (randomLen > 0) {
            buf.append(innerRandom(randomLen));
        }

        if (suffix != null) {
            buf.append(suffix);
        }

        return buf.toString();
    }

    public static Date timeFromDigitalUUID(String uuid) {
        Preconditions.checkArgument(org.apache.commons.lang3.StringUtils.isNotBlank(uuid), "uuid不能为空");
        Preconditions.checkArgument(uuid.length() >= 13, "uuid长度不能小于13");
        String timeStr = uuid.substring(0, 13);
        if (!NumberUtils.isDigits(timeStr)) {
            throw new RuntimeException("uuid[" + uuid + "]格式不正确");
        } else {
            return new Date(Long.parseLong(timeStr));
        }
    }

    public static long longUid() {
        StringBuilder buf = new StringBuilder(System.currentTimeMillis() - START_DATE.getTime() + "");
        if (buf.length() <= 12 && buf.toString().compareTo("922337203684") <= 0) {
            String hostName = NetUtils.getHostName();
            if (org.apache.commons.lang3.StringUtils.isBlank(hostName)) {
                buf.append(org.apache.commons.lang3.StringUtils.repeat('0', MACHINE_MIN_LEN));
            } else {
                buf.append(SecurityUtils.md5(true, new Object[]{hostName}).substring(0, MACHINE_MIN_LEN));
            }

            int randomLen = 7 - MACHINE_MIN_LEN;
            buf.append(innerRandom(randomLen));
            return Long.parseLong(buf.toString());
        } else {
            throw new RuntimeException("起始时间太小, 要更新了!");
        }
    }

    public static long longUid(long gene, int len, String... args) {
        long uid = longUid(args);
        uid = uid >> len << len;
        BitSet set = BitSet.valueOf(new long[]{gene});
        set.clear(len, 64);
        set.or(BitSet.valueOf(new long[]{uid}));
        return set.toLongArray()[0];
    }

    public static long longUid(String... args) {
        StringBuilder buf = new StringBuilder(System.currentTimeMillis() - START_DATE.getTime() + "");
        if (buf.length() <= 12 && buf.toString().compareTo("922337203684") <= 0) {
            String argsHash = SecurityUtils.md5(true, args);
            if (org.apache.commons.lang3.StringUtils.isBlank(argsHash)) {
                buf.append(innerRandom(7));
            } else {
                buf.append(argsHash.substring(0, 7));
            }

            return Long.parseLong(buf.toString());
        } else {
            throw new RuntimeException("起始时间太小, 要更新了!");
        }
    }

    public static Date timeFromLongUid(long uid) {
        Long diff = Long.parseLong((uid + "").substring(0, 12));
        return new Date(START_DATE.getTime() + diff);
    }

    public static String uuidSimple() {
        return uuid().replaceAll("-", "");
    }

    public static boolean contains(String src, String contained) {
        return contains(src, contained, "*#");
    }

    public static boolean contains(String src, String contained, String ignoreChars) {
        if (org.apache.commons.lang3.StringUtils.isBlank(ignoreChars)) {
            return src.contains(contained);
        } else {
            StringBuilder buf = new StringBuilder();

            for(int i = 0; i < contained.length(); ++i) {
                if (org.apache.commons.lang3.StringUtils.contains(ignoreChars, contained.charAt(i))) {
                    buf.append(".");
                } else {
                    buf.append(contained.charAt(i));
                }
            }

            Matcher matcher = Pattern.compile(buf.toString()).matcher(src);
            if (matcher.find()) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static String collapseWhiteSpace(String src) {
        return src == null ? null : src.replaceAll("[\\s\\u00A0]{2,}", " ");
    }

    public static String substringAfter(String src, String separator) {
        String result = org.apache.commons.lang3.StringUtils.substringAfter(src, separator);
        return (String) org.apache.commons.lang3.StringUtils.defaultIfBlank(result, src);
    }

    public static String underScoreToCamel(String src, boolean firstLetterUpperCase) {
        if (org.apache.commons.lang3.StringUtils.isBlank(src)) {
            return src;
        } else {
            src = src.replaceAll("_\\d{1,}$", "");
            StringBuilder buf = new StringBuilder();
            Arrays.stream(src.split("_")).forEach((s) -> {
                buf.append(toCamel(s));
            });
            String result = buf.toString();
            if (!org.apache.commons.lang3.StringUtils.isBlank(result) && result.length() >= 2) {
                if (firstLetterUpperCase) {
                    result = Character.toUpperCase(result.charAt(0)) + result.substring(1);
                } else {
                    result = Character.toLowerCase(result.charAt(0)) + result.substring(1);
                }

                return result;
            } else {
                return result;
            }
        }
    }

    public static String joinIgnoreBlank(String sep, String... args) {
        Preconditions.checkArgument(sep != null, "分隔符不能为null");
        StringBuilder buf = new StringBuilder();
        int index = 0;
        String[] var4 = args;
        int var5 = args.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String arg = var4[var6];
            if (org.apache.commons.lang3.StringUtils.isNotBlank(arg)) {
                if (index != 0) {
                    buf.append(sep);
                }

                buf.append(arg);
                ++index;
            }
        }

        return buf.toString();
    }

    public static String singularize(String plural) {
        return PLURALS.contains(plural) ? (String)SINGULARS.get(PLURALS.indexOf(plural)) : Inflector.getInstance().singularize(plural);
    }

    public static String pluralize(String singular) {
        return SINGULARS.contains(singular) ? (String)PLURALS.get(SINGULARS.indexOf(singular)) : Inflector.getInstance().pluralize(singular);
    }

    private static String toCamel(String word) {
        if (org.apache.commons.lang3.StringUtils.isBlank(word)) {
            return word;
        } else {
            word = Character.toUpperCase(word.charAt(0)) + word.substring(1);
            StringBuilder buf = new StringBuilder();
            Matcher matcher = Pattern.compile("[A-Z]{2,}").matcher(word);
            int end = 0;

            while(matcher.find()) {
                int start = matcher.start();
                end = matcher.end();
                buf.append(word.substring(0, start + 1));
                buf.append(matcher.group().substring(1).toLowerCase());
            }

            buf.append(word.substring(end));
            String result = buf.toString();
            return result.substring(0, result.length() - 1) + Character.toLowerCase(result.charAt(result.length() - 1));
        }
    }

    private static String innerRandom(int len) {
        long randomLong = SMALL_RANDOM.getAndIncrement();
        if (randomLong > 4611686018427387903L) {
            SMALL_RANDOM.set(0L);
        }

        String random = String.valueOf(randomLong);
        return random.length() < len ? org.apache.commons.lang3.StringUtils.leftPad(random, len, "0") : random.substring(0, len);
    }
}
