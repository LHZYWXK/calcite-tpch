package hk.hku.cs.calcite;

import hk.hku.cs.calcite.util.TpchUtil;
import org.junit.jupiter.api.Test;

public class TpchUtilTests {
    @Test
    public void testSargHander() {
        String test = "{\n  \"rels\": [\n    {\n      \"id\": \"0\",\n      \"relOp\": \"org.apache.calcite.adapter.enumerable.EnumerableTableScan\",\n      \"table\": [\n        \"SUPPLIER\"\n      ],\n      \"inputs\": []\n    },\n    {\n      \"id\": \"1\",\n      \"relOp\": \"org.apache.calcite.adapter.enumerable.EnumerableTableScan\",\n      \"table\": [\n        \"LINEITEM\"\n      ],\n      \"inputs\": []\n    },\n    {\n      \"id\": \"2\",\n      \"relOp\": \"org.apache.calcite.adapter.enumerable.EnumerableFilter\",\n      \"condition\": {\n        \"op\": {\n          \"name\": \"SEARCH\",\n          \"kind\": \"SEARCH\",\n          \"syntax\": \"INTERNAL\"\n        },\n        \"operands\": [\n          {\n            \"input\": 10,\n            \"name\": \"$10\"\n          },\n          {\n            \"literal\": Sarg[[1995-01-01..1996-12-31]],\n            \"type\": {\n              \"type\": \"DATE\",\n              \"nullable\": false\n            }\n          }\n        ]\n      }\n    },\n    {\n      \"id\": \"3\",\n      \"relOp\": \"org.apache.calcite.adapter.enumerable.EnumerableHashJoin\",\n      \"condition\": {\n        \"op\": {\n          \"name\": \"=\",\n          \"kind\": \"EQUALS\",\n          \"syntax\": \"BINARY\"\n        },\n        \"operands\": [\n          {\n            \"input\": 0,\n            \"name\": \"$0\"\n          },\n          {\n            \"input\": 9,\n            \"name\": \"$9\"\n          }\n        ]\n      },\n      \"joinType\": \"inner\",\n      \"inputs\": [\n        \"0\",\n        \"2\"\n      ]\n    },\n    {\n      \"id\": \"4\",\n      \"relOp\": \"org.apache.calcite.adapter.enumerable.EnumerableTableScan\",\n      \"table\": [\n        \"ORDERS\"\n      ],\n      \"inputs\": []\n    },\n    {\n      \"id\": \"5\",\n      \"relOp\": \"org.apache.calcite.adapter.enumerable.EnumerableHashJoin\",\n      \"condition\": {\n        \"op\": {\n          \"name\": \"=\",\n          \"kind\": \"EQUALS\",\n          \"syntax\": \"BINARY\"\n        },\n        \"operands\": [\n          {\n            \"input\": 7,\n            \"name\": \"$7\"\n          },\n          {\n            \"input\": 23,\n            \"name\": \"$23\"\n          }\n        ]\n      },\n      \"joinType\": \"inner\",\n      \"inputs\": [\n        \"3\",\n        \"4\"\n      ]\n    },\n    {\n      \"id\": \"6\",\n      \"relOp\": \"org.apache.calcite.adapter.enumerable.EnumerableTableScan\",\n      \"table\": [\n        \"CUSTOMER\"\n      ],\n      \"inputs\": []\n    },\n    {\n      \"id\": \"7\",\n      \"relOp\": \"org.apache.calcite.adapter.enumerable.EnumerableHashJoin\",\n      \"condition\": {\n        \"op\": {\n          \"name\": \"=\",\n          \"kind\": \"EQUALS\",\n          \"syntax\": \"BINARY\"\n        },\n        \"operands\": [\n          {\n            \"input\": 24,\n            \"name\": \"$24\"\n          },\n          {\n            \"input\": 32,\n            \"name\": \"$32\"\n          }\n        ]\n      },\n      \"joinType\": \"inner\",\n      \"inputs\": [\n        \"5\",\n        \"6\"\n      ]\n    },\n    {\n      \"id\": \"8\",\n      \"relOp\": \"org.apache.calcite.adapter.enumerable.EnumerableTableScan\",\n      \"table\": [\n        \"NATION\"\n      ],\n      \"inputs\": []\n    },\n    {\n      \"id\": \"9\",\n      \"relOp\": \"org.apache.calcite.adapter.enumerable.EnumerableHashJoin\",\n      \"condition\": {\n        \"op\": {\n          \"name\": \"=\",\n          \"kind\": \"EQUALS\",\n          \"syntax\": \"BINARY\"\n        },\n        \"operands\": [\n          {\n            \"input\": 3,\n            \"name\": \"$3\"\n          },\n          {\n            \"input\": 40,\n            \"name\": \"$40\"\n          }\n        ]\n      },\n      \"joinType\": \"inner\",\n      \"inputs\": [\n        \"7\",\n        \"8\"\n      ]\n    },\n    {\n      \"id\": \"10\",\n      \"relOp\": \"org.apache.calcite.adapter.enumerable.EnumerableHashJoin\",\n      \"condition\": {\n        \"op\": {\n          \"name\": \"AND\",\n          \"kind\": \"AND\",\n          \"syntax\": \"BINARY\"\n        },\n        \"operands\": [\n          {\n            \"op\": {\n              \"name\": \"=\",\n              \"kind\": \"EQUALS\",\n              \"syntax\": \"BINARY\"\n            },\n            \"operands\": [\n              {\n                \"input\": 35,\n                \"name\": \"$35\"\n              },\n              {\n                \"input\": 44,\n                \"name\": \"$44\"\n              }\n            ]\n          },\n          {\n            \"op\": {\n              \"name\": \"OR\",\n              \"kind\": \"OR\",\n              \"syntax\": \"BINARY\"\n            },\n            \"operands\": [\n              {\n                \"op\": {\n                  \"name\": \"AND\",\n                  \"kind\": \"AND\",\n                  \"syntax\": \"BINARY\"\n                },\n                \"operands\": [\n                  {\n                    \"op\": {\n                      \"name\": \"=\",\n                      \"kind\": \"EQUALS\",\n                      \"syntax\": \"BINARY\"\n                    },\n                    \"operands\": [\n                      {\n                        \"input\": 41,\n                        \"name\": \"$41\"\n                      },\n                      {\n                        \"literal\": \"FRANCE\",\n                        \"type\": {\n                          \"type\": \"VARCHAR\",\n                          \"nullable\": false,\n                          \"precision\": -1\n                        }\n                      }\n                    ]\n                  },\n                  {\n                    \"op\": {\n                      \"name\": \"=\",\n                      \"kind\": \"EQUALS\",\n                      \"syntax\": \"BINARY\"\n                    },\n                    \"operands\": [\n                      {\n                        \"input\": 45,\n                        \"name\": \"$45\"\n                      },\n                      {\n                        \"literal\": \"GERMANY\",\n                        \"type\": {\n                          \"type\": \"VARCHAR\",\n                          \"nullable\": false,\n                          \"precision\": -1\n                        }\n                      }\n                    ]\n                  }\n                ]\n              },\n              {\n                \"op\": {\n                  \"name\": \"AND\",\n                  \"kind\": \"AND\",\n                  \"syntax\": \"BINARY\"\n                },\n                \"operands\": [\n                  {\n                    \"op\": {\n                      \"name\": \"=\",\n                      \"kind\": \"EQUALS\",\n                      \"syntax\": \"BINARY\"\n                    },\n                    \"operands\": [\n                      {\n                        \"input\": 41,\n                        \"name\": \"$41\"\n                      },\n                      {\n                        \"literal\": \"GERMANY\",\n                        \"type\": {\n                          \"type\": \"VARCHAR\",\n                          \"nullable\": false,\n                          \"precision\": -1\n                        }\n                      }\n                    ]\n                  },\n                  {\n                    \"op\": {\n                      \"name\": \"=\",\n                      \"kind\": \"EQUALS\",\n                      \"syntax\": \"BINARY\"\n                    },\n                    \"operands\": [\n                      {\n                        \"input\": 45,\n                        \"name\": \"$45\"\n                      },\n                      {\n                        \"literal\": \"FRANCE\",\n                        \"type\": {\n                          \"type\": \"VARCHAR\",\n                          \"nullable\": false,\n                          \"precision\": -1\n                        }\n                      }\n                    ]\n                  }\n                ]\n              }\n            ]\n          }\n        ]\n      },\n      \"joinType\": \"inner\",\n      \"inputs\": [\n        \"9\",\n        \"8\"\n      ]\n    },\n    {\n      \"id\": \"11\",\n      \"relOp\": \"org.apache.calcite.adapter.enumerable.EnumerableProject\",\n      \"fields\": [\n        \"SUPP_NATION\",\n        \"CUST_NATION\",\n        \"L_YEAR\",\n        \"VOLUME\"\n      ],\n      \"exprs\": [\n        {\n          \"input\": 41,\n          \"name\": \"$41\"\n        },\n        {\n          \"input\": 45,\n          \"name\": \"$45\"\n        },\n        {\n          \"op\": {\n            \"name\": \"EXTRACT\",\n            \"kind\": \"EXTRACT\",\n            \"syntax\": \"FUNCTION\"\n          },\n          \"operands\": [\n            {\n              \"literal\": \"YEAR\",\n              \"type\": {\n                \"type\": \"SYMBOL\",\n                \"nullable\": false\n              }\n            },\n            {\n              \"input\": 17,\n              \"name\": \"$17\"\n            }\n          ]\n        },\n        {\n          \"op\": {\n            \"name\": \"*\",\n            \"kind\": \"TIMES\",\n            \"syntax\": \"BINARY\"\n          },\n          \"operands\": [\n            {\n              \"input\": 12,\n              \"name\": \"$12\"\n            },\n            {\n              \"op\": {\n                \"name\": \"-\",\n                \"kind\": \"MINUS\",\n                \"syntax\": \"BINARY\"\n              },\n              \"operands\": [\n                {\n                  \"literal\": 1,\n                  \"type\": {\n                    \"type\": \"INTEGER\",\n                    \"nullable\": false\n                  }\n                },\n                {\n                  \"input\": 13,\n                  \"name\": \"$13\"\n                }\n              ]\n            }\n          ]\n        }\n      ]\n    },\n    {\n      \"id\": \"12\",\n      \"relOp\": \"org.apache.calcite.adapter.enumerable.EnumerableAggregate\",\n      \"group\": [\n        0,\n        1,\n        2\n      ],\n      \"aggs\": [\n        {\n          \"agg\": {\n            \"name\": \"SUM\",\n            \"kind\": \"SUM\",\n            \"syntax\": \"FUNCTION\"\n          },\n          \"type\": {\n            \"type\": \"DOUBLE\",\n            \"nullable\": true\n          },\n          \"distinct\": false,\n          \"operands\": [\n            3\n          ],\n          \"name\": \"REVENUE\"\n        }\n      ]\n    },\n    {\n      \"id\": \"13\",\n      \"relOp\": \"org.apache.calcite.adapter.enumerable.EnumerableSort\",\n      \"collation\": [\n        {\n          \"field\": 0,\n          \"direction\": \"ASCENDING\",\n          \"nulls\": \"LAST\"\n        },\n        {\n          \"field\": 1,\n          \"direction\": \"ASCENDING\",\n          \"nulls\": \"LAST\"\n        },\n        {\n          \"field\": 2,\n          \"direction\": \"ASCENDING\",\n          \"nulls\": \"LAST\"\n        }\n      ]\n    }\n  ]\n}";
        test = test + test.replace("Sarg[[1995-01-01..1996-12-31]]", "Sarg[[2020-1202..20202304]]");

        TpchUtil.sargHandler(test);
    }
}
