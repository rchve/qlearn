package io.gh.rchve.qlearn.commands;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine;

@TopCommand
@CommandLine.Command(mixinStandardHelpOptions = true, version = "1.0.0")
public class QLearnCommand implements Runnable {
  @Override
  public void run() {}
}
