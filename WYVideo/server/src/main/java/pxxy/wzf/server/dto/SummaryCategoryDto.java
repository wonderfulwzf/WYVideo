package pxxy.wzf.server.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SummaryCategoryDto implements Serializable{

    /**
    * id标识
    */
    private String id;

    /**
    * 概览id
    */
    private Long summaryId;

    /**
    * 分类id
    */
    private String categoryId;

    private List<String> ids;
}