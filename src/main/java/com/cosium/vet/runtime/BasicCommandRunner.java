package com.cosium.vet.runtime;

import com.cosium.vet.log.Logger;
import com.cosium.vet.log.LoggerFactory;
import com.cosium.vet.thirdparty.apache_commons_io.IOUtils;
import com.cosium.vet.thirdparty.apache_commons_lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created on 16/02/18.
 *
 * @author Reda.Housni-Alaoui
 */
public class BasicCommandRunner implements CommandRunner {

  private static final Logger LOG = LoggerFactory.getLogger(BasicCommandRunner.class);

  @Override
  public String run(Path workingDir, String... command) {
    try {
      ProcessBuilder processBuilder =
          new ProcessBuilder(command)
              .directory(workingDir.toFile())
              .redirectInput(ProcessBuilder.Redirect.INHERIT)
              .redirectError(ProcessBuilder.Redirect.INHERIT);

      LOG.debug("Executing '{}'", StringUtils.join(command, StringUtils.SPACE));
      Process process = processBuilder.start();

      int exitCode = process.waitFor();
      if (exitCode != 0) {
        throw new CommandRunException(
            exitCode, IOUtils.toString(process.getInputStream(), "UTF-8"), command);
      }

      return StringUtils.defaultIfBlank(
          IOUtils.toString(process.getInputStream(), "UTF-8").trim(), null);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
