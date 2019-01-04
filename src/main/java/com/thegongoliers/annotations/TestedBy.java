package com.thegongoliers.annotations;

public @interface TestedBy {
    String team() default "5112";
    String year() default "2018";
}
