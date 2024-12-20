# :mega: ChatTester Maven Plugin

This is an implementation for the paper "No More Manual Tests? Evaluating and Improving ChatGPT for Unit Test Generation" [arxiv](https://arxiv.org/abs/2305.04207)

## Steps to run

### 0. Add our plugin to `pom.xml` and config

You can configure the plugin with the following parameters to your `pom.xml` file:

```xml

<plugin>
    <groupId>io.github.ZJU-ACES-ISE</groupId>
    <artifactId>chattester-maven-plugin</artifactId>
    <!-- Required: You need to change the version number to match the local chattester. -->
    <version>1.4.1</version>
    <configuration>
        <!-- Required: You must specify your OpenAI API keys. -->
        <apiKeys></apiKeys>
        <model>gpt-3.5-turbo</model>
        <testNumber>5</testNumber>
        <maxRounds>5</maxRounds>
        <minErrorTokens>500</minErrorTokens>
        <temperature>0.5</temperature>
        <topP>1</topP>
        <frequencyPenalty>0</frequencyPenalty>
        <presencePenalty>0</presencePenalty>
        <proxy>${proxy}</proxy>
    </configuration>
</plugin>
```

**Here's a detailed explanation of each configuration option:**

- `apiKeys`: (**Required**) Your OpenAI API keys. Example: `Key1, Key2, ...`.
- `model`: (**Optional**) The OpenAI model. Default: `gpt-3.5-turbo`.
- `url`: (**Optional**) The API to use your model. Default: `"https://api.openai.com/v1/chat/completions"`.
- `testNumber`: (**Optional**) The number of tests for each method. Default: `5`.
- `maxRounds`: (**Optional**) The maximum rounds of the repair process. Default: `5`.
- `minErrorTokens`: (**Optional**) The minimum tokens of error message in the repair process. Default: `500`.
- `temperature`: (**Optional**) The OpenAI API parameters. Default: `0.5`.
- `topP`: (**Optional**) The OpenAI API parameters. Default: `1`.
- `frequencyPenalty`: (**Optional**) The OpenAI API parameters. Default: `0`.
- `presencePenalty`: (**Optional**) The OpenAI API parameters. Default: `0`.
- `proxy`: (**Optional**) Your host name and port number if you need. Example:`127.0.0.1:7078`.
- `selectClass`: (**Optional**) Class under test. If the project contains the same class name in different packages, the className should be specified as the fully qualified name.
- `selectMethod`: (**Optional**) Method under test.
- `tmpOutput`: (**Optional**) The output path for parsed information. Default: `/tmp/chatunitest-info`.
- `testOutput`: (**Optional**) The output path for tests generated by `chatunitest`. Default: `{basedir}/chatunitest`.
- `project`: (**Optional**) The target project path. Default: `{basedir}`.
- `thread`: (**Optional**) Enable multi-threaded execution. Default: `true`.
- `maxThread`: (**Optional**) The maximum number of threads. Default: `CPU cores * 5`.
- `stopWhenSuccess`: (**Optional**) Stop the repair process when the test passes. Default: `true`.
- `noExecution`: (**Optional**) Skip the execution verification step of generated tests. Default: `false`.
- `merge` : (**Optional**) Merge all tests for each focal class. Default: `true`.
- `promptPath` : (**Optional**) Path to your custom prompt. Refer to default prompt in `src/main/resources/prompt`.
- `obfuscate` : (**Optional**) Enable prompt obfuscation for enhanced privacy protection. Default: `false`.
- `obfuscateGroupIds` : (**Opional**) Group IDs you want to obfuscate. Default: only your group ID.

All these parameters also can be specified in the command line with `-D` option.

Essentially, the only thing you need to provide are your API keys.

### 1. Add the following dependency to pom.xml

```xml
<dependency>
    <groupId>io.github.ZJU-ACES-ISE</groupId>
    <artifactId>chatunitest-starter</artifactId>
    <version>1.4.0</version>
    <type>pom</type>
</dependency>
```

### 2. Run

**First, you need to install your project and download all necessary dependencies, which can be done by running `mvn install` command.**

**You can run the plugin with the following command:**

**Generate unit tests for the target method:**

```shell
mvn chattester:method -DselectMethod=className#methodName
```

**Generate unit tests for the target class:**

```shell
mvn chattester:class -DselectClass=className
```

You must specify `selectMethod` and `selectClass` when executing `mvn chattester:method` or `mvn chattester:class`.
This is done using the -D option.

Example:

```
public class Example {
    public void method1(Type1 p1, ...) {...}
    public void method2() {...}
    ...
}
```

To test the class `Example` and all methods in it:

```shell
mvn chattester:class -DselectClass=Example
```

To test the method `method1` in the class `Example` (Now ChatTester will generate tests for all methods named method1
in the class)

```shell
mvn chattester:method -DselectMethod=Example#method1
```

**Generate unit tests for the whole project:**

:warning: :warning: :warning: For a large project, it may consume a significant number of tokens, resulting in a
substantial bill.

```shell
mvn chattester:project
```

**Clean the generated tests:**

```shell
mvn chattester:clean
```
Running this command will delete all generated tests and restore your test folder.


**Run the generated tests manually:**

```shell
mvn chattester:copy
```
Running this command will copy the generated tests into the `src/test/java/` directory for your convenience when you want
to run the tests manually. Your test folder will be backed up in the `src/back/` directory.

If the merge configuration is enabled, you can run the test suites instead of individual tests for each class.

```shell
mvn chattester:restore
```

Running this command will restore the test folder from the backup in the `src/back/` directory.

```
mvn chattester:generateCoverage
```
```xml
<plugin>
    <groupId>io.github.ZJU-ACES-ISE</groupId>
    <artifactId>chattester-maven-plugin</artifactId>
    <version>1.0.0</version>
    <configuration>
        <targetDir>D:\\coverage</targetDir>
        <mavenHome>C:\\software\\apache-maven-3.9.2</mavenHome>
        <sourceDir>chatunitest</sourceDir>
    </configuration>
</plugin>
```
Running this method will execute the tests in the folder `sourceDir`, the coverage 
result of the project will remove to folder `targetDir`. 
```shell
mvn chattester:generateMethodCoverage_separate
```
```xml
<plugin>
    <groupId>io.github.ZJU-ACES-ISE</groupId>
    <artifactId>chattester-maven-plugin</artifactId>
    <version>1.3.0</version>
    <configuration>
        <targetDir>D:\\coverage</targetDir>
        <mavenHome>C:\\software\\apache-maven-3.9.2</mavenHome>
        <sourceDir>chatunitest</sourceDir>
    </configuration>
</plugin>
```
Running this method will execute all the test classes in `sourceDir` separately 
and calculate separate coverage for each test class, the result are saved in the `methodCoverage_SEPARATE.json` file under `targetDir`.

```shell
mvn chattester:generateMethodCoverage_merge
```
Running this method will group the test classes according to the target test class(
the test classes for the same method will be summarized into a group). For test classes in the same grouping, the 
program generates test coverage by subdividing the groups from `1-1` to `1-n`
and the test coverage calculation will be performed by group, the result are saved in the 
`methodCoverage_MERGE.json` file under `targetDir`.

## Requirements

This Maven plugin can be executed in various operating systems with different Java Development Kits (JDK) and Maven
versions. The following environments have been tested and proven to work:

- Environment 1: Windows 11 / Oracle JDK 11 / Maven 3.9
- Environment 2: Windows 10 / Oracle JDK 11 / Maven 3.6
- Environment 3: Ubuntu 22.04 / OpenJDK 11 / Maven 3.6
- Environment 4: Darwin Kernel 22.1.0 / Oracle JDK 11 / Maven 3.8

Please note that these environments are tested and known to work. You can also try running the plugin in similar
environments. If you encounter any issues in other environments, please refer to the documentation or seek appropriate
support.

## :email: Contact us

If you have any questions or would like to inquire about our experimental results, please feel free to contact us via
email. The email addresses of the authors are as follows:

1. Corresponding author: `zjuzhichen AT zju.edu.cn`
2. Author: `yh_ch AT zju.edu.cn`, `xiezhuokui AT zju.edu.cn`
