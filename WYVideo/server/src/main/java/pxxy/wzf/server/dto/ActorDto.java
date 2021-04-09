package pxxy.wzf.server.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ActorDto implements Serializable {
    private static final long serialVersionUID = -4865069019159057170L;
    private Long id;

    private String name;

    private String image;

    private String intro;
}