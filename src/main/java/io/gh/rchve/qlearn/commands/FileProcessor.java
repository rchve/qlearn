package io.gh.rchve.qlearn.commands;

import io.quarkus.tika.TikaMetadata;
import io.quarkus.tika.TikaParser;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.watch.FileWatchComponent;
import org.apache.camel.component.file.watch.constants.FileEventEnum;
import org.apache.tika.io.TikaInputStream;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.IOException;

@ApplicationScoped
class FileProcessor implements Processor {
  private final TikaParser parser;

  FileProcessor(TikaParser parser) {
    this.parser = parser;
  }

  @Override
  public void process(Exchange exchange) throws IOException {
    System.out.println("----------------------------------------------------");
    final String path = (String) exchange.getMessage().getHeader(Exchange.FILE_PATH);
    final FileEventEnum eventType =
        (FileEventEnum) exchange.getMessage().getHeader(FileWatchComponent.EVENT_TYPE_HEADER);
    System.out.println(String.format("File %s was %s.", path, eventType));
    final TikaMetadata metadata = parser.getMetadata(TikaInputStream.get(new File(path).toURI()));
    System.out.println("Content Type" + metadata.getValues(Exchange.CONTENT_TYPE));
    System.out.println("----------------------------------------------------\n");
  }
}
