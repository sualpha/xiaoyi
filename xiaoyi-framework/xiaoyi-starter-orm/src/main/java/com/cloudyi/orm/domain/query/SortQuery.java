package com.cloudyi.orm.domain.query;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import static com.baomidou.mybatisplus.core.enums.SqlKeyword.*;


@Data
@Valid
public class SortQuery {

    @Schema(title = "排序字段", requiredMode = Schema.RequiredMode.REQUIRED, example = "id")
    private String orderBy;

    @Schema(title = "是否正序", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean asc;

    public void completeOrderBySql(LambdaQueryWrapper lambdaQueryWrapper){
        if(StringUtils.isNotBlank(this.getOrderBy())){
            orderBy = StrUtil.toUnderlineCase(orderBy);
        }
        if (StringUtils.isNotBlank(this.getOrderBy()) && this.getAsc() != null) {
            lambdaQueryWrapper.getExpression().add(ORDER_BY, this::getOrderBy, this.getAsc() ? ASC : DESC);
        }else if (StringUtils.isNotBlank(this.getOrderBy()) ) {
            lambdaQueryWrapper.getExpression().add(ORDER_BY, this::getOrderBy, DESC );
        }
    }
}

