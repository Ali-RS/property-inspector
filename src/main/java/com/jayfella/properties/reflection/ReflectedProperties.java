//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.jayfella.properties.reflection;

import org.reflections.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReflectedProperties {
    private Object object;
    private List<Method> getters = new ArrayList<>();
    private List<Method> setters = new ArrayList<>();
    String[] ignoredProperties;

    public ReflectedProperties(Object object, String... ignoredProperties) {
        this.object = object;
        this.ignoredProperties = ignoredProperties;
        this.create();
    }

    public Object getObject() {
        return this.object;
    }

    public List<Method> getGetters() {
        return this.getters;
    }

    public List<Method> getSetters() {
        return this.setters;
    }

    private void create() {
        Set<Method> getters = ReflectionUtils.getAllMethods(this.object.getClass(), ReflectionUtils.withModifier(1), ReflectionUtils.withPrefix("get"), ReflectionUtils.withParametersCount(0));
        Set<Method> gettersBoolean = ReflectionUtils.getAllMethods(this.object.getClass(), ReflectionUtils.withModifier(1), ReflectionUtils.withPrefix("is"), ReflectionUtils.withParametersCount(0));
        getters.addAll(gettersBoolean);
        Set<Method> setters = ReflectionUtils.getAllMethods(this.object.getClass(), ReflectionUtils.withModifier(1), ReflectionUtils.withPrefix("set"), ReflectionUtils.withParametersCount(1));
        Set<Method> settersBoolean = ReflectionUtils.getAllMethods(this.object.getClass(), ReflectionUtils.withModifier(1), ReflectionUtils.withPrefix("is"), ReflectionUtils.withParametersCount(1));
        setters.addAll(settersBoolean);
        this.removeIgnoredGetters(getters);
        this.cleanGetters(getters, setters);
        this.cleanSetters(getters, setters);
        this.cleanGetters(getters, setters);
        this.getters.addAll(getters);
        this.setters.addAll(setters);
    }

    private void removeIgnoredGetters(Set<Method> getters) {
        getters.removeIf((getter) -> {
            String suffix = getSuffix(getter.getName());
            String[] var3 = this.ignoredProperties;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String ignore = var3[var5];
                if (ignore.equalsIgnoreCase(suffix)) {
                    return true;
                }
            }

            return false;
        });
    }

    private void cleanGetters(Set<Method> getters, Set<Method> setters) {
        getters.removeIf((getter) -> {
            if (getter.toString().contains("abstract")) {
                return true;
            } else {
                String suffix = getSuffix(getter.getName());
                Method setMethod = setters.stream().filter((m) -> {
                    String setterSuffix = getSuffix(m.getName());
                    return setterSuffix.equalsIgnoreCase(suffix);
                }).findFirst().orElse(null);
                return setMethod == null;
            }
        });
    }

    private void cleanSetters(Set<Method> getters, Set<Method> setters) {
        setters.removeIf((setter) -> {
            if (setter.toString().contains("abstract")) {
                return true;
            } else {
                String suffix = getSuffix(setter.getName());
                Method getter = getters.stream().filter((g) -> {
                    String getterSuffix = getSuffix(g.getName());
                    return getterSuffix.equalsIgnoreCase(suffix);
                }).findFirst().orElse(null);
                if (getter == null) {
                    return true;
                } else {
                    Class<?>[] params = setter.getParameterTypes();
                    boolean same = params[0].isAssignableFrom(getter.getReturnType());
                    return !same;
                }
            }
        });
    }

    public static String getSuffix(String name) {
        if (name.startsWith("is")) {
            name = name.substring(2);
        } else if (name.startsWith("get")) {
            name = name.substring(3);
        } else if (name.startsWith("set")) {
            name = name.substring(3);
        }

        return name;
    }
}
