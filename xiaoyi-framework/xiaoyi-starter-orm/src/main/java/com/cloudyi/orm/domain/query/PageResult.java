package com.cloudyi.orm.domain.query;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "分页结果")
@Data
public class PageResult<T> {

    @Schema(title = "数据", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private List<T> list;

    @Schema(description = "总量",  requiredMode = Schema.RequiredMode.REQUIRED)
    private Long total;

    @Schema(description = "分页大小",  requiredMode = Schema.RequiredMode.REQUIRED)
    private Long size;

    @Schema(description = "当前页",  requiredMode = Schema.RequiredMode.REQUIRED)
    private Long current;

    @Schema(description = "总页数",  requiredMode = Schema.RequiredMode.REQUIRED)
    private Long Pages;

    public PageResult() {

    }

    public PageResult(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }

    public PageResult(Long total) {
        this.list = new ArrayList<>();
        this.total = total;
    }

    public static <T> PageResult<T> empty() {
        return new PageResult<>(0L);
    }

}
