package net.bean.java.open.messenger.rest.resource.patch;

import lombok.Data;

import java.util.List;

@Data
public class PatchOperations {

    private List<PatchOperation> operationList;

}
