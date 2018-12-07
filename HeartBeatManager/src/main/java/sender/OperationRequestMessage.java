package sender;

import java.util.List;

public class OperationRequestMessage {

    List<String> parameters;
    String serviceName;

    public List<String> getParameters() {
        return parameters;
    }

    public OperationRequestMessage setParameters(List<String> parameters) {
        this.parameters = parameters;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public OperationRequestMessage setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }
}
