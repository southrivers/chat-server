package org.visitor;

import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.UseStatement;
import net.sf.jsqlparser.statement.select.*;

import java.util.List;

public class MyStatementVisitor extends StatementVisitorAdapter {

    @Override
    public void visit(Select select) {
        select.accept(new SelectVisitorAdapter() {
            @Override
            public void visit(PlainSelect plainSelect) {
                FromItem fromItem = plainSelect.getFromItem();
                GroupByElement groupBy = plainSelect.getGroupBy();
                List<SelectItem<?>> selectItems = plainSelect.getSelectItems();
                for (SelectItem<?> selectItem : selectItems) {
                    selectItem.getExpression().accept(new ExpressionVisitorAdapter() {
                        // 保留selectItems，解析selectItem
                    });
                }
            }
        });
    }

    @Override
    public void visit(UseStatement use) {
        super.visit(use);
    }
}
