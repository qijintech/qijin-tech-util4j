package tech.qijin.util4j.file.test;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import tech.qijin.util4j.file.excel.ExcelImporter;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExcelTest {
    @Test
    public void testImporter() throws Exception {
        File file = new File("/tmp/test2.xlsx");
        InputStream inputStream = new FileInputStream(file);
        List<TestExcelSheet1> list1 = ExcelImporter.importExcel(inputStream, TestExcelSheet1.class, 0);
        System.out.println(list1);
        Set<String> gameLevelLibIdSets = list1.stream()
                .map(TestExcelSheet1::getGameLevelLibId)
                .collect(Collectors.toSet());
        System.out.println(gameLevelLibIdSets);
    }
}
