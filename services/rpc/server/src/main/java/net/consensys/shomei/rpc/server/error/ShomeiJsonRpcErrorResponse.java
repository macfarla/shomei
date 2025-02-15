/*
 * Copyright ConsenSys Software Inc., 2023
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package net.consensys.shomei.rpc.server.error;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hyperledger.besu.ethereum.api.jsonrpc.internal.response.JsonRpcError;
import org.hyperledger.besu.ethereum.api.jsonrpc.internal.response.JsonRpcErrorResponse;
import org.hyperledger.besu.ethereum.api.jsonrpc.internal.response.RpcErrorType;
import org.hyperledger.besu.plugin.services.rpc.RpcResponseType;

@JsonPropertyOrder({"jsonrpc", "id", "code"})
public class ShomeiJsonRpcErrorResponse extends JsonRpcErrorResponse {

  private final JsonError jsonError;

  public ShomeiJsonRpcErrorResponse(
      final Object id, final RpcErrorType error, final String message, Object data) {
    super(id, error);
    this.jsonError = new JsonError(error, message, data);
  }

  public ShomeiJsonRpcErrorResponse(
      final Object id, final RpcErrorType error, final String message) {
    this(id, error, message, null);
  }

  @JsonGetter("error")
  public JsonError getJsonError() {
    return jsonError;
  }

  @Override
  @JsonIgnore
  public JsonRpcError getError() {
    return super.getError();
  }

  @Override
  @JsonIgnore
  public RpcResponseType getType() {
    return RpcResponseType.ERROR;
  }

  @SuppressWarnings("unused")
  private record JsonError(
      @JsonIgnore RpcErrorType jsonRpcErrorCode,
      @JsonGetter("message") String message,
      @JsonGetter("data") @JsonInclude(JsonInclude.Include.NON_NULL) Object data) {

    @JsonGetter("code")
    public int getCode() {
      return jsonRpcErrorCode.getCode();
    }
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    ShomeiJsonRpcErrorResponse that = (ShomeiJsonRpcErrorResponse) o;
    return Objects.equals(jsonError, that.jsonError);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), jsonError);
  }

  @Override
  public String toString() {
    return "JsonRpcErrorResponse{" + "jsonError=" + jsonError + '}';
  }
}
