package com.thegongoliers.annotations;

/**
 * Indicates if a class or method was used by a team in competition code
 */
public @interface UsedInCompetition {
    String team() default "5112";
    String year();
}
