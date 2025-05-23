package com.mengnankk.common.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * StoreCategoryTreeList
 * +----------------------------------------------------------------------
 * | CRMEB [ CRMEB赋能开发者，助力企业发展 ]
 * +----------------------------------------------------------------------
 * | Copyright (c) 2016~2022 https://www.crmeb.com All rights reserved.
 * +----------------------------------------------------------------------
 * | Licensed CRMEB并不是自由软件，未经许可不能去掉CRMEB相关版权
 * +----------------------------------------------------------------------
 * | Author: CRMEB Team <admin@crmeb.com>
 * +----------------------------------------------------------------------
 */
@Data
public class StoreCategoryTreeList {
    private static final long serialVersionUID=1L;

    private Integer id;

    private Integer pid;

    private String cateName;

    private Integer sort;

    private String pic;

    private Boolean isShow;

    private Integer addTime;

    private List<StoreCategoryTreeList> child = new ArrayList<>();
}
