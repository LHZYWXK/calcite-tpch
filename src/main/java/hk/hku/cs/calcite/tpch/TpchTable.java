package hk.hku.cs.calcite.tpch;

import org.apache.calcite.DataContext;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.rel.type.*;
import org.apache.calcite.schema.ScannableTable;
import org.apache.calcite.schema.impl.AbstractTable;

import java.util.List;

public class TpchTable extends AbstractTable implements ScannableTable {
    private final String tableName;
    private final List<String> fieldNames;
    private final List<RelDataType> fieldTypes;

    private final RelDataType dataType;

    public TpchTable(String tableName, List<String> fieldNames, List<RelDataType> fieldTypes, RelDataType dataType) {
        this.tableName = tableName;
        this.fieldNames = fieldNames;
        this.fieldTypes = fieldTypes;
        this.dataType = dataType;
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public RelDataType getRowType(RelDataTypeFactory typeFactory) {
        return typeFactory.copyType(dataType);
    }

    @Override
    public Enumerable<Object[]> scan(DataContext root) {
        throw new UnsupportedOperationException("Not implemented");
    }

}
