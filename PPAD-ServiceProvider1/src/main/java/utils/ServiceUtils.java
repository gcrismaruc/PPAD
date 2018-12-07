package utils;

import sender.OperationRequestMessage;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceUtils {

    public static int addTwoIntegers(int a, int b) {
        return a + b;
    }

    public static int multiplyTwoIntegers(int a, int b) {
        return a * b;
    }

    public static String getResult(OperationRequestMessage operationMessage) {
        List<Integer> parameters = getParametersFromString(operationMessage.getParameters());

        switch (operationMessage.getServiceName()) {
            case "addTwoIntegers":
                return String.valueOf(addTwoIntegers(parameters.get(0), parameters.get(1)));
            case "multiplyTwoIntegers":
                return String.valueOf(multiplyTwoIntegers(parameters.get(0), parameters.get(1)));
        }

        return String.valueOf(0);
    }

    private static List<Integer> getParametersFromString(List<String> parametersAsString) {
        List<Integer> parameters = parametersAsString.stream()
                .map(s -> Integer.parseInt(s))
                .collect(Collectors.toList());

        return parameters;
    }
}
