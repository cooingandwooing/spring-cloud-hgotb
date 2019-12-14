package com.github.cooingandwooing.user.api.module;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.cooingandwooing.common.core.persistence.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 部门
 *
 * @author cooingandwooing
 * @date 2018/8/26 22:25
 */
@Data
public class Dept extends BaseEntity<Dept> {

    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    private String deptName;

    /**
     * 部门描述
     */
    private String deptDesc;

    /**
     * 部门负责人
     */
    private String deptLeader;

    /**
     * 父部门ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long parentId;

    /**
     * 排序
     */
    private Integer sort;
}
