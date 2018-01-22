package org.jpmorgan.message.operation;

/**
 * Created by paulabenzar on 21.01.2018.
 */
public enum OperationFactory {

    RECORD(new StoreOperation()),
    ADD(new AddOperation()),
    SUBSTRACT(new SubstractOperation()),
    MULTIPLY(new MultiplyOperation());


    private Operation operation;

    OperationFactory(Operation operation) {
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }
}
