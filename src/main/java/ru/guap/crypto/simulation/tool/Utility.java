package ru.guap.crypto.simulation.tool;

import java.time.format.DateTimeFormatter;

public class Utility {

    private static final String DATETIME_FORMAT = "yyyy-MM-dd";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
    public static final String MODEL_FILE_EXT = "nnb";

    // Exceptions
    public static final String EXCEPTION_PLEASE_CHECK_THAT_QUOTE_AND_CLOSE_RATE_AND_CLOSE_VOLUME_WERE_PROVIDED = "Please check that \"quote\" and \"closeRate\"  and \"closeVolume\" were provided";
    public static final String EXCEPTION_PLEASE_CHECK_THAT_QUOTE_AND_DATE_FROM_AND_DATE_TO_AND_INTERVAL_WERE_PROVIDED = "Please check that \"quote\" and \"dateFrom\" and \"dateTo\" and \"interval\" were provided";

}
