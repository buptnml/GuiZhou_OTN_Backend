package com.bupt.util;


import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Example工厂，根据输入的条件生产所需的Example
 * 采用了建造者模式的一种形式
 * 主要目的是为了协助Service和Facade层的代码编写，节省代码
 * 1.第一步是实现纯粹只有andCondition的代码
 * 2.第二部增加OrderBy的功能
 */
public class ExampleFactory {
    private Example example;

    public ExampleFactory(Object object, Builder builder) {
        this.example = new Example(object.getClass());
        Example.Criteria criteria = this.example.createCriteria();
        builder.conditionList.stream().forEach(criterion -> {
            switch (criterion.condition.size) {

            }
//            try {
            //todo 修改 getMethod(criteria.getClass(),criterion.getMethodName()).invoke(criteria, criterion.getArgs());
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
        });
    }

    private Method getMethod(Class<?> entryClass, String methodName) {
        Method[] methods = entryClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }


    public Example getExample() {
        return example;
    }

    enum methodNames {
        andIsNull, andIsNotNull, andEqualTo, andNotEqualTo, andGreaterThan, andGreaterThanOrEqualTo, andLessThan,
        andLessThanOrEqualTo, andIn, andNotIn, andBetween, andNotBetween, andLike, andNotLike
    }

    private static class Criterion {
        private methodNames methodName;
        private Condition condition;

        public Criterion(methodNames methodName, Condition condition) {
            this.methodName = methodName;
            this.condition = condition;
        }

        public Object[] getArgs() {
            return condition.getArgs();
        }

        public String getMethodName() {
            return methodName.toString();
        }
    }

    private static class Condition {
        private String property;
        private Object value;
        private Iterable values;
        private String likeValue;
        private Object betweenValue1;
        private Object betweenValue2;
        private int size;

        /*for isNUll,IsNotNull*/
        public Condition(String property) {
            this.property = property;
            size = 1;
            this.value = null;
            this.values = null;
            this.likeValue = null;
            this.betweenValue1 = null;
            this.betweenValue2 = null;
        }

        /*for equalTo,notEqualTo,greaterThan,greaterThanOrEqualTo,lessThan,lessThanOrEqualTo*/
        public Condition(String property, Object value) {
            this.property = property;
            this.value = value;
            size = 2;
            this.values = null;
            this.likeValue = null;
            this.betweenValue1 = null;
            this.betweenValue2 = null;
        }

        /*for in,notIn*/
        public Condition(String property, Iterable values) {
            this.property = property;
            this.values = values;
            size = 2;
            this.value = null;
            this.likeValue = null;
            this.betweenValue1 = null;
            this.betweenValue2 = null;
        }

        /*for between,notBetween*/
        public Condition(String property, Object value1, Object value2) {
            this.property = property;
            this.betweenValue1 = value1;
            this.betweenValue2 = value2;
            size = 3;
            this.value = null;
            this.values = null;
            this.likeValue = null;
        }

        /*for like,notLike*/
        public Condition(String property, String value) {
            this.property = property;
            this.likeValue = value;
            size = 2;
            this.betweenValue1 = null;
            this.betweenValue2 = null;
            this.value = null;
            this.values = null;
        }

        public Object[] getArgs() {
            Object[] results = new Object[size];
            results[0] = property;
            if (size > 1) {
                results[1] = value == null ? values == null ? likeValue == null ? betweenValue1 : likeValue : values :
                        value;
            }
            if (size == 3) {
                results[2] = betweenValue2;
            }
            return results;
        }

    }

    public static class Builder {
        private List<Criterion> conditionList = new ArrayList<>();

        public List<Criterion> getConditionList() {
            return conditionList;
        }

        public Builder andIsNull(String property) {
            conditionList.add(new Criterion(methodNames.andIsNull, new Condition(property)));
            return this;
        }

        public Builder andIsNotNull(String property) {
            conditionList.add(new Criterion(methodNames.andIsNotNull, new Condition(property)));
            return this;
        }

        public Builder andEqualTo(String property, Object value) {
            conditionList.add(new Criterion(methodNames.andEqualTo, new Condition(property, value)));
            return this;
        }

        public Builder andNotEqualTo(String property, Object value) {
            conditionList.add(new Criterion(methodNames.andNotEqualTo, new Condition(property, value)));
            return this;
        }

        public Builder andGreaterThan(String property, Object value) {
            conditionList.add(new Criterion(methodNames.andGreaterThan, new Condition(property, value)));
            return this;
        }

        public Builder andGreaterThanOrEqualTo(String property, Object value) {
            conditionList.add(new Criterion(methodNames.andGreaterThanOrEqualTo, new Condition(property, value)));
            return this;
        }

        public Builder andLessThan(String property, Object value) {
            conditionList.add(new Criterion(methodNames.andLessThan, new Condition(property, value)));
            return this;
        }

        public Builder andLessThanOrEqualTo(String property, Object value) {
            conditionList.add(new Criterion(methodNames.andLessThanOrEqualTo, new Condition(property, value)));
            return this;
        }

        public Builder andIn(String property, Iterable values) {
            conditionList.add(new Criterion(methodNames.andIn, new Condition(property, values)));
            return this;
        }

        public Builder andNotIn(String property, Iterable values) {
            conditionList.add(new Criterion(methodNames.andNotIn, new Condition(property, values)));
            return this;
        }

        public Builder andBetween(String property, Object value1, Object value2) {
            conditionList.add(new Criterion(methodNames.andBetween, new Condition(property, value1, value2)));
            return this;
        }

        public Builder andNotBetween(String property, Object value1, Object value2) {
            conditionList.add(new Criterion(methodNames.andNotBetween, new Condition(property, value1, value2)));
            return this;
        }

        public Builder andLike(String property, String value) {
            conditionList.add(new Criterion(methodNames.andLike, new Condition(property, value)));
            return this;
        }

        public Builder andNotLike(String property, String value) {
            conditionList.add(new Criterion(methodNames.andNotLike, new Condition(property, value)));
            return this;
        }

        public ExampleFactory build(Object object) {
            return new ExampleFactory(object, this);
        }

    }
}
