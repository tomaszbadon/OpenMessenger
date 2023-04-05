package net.bean.java.open.messenger.rest.resource.patch;

import lombok.Data;

@Data
public class PatchOperation {

    private String op;

    private String path;

    private String value;

}
