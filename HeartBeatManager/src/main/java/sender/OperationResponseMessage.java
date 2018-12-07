package sender;

public class OperationResponseMessage {
    String response;
    OperationState state;
    String errorMessage;

    public OperationState getState() {
        return state;
    }

    public OperationResponseMessage setState(OperationState state) {
        this.state = state;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public OperationResponseMessage setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public String getResponse() {
        return response;
    }

    public OperationResponseMessage setResponse(String response) {
        this.response = response;
        return this;
    }
}
