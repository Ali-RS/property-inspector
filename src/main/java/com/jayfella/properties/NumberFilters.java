//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.jayfella.properties;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class NumberFilters {
    private static NumberFilters.IsDigit IS_DIGIT = new NumberFilters.IsDigit();
    private static NumberFilters.IsPeriod IS_PERIOD = new NumberFilters.IsPeriod();
    private static NumberFilters.IsMinus IS_MINUS = new NumberFilters.IsMinus();
    private static Function<Character, Character> FLOAT = charFilter(Predicates.or(IS_DIGIT, IS_PERIOD, IS_MINUS));

    public NumberFilters() {
    }

    public static Function<Character, Character> floatFilter() {
        return FLOAT;
    }

    public static Function<Character, Character> charFilter(Predicate<Character> predicate) {
        return new NumberFilters.CharFilter(predicate);
    }

    /**
     * Removes multiple period and minus characters from a String float.
     * @param text input text
     * @return the formatted float String.
     */
    public static String filterFloatValue(String text) {
        text = removeMultipleChars(text, '.', 0);
        text = removeMultipleChars(text,  '-', 1);

        return text;
    }

    public static String removeMultipleChars(String text, char character, int start) {

        if (text.length() == 0) {
            return "0";
        } else {

            if (start >= text.length()) {
                return text;
            }

            int charCount = 0;
            StringBuilder stringBuilder = new StringBuilder(text);
            int length = stringBuilder.length();

            for(int i = start; i < length; ++i) {
                char c = text.charAt(i);
                if (c == character) {
                    ++charCount;
                }

                if (charCount > 1) {
                    stringBuilder.deleteCharAt(i);
                    --i;
                    --length;
                }
            }

            return stringBuilder.toString();
        }

    }

    private static class CharFilter implements Function<Character, Character> {
        private Predicate<Character> predicate;

        public CharFilter(Predicate<Character> predicate) {
            this.predicate = predicate;
        }

        public Character apply(Character c) {
            return this.predicate.apply(c) ? c : null;
        }
    }

    private static class IsPeriod implements Predicate<Character> {
        private IsPeriod() {
        }

        public boolean apply(Character input) {
            return input != null && input.equals('.');
        }
    }

    private static class IsDigit implements Predicate<Character> {
        private IsDigit() {
        }

        public boolean apply(Character c) {
            return Character.isDigit(c);
        }
    }

    private static class IsMinus implements Predicate<Character> {
        private IsMinus() {
        }

        @Override
        public boolean apply(Character input) {
            return input != null && input.equals('-');
        }
    }

}
