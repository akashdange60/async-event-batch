package com.async_event.util;

import java.util.Map;

public class CsvUtil {
	
	public static String toCsv(Map<String, Object> metrics) {

        StringBuilder csv = new StringBuilder();
        csv.append("Metric,Value\n");

        metrics.forEach((key, value) ->
                csv.append(key)
                   .append(",")
                   .append(value)
                   .append("\n")
        );

        return csv.toString();
    }

}
