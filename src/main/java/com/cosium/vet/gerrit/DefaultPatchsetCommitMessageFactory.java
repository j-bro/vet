package com.cosium.vet.gerrit;

import com.cosium.vet.git.CommitMessage;
import com.cosium.vet.git.GitClient;
import com.cosium.vet.log.Logger;
import com.cosium.vet.log.LoggerFactory;
import com.cosium.vet.thirdparty.apache_commons_codec.DigestUtils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

/**
 * Created on 08/05/18.
 *
 * @author Reda.Housni-Alaoui
 */
class DefaultPatchsetCommitMessageFactory implements PatchsetCommitMessageFactory {

  private static final Logger LOG =
      LoggerFactory.getLogger(DefaultPatchsetCommitMessageFactory.class);

  private static final String COMMIT_MESSAGE_CHANGE_ID_PREFIX = "Change-Id: ";

  private final GitClient git;

  DefaultPatchsetCommitMessageFactory(GitClient git) {
    this.git = requireNonNull(git);
  }

  @Override
  public CommitMessage build(Patchset latestPatchset) {
    String changeChangeId;
    CommitMessage commitMessage;
    if (latestPatchset == null) {
      commitMessage = git.getLastCommitMessage();
      changeChangeId = generateChangeChangeId(commitMessage);
    } else {
      commitMessage = latestPatchset.getCommitMessage();
      changeChangeId = parseChangeChangeId(commitMessage);
    }

    String body = commitMessage.removeLinesStartingWith(COMMIT_MESSAGE_CHANGE_ID_PREFIX);

    String footer = String.join("\n", COMMIT_MESSAGE_CHANGE_ID_PREFIX + changeChangeId);

    return CommitMessage.of(body + "\n\n" + footer);
  }

  @Override
  public CommitMessage build() {
    return build(null);
  }

  private String generateChangeChangeId(CommitMessage commitMessage) {
    String changeId =
        "I"
            + DigestUtils.shaHex(
                String.format("%s|%s", UUID.randomUUID(), commitMessage.toString()));
    LOG.debug("Generated change change id '{}'", changeId);
    return changeId;
  }

  private String parseChangeChangeId(CommitMessage commitMessage) {
    Pattern pattern = Pattern.compile(Pattern.quote(COMMIT_MESSAGE_CHANGE_ID_PREFIX) + "(.*)");
    Matcher matcher = pattern.matcher(commitMessage.toString());
    if (!matcher.find()) {
      throw new RuntimeException(
          "Could not parse any change id from commit message '" + commitMessage + "'");
    }
    String changeId = matcher.group(1);
    LOG.debug("Found change change id '{}'", changeId);
    return changeId;
  }
}
