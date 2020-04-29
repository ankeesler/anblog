package com.marshmallow.anblog;

import com.marshmallow.anblog.generated.grpc.SpiffeWorkloadAPIGrpc;
import com.marshmallow.anblog.generated.grpc.ValidateJWTSVIDRequest;
import com.marshmallow.anblog.generated.grpc.ValidateJWTSVIDResponse;

public class SpiffeTokenValidator implements TokenValidator {

  private final SpiffeWorkloadAPIGrpc.SpiffeWorkloadAPIBlockingStub client;

  public SpiffeTokenValidator(final SpiffeWorkloadAPIGrpc.SpiffeWorkloadAPIBlockingStub client) {
    this.client = client;
  }
  
  @Override
  public String validate(String token) {
    final ValidateJWTSVIDRequest req = null;
    final ValidateJWTSVIDResponse rsp = client.validateJWTSVID(req);
    return rsp.getSpiffeId();
  }
}
