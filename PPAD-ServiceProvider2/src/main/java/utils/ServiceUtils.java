package utils;

import sender.OperationRequestMessage;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceUtils {
    public static int subTwoIntegers(int a, int b) {
        return a - b;
    }

    public static String multiplyString(String s, int multiplicationTimes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < multiplicationTimes; i++) {
            stringBuilder.append(s);
        }

        return stringBuilder.toString();
    }

    public static String getResult(OperationRequestMessage operationMessage) {

        switch (operationMessage.getServiceName()) {
            case "subTwoIntegers":
                List<Integer> parameters = getIntegerParametersFromString(
                        operationMessage.getParameters());
                return String.valueOf(subTwoIntegers(parameters.get(0), parameters.get(1)));

            case "multiplyString":
                return String.valueOf(multiplyString(operationMessage.getParameters()
                        .get(0), Integer.parseInt(operationMessage.getParameters()
                        .get(1))));
        }

        return String.valueOf(0);
    }

    private static List<Integer> getIntegerParametersFromString(List<String> parametersAsString) {
        List<Integer> parameters = parametersAsString.stream()
                .map(s -> Integer.parseInt(s))
                .collect(Collectors.toList());

        return parameters;
    }
}
