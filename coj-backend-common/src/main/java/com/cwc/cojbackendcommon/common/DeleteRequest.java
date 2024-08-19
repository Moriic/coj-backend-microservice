package com.cwc.cojbackendcommon.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 删除请求
 *

 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private List<Long> ids;

    private static final long serialVersionUID = 1L;
}