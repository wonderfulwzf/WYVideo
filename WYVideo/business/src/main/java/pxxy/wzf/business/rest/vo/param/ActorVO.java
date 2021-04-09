package pxxy.wzf.business.rest.vo.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class ActorVO implements Serializable {

    private static final long serialVersionUID = -4231450578460723393L;

    private Long id;

    private String name;

    private String image;

    private String intro;
}