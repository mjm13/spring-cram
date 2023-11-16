package com.meijm.toolbox.easyexcel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;
import com.alibaba.excel.write.handler.context.WorkbookWriteHandlerContext;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
public class EasyExcelTest {
    public static void main(String[] args) {
        Faker faker = Faker.instance(Locale.CHINA);
        List<DemoVo> datas = new ArrayList<>();
        for (int i = 0; i < 1048575; i++) {
            DemoVo demoVo = new DemoVo();
            demoVo.setId(faker.idNumber().invalid());
            demoVo.setName(faker.company().name());
            datas.add(demoVo);
        }
        File file = new File("D:\\usr\\temp.xlsx");
        try(ExcelWriter excelWriter = EasyExcelFactory.write(file).head(DemoVo.class).build()) {
            for (int i = 1; i <= 9; i++) {
                List<DemoVo> temp = datas.subList((i - 1)*100000, i * 100000);
                int finalI = i;
                WriteSheet writeSheet = EasyExcelFactory.writerSheet("Sheet1")
                        .registerWriteHandler(new SheetWriteHandler() {
                            public void afterWorkbookDispose(WriteWorkbookHolder writeWorkbookHolder) {
                                log.info("写入完成:{}", finalI);
                            }
                        })
                        .build();
                excelWriter.write(temp,writeSheet);
            }
        }
    }
}
