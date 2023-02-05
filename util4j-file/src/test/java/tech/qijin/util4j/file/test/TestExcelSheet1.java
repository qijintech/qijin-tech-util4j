package tech.qijin.util4j.file.test;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class TestExcelSheet1 {
    @Excel(name="game_level_grade_id")
    private String gameLevelGradeId;
    @Excel(name="game_level_lib_id")
    private String gameLevelLibId;
}
