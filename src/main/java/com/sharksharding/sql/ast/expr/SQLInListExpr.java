/*
 * Copyright 1999-2101 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sharksharding.sql.ast.expr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sharksharding.sql.ast.SQLExpr;
import com.sharksharding.sql.ast.SQLExprImpl;
import com.sharksharding.sql.visitor.SQLASTVisitor;

public class SQLInListExpr extends SQLExprImpl implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean           not              = false;
    private SQLExpr           expr;
    private List<SQLExpr>     targetList       = new ArrayList<SQLExpr>();

    public SQLInListExpr(){

    }

    public SQLInListExpr(SQLExpr expr){

        this.expr = expr;
    }

    public SQLInListExpr(SQLExpr expr, boolean not){

        this.expr = expr;
        this.not = not;
    }

    public boolean isNot() {
        return this.not;
    }

    public void setNot(boolean not) {
        this.not = not;
    }

    public SQLExpr getExpr() {
        return this.expr;
    }

    public void setExpr(SQLExpr expr) {
        this.expr = expr;
    }

    public List<SQLExpr> getTargetList() {
        return this.targetList;
    }

    public void setTargetList(List<SQLExpr> targetList) {
        this.targetList = targetList;
    }

    protected void accept0(SQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, this.expr);
            acceptChild(visitor, this.targetList);
        }

        visitor.endVisit(this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((expr == null) ? 0 : expr.hashCode());
        result = prime * result + (not ? 1231 : 1237);
        result = prime * result + ((targetList == null) ? 0 : targetList.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SQLInListExpr other = (SQLInListExpr) obj;
        if (expr == null) {
            if (other.expr != null) {
                return false;
            }
        } else if (!expr.equals(other.expr)) {
            return false;
        }
        if (not != other.not) {
            return false;
        }
        if (targetList == null) {
            if (other.targetList != null) {
                return false;
            }
        } else if (!targetList.equals(other.targetList)) {
            return false;
        }
        return true;
    }
}