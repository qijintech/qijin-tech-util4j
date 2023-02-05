package tech.qijin.util4j.file.excel;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

import java.io.InputStream;
import java.util.List;

public class ExcelImporter {

    public static <T> List<T> importExcel(InputStream inputstream, Class<?> pojoClass, Integer sheetIdx) throws Exception {
        ImportParams params = new ImportParams();
        // 设置表格坐标
        params.setStartSheetIndex(sheetIdx);
        // 校验Excel文件，去掉空行
        params.setNeedVerify(true);
        return ExcelImportUtil.importExcel(inputstream, pojoClass, params);
    }
}
