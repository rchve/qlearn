package io.gh.rchve.qlearn.commands;

import io.quarkus.runtime.Quarkus;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import picocli.CommandLine;

@CommandLine.Command(
    name = "watch",
    mixinStandardHelpOptions = true,
    description = "Watches a path for file changes.")
public class WatchCommand implements Runnable {
  @CommandLine.Option(
      names = {"-p", "--path"},
      description = "Path to watch.",
      defaultValue = ".")
  String path;

  private final CamelContext ctx;
  private final FileProcessor processor;

  public WatchCommand(CamelContext ctx, FileProcessor processor) {
    this.ctx = ctx;
    this.processor = processor;
  }

  @Override
  public void run() {
    try {
      System.out.println("Configuring Watches ... ");
      ctx.addRoutes(
          new EndpointRouteBuilder() {
            @Override
            public void configure() {
              from(fileWatch(path)).process(processor);
            }
          });
      System.out.println("Waiting for file changes ... Press Ctrl+C to exit.");
      Quarkus.waitForExit();
    } catch (Exception e) {
      System.err.println("Unable to setup watch");
      System.exit(-1);
    }
  }
}
