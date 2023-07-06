package hk.hku.cs.calcite.entity;

public class Plan {
    private int id;
    private String sql;
    private String parsedSql;
    private String validatedSql;
    private String logicalPlan;
    private String physicalPlan;
    private String jsonPlan;
    private String xmlPlan;
    private String dotPlan;

    public Plan(int id, String sql) {
        this.id = id;
        this.sql = sql;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getParsedSql() {
        return parsedSql;
    }

    public void setParsedSql(String parsedSql) {
        this.parsedSql = parsedSql;
    }

    public String getValidatedSql() {
        return validatedSql;
    }

    public void setValidatedSql(String validatedSql) {
        this.validatedSql = validatedSql;
    }

    public String getLogicalPlan() {
        return logicalPlan;
    }

    public void setLogicalPlan(String logicalPlan) {
        this.logicalPlan = logicalPlan;
    }

    public String getPhysicalPlan() {
        return physicalPlan;
    }

    public void setPhysicalPlan(String physicalPlan) {
        this.physicalPlan = physicalPlan;
    }

    public String getJsonPlan() {
        return jsonPlan;
    }

    public void setJsonPlan(String jsonPlan) {
        this.jsonPlan = jsonPlan;
    }

    public String getXmlPlan() {
        return xmlPlan;
    }

    public void setXmlPlan(String xmlPlan) {
        this.xmlPlan = xmlPlan;
    }

    public String getDotPlan() {
        return dotPlan;
    }

    public void setDotPlan(String dotPlan) {
        this.dotPlan = dotPlan;
    }
}
