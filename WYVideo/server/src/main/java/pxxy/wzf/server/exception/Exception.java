package pxxy.wzf.server.exception;

import java.io.Serializable;

public interface Exception extends Serializable {
    Integer getMessageCode();

    Object[] getMessageParams();
}