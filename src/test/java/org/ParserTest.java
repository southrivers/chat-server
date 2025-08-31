package org;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import org.visitor.MyStatementVisitor;

public class ParserTest {

    static String sql = "SELECT \n" +
            "    d.department_name AS 部门名称,\n" +
            "    COUNT(e.employee_id) AS 员工总数,\n" +
            "    SUM(\n" +
            "        CASE \n" +
            "            WHEN e.salary >= 10000 THEN 1 \n" +
            "            ELSE 0 \n" +
            "        END\n" +
            "    ) AS 高薪员工数量,\n" +
            "    SUM(\n" +
            "        CASE \n" +
            "            WHEN e.salary < 10000 THEN e.salary \n" +
            "            ELSE 0 \n" +
            "        END\n" +
            "    ) AS 普通薪资总额,\n" +
            "    AVG(e.salary) AS 平均薪资,\n" +
            "    (SELECT MAX(salary) FROM employees WHERE department_id = d.department_id) AS 部门最高薪资\n" +
            "FROM \n" +
            "    departments d\n" +
            "LEFT JOIN \n" +
            "    employees e ON d.department_id = e.department_id\n" +
            "WHERE \n" +
            "    d.department_id IN (\n" +
            "        SELECT department_id \n" +
            "        FROM employees \n" +
            "        GROUP BY department_id \n" +
            "        HAVING COUNT(*) > 5\n" +
            "    )\n" +
            "GROUP BY \n" +
            "    d.department_id, d.department_name\n" +
            "HAVING \n" +
            "    AVG(e.salary) > 5000\n" +
            "ORDER BY \n" +
            "    平均薪资 DESC;";
    public static void main(String[] args) throws JSQLParserException {
        Statement parse = CCJSqlParserUtil.parse(sql);
        parse.accept(new MyStatementVisitor());
        System.out.println(parse);
    }
}
