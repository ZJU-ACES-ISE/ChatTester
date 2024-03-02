package zju.cst.aces.chattester;

import zju.cst.aces.api.Runner;
import zju.cst.aces.api.config.Config;
import zju.cst.aces.dto.MethodInfo;
import zju.cst.aces.runner.ClassRunner;

import java.io.IOException;

public class TesterRunner implements Runner {

    Config config;

    public TesterRunner(Config config) {
        this.config = config;
    }

    public void runClass(String fullClassName) {
        try {
            //TODO: use TesterMethodRunner in ClassRunner.
            new ClassRunner(config, fullClassName).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void runMethod(String fullClassName, MethodInfo methodInfo) {
        try {
            new TesterMethodRunner(config, fullClassName, methodInfo).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
