package com.marshmallow.anblog;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class DownloadController {
  
  @RequestMapping(value = "/download", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  private String download() {
    final StringBuilder builder = new StringBuilder();
    builder.append("dir=\"$(mktemp -d)\"").append('\n');
    builder.append("git clone https://github.com/ankeesler/anblog \"$dir\"").append('\n');
    builder.append("pushd \"$dir/client\"").append('\n');
    builder.append("  gem build anblog.gemspec").append('\n');
    builder.append("  gem install anblog-1.0.0.gem").append('\n');
    builder.append("popd").append('\n');
    builder.append("rm -rf \"$dir\"").append('\n');
    return builder.toString();
  }
}
