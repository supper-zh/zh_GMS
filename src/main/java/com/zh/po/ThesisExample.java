package com.zh.po;

import java.util.ArrayList;
import java.util.List;

public class ThesisExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ThesisExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andThesisIdIsNull() {
            addCriterion("thesis_id is null");
            return (Criteria) this;
        }

        public Criteria andThesisIdIsNotNull() {
            addCriterion("thesis_id is not null");
            return (Criteria) this;
        }

        public Criteria andThesisIdEqualTo(Long value) {
            addCriterion("thesis_id =", value, "thesisId");
            return (Criteria) this;
        }

        public Criteria andThesisIdNotEqualTo(Long value) {
            addCriterion("thesis_id <>", value, "thesisId");
            return (Criteria) this;
        }

        public Criteria andThesisIdGreaterThan(Long value) {
            addCriterion("thesis_id >", value, "thesisId");
            return (Criteria) this;
        }

        public Criteria andThesisIdGreaterThanOrEqualTo(Long value) {
            addCriterion("thesis_id >=", value, "thesisId");
            return (Criteria) this;
        }

        public Criteria andThesisIdLessThan(Long value) {
            addCriterion("thesis_id <", value, "thesisId");
            return (Criteria) this;
        }

        public Criteria andThesisIdLessThanOrEqualTo(Long value) {
            addCriterion("thesis_id <=", value, "thesisId");
            return (Criteria) this;
        }

        public Criteria andThesisIdIn(List<Long> values) {
            addCriterion("thesis_id in", values, "thesisId");
            return (Criteria) this;
        }

        public Criteria andThesisIdNotIn(List<Long> values) {
            addCriterion("thesis_id not in", values, "thesisId");
            return (Criteria) this;
        }

        public Criteria andThesisIdBetween(Long value1, Long value2) {
            addCriterion("thesis_id between", value1, value2, "thesisId");
            return (Criteria) this;
        }

        public Criteria andThesisIdNotBetween(Long value1, Long value2) {
            addCriterion("thesis_id not between", value1, value2, "thesisId");
            return (Criteria) this;
        }

        public Criteria andSIdIsNull() {
            addCriterion("s_id is null");
            return (Criteria) this;
        }

        public Criteria andSIdIsNotNull() {
            addCriterion("s_id is not null");
            return (Criteria) this;
        }

        public Criteria andSIdEqualTo(String value) {
            addCriterion("s_id =", value, "sId");
            return (Criteria) this;
        }

        public Criteria andSIdNotEqualTo(String value) {
            addCriterion("s_id <>", value, "sId");
            return (Criteria) this;
        }

        public Criteria andSIdGreaterThan(String value) {
            addCriterion("s_id >", value, "sId");
            return (Criteria) this;
        }

        public Criteria andSIdGreaterThanOrEqualTo(String value) {
            addCriterion("s_id >=", value, "sId");
            return (Criteria) this;
        }

        public Criteria andSIdLessThan(String value) {
            addCriterion("s_id <", value, "sId");
            return (Criteria) this;
        }

        public Criteria andSIdLessThanOrEqualTo(String value) {
            addCriterion("s_id <=", value, "sId");
            return (Criteria) this;
        }

        public Criteria andSIdLike(String value) {
            addCriterion("s_id like", value, "sId");
            return (Criteria) this;
        }

        public Criteria andSIdNotLike(String value) {
            addCriterion("s_id not like", value, "sId");
            return (Criteria) this;
        }

        public Criteria andSIdIn(List<String> values) {
            addCriterion("s_id in", values, "sId");
            return (Criteria) this;
        }

        public Criteria andSIdNotIn(List<String> values) {
            addCriterion("s_id not in", values, "sId");
            return (Criteria) this;
        }

        public Criteria andSIdBetween(String value1, String value2) {
            addCriterion("s_id between", value1, value2, "sId");
            return (Criteria) this;
        }

        public Criteria andSIdNotBetween(String value1, String value2) {
            addCriterion("s_id not between", value1, value2, "sId");
            return (Criteria) this;
        }

        public Criteria andTitlIdIsNull() {
            addCriterion("titl_id is null");
            return (Criteria) this;
        }

        public Criteria andTitlIdIsNotNull() {
            addCriterion("titl_id is not null");
            return (Criteria) this;
        }

        public Criteria andTitlIdEqualTo(Long value) {
            addCriterion("titl_id =", value, "titlId");
            return (Criteria) this;
        }

        public Criteria andTitlIdNotEqualTo(Long value) {
            addCriterion("titl_id <>", value, "titlId");
            return (Criteria) this;
        }

        public Criteria andTitlIdGreaterThan(Long value) {
            addCriterion("titl_id >", value, "titlId");
            return (Criteria) this;
        }

        public Criteria andTitlIdGreaterThanOrEqualTo(Long value) {
            addCriterion("titl_id >=", value, "titlId");
            return (Criteria) this;
        }

        public Criteria andTitlIdLessThan(Long value) {
            addCriterion("titl_id <", value, "titlId");
            return (Criteria) this;
        }

        public Criteria andTitlIdLessThanOrEqualTo(Long value) {
            addCriterion("titl_id <=", value, "titlId");
            return (Criteria) this;
        }

        public Criteria andTitlIdIn(List<Long> values) {
            addCriterion("titl_id in", values, "titlId");
            return (Criteria) this;
        }

        public Criteria andTitlIdNotIn(List<Long> values) {
            addCriterion("titl_id not in", values, "titlId");
            return (Criteria) this;
        }

        public Criteria andTitlIdBetween(Long value1, Long value2) {
            addCriterion("titl_id between", value1, value2, "titlId");
            return (Criteria) this;
        }

        public Criteria andTitlIdNotBetween(Long value1, Long value2) {
            addCriterion("titl_id not between", value1, value2, "titlId");
            return (Criteria) this;
        }

        public Criteria andFIdIsNull() {
            addCriterion("f_id is null");
            return (Criteria) this;
        }

        public Criteria andFIdIsNotNull() {
            addCriterion("f_id is not null");
            return (Criteria) this;
        }

        public Criteria andFIdEqualTo(Long value) {
            addCriterion("f_id =", value, "fId");
            return (Criteria) this;
        }

        public Criteria andFIdNotEqualTo(Long value) {
            addCriterion("f_id <>", value, "fId");
            return (Criteria) this;
        }

        public Criteria andFIdGreaterThan(Long value) {
            addCriterion("f_id >", value, "fId");
            return (Criteria) this;
        }

        public Criteria andFIdGreaterThanOrEqualTo(Long value) {
            addCriterion("f_id >=", value, "fId");
            return (Criteria) this;
        }

        public Criteria andFIdLessThan(Long value) {
            addCriterion("f_id <", value, "fId");
            return (Criteria) this;
        }

        public Criteria andFIdLessThanOrEqualTo(Long value) {
            addCriterion("f_id <=", value, "fId");
            return (Criteria) this;
        }

        public Criteria andFIdIn(List<Long> values) {
            addCriterion("f_id in", values, "fId");
            return (Criteria) this;
        }

        public Criteria andFIdNotIn(List<Long> values) {
            addCriterion("f_id not in", values, "fId");
            return (Criteria) this;
        }

        public Criteria andFIdBetween(Long value1, Long value2) {
            addCriterion("f_id between", value1, value2, "fId");
            return (Criteria) this;
        }

        public Criteria andFIdNotBetween(Long value1, Long value2) {
            addCriterion("f_id not between", value1, value2, "fId");
            return (Criteria) this;
        }

        public Criteria andAgreeIsNull() {
            addCriterion("agree is null");
            return (Criteria) this;
        }

        public Criteria andAgreeIsNotNull() {
            addCriterion("agree is not null");
            return (Criteria) this;
        }

        public Criteria andAgreeEqualTo(String value) {
            addCriterion("agree =", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeNotEqualTo(String value) {
            addCriterion("agree <>", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeGreaterThan(String value) {
            addCriterion("agree >", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeGreaterThanOrEqualTo(String value) {
            addCriterion("agree >=", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeLessThan(String value) {
            addCriterion("agree <", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeLessThanOrEqualTo(String value) {
            addCriterion("agree <=", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeLike(String value) {
            addCriterion("agree like", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeNotLike(String value) {
            addCriterion("agree not like", value, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeIn(List<String> values) {
            addCriterion("agree in", values, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeNotIn(List<String> values) {
            addCriterion("agree not in", values, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeBetween(String value1, String value2) {
            addCriterion("agree between", value1, value2, "agree");
            return (Criteria) this;
        }

        public Criteria andAgreeNotBetween(String value1, String value2) {
            addCriterion("agree not between", value1, value2, "agree");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}