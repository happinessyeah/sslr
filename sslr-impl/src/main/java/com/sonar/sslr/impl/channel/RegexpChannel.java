/*
 * Copyright (C) 2010 SonarSource SA
 * All rights reserved
 * mailto:contact AT sonarsource DOT com
 */
package com.sonar.sslr.impl.channel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sonar.channel.Channel;
import org.sonar.channel.CodeReader;

import com.sonar.sslr.api.LexerOutput;
import com.sonar.sslr.api.TokenType;

public class RegexpChannel extends Channel<LexerOutput> {

  private final StringBuilder tmpBuilder = new StringBuilder();
  private final TokenType type;
  private final Matcher matcher;

  public RegexpChannel(TokenType type, String regexp) {
    matcher = Pattern.compile(regexp).matcher("");
    this.type = type;
  }

  @Override
  public boolean consume(CodeReader code, LexerOutput output) {
    if (code.popTo(matcher, tmpBuilder) > 0) {
      output.addTokenAndProcess(type, tmpBuilder.toString(), code.getLinePosition(), code.getColumnPosition());
      tmpBuilder.delete(0, tmpBuilder.length());
      return true;
    }
    return false;
  }
}
