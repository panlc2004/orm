package com.czy.core.orm.sql.template;

import com.czy.core.orm.base.Procedure;
import com.czy.core.orm.sql.entity.EntityTable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by panlc on 2017-04-17.
 */
public class ProcedureSqlTemplate {

    /**
     *生成存储过程
     * @param procedure
     * @return
     */
    public static InputStream createSqlInputStream(Procedure procedure) {

        String process = FreeMarkerUtil.process("procedure", procedure);
        InputStream inputStream = new ByteArrayInputStream(process.getBytes());
        return inputStream;
    }
}
