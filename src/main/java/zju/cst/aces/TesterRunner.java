package zju.cst.aces;

import zju.cst.aces.api.Runner;
import zju.cst.aces.api.config.Config;
import zju.cst.aces.api.impl.ChatGenerator;
import zju.cst.aces.api.impl.PromptConstructorImpl;
import zju.cst.aces.api.impl.RepairImpl;
import zju.cst.aces.dto.ClassInfo;
import zju.cst.aces.dto.MethodInfo;
import zju.cst.aces.dto.PromptInfo;
import zju.cst.aces.dto.RoundRecord;
import zju.cst.aces.runner.AbstractRunner;
import zju.cst.aces.runner.ClassRunner;
import zju.cst.aces.TesterMethodRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

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
